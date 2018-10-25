package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.net.ConnectException;
import java.security.SecureRandom;
import java.util.Date;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;
import mcalzaferri.project.heatmap.common.entities.IdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.IdentifiedSensorData;
import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;

public class RandomSensorClient extends SensorClient{

	public RandomSensorClient(long defaultId, HttpPostClient client, GeoLocation location) throws IOException {
		super(defaultId, client, location);
		// TODO Auto-generated constructor stub
	}
	
	public RandomSensorClient(HttpPostClient client, GeoLocation location) throws IOException {
		super(client, location);
	}
	
	@Override
	protected void requestId() {
		NotIdentifiedSensor noIdSensor = new NotIdentifiedSensor(getLocation());
		IdentifiedSensor idSensor = post(noIdSensor, IdentifiedSensor.class);
		if(idSensor != null) {
			setId(idSensor.getId());
		}
	}

	@Override
	protected void sendData() {
		IdentifiedSensorData data = new IdentifiedSensorData(getTemperature(), new Date(), getId());
		post(data,Object.class);
	}

	private <T> T post(Object data, Class<T> expectedResponse) {
		try {
			return getClient().post(data, expectedResponse);
		} catch (ConnectException ce) {
			System.err.println("Lost connection to server!");
			disconnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			disconnect();
		}
		return null;
	}
	
	public double getTemperature() {
		return (new SecureRandom().nextDouble()) * 70 - 30; //Generates temperatures between -30 and + 40 degrees
	}
	

}
