package com.bookit.tests;

import static org.testng.Assert.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class APIDay1 {
	
	/* When I send a Get request to url/employees
	 * Then response status code should be 200
	 */
	
	
  @Test
  public void simpleStatusCode() {
	  
	  when().get("http://52.204.37.234:1000/ords/hr/employees").
	  then().statusCode(200);
	  
  }
  
  /* When I send a Get request to url/countries
	 * Then I should see the json response body
	 */
  
  @Test
  public void printResponse() {
	  
	  when().get("http://52.204.37.234:1000/ords/hr/countries")
	  .body().prettyPrint();
	  
  }
  
  	/* When I send a Get request to url/countries
  	 * And Accept type is "application/json"
	 * Then I should see the json response body
	 */
  
  @Test
  public void getWithHeaders() {
	  
	  given().accept(ContentType.JSON).
	  when().get("http://52.204.37.234:1000/ords/hr/countries")
	  .then().statusCode(200);
	  
  }
  
  	/* When I send a Get request to url/employees/1234
   	 * Then response status code should be 404 
	 */
  
  @Test
  public void negativeSet() {
	  
	  when().get("http://52.204.37.234:1000/ords/hr/employees/1234")
	  .then().statusCode(404);
	  
  }
  
  /*
   * WHEN I send a GET request to URL/employess/1234
   * Then status code is 404
   * And response body error message should include "Not Found"
   */
  
  @Test
  public void negativeWithBody() {
	  
	  Response response = when().get("http://52.204.37.234:1000/ords/hr/employees/1234");
	  
	  //status code check
	  
	  assertEquals(response.statusCode(),404);
	  
	  //check body includes "Not Found"
	  assertTrue(response.asString().contains("Not Found"));
	  
	  //print the response
	  response.prettyPrint();
	  
  }
  
  /*
   * When I sned a Get request to url/employees/110
   * And accept type is JSon
   * then status code is 200
   * and response content should be Json
   */
  
  @Test
  public void verifyContentType() {
	  
	  given().accept(ContentType.JSON).
	  when().get("http://52.204.37.234:1000/ords/hr/employees/110")
	  .then().statusCode(200).and().contentType(ContentType.XML);
	  
  }
  
  /*
   * Given accept type is JSOn
   * When I send a get request to url/employees/110
   * Then status code is 200
   * And response content should be json
   * and first name should be "JOHN"
   */
  
  String baseUrl="http://52.204.37.234:1000/ords/hr";
  
  @Test
  public void verifyFirstName() {
	  
	  given().accept(ContentType.JSON).
	  when().get(baseUrl+"/employees/110").
	  then().statusCode(200).and()
	  .contentType(ContentType.JSON)
	  .and().body("first_name", Matchers.equalTo("John"));
	  
  }
  
  /*
   * Given accept type is JSOn
   * When I send a get request to url/employees/150
   * Then status code is 200
   * And response content should be json
   * and last name should be "Tucker"
   * and job_id should be "SA_REP"
   */
  
  @Test
  public void verifyJobLastName() {
	  
	  given().accept(ContentType.JSON)
	  .when().get(baseUrl+"/employees/150")
	  .then().statusCode(200)
	  .and().contentType(ContentType.JSON)
	  .and().body("last_name", Matchers.equalTo("Tucker"))
	  .and().body("job_id", Matchers.equalTo("SA_REP"));
	  
  }
  
  /*
   * Given accept type is JSOn
   * When I send a get request to url/regions
   * Then status code is 200
   * And response content should be json
   * And 4 regions should be returned
   */
  
  @Test
  public void verifyRegionCount() {
	  
	  given().accept(ContentType.JSON)
	  .when().get(baseUrl+"/regions")
	  .then().assertThat().statusCode(200)
	  .and().assertThat().contentType(ContentType.JSON)
	  .and().assertThat().body("items.region_id", Matchers.hasSize(4))
	  .and().assertThat().body("items.region_name", Matchers.hasItem("Europe"))
	  .and().assertThat().body("items.region_name", Matchers.hasItems("Americas","Middle East and Africa"));
	  
	  
  }
  
  
}
