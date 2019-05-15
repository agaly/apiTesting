package com.bookit.tests;

import static org.testng.Assert.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;






public class APIDay2 {
	
	
	 String baseUrl="http://52.204.37.234:1000/ords/hr";
	 
	/*
	 * Given accept type is json
	 * when i send get request to url/employees
	 * then status code is 200
	 * and response content should be json
	 * and 100 employees data should be in json response body
	 */
	
	
  @Test
  public void employeesWithParams() {
	  
	  given().accept(ContentType.JSON).and().param("limit", 100)
	  .when().get(baseUrl+"/employees/").then()
	  .assertThat().statusCode(200).and()
	  .contentType(ContentType.JSON).and()
	  .body("items.employee_id", Matchers.hasSize(100));
	  
  }
  
  	/*
	 * Given accept type is json
	 * when i send get request to url/employees/110
	 * then status code is 200
	 * and response content should be json
	 * and first_name must be "John"
	 * and email must be "JCHEN"
	 */
  
  @Test
  public void testWithPathParams() {
	  
	  given().accept(ContentType.JSON).and()
	  .pathParam("id", 110)
	  .when().get(baseUrl+"/employees/{id}")
	  .then().statusCode(200)
	  .and().contentType(ContentType.JSON)
	  .and().assertThat().body("first_name",Matchers.equalTo("John") ,"email", Matchers.equalTo("JCHEN"));
	  
  }
  
  	/*
	 * Given accept type is json
	 * when i send get request to url/employees/110
	 * then status code is 200
	 * and response content should be json
	 * and first_name must be "John"
	 * and email must be "JCHEN"
	 */
  
  @Test
  public void testWithJsonPath() {
	  
	  Response response=given().accept(ContentType.JSON)
			  .and().pathParam("id", 110).when().get(baseUrl+"/employees/{id}");
	  
	  //get json body and assign to JsonPath Object
	  JsonPath json=response.jsonPath();
	  
	  System.out.println(json.getString("first_name"));
	  System.out.println(json.getString("job_id"));
	  System.out.println(json.getString("employee_id"));
	  
	  String actualFirstname=json.getString("first_name");
	  String expectedFirstname="John";
	  
	  assertEquals(actualFirstname,expectedFirstname);
	  
  }
  
  	/*
	 * Given accept type is json
	 * when i send get request to url/employees/
	 * then status code is 200
	 * and response content should be json
	 * and all data should be returned
	 */
  
  @Test
  public void testJsonPathWithList() {
	  
	  Map<String,Integer> paramMap=new HashMap<>();
	  paramMap.put("limit", 100);
	  
	  Response response=given().accept(ContentType.JSON)
			  .and().params(paramMap).when().get(baseUrl+"/employees/");
	  
	  JsonPath json=response.jsonPath();
	  
	  //verify status code
	  assertEquals(response.statusCode(),200);
	  
	  //get all employee ids and assign to one list
	  List<Integer> empIDs=json.getList("items.employee_id");
	  System.out.println(empIDs.toString());
	  assertEquals(empIDs.size(),100);
	  
	  //get all emails and assign one list and print them
	  List<String> emails=json.getList("items.email");
	  System.out.println(emails.toString());
	
	  //get all employee id's that are greater than 150
	  List<Integer> empID150=json.getList("items.findAll{it.employee_id >150}.employee_id");
	  System.out.println(empID150);
	  
	  //get all employees lastname,whose salary is greater than 7000
	  List<String> lastName7000=json.getList("items.findAll{it.salary > 10000}.last_name");
	  System.out.println(lastName7000);
	  
  }
  	
  	/*
	 * Given accept type is json
	 * when i send get request to url/employees/
	 * then status code is 200
	 * and response content should be json
	 * and all data should be returned
	 */
  
  @Test
  public void testWithJSONtoMap() {
	  
	  Response response=given().accept(ContentType.JSON)
				 .and().when().get(baseUrl+"/employees/140");
	  
	  //we convert JSON result to hashmap data structure
	  Map<String,Object> jsonmap=response.as(HashMap.class);
	  
	  //get firstname value from map
	  System.out.println(jsonmap.get("first_name"));
	  
	  //get salary value from map
	  System.out.println(jsonmap.get("salary"));
	  
	  assertEquals(jsonmap.get("first_name"),"Joshua");
	  
  }
  
  
  
