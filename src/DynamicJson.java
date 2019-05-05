import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.*;

import files.payLoad;
import files.reusableMethods;

public class DynamicJson {

	//excell driven--->
	
	@Test(dataProvider="BooksData")
	public void AddBook(String isbn, String aisle)
	{
		// TODO Auto-generated method stub

		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response resp=given().
				header("Content-Type","application/json").
				body(payLoad.AddBook(isbn,aisle)).
				when().
				post("/Library/Addbook.php").
				then().assertThat().	
				statusCode(200).
				extract().response();
		
		JsonPath js=reusableMethods.rawToJSON(resp);
		
		String id=js.get("ID");
		System.out.println(id);
		
	}
	
	@Test(dataProvider="BooksData")
	public void DeleteBook(String isbn, String aisle)
	{
		// TODO Auto-generated method stub
		String bookID=isbn+aisle;
		//BaseUrl or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response resp=given().
				header("Content-Type","application/json").
				body("{"+
					 
					"\"ID\" : \""+bookID+"\""+
					 
					"}").
				when().
				post("/Library/DeleteBook.php").
				then().assertThat().	
				statusCode(200).
				extract().response();
		
		JsonPath js=reusableMethods.rawToJSON(resp);
		
		String actual=js.get("msg");
		
		
		Assert.assertEquals(actual, "book is successfully deleted");
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		//array=collection of elements
		//multidimensional array = collection of arrays
		return new Object[][]{{"ojwsd","9363"},{"asafwq","123456422"},{"trewq","0385"}};
		
	}
	

}
