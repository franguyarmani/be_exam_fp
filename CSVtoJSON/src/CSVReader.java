import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;


public class CSVReader {
	private Scanner scanner; 
	private Path file;
	
	
	CSVReader(Path path) throws IOException{
		this.file = path;
		scanner = new Scanner(file);	
	}
	
	List<String[]> readfile() {
		List<String[]> data = new ArrayList<String[]>();
		while (scanner.hasNextLine()) {
			data.add(scanner.nextLine().split(","));
		}
		return data;

		
		
	}

}