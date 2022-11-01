package com.TestClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; //Write manually coz its a static package
import static org.hamcrest.Matchers.*; //Write manually coz its a static package

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Payloads.InputPayloads;

public class DynamicJson {
	
	public String bUri = "http://216.10.245.166";
	String bookID;
		
	@Test(dataProvider = "BookData",priority = 1)
	public void addBook(String isbn, String aile) {
		
		RestAssured.baseURI= bUri;
		String addBkResponse = given().log().all().header("Content-Type","application/json").body(InputPayloads.AddBook(isbn,aile))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp = new JsonPath(addBkResponse);
		System.out.println(jp.get("ID"));
		bookID = jp.get("ID");
		
	}
	
	@Test(dataProvider = "BookData",priority = 2)
	public void deleteBook(String isbn, String aile) {
		String ID = isbn+aile;
		RestAssured.baseURI=bUri;
		given().log().all().body(InputPayloads.DeleteBook(ID))
		.when().delete("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
	}
	
	@DataProvider(name="BookData")
	public Object[][] multiData(){
		return new Object[][]{{"DSPD", "301"},{"FDFS", "430"}};
	}

}
