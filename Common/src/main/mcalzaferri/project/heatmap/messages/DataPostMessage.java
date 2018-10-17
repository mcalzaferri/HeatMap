package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.attributes.DoubleAttribute;
import mcalzaferri.net.http.HttpMessage;

public class DataPostMessage extends HttpMessage{
    public static final String messageType = "DataPostMessage";
    private DoubleAttribute temperatureAttribute;
    
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

    @Override
    protected void initAttributes() {
        temperatureAttribute = new DoubleAttribute("temperature");
        putAttribute(temperatureAttribute);
    }
}