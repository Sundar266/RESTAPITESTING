package com.TestClasses;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; //Write manually coz its a static package

public class AuthorizationPractice {

	public void step1() {
		
		String accessCode = given().queryParam("code", "")
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jsP = new JsonPath(accessCode);
		String accessToken = jsP.getString("access_token");
		
		String response = given().queryParam("access_token", accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		System.out.println(response);
	}
	
}
