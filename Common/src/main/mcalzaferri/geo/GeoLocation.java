package mcalzaferri.geo;

public class GeoLocation {
    public double latitude;
    public double longitude;

    public GeoLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }    
}