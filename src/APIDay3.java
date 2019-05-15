package com.bookit.tests;


import static org.testng.Assert.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.bookit.beans.Country;
import com.bookit.beans.CountryResponse;
import com.bookit.beans.Region;
import com.bookit.beans.RegionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;


import org.testng.annotations.Test;



public class APIDay3 {
	
	String baseUrl="http://52.204.37.234:1000/ords/hr";
	
	/*
	 * Given accept type is Json
	 * And content type is json
	 * When I send post request to URL/regions/
	 * with following JSON body
	 * {
	 * "region_id":11,
	 * "region_name":"myregion"
	 * }
	 * Then status code must be 201
	 * and response body shoould match the request body
	 */
	
  @Test
  public void regionPOSTv1() {
	  
	  String requestBody="{\"region_id\":15,\"region_name\":\"myregion\"}";
	  
	  Response response=given().accept(ContentType.JSON).and()
			  .contentType(ContentType.JSON).and().body(requestBody)
			  .when().post(baseUrl+"/regions/");
	  
	  //verify status code for POST request
	  assertEquals(response.statusCode(),201);
	  
	  JsonPath json=response.jsonPath();
	  //verify region name
	  assertEquals(json.getString("region_name"),"myregion");
	  //verify region id
	  assertEquals(json.getInt("region_id"),15);
	  
  }
  
  @Test
  public void regionPOSTv2() {
	  
	  //not a good way to pass JSON Request BODY
	  //String requestBody="{\"region_id\":15,\"region_name\":\"myregion\"}";
	  
	  Map requestMap=new HashMap<>();
	  
	  requestMap.put("region_id", 17);
	  requestMap.put("region_name", "testregion");
	  
	  Response response=given().accept(ContentType.JSON).and()
			  .contentType(ContentType.JSON).and().body(requestMap)
			  .when().post(baseUrl+"/regions/");
	  
	  //verify status code for POST request
	  assertEquals(response.statusCode(),201);
	  
	  JsonPath json=response.jsonPath();
	  //verify region name
	  assertEquals(json.getString("region_name"),"testregion");
	  //verify region id
	  assertEquals(json.getInt("region_id"),17);
	  
  }
  
  @Test
  public void regionPOSTv3() {
	  
	  Region region=new Region();
	  
	  region.setRegion_id(111);
	  region.setRegion_name("mynewregion");
	  
	  Response response=given().accept(ContentType.JSON).and()
			  .contentType(ContentType.JSON).and().body(region)
			  .when().post(baseUrl+"/regions/");
	  
	  //verify status code for POST request
	  assertEquals(response.statusCode(),201);
	  
	  JsonPath json=response.jsonPath();
	  //verify region name
	  assertEquals(json.getString("region_name"),"mynewregion");
	  //verify region id
	  assertEquals(json.getInt("region_id"),111);
	  
  }
  
  @Test
  public void regionPOSTv4() {
	  
	  Region region=new Region();
	  
	  region.setRegion_id(112);
	  region.setRegion_name("mynewregion");
	  
	  Response response=given().accept(ContentType.JSON).and()
			  .contentType(ContentType.JSON).and().body(region)
			  .when().post(baseUrl+"/regions/");
	  
	  //verify status code for POST request
	  assertEquals(response.statusCode(),201);
	 
	  //create our pojo object for response body
	  
	  RegionResponse regionResponse=response.as(RegionResponse.class);
	  
	  assertEquals(regionResponse.getRegion_id(),region.getRegion_id());
	  assertEquals(regionResponse.getRegion_name(),region.getRegion_name());
	  
  }
  
  
  	/*
	 * Given accept type is Json
	 * And content type is json
	 * When I send post request to URL/countries/
	 * with following JSON body
			{
			"country_id": "AA",
            "country_name": "Cybertek",
            "region_id": 2
			}
	 * Then status code must be 201
	 * and response body shoould match the request body
	 */
  
  	
  @Test
  public void countryPost() {
	  
	  Country country=new Country();
	  
	  country.setCountry_id("AA");
	  country.setCountry_name("Cybertek");
	  country.setRegion_id(2);
	  
	  Response response=given().accept(ContentType.JSON)
			  .and().contentType(ContentType.JSON).and().body(country)
			  .when().post(baseUrl+"/countries/");
	  
	  //start testing
	  
	  //status code
	  assertEquals(response.statusCode(),201);
	  
	  //get JSON body from response and convert it to Java object(countryResponse)
	  CountryResponse countryResponse=response.as(CountryResponse.class);
	  
	  //verify req body and response body
	  
	  assertEquals(countryResponse.getCountry_id(),country.getCountry_id());
	  assertEquals(countryResponse.getCountry_name(),country.getCountry_name());
	  assertEquals(countryResponse.getRegion_id(),country.getRegion_id());
	  
  }
  
  @Test
  public void countryPut() {
	  
	  Country country=new Country();
	  
	  country.setCountry_id("AA");
	  country.setCountry_name("CCC");
	  country.setRegion_id(2);
	  
	  Response response=given().accept(ContentType.JSON)
			  .and().contentType(ContentType.JSON).and().body(country)
			  .when().put(baseUrl+"/countries/"+country.getCountry_id());
	  
	  //start testing
	  
	  //status code
	  assertEquals(response.statusCode(),200);
	  
	  //get JSON body from response and convert it to Java object(countryResponse)
	  CountryResponse countryResponse=response.as(CountryResponse.class);
	  
	  //verify req body and response body
	  
	  assertEquals(countryResponse.getCountry_id(),country.getCountry_id());
	  assertEquals(countryResponse.getCountry_name(),country.getCountry_name());
	  assertEquals(countryResponse.getRegion_id(),country.getRegion_id());
	  
  }
  
  @Test
  public void CountryDelete() {
	  
	  Response response=given().accept(ContentType.JSON).when()
			  .delete(baseUrl+"/countries/AA");
	  
	  assertEquals(response.statusCode(),200);
	  
	  JsonPath json=response.jsonPath();
	  
	  assertEquals(json.getInt("rowsDeleted"),1);
	  
  }
  
  @Test
  public void jacksonExample() throws JsonProcessingException {
	  
	  Country country=new Country();
	  
	  country.setCountry_id("AA");
	  country.setCountry_name("CCC");
	  country.setRegion_id(2);
	  
	  ObjectMapper mapper=new ObjectMapper();
	  
	  String jsonRequest=mapper.writeValueAsString(country);
	  
	  System.out.println(jsonRequest);
	  
  }
  
  
  
  
  
  
  
  
  
}
