package mcalzaferri.project.heatmap.data;

import java.io.IOException;

import com.google.cloud.datastore.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mcalzaferri.project.heatmap.data.config.DatastoreConfigReader;
import mcalzaferri.project.heatmap.data.config.DatastoreConfigVerifier;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;
import mcalzaferri.project.heatmap.data.config.RessourceNotFoundException;
import mcalzaferri.project.heatmap.data.config.VerificationException;

public class HeatmapDatastore{
	private Datastore datastore;
	private DatastoreKeyFactory keyFactory;
	private DatastoreConfigReader configReader;
	private DatastoreConfigVerifier configVerifier;
	private SensorDatastoreReader datastoreReader;
	private SensorDatastoreWriter datastoreWriter;
	private DatastoreEntityFactory entityFactory;
	private RequestedRessourceFactory resFactory;
	private DatastoreQueryFactory queryFactory;
	
	private static HeatmapDatastore instance;
	
	public static HeatmapDatastore getLocalInstance(String configPath) throws IOException {
		if(instance == null) {
			instance = new HeatmapDatastore("http://localhost:8081","heatmap-219120", configPath);
		}
		return instance;
	}
	
	public static HeatmapDatastore getDefaultInstance(String configPath) throws IOException {
		if(instance == null) {
			instance = new HeatmapDatastore(configPath);
		}
		return instance;
	}
	
	private HeatmapDatastore(String host, String projectId, String configPath) throws IOException {
		this(DatastoreOptions.newBuilder().setHost(host).setProjectId(projectId).build(), configPath);
	}
	
	private HeatmapDatastore(String configPath) throws IOException {
		this(DatastoreOptions.getDefaultInstance(), configPath);
	}
	
	private HeatmapDatastore(DatastoreOptions options, String configPath) throws IOException {
		datastore = options.getService();
		configReader = new DatastoreConfigReader(configPath);
		configVerifier = new DatastoreConfigVerifier(configReader);
		entityFactory = DatastoreEntityFactory.newFactory();
		keyFactory = DatastoreKeyFactory.newFactory(getDatastore());
		datastoreWriter = new SensorDatastoreWriter(this);
		datastoreReader = new SensorDatastoreReader(this);
		resFactory = RequestedRessourceFactory.newFactory();
		queryFactory = DatastoreQueryFactory.newFactory(keyFactory);
	}
	
	public String getJson(String uri, String queryString) throws RessourceNotFoundException, FieldNotFoundException {
		RequestedRessource res = resFactory.buildFromUri(uri);
		return datastoreReader.query(res, queryString);
	}
	
	public JsonObject storeJson(String uri, String json) throws RessourceNotFoundException, VerificationException {
		JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
		RequestedRessource res = resFactory.buildFromUri(uri);
		long id = getWriter().storeJsonObject(res, jsonObj);
		jsonObj.addProperty("id",id);
		return jsonObj;
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	public DatastoreKeyFactory getKeyFactory() {
		return keyFactory;
	}
	public DatastoreConfigReader getConfigReader() {
		return configReader;
	}
	public DatastoreConfigVerifier getConfigVerifier() {
		return configVerifier;
	}
	public SensorDatastoreReader getReader() {
		return datastoreReader;
	}
	public SensorDatastoreWriter getWriter() {
		return datastoreWriter;
	}
	public DatastoreEntityFactory getEntityFactory() {
		return entityFactory;
	}
	
	public DatastoreQueryFactory getQueryFactory() {
		return queryFactory;
	}
}
