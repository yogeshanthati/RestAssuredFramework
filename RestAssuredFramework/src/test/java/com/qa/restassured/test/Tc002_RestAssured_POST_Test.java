package com.qa.restassured.test;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.restassured.base.TestBase;
import com.qa.restassured.utilities.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;



public class Tc002_RestAssured_POST_Test extends TestBase {
	String empName=RestUtils.empName();
	String empSalary=RestUtils.empSal();
	String empAge=RestUtils.empAge();
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	void createEmployee() throws InterruptedException
	{
		RestAssured.baseURI = prop.getProperty("URL");
		httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
	    requestParams.put("salary", empSalary);
		requestParams.put("age", empAge);
		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		httpResponse = httpRequest.request(Method.POST,prop.getProperty("ServicePOSTURL"));
		Thread.sleep(5000);
	}
	
	@Test(priority=0)
	void checkResposeBody()
	{
		String responseBody = httpResponse.getBody().asString();
		System.out.println(responseBody);
		System.out.println(empName);
		System.out.println(empSalary);
		System.out.println(empAge);
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(empSalary), true);
		Assert.assertEquals(responseBody.contains(empAge), true);
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
			String headerContentLength=httpResponse.getHeader("Content-Length");
		    System.out.println("Content Length is"+Integer.parseInt(headerContentLength));
		    String contentEncoding = httpResponse.header("Content-Encoding");
		    Assert.assertEquals(contentEncoding, null);
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
