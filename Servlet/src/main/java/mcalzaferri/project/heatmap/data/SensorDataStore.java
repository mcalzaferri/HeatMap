package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.*;

public class SensorDataStore{
	private Datastore datastore;
	private DataStoreKeyFactory keyFactory;
	private SensorDataStoreReader datastoreReader;
	private SensorDataStoreWriter datastoreWriter;
	public static final String SENSORDATA = "SensorData";
	public static final String SENSOR = "Sensor";
	private static SensorDataStore instance;
	
	public static SensorDataStore getLocalInstance() {
		if(instance == null) {
			instance = new SensorDataStore("http://localhost:8081","heatmap-219120");
		}
		return instance;
	}
	
	public static SensorDataStore getDefaultInstance() {
		if(instance == null) {
			instance = new SensorDataStore();
		}
		return instance;
	}
	
	private SensorDataStore(String host, String projectId) {
		datastore = DatastoreOptions.newBuilder().setHost(host).setProjectId(projectId).build().getService();
		keyFactory = new DataStoreKeyFactory(datastore);	
		datastoreReader = new SensorDataStoreReader(datastore, keyFactory);
		datastoreWriter = new SensorDataStoreWriter(datastore, keyFactory);
	}
	private SensorDataStore() {
		datastore = DatastoreOptions.getDefaultInstance().getService();
		keyFactory = new DataStoreKeyFactory(datastore);	
		datastoreReader = new SensorDataStoreReader(datastore, keyFactory);
		datastoreWriter = new SensorDataStoreWriter(datastore, keyFactory);
	}
	
	public SensorDataStoreReader getReader() {
		return datastoreReader;
	}
	
	public SensorDataStoreWriter getWriter() {
		return datastoreWriter;
	}
}
