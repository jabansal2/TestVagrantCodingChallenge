package TestScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SingleBrowserClass {

	// instance of singleton class
	private static SingleBrowserClass instanceOfSingleBrowserClass=null;
	
	
    private WebDriver driver;

    // Constructor
    static ChromeOptions options = new ChromeOptions();
    
    private SingleBrowserClass(){
    	options.addArguments("--disable-notifications");
    	System.setProperty("webdriver.chrome.driver","/home/pardeepkaur/Downloads/Ubuntu_Downloads_bkup/ChromeDriver/chromedriver");
		driver= new ChromeDriver(options);
    }

    // TO create instance of class
    public static SingleBrowserClass getInstanceOfSingletonBrowserClass(){
 //   	options.addArguments("--disable-notifications");
        if(instanceOfSingleBrowserClass==null){
        	instanceOfSingleBrowserClass = new SingleBrowserClass();
        }
        return instanceOfSingleBrowserClass;
    }
    
    // To get driver
    public WebDriver getDriver()
    {
    	return driver;
    }
    

   
}