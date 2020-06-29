package TestScripts;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NdtvWeatherPage {

	private WebDriver driver;

	public NdtvWeatherPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, id="searchBox")
	private WebElement searchBox;
	
	@FindBy(how = How.ID, id="messages")
	private WebElement messages;
	
	@FindBys(@FindBy(how = How.XPATH, xpath = "//*[@id='messages']/div[@class='message']/label/input"))
	private List<WebElement> citiespath;
	
	@FindBy(how = How.XPATH, xpath = "")
	private WebElement cityName;
	
	@FindBys(@FindBy(how = How.CSS, css = "div[id='map_canvas']>div div:nth-of-type(4) div div div:nth-of-type(2)"))
	private List<WebElement> cityNamesOnMap;
	
//	@FindBy(how = How.XPATH, xpath = "//*[@id='map_canvas']/div/child::div[6]")
//	private WebElement cityDetailsOnMap;
	
	
	@FindBys(@FindBy(how = How.XPATH, xpath = "//*[@id='map_canvas']/div[1]/div[6]/div/div[1]/div/div/span/b"))
	private List<WebElement> cityDetailsOnMap;

//	@FindBys(@FindBy(how = How.CSS, css = "div[id='map_canvas']>div div:nth-of-type(6)>div>div>div>div span"))
//	private List<WebElement> cityDetailsOnMap;
	
	
	public  boolean isPageOpened() {
		return driver.getCurrentUrl().contains("https://social.ndtv.com/static/Weather/report/?pfrom=home-topsubnavigation");
	}
	
	
	String city;
	
	public void setCityToPin(String name) {
		this.city = name;
		System.out.println("city = " + city);
	}
	
	public void sendCityName() {
		searchBox.clear();
		searchBox.sendKeys(city);
	}

	/*
	 * public int number_of_Cities() { List<WebElement> numOfCities =
	 * messages.findElements(By.className("message")); return numOfCities.size(); }
	 * 
	 * public int returnCitySizeUsingFindAll() { return citiespath.size(); }
	 * 
	 * public void getNumofCities() {
	 * System.out.println(returnCitySizeUsingFindAll()); }
	 * 
	 * public void printCityName() { for(WebElement element: citiespath) {
	 * if(element.getAttribute("id").equalsIgnoreCase(city))
	 * System.out.println("print city name as : " + element.getAttribute("id"));
	 * 
	 * } }
	 * 
	 */	
	
	public void selectCityToPin() {
		for(WebElement element: citiespath) {
			if(element.getAttribute("id").equalsIgnoreCase(city)) {
				System.out.println("print city name as : " + element.getAttribute("id"));
				if(element.getAttribute("type").equalsIgnoreCase("checkbox")) {
					if(!element.isSelected()) {
						element.click();
					}
					isCheckBoxSelected(element);
				}
			}

		}

	}
	
	public boolean isCheckBoxSelected(WebElement e) {
		
		return e.isSelected();
	}
	
	
	public boolean isSelectedCityOnMap() {
		boolean flag = false;
		for(WebElement e : cityNamesOnMap) {
			if(e.getAttribute("class").equalsIgnoreCase("cityText")) {
				if(e.getText().equalsIgnoreCase(city)) {
					flag = true;
					System.out.println("flag = " + flag);
					e.click();
					return flag;
				}
			}

		}
		return flag;
	}
	
	public List<String> getCityDetailsOnMap(){
		
		List<String> weatherDetailsList = new ArrayList<String>();
		System.out.println("this is tough");
		System.out.println(cityDetailsOnMap.size());
		
		for(WebElement e: cityDetailsOnMap) {
			System.out.println("weatherDetails = " + e.getText());
			weatherDetailsList.add(e.getText());
		}
		System.out.println("weatherDetailsList = " + weatherDetailsList);
		return weatherDetailsList;
	}
}
