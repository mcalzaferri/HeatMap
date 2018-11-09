package mcalzaferri.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonToCsvConverterTest {
	public static void main(String[] args) {
		JsonToCsvConverter converter = new JsonToCsvConverter();

		JsonArray jsonArray = new JsonArray();
		for(int i = 0; i < 10; i++) {		
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("stringProperty", "stringvalue" + Integer.toString(i));
			jsonObject.addProperty("intProperty", Long.MAX_VALUE);
			jsonObject.addProperty("doubleProeprty", 123.128341298347);
			JsonObject longLat = new JsonObject();
			longLat.addProperty("latitude", 12.1234);
			longLat.addProperty("longitude", 23.238);
			jsonObject.add("location", longLat);
			jsonArray.add(jsonObject);
		}
		System.out.println(converter.toCsv(jsonArray));
	}
}
