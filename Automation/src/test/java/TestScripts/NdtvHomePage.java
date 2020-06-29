package TestScripts;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NdtvHomePage {

	private WebDriver driver;

	private static String PAGE_URL = "https://www.ndtv.com/";

	@FindBy(how = How.ID, id = "h_sub_menu")
	private WebElement subMenu;

	@FindBy(how = How.LINK_TEXT, linkText = "WEATHER")
	private WebElement weather;

	public NdtvHomePage(WebDriver driver) {
		this.driver = driver;
		driver.get(PAGE_URL);
		PageFactory.initElements(driver, this);
	}

	public boolean isPageOpened() {
		return driver.getCurrentUrl().contains("https://www.ndtv.com/");
	}

	public void clickOnSubMenu() {
		subMenu.click();
	}

	public void clickOnWeather() {
		weather.click();
	}

}
