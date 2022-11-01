package com.ECom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; //Write manually coz its a static package
import static org.hamcrest.Matchers.*; //Write manually coz its a static package

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;


public class Class1 {
	
	String token;
	String userId;
	String productID;
	
	//To retrieve the token
	@Test(priority = 0)
	public void getToken() {
		
		RequestSpecification RS = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginSerialPOJO Login = new LoginSerialPOJO();
		Login.setuserEmail("sundarsnipes@gmail.com");
		Login.setuserPassword("Test@123");
		
		LoginDeserialPOJO LoginResponse = given().log().all().spec(RS).body(Login)
		.when().log().all().post("/api/ecom/auth/login")
		.then().extract().response().as(LoginDeserialPOJO.class);
		
		token = LoginResponse.getToken();
		userId = LoginResponse.getUserId();
		
		System.out.println("Token is:\t" +token);
		System.out.println(LoginResponse.getMessage());
	}
	
	@Test(priority = 1)
	public void addProduct() {
		
		RequestSpecification RS1 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.MULTIPART).build();
		
		String productResponse = given().log().all().spec(RS1)
		.param("productName", "MMA Gloves") //Since this is Form Data, param method is used
		.param("productAddedBy", userId)
		.param("productCategory", "Sports")
		.param("productSubCategory", "Combat").param("productPrice", "250.55").param("productDescription", "Under Armour")
		.param("productFor", "Men")
		.multiPart("productImage",new File("C:\\Users\\2126765\\eclipse-workspace\\RestProject\\RandomPics\\CombatSports.jpg"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		//Here we aren't using POJO classes
		JsonPath jp = new JsonPath(productResponse);
		productID = jp.get("productID");
		
	}
	
	@Test(priority = 2)
	public void createOrder() {
		
		RequestSpecification RS2 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		CreateOrderSerialPOJO2 details = new CreateOrderSerialPOJO2(); //If there are more than one Country and product, then create
		                                                               //multiple class objects.
		details.setCountry("Russia");
		details.setProductOrderedId(productID);
		
		List<CreateOrderSerialPOJO2> values = new ArrayList<CreateOrderSerialPOJO2>();
		values.add(details);
		
		CreateOrderSerialPOJO createOrder = new CreateOrderSerialPOJO();
		createOrder.setOrders(values);
		
		String createOrderResponse = given().log().all().spec(RS2).body(createOrder)
		.when().log().all().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		JsonPath jp = new JsonPath(createOrderResponse);
		
	}

	@Test(priority = 3)
	public void deleteProduct() {
		
		RequestSpecification RS3 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		String deleteProductResponse = given().log().all().spec(RS3).pathParam("productID" , productID)
		.when().delete("/api/ecom/product/delete-product/{productID}")
		.then().log().all().extract().response().asString();
		
		JsonPath jp = new JsonPath(deleteProductResponse);
		System.out.println(jp.get("message"));
	}
}
