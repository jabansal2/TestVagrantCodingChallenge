package TestScripts;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class NewDataProvider {

	@Factory(dataProvider = "dp")
	public Object[] createInstances(String cityName) {
		return new Object[] {new NdtvTest(cityName)};
	}
	
	@DataProvider(name="dp")
	public 	static Object[][] dataProvider(){
		Object[][] dataArr = {{"Amritsar"}, {"Ahmedabad"},{"New Delhi"},{"Bengaluru"}};
		
		return dataArr;
	}
}
