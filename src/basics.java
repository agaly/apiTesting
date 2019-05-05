import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import org.testng.annotations.*;

public class basics {

	@Test
	public void Test1() 
	{
		// TODO Auto-generated method stub

		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		given().
				param("key","qaclick123").
				/*
				 * header("....","....").
				 * cookie("...","....").
				 * body()*/
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().	
				statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0.name]", equalTo("Sydney")).and()
				.header("Server", "pablo");
		
		
		
	}

}
