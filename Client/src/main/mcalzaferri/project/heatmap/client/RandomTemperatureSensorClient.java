package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.net.ConnectException;
import java.security.SecureRandom;
import java.util.Date;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.IHttpClient;
import mcalzaferri.net.http.HttpMessage;
import mcalzaferri.project.heatmap.messages.*;

public class RandomTemperatureSensorClient implements Runnable, IMessageHandler {
	public static final int connectTimeout = 5000; // ms
	private IHttpClient client;
	private long id;
	private boolean connected;
	private GeoLocation location;

	public RandomTemperatureSensorClient(long defaultId, IHttpClient client, GeoLocation location) throws IOException {
		this(client, location);
		id = defaultId;
	}

	public RandomTemperatureSensorClient(IHttpClient client, GeoLocation location) throws IOException {
		this.location = location;
		this.client = client;
		client.setConnectTimeout(connectTimeout);
	}

	@Override
	public void run() {
		while (true) {
			connected = connect();
			while (connected) {
				updateServer();
				sleep(5000);
			}
			disconnect();
			sleep(1000);
		}
	}

	private boolean connect() {
		try {
			client.connect();
			System.out.println("Successfully connected to server!");
			return true;
		} catch (IOException ioe) {
			System.err.println("Could not connect to server!");
			return false;
		}
	}

	private void disconnect() {
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

	private void requestId() {
		IdRequestMessage msg = new IdRequestMessage();
		msg.setGeoLocation(location);
		postMessage(msg);
	}

	private void sendData() {
		DataPostMessage msg = new DataPostMessage();
		msg.setTemperature(new SecureRandom().nextDouble() * 40);
		msg.setSensorId(id);
		msg.setTimestamp(new Date());
		postMessage(msg);
	}

	private void postMessage(HttpMessage message) {
		try {
			String response = client.post(message);
			IMessageHandler.handleMessage(response, this);
		} catch (ConnectException ce) {
			System.err.println("Lost connection to server!");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			connected = false;
		}
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleMessage(DataResponseMessage msg) {
		System.out.println(id + " DataResponseMessage received!");
	}

	@Override
	public void handleMessage(IdResponseMessage msg) {
		id = msg.getId();
		System.out.println(id + " IdResponseMessage received!");
		System.out.println(msg.getId());
	}

	@Override
	public void handleMessage(ErrorResponseMessage msg) {
		System.err.println("Received ErrorResponseMessage from server: \r\n" + msg.getErrorDescription());
		connected = false;
	}

	@Override
	public void handleMessage(IdRequestMessage msg) {
		System.out.println("IdRequestMessage received!");
	}

	@Override
	public void handleMessage(DataPostMessage msg) {
		System.out.println(id + " DataPostMessage received!");
	}

}
