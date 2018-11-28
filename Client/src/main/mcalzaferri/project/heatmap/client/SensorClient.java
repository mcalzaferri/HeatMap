package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.net.ConnectException;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.geo.CountryCapital;
import mcalzaferri.geo.IdentifiedCountryCapital;
import mcalzaferri.net.http.HttpPostClient;
import mcalzaferri.project.heatmap.common.entities.IdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;

public abstract class SensorClient implements Runnable {
	public static final int maxCount = 1;
	public static final int connectTimeout = 5000; // ms
	private HttpPostClient client;
	private String host;
	private long id;
	private long count;
	private boolean connected;
	private CountryCapital capital;
	private CapitalManager manager;
	private int sendInterval; //ms

	public SensorClient(String host, CapitalManager manager) throws IOException {
		this.manager = manager;
		this.capital = manager.getNextCapital();
		this.host = host;
		this.client = new HttpPostClient();
		this.count = 0;
		client.setConnectTimeout(connectTimeout);
		if(capital instanceof IdentifiedCountryCapital) {
			id = ((IdentifiedCountryCapital)capital).getId();
		}
		
		sendInterval = 1000;
	}

	@Override
	public void run() {
		while (count < maxCount) {
			connect();
			while (connected && count < maxCount) {
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
			count++;
		}
	}
	
	protected void requestId() {
		NotIdentifiedSensor noIdSensor = new NotIdentifiedSensor(capital.getLocation());
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
		manager.identifyCapital(capital, id);
	}
}
