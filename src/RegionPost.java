import org.junit.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.io.IOException;

public class RegionPost {

	@Test
	public void regionPost() throws IOException {
		
		RestAssured.baseURI="http://3.90.32.1:1000/ords/hr";
		
		given().header("Content-Type","application/json")
		.body(reusableMethods.GenerateStringFromResource("/Users/agalyjumaklychev/Desktop/body.json"))
		.when().post("/regions/")
		.then().statusCode(201);
		
	}
	
}
