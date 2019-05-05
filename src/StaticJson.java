import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import files.payLoad;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StaticJson {

	@Test
	public void AddBook() throws IOException
	{
		// TODO Auto-generated method stub

		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response resp=given().
				header("Content-Type","application/json").
				body(reusableMethods.GenerateStringFromResource("../Desktop/bookAddData.json")).
				when().
				post("/Library/Addbook.php").
				then().assertThat().	
				statusCode(200).
				extract().response();
		
		JsonPath js=reusableMethods.rawToJSON(resp);
		
		String id=js.get("ID");
		System.out.println(id);
		
	}
	
	
	
}
