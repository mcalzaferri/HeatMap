import java.io.IOException;
import java.util.Random;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.client.RandomTemperatureSensorClient;

/**
 * Test
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread t = new Thread(new RandomTemperatureSensorClient("https://heatmap-219120.appspot.com/datapool", createRandomGeoLocation()));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
private static GeoLocation createRandomGeoLocation(){
    Random rm = new Random();
    return new GeoLocation(90.0 - rm.nextDouble() * 180.0,180.0 - rm.nextDouble() * 360.0);
}

}