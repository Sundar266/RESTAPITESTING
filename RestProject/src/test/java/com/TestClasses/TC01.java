package com.TestClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; //Write manually coz its a static package
import static org.hamcrest.Matchers.*; //Write manually coz its a static package

import org.testng.annotations.Test;

import com.Payloads.InputPayloads;

public class TC01 {
	
	//given - all the input fields should come under this
	//when - all the methods post the input providing comes under this
	//then - all the post request validations can be done under this 
	
	public String buri = "https://rahulshettyacademy.com";
	public String placeID;
	
	@Test(priority = 0)
	public void AddPlace() {	
		RestAssured.baseURI = buri;
		String response = given().queryParam("Key", "qaclick123").header("Content-Type", "application/json") //log.all is to record the outcomes
		.body(InputPayloads.AddPlace()).
		when().post("/maps/api/place/add/json") //Bringing the body from other class - Input Payloads
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		
		JsonPath jp = new JsonPath(response);
		placeID = jp.getString("place_id");
		System.out.println("Place ID retrieved is: "+jp.getString("place_id"));
	}
	
	@Test(priority = 1)
	public void UpdatePlace() {
		RestAssured.baseURI = buri;
		Response updateResponse = given().queryParam("Key", "qaclick123").queryParam("place_id", placeID).
		body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\"70 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response();
			
		System.out.println("Update Response is: "+updateResponse.asPrettyString());
	}		
		
	@Test(priority = 2)
	public void GetPlace() {
		//String newAddress = "70 winter walk, USA"; 	
		String compareAddress = given().log().all().queryParam("Key","qaclick123").queryParam("place_id", placeID)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();		
		System.out.println("Get REponse is: "+compareAddress);
	}
}
