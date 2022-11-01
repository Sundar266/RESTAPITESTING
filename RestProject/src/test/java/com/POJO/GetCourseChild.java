package com.POJO;

import java.util.List;

public class GetCourseChild {

	private List<WebAutomationCourse> WebAutomation; //As this is an Array we say as it returns List
	private List<ApiCourse> api;
	private List<MobileCourse> mobile;
	
	public List<WebAutomationCourse> getWebAutomation() {
		return WebAutomation;
	}
	public void setWebAutomation(List<WebAutomationCourse> webAutomation) {
		WebAutomation = webAutomation;
	}
	public List<ApiCourse> getApi() {
		return api;
	}
	public void setApi(List<ApiCourse> api) {
		this.api = api;
	}
	public List<MobileCourse> getMobile() {
		return mobile;
	}
	public void setMobile(List<MobileCourse> mobile) {
		this.mobile = mobile;
	}
	
}
