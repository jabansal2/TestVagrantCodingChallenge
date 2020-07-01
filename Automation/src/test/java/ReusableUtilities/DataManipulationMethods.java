package ReusableUtilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class DataManipulationMethods {

	public HashMap<String, String> getWeatherDataFromUiInMap(List<String> list) {
		HashMap<String, String> map = new HashMap<String, String>();

		for (String s : list) {
			String[] tempStrArr = new String[2];
			tempStrArr = splitString(s, ":");
			map.put(tempStrArr[0], tempStrArr[1]);
		}
		return map;
	}

	public String[] splitString(String str, String ex) {
		String[] strArr = new String[2];

		strArr = str.split(ex);

		return strArr;
	}

	public HashMap<String, Object> getUIDataForComparison(List<String> list) {
		HashMap<String, Object> m1 = new HashMap<String, Object>();

		DecimalFormat df = new DecimalFormat("#.#");// this formats speed value after comverting from km/h to m/s to 1
													// place of decimal
		for (String s : list) {
			String[] tempStrArr = new String[2];
			tempStrArr = splitString(s, ":");

			if (tempStrArr[0].equalsIgnoreCase("Wind")) {
				System.out.println("tempStrArr[1] = " + tempStrArr[1]);
				String[] windArr = StringUtils.split(tempStrArr[1]);// used to split string by white spaces
				System.out.println("windArr = " + Arrays.asList(windArr));
				System.out.println(windArr[0] + windArr[1] + windArr[2] + windArr[3]);
				float minSpeed = (Float.valueOf(windArr[0]) * 1000) / 3600;
				float maxSpeed = (Float.valueOf(windArr[4]) * 1000) / 3600;
				m1.put("WindSpeedMin", df.format(minSpeed));
				m1.put("WindSpeedMax", df.format(maxSpeed));
			}

			if (tempStrArr[0].equalsIgnoreCase("Humidity")) {
				int humidityValue = gethumidityValueInInteger(tempStrArr[1]);
				m1.put("humidity", humidityValue);
				System.out.println("humidity = " + Arrays.asList(tempStrArr[1].split("\\s")));
			}

			if (tempStrArr[0].equalsIgnoreCase("Temp in Degrees")) {
				float tempInKelvin = 273 + Float.valueOf(tempStrArr[1]);
				m1.put("Temperature", tempInKelvin);
			}

		}
		return m1;
	}

	public void comparator(HashMap<String, Object> ui, HashMap<String, Object> api, int variance) {

		SoftAssert assertion = new SoftAssert();
		float tempUI = (float) ui.get("Temperature");
		float minTempApi = (float) api.get("minTemp");
		float maxTempApi = (float) api.get("maxTemp");

		float windSpeedMinUi = Float.valueOf((String) ui.get("WindSpeedMin"));
		float windSpeedMaxUi = Float.valueOf((String) ui.get("WindSpeedMax"));
		float windSpeedapi = (float) api.get("windSpeed");
		
		int humidityUI  = (int) ui.get("humidity");
		int humidityApi = (int) ui.get("humidity");
		
		int var = variance;
		
		if((tempUI == minTempApi) || (tempUI == maxTempApi)){
			assertion.assertTrue(true, "Temperature matches");
		}
		else if(((tempUI > minTempApi)&&(tempUI <maxTempApi)) || (tempUI <= maxTempApi +var)) {
			assertion.assertTrue(true, "Temperature is in defined range");
		}
		else {
			System.out.println("temperature neither is matching max and min value nor is in the specified variance");
			assertion.assertTrue(false, "temperature neither is matching max and min value nor is in the specified variance");
		}
		if((windSpeedapi == windSpeedMinUi) || (windSpeedapi == windSpeedMaxUi)) {
			assertion.assertTrue(true, "WindSpeed is matching");
		}
		else if((windSpeedapi <= windSpeedMinUi+var)||(windSpeedapi <= (windSpeedMaxUi-var) ||((windSpeedapi > windSpeedMinUi) && (windSpeedapi<windSpeedMaxUi)))){
			assertion.assertTrue(true, "WindSpeed is in defined range");
			System.out.println("1 = " + (windSpeedMaxUi +Float.valueOf(var)));
			System.out.println("2 = " + (windSpeedMinUi -Float.valueOf(var)));
		}
		else {
			System.out.println("WindSpeed neither is matching nor falling in specified variance");
			assertion.assertTrue(false, "windSpeed Mismatch");
			
		}
		
		if((humidityUI == humidityApi))
			assertion.assertTrue(true, "Humidity is matching");
		else if((humidityUI < humidityApi +var)||(humidityUI > humidityApi -var))
			assertion.assertTrue(true, "Humidity is falling in the specified variance");
		else {
			System.out.println("Humidity is not macthing");
			assertion.assertTrue(false, "Humidity is not macthing");
		}
		assertion.assertAll();
	}

	public int gethumidityValueInInteger(String s) {
		int val = 0;
		s = s.trim();
		String[] sArr = s.split("");
		System.out.println("sArr = " + Arrays.asList(sArr));
		StringBuilder strbld = new StringBuilder();
		for (int i = 0; i < sArr.length; i++) {
			if (i != sArr.length - 1)
				strbld.append(sArr[i]);
		}
		String temp = strbld.toString();
		val = Integer.valueOf(temp);

		return val;
	}

}
