import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import files.payLoad;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import files.resources;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics3 {

	Properties prop=new Properties();
	@BeforeTest
	public void getData() throws IOException {
		
		FileInputStream fis=new FileInputStream("../DemoProjectt/src/files/env.properties");
		prop.load(fis);
//		prop.get("HOST");
	}
	
	@Test
	public void AddandDeletePlace() {
		
	
		//Task1 Grab the response
		RestAssured.baseURI=prop.getProperty("HOST");
		
		Response res=given().
		queryParam("key",prop.getProperty("KEY")).
		body(payLoad.getPostData()).log().all().
		when().
		post(resources.placePostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
		.body("status", equalTo("OK")).log().body().
		extract().response();
		
		//Task2 Grab the place id from response
		String responseString=res.asString();
		System.out.println(responseString);
		JsonPath js=new JsonPath(responseString);
		String placeId=js.get("place_id");
		System.out.println(placeId);
		
		//Task3 place this placeid in the Delete request
		
		given().
		queryParam("key",prop.getProperty("KEY")).
		body("{"+
		"\"place_id\":\""+placeId+"\""
		+"}").
		when().
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status", equalTo("OK"));
		
	}
	
	
	
}
