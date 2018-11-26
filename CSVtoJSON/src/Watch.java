import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.List;


public class Watch implements Runnable {
	private WatchService watcher;
	private File Input_dir;
	private File Output_dir;
	private WatchKey key;
	private HashSet<Path> processed;
	
	
	public Watch(File input_dir, File output_dir, File error_dir) throws IOException{
		this.watcher = FileSystems.getDefault().newWatchService();
		this.Input_dir = input_dir;
		this.Output_dir = output_dir;
		this.key = input_dir.toPath().register(watcher, ENTRY_CREATE);
		processed = new HashSet<Path>();
		
		initializeWatch();
		
	}
	
	
	public void run() {
		System.out.println("watcher started");
		for(;;) {
			
			this.key = getKey();
			System.out.println("key signaled");
			List<WatchEvent<?>> events = key.pollEvents();
			for(WatchEvent<?> event: events) {
				if(event.kind() == OVERFLOW) {
					System.out.println("OVERFLOW" + event.context());
					continue;
				}
				
				File filepath = new File(Input_dir, event.context().toString());
				if (processed.contains(filepath.toPath())) {
					continue;
				}
				try {
					this.processFile(filepath.toPath());
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} 
				processed.add(filepath.toPath());
				filepath.delete();
			}
			if(!this.key.reset()) {
				System.out.println("ERROR: Broken Key");
				break;
			}
		}
		
	}
	

	private void initializeWatch() throws IOException  {
		for(File file: Input_dir.listFiles()) {
			this.processFile(file.toPath());
		}
	}
	
	private void processFile(Path filepath) throws IOException {
		
		CSVReader r = new CSVReader(filepath);
		List<String[]> data = r.readfile();
		JSONWriter j = new JSONWriter(Output_dir);
		String nameString = filepath.getFileName().toString();
		j.WriteToJSON(new File(
				Output_dir, 
				nameString.substring(0, nameString.lastIndexOf('.'))+".json"
				), data);
		System.out.println("Json written");
		processed.add(filepath);
		filepath.toFile().delete();
		
	}
	

	public static void main(String[] args) {
		File input = Paths.get(args[0]).toFile();
		File output = new File(input.getParentFile(), "Output");
		File error = new File(input.getParentFile(), "Error");

		if (output.exists()) {
			cleanDirectory(output);
		} else {
			output.mkdir();
		}
		if (error.exists()) {
			cleanDirectory(error);
		} else {
			error.mkdir();
		}
		
		
		try {
			Watch watcher = new Watch(input, output, error);
			watcher.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void cleanDirectory(File dir) {
		for (File file: dir.listFiles())
		    file.delete();
	}

	public WatchKey getKey() {
		try {
			return this.watcher.take();
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
			e.printStackTrace();
			return null;
		}
		
	}

	public void setKey(WatchKey key) {
		this.key = key;
	}

}
