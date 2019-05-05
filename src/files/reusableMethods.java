package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class reusableMethods {

	public static JsonPath rawToJSON(Response r) {
		
		String respon=r.asString();
		JsonPath j=new JsonPath(respon);
		return j;
		
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	
	
	
}
