package mcalzaferri.project.heatmap.messages;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.attributes.GeoLocationAttribute;
import mcalzaferri.net.http.HttpMessage;

public class IdRequestMessage extends HttpMessage{
    public static final String messageType = "IdRequestMessage";
    private GeoLocationAttribute geoLocationAttribute;
    
    public IdRequestMessage(String data){
        super(messageType, data);

    }
    public IdRequestMessage(){
        super(messageType);
    }

    public void setGeoLocationAttribute(GeoLocation geoLocation){
        geoLocationAttribute.value = geoLocation;
    }

    @Override
    protected void initAttributes() {
        geoLocationAttribute = new GeoLocationAttribute("location");
        putAttribute(geoLocationAttribute);
    }
}