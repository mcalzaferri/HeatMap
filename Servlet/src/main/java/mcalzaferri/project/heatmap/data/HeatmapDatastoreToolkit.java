package mcalzaferri.project.heatmap.data;

import java.io.IOException;

import com.google.cloud.datastore.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mcalzaferri.project.heatmap.data.config.DatastoreConfigReader;
import mcalzaferri.project.heatmap.data.config.DatastoreConfigVerifier;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;
import mcalzaferri.project.heatmap.data.config.RessourceNotFoundException;
import mcalzaferri.project.heatmap.data.config.VerificationException;

public class HeatmapDatastoreToolkit{
	private Datastore datastore;
	
	private static HeatmapDatastoreToolkit instance;
	
	public static HeatmapDatastoreToolkit getLocalInstance(String configPath) throws IOException {
		if(instance == null) {
			instance = new HeatmapDatastoreToolkit("http://localhost:8081","heatmap-219120", configPath);
		}
		return instance;
	}
	
	public static HeatmapDatastoreToolkit getDefaultInstance(String configPath) throws IOException {
		if(instance == null) {
			instance = new HeatmapDatastoreToolkit(configPath);
		}
		return instance;
	}
	
	private HeatmapDatastoreToolkit(String host, String projectId, String configPath) throws IOException {
		this(DatastoreOptions.newBuilder().setHost(host).setProjectId(projectId).build(), configPath);
	}
	
	private HeatmapDatastoreToolkit(String configPath) throws IOException {
		this(DatastoreOptions.getDefaultInstance(), configPath);
	}
	
	private HeatmapDatastoreToolkit(DatastoreOptions options, String configPath) throws IOException {
		DatastoreConfigReader.initInstance(configPath);
		datastore = options.getService();
	}
	
	public JsonArray getJson(String uri, String queryString) throws RessourceNotFoundException, FieldNotFoundException {
		RequestedRessource res = getRessourceFactory().newBuilder().buildFromUri(uri);
		return getReader().query(res, queryString);
	}
	
	public JsonObject storeJson(String uri, String json) throws RessourceNotFoundException, VerificationException {
		JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
		RequestedRessource res = getRessourceFactory().newBuilder().buildFromUri(uri);
		long id = getWriter().storeJsonObject(res, jsonObj);
		jsonObj.addProperty("id",id);
		return jsonObj;
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	public DatastoreKeyFactory getKeyFactory() {
		return DatastoreKeyFactory.getInstance(getDatastore());
	}
	public DatastoreConfigReader getConfigReader() {
		return DatastoreConfigReader.getInstance();
	}
	public DatastoreConfigVerifier getConfigVerifier() {
		return DatastoreConfigVerifier.getInstance(getConfigReader());
	}
	public SensorDatastoreReader getReader() {
		return SensorDatastoreReader.getInstance(this);
	}
	public SensorDatastoreWriter getWriter() {
		return SensorDatastoreWriter.getInstance(this);
	}
	public DatastoreEntityFactory getEntityFactory() {
		return DatastoreEntityFactory.getInstance();
	}
	public RequestedRessourceFactory getRessourceFactory() {
		return RequestedRessourceFactory.getInstance();
	}
	public DatastoreQueryFactory getQueryFactory() {
		return DatastoreQueryFactory.getInstance(getKeyFactory());
	}
}
