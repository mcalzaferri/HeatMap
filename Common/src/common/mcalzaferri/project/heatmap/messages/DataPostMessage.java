package mcalzaferri.project.heatmap.messages;

import java.util.Date;

import mcalzaferri.net.attributes.DoubleAttribute;
import mcalzaferri.net.attributes.LongAttribute;
import mcalzaferri.net.attributes.TimestampAttribute;
import mcalzaferri.net.http.HttpMessage;

public class DataPostMessage extends HttpMessage{
    public static final String messageType = "DataPostMessage";
    private DoubleAttribute temperatureAttribute;
    private LongAttribute sensorIdAttribute;
    private TimestampAttribute timestampAttribute;
    
    
    public DataPostMessage(String data){
        super(messageType, data);

    }
    public DataPostMessage(){
        super(messageType);
    }

    public void setTemperature(double temperature){
        temperatureAttribute.value = temperature;
    }

    public double getTemperature(){
        return temperatureAttribute.value;
    }
    
    public void setSensorId(long sensorId) {
    	sensorIdAttribute.value = sensorId;
    }
    
    public long getSensorId() {
    	return sensorIdAttribute.value;
    }
    
    public void setTimestamp(Date timestamp) {
    	timestampAttribute.value = timestamp;
    }
    
    public Date getTimestamp() {
    	return timestampAttribute.value;
    }

    @Override
    protected void initAttributes() {
        temperatureAttribute = new DoubleAttribute("temperature");
        sensorIdAttribute = new LongAttribute("sensorid");
        timestampAttribute = new TimestampAttribute("timestamp");
        putAttribute(temperatureAttribute);
        putAttribute(sensorIdAttribute);
        putAttribute(timestampAttribute);
    }
}