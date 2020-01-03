package com.qa.restassured.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.restassured.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class Tc004_RestAssured_DELETE_Test extends TestBase {

	
	@BeforeClass
	public void deleteEmployee() throws InterruptedException
	{
		
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		httpResponse = httpRequest.request(Method.GET, "/employees");
				
		// First get the JsonPath object instance from the httpResponse interface
		JsonPath jsonPathEvaluator = httpResponse.jsonPath();
		System.out.println(jsonPathEvaluator.toString());
		//Capture id
		String empID=jsonPathEvaluator.get("[0].id");
		System.out.println(empID);
		httpResponse = httpRequest.request(Method.DELETE, "/delete/"+empID); //Pass ID to delete record
		
		Thread.sleep(3000);
	}
	
	@Test(priority=0)
	void checkResposeBody()
	{
		String httpResponseBody = httpResponse.getBody().asString();
		System.out.println(httpResponseBody);
		Assert.assertEquals(httpResponseBody.contains("successfully! deleted Records"), true);

	}
		
	@Test(priority=1)
	void checkStatusCode()
	{
		int statusCode = httpResponse.getStatusCode(); 
		// Gettng status code
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 200);
	}
		
	@Test(priority=2)
	void checkstatusLine()
	{
		String statusLine = httpResponse.getStatusLine(); // Gettng status Line
		System.out.println(statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		
	}
	
	@Test(priority=3)
	void checkContentType()
	{
		String contentType = httpResponse.header("Content-Type");
		System.out.println(contentType);
		Assert.assertEquals(contentType, "text/html; charset=UTF-8");
	}

	@Test(priority=4)
	void checkserverType()
	{
		String serverType = httpResponse.header("Server");
		Assert.assertEquals(serverType, "nginx/1.16.0");
	}

	@Test(priority=5)
	void checkcontentEncoding()
	{
		String contentEncoding = httpResponse.header("Content-Encoding");
		Assert.assertEquals(contentEncoding, null);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
