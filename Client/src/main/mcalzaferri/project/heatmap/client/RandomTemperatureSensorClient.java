package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.security.SecureRandom;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpMessage;
import mcalzaferri.net.https.HttpsClient;
import mcalzaferri.project.heatmap.messages.*;

public class RandomTemperatureSensorClient implements Runnable, IMessageHandler {
	public static final int connectTimeout = 5000; // ms
	private HttpsClient client;
	private String id;
	private boolean connected;
	private GeoLocation location;

	public RandomTemperatureSensorClient(String defaultId, String server, GeoLocation location) throws IOException {
		this(server, location);
		id = defaultId;
	}

	public RandomTemperatureSensorClient(String server, GeoLocation location) throws IOException {
		this.location = location;
		client = new HttpsClient(server);
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
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	private void disconnect() {
		client.disconnect();
		connected = false;
	}

	private void updateServer() {
		if (id == null) {
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
		msg.setTemperature(new SecureRandom().nextDouble());
		postMessage(msg);
	}

	private void postMessage(HttpMessage message) {
		try {
			String response = client.post(message);
			IMessageHandler.handleMessage(response, this);
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
	}

	@Override
	public void handleMessage(IdResponseMessage msg) {
		id = msg.getId();
	}

	@Override
	public void handleMessage(ErrorResponseMessage msg) {
		System.err.println("Received ErrorResponseMessage from server: \r\n" + msg.getErrorDescription());
		connected = false;
	}

	@Override
	public void handleMessage(IdRequestMessage msg) {
	}

	@Override
	public void handleMessage(DataPostMessage msg) {
	}

}
