package com.qa.restassured.test;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.restassured.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class Tc001_RestAssured_GET_Test extends TestBase{
	
	 
	@BeforeClass
	public void SetUpGET() {
		RestAssured.baseURI=prop.getProperty("URL");
		httpRequest=RestAssured.given();
		httpResponse=httpRequest.request(Method.GET,prop.getProperty("ServiceGetURL"));
		
	}

	@Test(priority=0)
	public void getAllEmployeeRecords() {
		String responseString=httpResponse.getBody().asString();
		System.out.println(responseString);
		Assert.assertTrue(responseString!=null);
	}

	

	@Test(priority=1)
	public void checkStatusCode() {
		int statusCode=httpResponse.statusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(priority=2)
	public void checkStatusLine() {
		String statusLine=httpResponse.getStatusLine();
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	
	@Test(priority=3)
	public void checkHeaders() {
		//content type
		String headerContentType=httpResponse.getHeader("Content-Type");
		System.out.println(headerContentType);
		Assert.assertEquals(headerContentType, "text/html; charset=UTF-8");
		//content encoding
		String headerContentLength=httpResponse.getHeader("Content-Length");
	    System.out.println("Content Length is"+Integer.parseInt(headerContentLength));
	    //content encoding
	    String contentEncoding = httpResponse.header("Content-Encoding");
	    Assert.assertEquals(contentEncoding, "gzip");
	
	}
	
	@Test(priority=4)
	public void checkResponseTime() {
		
		long responseTime=httpResponse.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response time.............> "+responseTime+"milliSeconds");
	}
	
	
	@Test(priority=5)
	void checkCookies()
	{

		String cookie = httpResponse.getCookie("PHPSESSID");
		System.out.println(cookie);
		
		
	}
	@Test(priority=6)
	void checkserverType()
	{
		String serverType = httpResponse.header("Server");
		Assert.assertEquals(serverType, "nginx/1.16.0");
	}
	
	

	

	
	
	

	
}
