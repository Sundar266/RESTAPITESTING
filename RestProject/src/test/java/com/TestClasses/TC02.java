package com.TestClasses;

import com.Payloads.InputPayloads;

import io.restassured.path.json.JsonPath;

public class TC02 {
	
	public void NestedJsonTest() {
		
		JsonPath jp = new JsonPath(InputPayloads.NestedJsonData()); 
		jp.getInt("courses.size()");
	}

}
