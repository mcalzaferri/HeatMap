package mcalzaferri.net.attributes;

import mcalzaferri.geo.GeoLocation;

public class GeoLocationAttribute extends Attribute {
    public GeoLocation value;
    public GeoLocationAttribute(String attributeName){
        super(attributeName);
    }

    public GeoLocationAttribute(String attributeName, GeoLocation location){
        this(attributeName);
        value = location;
    }

    @Override
    public String toString() {
        return encode();
    }

    @Override
    public String encode() {
        return value.latitude + "," + value.longitude;
    }

    @Override
    public void decode(String data) {
        String[] values = data.split(",");
        if(values.length == 2){
            value.latitude = Double.parseDouble(values[0]);
            value.longitude = Double.parseDouble(values[1]);
        }else{
            throw new IllegalArgumentException("Argument is not of type (latitude,longitude)");
        }
    }
    
}