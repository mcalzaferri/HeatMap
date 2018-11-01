package mcalzaferri.project.heatmap.data.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DatastoreConfigFactory {
	private DatastoreConfigFactory() {}
	
	public static DatastoreConfig buildFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.fromJson(json, DatastoreConfig.class);
	}
}