  @Test
  public void convertJSONtoListofMaps() {
	  
	  Response response=given().accept(ContentType.JSON)
			  .when().get(baseUrl+"/departments");
	  
	  JsonPath json = response.jsonPath();
	  
	  //we are getting JSON response and assign to list of maps
	  List<Map> result = json.getList("items",Map.class);
	  
	  String actualDepartmentName=(String) result.get(4).get("department_name");
	  String expectedDepartmentName="Shipping";
	  
	  //compare expected and actual department names
	  assertEquals(actualDepartmentName,expectedDepartmentName);
	  
	  //verify department_id
	  
	  int actualDepartmentId=(int) result.get(4).get("department_id");
	  int expectedDepartmentId=50;
	  assertEquals(actualDepartmentId,expectedDepartmentId);
	  
  }
  
  	/*
  	 *Given Content type is JSON
  	 *And limit is 10
  	 *When I send the Get request to url/regions
  	 *the status code must be 200
  	 *Then I should see the following data
  	 *		1Europe
  	 *		2Americas
  	 *		3Asia
  	 *		4Middle East and Africa 
  	 */
  
  @Test
  public void regionTask() {
	  
	  Response response = given().accept(ContentType.JSON)
			  .and().params("limit", 10)
			  .when().get(baseUrl+"/regions");
	  
	  assertEquals(response.statusCode(),200);
	  
	  JsonPath json=response.jsonPath();
	  
	  List<Map> result=json.getList("items",Map.class);
	  
	  String actualRegionName=(String) result.get(0).get("region_name");
	  String expectedRegionName="Europe";
	  assertEquals(actualRegionName,expectedRegionName);
	  
	  String actualRegionName1=(String) result.get(1).get("region_name");
	  String expectedRegionName1="Americas";
	  assertEquals(actualRegionName1,expectedRegionName1);
	  
	  String actualRegionName2=(String) result.get(2).get("region_name");
	  String expectedRegionName2="Asia";
	  assertEquals(actualRegionName2,expectedRegionName2);
	  
	  String actualRegionName3=(String) result.get(3).get("region_name");
	  String expectedRegionName3="Middle East and Africa";
	  assertEquals(actualRegionName3,expectedRegionName3);
	  
  }
  
  
  @Test
  public void regionTaskV1() {
	  
	  given().accept(ContentType.JSON).and().params("limit",10)
	  .when().get(baseUrl+"/regions").then().statusCode(200).and()
	  .assertThat()
	  .body("items.region_name", Matchers.contains("Europe","Asia","Americas","Middle East and Africa"));
	  
  }
  
  @Test
  public void regionTaskV2() {
	  
	  Response response=given().accept(ContentType.JSON).and().params("limit",10)
	  .when().get(baseUrl+"/regions");
	  
	  JsonPath json=response.jsonPath();
	  
	  //status code check
	  assertEquals(response.statusCode(),200);
	  
	  //regions verify
	  assertEquals(json.getString("items[0].region_name"),"Europe");
	  assertEquals(json.getString("items[1].region_name"),"Americas");
	  assertEquals(json.getString("items[2].region_name"),"Asia");
	  assertEquals(json.getString("items[3].region_name"),"Middle East and Africa");
	  
  }
  
  @Test
  public void regionTaskV3() {
	  
	  Response response=given().accept(ContentType.JSON).and().params("limit",10)
	  .when().get(baseUrl+"/regions");
	  
	  JsonPath json=response.jsonPath();
	  
	  //status code check
	  assertEquals(response.statusCode(),200);
	  
	  //JSON into list of maps
	  List<Map> result=json.getList("items",Map.class);
	  
	  assertEquals(result.get(0).get("region_name"),"Europe");
	  assertEquals(result.get(1).get("region_name"),"Americas");
	  assertEquals(result.get(2).get("region_name"),"Asia");
	  assertEquals(result.get(3).get("region_name"),"Middle East and Africa");
	  
  }
  
  
  
  
}
