package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.http.HttpMessage;

public class DataResponseMessage extends HttpMessage{
    public static final String messageType = "DataResponseMessage";
    
    public DataResponseMessage(String data){
        super(messageType, data);

    }
    public DataResponseMessage(){
        super(messageType);
    }

    @Override
    protected void initAttributes() {
        //No Attributes
    }
}