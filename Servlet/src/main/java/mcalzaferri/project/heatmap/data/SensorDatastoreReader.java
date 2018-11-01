package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.*;

@SuppressWarnings("all")
public class SensorDatastoreReader {
	private Datastore datastore;
	private DatastoreKeyFactory keyFactory;
	
	public SensorDatastoreReader(Datastore datastore, DatastoreKeyFactory keyFactory) {
		this.datastore = datastore;
		this.keyFactory = keyFactory;
	}
	
	/*
	public TemperatureSensor readSensor(long id) {
		Entity sensor = datastore.get(keyFactory.getSensorKey(id));
		if(sensor != null) {
			return new TemperatureSensor(sensor);
		}else {
			throw new NoSuchElementException("No sensor with id " + id + " found");
		}
	}
	*/
	/*
	public List<TemperatureSensor> listSensors(){
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind(SensorDataStore.SENSOR)
				.build();
		QueryResults<Entity> resultList = datastore.run(query);
		List<TemperatureSensor> sensorList = new ArrayList<>();
		while(resultList.hasNext()) {
			sensorList.add(new TemperatureSensor(resultList.next()));
		}
		return sensorList;
	}
	
	public List<TemperatureData> listSensorData(long sensorId){
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind(SensorDataStore.SENSORDATA)
				.setFilter(PropertyFilter.eq(TemperatureData.SENSORID, sensorId))
				.build();
		QueryResults<Entity> resultList = datastore.run(query);
		List<TemperatureData> dataList = new ArrayList<>();
		while(resultList.hasNext()) {
			dataList.add(new TemperatureData(resultList.next()));
		}
		return dataList;
	}
	*/
}
