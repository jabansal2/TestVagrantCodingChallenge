package TestScripts;

import org.testng.annotations.Test;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WeatherDetailsAPI {

	Response res;

	public Response getResponse(String city) {
		res = given()
						.param("q", city)
						.param("appid", "7fe67bf08c80ded756e598d6f8fedaea")
						.when()
						.get();
		return res;
	}
	
	

	public float getMinTemp(Response res) {
		float minTemp =res.path("main.temp_min");
		return minTemp;
	}
	

	public float getMaxTemp(Response res) {
	//	System.out.println("temp max class = " + res.path("main.temp_max").getClass().getName());
		float maxTemp = res.path("main.temp_max");
		return maxTemp;
	}
	

	public int getHumidity(Response res) {
		return res.path("main.humidity");
	}
	
	public float getWindSpeed(Response res) {
//		System.out.println("class name - " + res.path("wind.speed").getClass().getName());
		float result = 0;
		if(res.path("wind.speed").getClass().getName().equalsIgnoreCase("java.lang.Float"))
			result = res.path("wind.speed");
		if(res.path("wind.speed").getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
			Integer val = res.path("wind.speed");
			result = val.floatValue();
		}
			return result;

	}

	public void getTempFromAPI(Response res) {
		System.out.println("min: " + getMinTemp(res));
		System.out.println("max: " + getMaxTemp(res));
		System.out.println("humidity: " + getHumidity(res));
		System.out.println("windSpeed: " + getWindSpeed(res));

	}
	
	
	public HashMap<String, Object> getApiDataForComparison(Response res){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("minTemp", getMinTemp(res));
		map.put("maxTemp", getMaxTemp(res));
		map.put("humidity",getHumidity(res));
		map.put("windSpeed", getWindSpeed(res));
		return map;
	}


}
