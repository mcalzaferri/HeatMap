package mcalzaferri.project.heatmap.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.LatLng;

import mcalzaferri.net.http.HttpMessage;
import mcalzaferri.project.heatmap.data.TemperatureDataStore;
import mcalzaferri.project.heatmap.data.objects.*;
import mcalzaferri.project.heatmap.messages.*;

@WebServlet(
	    name = "Datapool",
	    urlPatterns = {"/datapool"}
	)

public class DataPoolServlet extends HttpServlet implements IMessageHandler{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;
	private TemperatureDataStore dataStore;
	
	public DataPoolServlet() {
		dataStore = TemperatureDataStore.getInstance();
	}
	
	private HttpMessage responseMsg;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			responseMsg = new ErrorResponseMessage();
			((ErrorResponseMessage)responseMsg).setErrorDescription("Message not supported");
			IMessageHandler.handleMessage(sb.toString(), this);
			response.getWriter().write(responseMsg.encode());
		}catch(Exception e) {
			responseMsg = new ErrorResponseMessage();
			((ErrorResponseMessage)responseMsg).setErrorDescription(e.toString());
			response.getWriter().write(responseMsg.encode());
			e.printStackTrace(response.getWriter());
		}
		
	}

	@Override
	public void handleMessage(DataPostMessage msg) {
		responseMsg = new DataResponseMessage();
		if(dataStore.containsSensor(msg.getSensorId())) {
			TemperatureData data = new TemperatureData(msg.getSensorId(), Timestamp.of(msg.getTimestamp()), msg.getTemperature());
			dataStore.storeData(data);
		}else {
			throw new NoSuchElementException("The sensor with the id " + msg.getSensorId() + " does not exist");
		}
	}

	@Override
	public void handleMessage(DataResponseMessage msg) {
	}

	@Override
	public void handleMessage(IdRequestMessage msg) {
		responseMsg = new IdResponseMessage();
		TemperatureSensor sensor = new TemperatureSensor(LatLng.of(msg.getGeoLocation().latitude, msg.getGeoLocation().longitude));
		((IdResponseMessage)responseMsg).setId(dataStore.createSensor(sensor));
	}

	@Override
	public void handleMessage(IdResponseMessage msg) {

	}
	@Override
	public void handleMessage(ErrorResponseMessage msg) {
		
	}
}
