package mcalzaferri.geo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mcalzaferri.geo.CountryCapital;
import mcalzaferri.geo.IdentifiedCountryCapital;

public class CapitalJsonEditor {
	
	public List<CountryCapital> getCapitals() throws IOException{
		return readCountryCapitalsFromJson(readFile("src/main/CountryCapitals.json"));
	}
	
	public List<IdentifiedCountryCapital> getIdentifiedCapitals() throws IOException{
		return readIdentifiedCountryCapitalsFromJson(readFile("src/main/IdentifiedCountryCapitals.json"));
	}
	
	private List<CountryCapital> readCountryCapitalsFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		Type listType = new TypeToken<ArrayList<CountryCapital>>(){}.getType();
		return gson.fromJson(json, listType);
	}
	
	private List<IdentifiedCountryCapital> readIdentifiedCountryCapitalsFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		Type listType = new TypeToken<ArrayList<IdentifiedCountryCapital>>(){}.getType();
		return gson.fromJson(json, listType);
	}
	
	private String readFile(String configFilePath) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader r = Files.newBufferedReader(Paths.get(configFilePath));
		r.lines().forEach(sb::append);
		r.close();
		return sb.toString();
	}
	
	public void saveIdentifiedCapitals(List<IdentifiedCountryCapital> capitals) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(capitals);
		try {
			PrintWriter writer = new PrintWriter("src/main/IdentifiedCountryCapitals.json", "UTF-8");
			writer.print(json);
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
