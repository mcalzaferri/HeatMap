package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.net.ConnectException;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;
import mcalzaferri.project.heatmap.common.entities.IdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;

public abstract class SensorClient implements Runnable {
	public static final int connectTimeout = 5000; // ms
	private HttpPostClient client;
	private String host;
	private long id;
	private boolean connected;
	private GeoLocation location;
	private int sendInterval; //ms

	public SensorClient(long defaultId, String host, GeoLocation location) throws IOException {
		this(host, location);
		id = defaultId;
		sendInterval = 1000;
	}

	public SensorClient(String host, GeoLocation location) throws IOException {
		this.setLocation(location);
		this.host = host;
		this.client = new HttpPostClient();
		client.setConnectTimeout(connectTimeout);
		sendInterval = 1000;
	}

	@Override
	public void run() {
		while (true) {
			connect();
			while (connected) {
				updateServer();
				sleep(sendInterval);
			}
			disconnect();
			sleep(1000);
		}
	}

	protected void connect() {
		try {
			if(id == 0)
				client.setUrl(host);
			else
				client.setUrl(host + "/" + id + "/measurements");
			client.connect();
			System.out.println("Successfully connected to server!");
			connected = true;
		} catch (IOException ioe) {
			System.err.println("Could not connect to server!");
			connected = false;
		}
	}

	protected void disconnect() {
		client.disconnect();
		connected = false;
	}

	private void updateServer() {
		if (id == 0) {
			requestId();
		} else {
			sendData();
		}
	}
	
	protected void requestId() {
		NotIdentifiedSensor noIdSensor = new NotIdentifiedSensor(getLocation());
		IdentifiedSensor idSensor = post(noIdSensor, IdentifiedSensor.class);
		if(idSensor != null) {
			setId(idSensor.getId());
			disconnect();
		}
	}

	protected void sendData() {
		Object data = getSensorData();
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

	protected abstract Object getSensorData();

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}
	
	public int getSendInterval() {
		return sendInterval;
	}
	
	public void setSendInterval(int interval) {
		this.sendInterval = interval;
	}
	
	public HttpPostClient getClient() {
		return client;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
