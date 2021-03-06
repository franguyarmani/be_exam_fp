import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CSVReader {
	private Scanner scanner; 
	private Path file;
	
	
	public CSVReader(Path path) throws IOException{
		this.file = path;
		scanner = new Scanner(file);	
	}
	
	List<String[]> readfile() throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		scanner.nextLine();
		int lineNumber = 1;
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			try {
				this.checkForErrors(line, lineNumber);
				data.add(line);
			} catch (MalformedDataException e) {
				writeError(e, this.file);
			}
			lineNumber+=1;
		}
		return data;
	}
	
	static void writeError(MalformedDataException e, Path pathToError) throws IOException {
		File ErrorFolder = new File(pathToError.getParent().getParent().resolve("Error").toString());
		File ErrorFile = new File(ErrorFolder,pathToError.getFileName().toString());
		BufferedWriter bw = new BufferedWriter(new FileWriter(ErrorFile, true));
		bw.write("Line: "+ e.getLineNumber()+","+e.getMessage()+"\n");
		bw.flush();
		bw.close();
		
		
	}
	
	private void checkForErrors(String[] line, int lineNumber) throws MalformedDataException  {
		if (line.length == 0) {
			throw new MalformedDataException("Empty Line", lineNumber);
		}
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