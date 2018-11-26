import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;


public class CSVReader {
	private Scanner scanner; 
	private Path file;
	
	
	public CSVReader(Path path) throws IOException{
		this.file = path;
		scanner = new Scanner(file);	
	}
	
	List<String[]> readfile() throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		int lineNumber = 0;
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			try {
				this.checkForErrors(line, lineNumber);
			} catch (MalformedDataException e) {
				File ErrorFile = new File(this.file.getParent().getParent().resolve("Error").toString());
				BufferedWriter bw = new BufferedWriter(new FileWriter(ErrorFile, true));
				bw.append(e.getLineNumber()+","+e.getMessage());
			}
			data.add(line);
			lineNumber+=1;
		}
		return data;

		
		
	}
	
	private void checkForErrors(String[] line, int lineNumber) throws MalformedDataException  {
		if (line.length != 5) {
			throw new MalformedDataException("Wrong Number of Elements", lineNumber);
		}
		if(line[0].length() != 8) {
			throw new MalformedDataException("INTERNAL_ID is the wrong length", lineNumber);
		}
		if (line[1].trim().length() <= 0) {
			throw new MalformedDataException("FIRST_NAME cannot be null", lineNumber);
		}
		if (line[3].trim().length() <= 0) {
			throw new MalformedDataException("LAST_NAME cannot be null", lineNumber);
		}
		if(line[1].length() >= 15) {
			throw new MalformedDataException("FIRST_NAME is too long", lineNumber);
		}
		if(line[2].length() >= 15) {
			throw new MalformedDataException("MIDDLE_NAME is too long", lineNumber);
		}
		if(line[3].length() >= 15) {
			throw new MalformedDataException("LAST_NAME is too long", lineNumber);
		}
		if (!line[4].matches("\\d{3}-\\d{3}-\\d{4}")){
			throw new MalformedDataException("Phone must be in format '###-###-####'", lineNumber);
			
		}
	}

}