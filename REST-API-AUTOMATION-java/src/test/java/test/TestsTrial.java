package test;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
public class TestsTrial extends ReportTestNG {
	
	
	
	
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> classLevelLogger = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> testLevelLogger = new ThreadLocal<ExtentTest>();

	@BeforeSuite
	public void setUp() {
		extent = ReportTestNG.getExtent();
	}

	@BeforeClass
	public void beforeClass() {
		ExtentTest parent = extent.createTest(getClass().getName());
		classLevelLogger.set(parent);
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		ExtentTest child = classLevelLogger.get().createNode(method.getName());
		testLevelLogger.set(child);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) 
		{
			testLogger().log(Status.FAIL, "Testcase is failed due to below mentioned problem: ");
			testLogger().log(Status.FAIL, result.getThrowable());
		} 
		else if (result.getStatus() == ITestResult.SUCCESS) {
			testLogger().log(Status.PASS, result.getName() + " This test is Pass");
		} 
		else if (result.getStatus() == ITestResult.SKIP) {
			testLogger().log(Status.SKIP, result.getName() + " This test is Skipped");
			testLogger().log(Status.SKIP, result.getThrowable());
		}

		extent.flush();
	}

	public static ExtentTest testLogger() {
		return testLevelLogger.get();
	}

	
//	@Test
//	public void test_1() {
//		baseURI = "https://reqres.in/api/";
//		Response response = given().get("/users?page=1&per_page=10");
//		System.out.println(response.getStatusCode());
//		System.out.println(response.getBody().asPrettyString());
//		
//		
//		
//	}
//	@Test
//	public void test_2() {
//		
//		given().get("/users?page=2&per_page=6").
//		then().
//		body("data[0].id", equalTo(7));
//	}
}
