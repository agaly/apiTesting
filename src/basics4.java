import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import org.testng.annotations.*;

import files.reusableMethods;

public class basics4 {

	@Test
	public void Test1() 
	{
		// TODO Auto-generated method stub

		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response res=given().
				param("key","qaclick123").
				/*
				 * header("....","....").
				 * cookie("...","....").
				 * body()*/
				when().
				get("/maps/api/place/add/json").
				then().assertThat().	
				statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0].name", equalTo("Sydney")).and()
				.header("Server", "pablo").
				extract().response();
		JsonPath js=reusableMethods.rawToJSON(res);
		
		int count=js.getInt("results.size()");
		System.out.println(count);
		
	}

}
