package TestScripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ReusableUtilities.DataManipulationMethods;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NdtvTest {

	WebDriver driver;
	String city = "Amritsar";
	Response res;
	
	HashMap<String, Object> weatherDetailsFromUI = new HashMap<String, Object>();
	HashMap<String, Object> weatherDetailsFromApi = new HashMap<String, Object>();
	
	WeatherDetailsAPI apiInstance = new WeatherDetailsAPI();
	DataManipulationMethods dm = new DataManipulationMethods();

	@BeforeTest
	public void setupUI() {
		// use Chrome Driver
		RestAssured.baseURI = "http://api.openweathermap.org";
		RestAssured.basePath = "/data/2.5/weather";
		System.setProperty("webdriver.chrome.driver","/home/pardeepkaur/Downloads/Ubuntu_Downloads_bkup/ChromeDriver/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testUI() {
		NdtvHomePage homePage = new NdtvHomePage(driver);
		homePage.isPageOpened();
		homePage.clickOnSubMenu();
		homePage.clickOnWeather();
		
		NdtvWeatherPage weatherPage = new NdtvWeatherPage(driver);
		weatherPage.setCityToPin(city);
		weatherPage.isPageOpened();
		weatherPage.sendCityName();
		weatherPage.selectCityToPin();
		boolean citySelected = weatherPage.isSelectedCityOnMap();
		Assert.assertTrue("City either not selected or not on map", citySelected);
		List<String> list = new ArrayList<String>();
		
		list = weatherPage.getCityDetailsOnMap();
		
		
		weatherDetailsFromUI = dm.getUIDataForComparison(list);
		System.out.println("weatherDetailsFromUImap = " + weatherDetailsFromUI);
		System.out.println("formatted data = " + dm.getUIDataForComparison(list));
		
		
	}
	
	@Test
	public void getApiResponse() {
		
		res = apiInstance.getResponse(city);
		System.out.println("response captured = " + res.prettyPrint());

	}
	
	
	@Test(dependsOnMethods = {"getApiResponse"})
	public void statusCodeVerification() {
		Assert.assertEquals(200, res.getStatusCode());
		System.out.println("status code verified");
	}
	
	@Test(dependsOnMethods = {"getApiResponse"})
	public void contentTypeVerification() {

		Assert.assertEquals("application/json; charset=utf-8",res.header("Content-Type"));
		System.out.println("content type is verified");
	}
	
	
	@Test(dependsOnMethods = {"testUI","getApiResponse"})
	public void getValidations() {
		weatherDetailsFromApi = apiInstance.getApiDataForComparison(res);
		System.out.println("weatherDetailsFromApi = " + weatherDetailsFromApi);
		dm.comparator(weatherDetailsFromUI, weatherDetailsFromApi, 1);
	}
	
	
	@AfterTest
	public void closeTheDriver() {
		driver.close();
	}

	
}
