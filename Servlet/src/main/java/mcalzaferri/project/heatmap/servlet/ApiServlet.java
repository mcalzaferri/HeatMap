package mcalzaferri.project.heatmap.servlet;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;
import mcalzaferri.project.heatmap.data.SensorDataStore;

@WebServlet(
	    name = "Api",
	    urlPatterns = {"/api/*"}
	)
@SuppressWarnings("unused")
public class ApiServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4406189919961725742L;

	private SensorDataStore dataStore;
	
	public ApiServlet() {
		dataStore = SensorDataStore.getDefaultInstance();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(request.getRequestURI().toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		StringBuilder sb = new StringBuilder();
		SecureRandom rm = new SecureRandom();
		response.setContentType("text/plain");
		try {
			long sensorId = dataStore.getWriter().createSensor(new NotIdentifiedSensor(new GeoLocation(90 * rm.nextDouble(), 90*rm.nextDouble())));
			sb.append("Created sensor: " + sensorId + "\r\n");
			for(int i = 0; i < 10; i++) {
				long dataId = dataStore.getWriter().storeSensorData(new TemperatureSensorData(rm.nextDouble()*30, new Date()), sensorId);
				sb.append("Created data: " + dataId + "\r\n");
			}
			response.getWriter().write(sb.toString());
		}catch(Exception e) {
			e.printStackTrace(response.getWriter());
		}
		*/
		
		
		response.getWriter().write("URI = " + request.getRequestURI().toString() + " Query = " + request.getQueryString());
	}
}
