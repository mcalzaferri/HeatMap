import java.io.IOException;
import java.util.Random;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;
import mcalzaferri.project.heatmap.client.RandomTemperatureSensorClient;

/**
 * Test
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
    	boolean runLocal = true;
    	boolean runOnThreads = false;
    	String host;
    	HttpPostClient client;
    	
    	//initialization
    	if(runLocal) {
    		host = "http://localhost:8080/datapool";
    		client = new HttpPostClient(host);
    	}else {
    		host = "https://heatmap-219120.appspot.com/datapool";
    		client = new HttpPostClient(host);
    	}
    	
    	//run
    	if(runOnThreads) {
        	for(int i = 0; i < 50; i++) {
        		Thread t = new Thread(new RandomTemperatureSensorClient(client, createRandomGeoLocation()));
        		
        		t.start();
        		Thread.sleep(100);
        	}
    	}else {
    		RandomTemperatureSensorClient sensor = new RandomTemperatureSensorClient(client, createRandomGeoLocation());
    		sensor.run();
    	}

    }
private static GeoLocation createRandomGeoLocation(){
    Random rm = new Random();
    return new GeoLocation(90.0 - rm.nextDouble() * 180.0,180.0 - rm.nextDouble() * 360.0);
}

}