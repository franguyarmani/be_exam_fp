import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONWriter {
	private Path dir;
	
	
	JSONWriter(Path dir){
		this.dir = dir;
	}
	
	public void WriteToJSON(Path name, List<String[]> data) throws IOException {
		JSONArray obj = buildJSON(data);

		writeToFile(this.dir.resolve(name), obj);

	}
	
	private JSONArray buildJSON(List<String[]> data) {
		data.remove(0);
		JSONArray entries = new JSONArray();
		for(String[] person:data) {
			
			JSONObject entry = new JSONObject();
			entry.put("id", person[0]);

			JSONObject name = new JSONObject();
			name.put("first", person[1]);
			if (person[2].length() > 0) {
				name.put("middle", person[2]);
			}
			name.put("last", person[3]);
			entry.put("name", name);
			
			entry.put("phone", person[4]);
	
			entries.add(entry);
		}
		
		return entries;
		
	}
	
	private void writeToFile(Path path, JSONArray obj) throws IOException{
		FileWriter w = new FileWriter(path.toString());
		Gson pretty = new GsonBuilder().setPrettyPrinting().create();
		w.write(pretty.toJson(obj));
		w.close();
	}
	
	
	
	

}