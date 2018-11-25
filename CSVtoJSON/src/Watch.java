import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;


public class Watch {
	private WatchService watcher;
	private Path dir;
	private WatchKey key;
	
	
	Watch(Path dir) throws IOException{
		this.watcher = FileSystems.getDefault().newWatchService();
		this.key = dir.register(watcher, ENTRY_CREATE);
		for(;;) {
			
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
				e.printStackTrace();
				return;
			}
			
			List<WatchEvent<?>> events = key.pollEvents();
			for(WatchEvent<?> event: events) {
				if(event.kind() == OVERFLOW) {
					System.out.println("OVERFLOW" + event.context());
					continue;
				}
				Path filename = (Path) event.context();
				System.out.println(filename + " dectected");
			}
			if(!key.reset()) {
				break;
			}
		}
		
		
	}

	public static void main(String[] args) {
		Path path = Paths.get("C:\\Users\\fbpea\\Desktop\\test");
		try {
			Watch watcher = new Watch(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}
	
	
	

}
