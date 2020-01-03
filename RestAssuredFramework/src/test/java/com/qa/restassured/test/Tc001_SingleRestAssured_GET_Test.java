package com.qa.restassured.test;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.qa.restassured.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;


public class Tc001_SingleRestAssured_GET_Test extends TestBase{
		@BeforeClass
		public void SetUpGETSingle() {
			RestAssured.baseURI=prop.getProperty("SingleURL");
			httpRequest=RestAssured.given();
			httpResponse=httpRequest.request(Method.GET,prop.getProperty("ServiceSingleGETURL"));
		}

		@Test(priority=0)
		public void getAllEmployeeRecords() {
				
			// Refer:https://www.toolsqa.com/rest-assured/read-json-response-body-using-rest-assured/
			
			JsonPath jsonpathEvaluator=httpResponse.jsonPath();
			HashMap<String,String>dataMap =jsonpathEvaluator.get("data");
			
			if(dataMap.containsKey("last_name")){
				String lastname= dataMap.get("last_name");
				System.out.println(lastname);
        }
			System.out.println(dataMap);
		
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
			//Assert.assertEquals(headerContentType, "text/html;charset=UTF-8");
			//content encoding
			String headerContentLength=httpResponse.getHeader("Content-Length");
		 //   System.out.println("Content Length is"+Integer.parseInt(headerContentLength));
		    //content encoding
		    String contentEncoding = httpResponse.header("Content-Encoding");
		    System.out.println(contentEncoding);
		  //  Assert.assertEquals(contentEncoding, "gzip");
		
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
			Assert.assertEquals(serverType, "cloudflare");
		}
		
		
}
