import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import org.testng.annotations.*;

import files.reusableMethods;

public class basics5 {

	//excell driven--->
	
	@Test
	public void addBook()
	{
		// TODO Auto-generated method stub

		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response resp=given().
				header("Content-Type","application/json").
				body("{\n" + 
						"\n" + 
						"\"name\":\"Learn Appium Automation with Java\",\n" + 
						"\"isbn\":\"bczd33\",\n" + 
						"\"aisle\":\"22722\",\n" + 
						"\"author\":\"John foe\"\n" + 
						"}").
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
