import java.io.IOException;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.project.heatmap.client.SimulatedTemperatureSensorClient;

/**
 * Test
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
    	boolean runLocal = false;
    	boolean runOnThreads = true;
    	String host;
    	
    	//initialization
    	if(runLocal) {
    		host = "http://localhost:8080/api/sensors";
    	}else {
    		host = "https://heatmap-219120.appspot.com/api/sensors";
    	}
    	
    	//run
    	if(runOnThreads) {
        	for(int i = 0; i < 100; i++) {
        		Thread t = new Thread(new SimulatedTemperatureSensorClient(host, CapitalManager.getInstance()));
        		
        		t.start();
        		Thread.sleep(100);
        	}
    	}else {
    		SimulatedTemperatureSensorClient sensor = new SimulatedTemperatureSensorClient(host, CapitalManager.getInstance());
    		sensor.run();
    	}

    }
}