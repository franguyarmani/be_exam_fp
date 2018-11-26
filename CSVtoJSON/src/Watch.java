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
	private Path Input_dir;
	private Path Output_dir;
	private WatchKey key;
	private HashSet<Path> processed;
	
	
	public Watch(Path input_dir, Path output_dir, Path error_dir) throws IOException{
		this.watcher = FileSystems.getDefault().newWatchService();
		this.Input_dir = input_dir;
		this.Output_dir = output_dir;
		this.key = input_dir.register(watcher, ENTRY_CREATE);
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
				
				Path filepath = Input_dir.resolve((Path) event.context());
				if (processed.contains(filepath)) {
					continue;
				}
				try {
					this.processFile(filepath);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} 
				processed.add(filepath);
				filepath.toFile().delete();
			}
			if(!this.key.reset()) {
				break;
			}
		}
		
	}
	

	private void initializeWatch() throws IOException  {
		File folder = new File(Input_dir.toString());
		for(File file: folder.listFiles()) {
			Path filepath = Input_dir.resolve(file.toPath());
			this.processFile(filepath);
		}
	}
	
	private void processFile(Path filepath) throws IOException {
		CSVReader r = new CSVReader(filepath);
		List<String[]> data = r.readfile();
		JSONWriter j = new JSONWriter(Output_dir);
		String nameString = filepath.getFileName().toString();
		j.WriteToJSON(Output_dir.resolve(
				nameString.substring(0, nameString.lastIndexOf('.'))+".json"), data);
		processed.add(filepath);
	}
	

	public static void main(String[] args) {
		Path input = Paths.get("C:\\Users\\fbpea\\Git_Repositories\\ScoirExam\\input");
		Path output = Paths.get("C:\\Users\\fbpea\\Git_Repositories\\ScoirExam\\output");
		Path error = Paths.get("C:\\Users\\fbpea\\Git_Repositories\\ScoirExam\\Error");
		cleanDirectory(output);
		cleanDirectory(error);
		try {
			Watch watcher = new Watch(input, output, error);
			watcher.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void cleanDirectory(Path dir) {
		for (File file: dir.toFile().listFiles())
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
