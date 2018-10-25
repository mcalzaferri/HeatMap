import java.util.Date;

import com.google.gson.*;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.CompleteSensor;
import mcalzaferri.project.heatmap.common.entities.IdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.SensorData;


public class TestCommon {
	public static void main(String[] args) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		SensorData[] data = new SensorData[2];
		data[0] = new SensorData(30.0,new Date());
		data[1] = new SensorData(31.0,new Date());
		CompleteSensor sensor = new CompleteSensor(new GeoLocation(1.234, 2.565), 
				1234, data);
		String value = gson.toJson(sensor);
		System.out.println(gson.toJson(sensor));
		Object sensor2 = gson.fromJson(value, Object.class);
		System.err.println(gson.toJson(sensor2));
	}
}
