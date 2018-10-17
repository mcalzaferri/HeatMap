package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.attributes.StringAttribute;
import mcalzaferri.net.http.HttpMessage;

public class IdResponseMessage extends HttpMessage{
    public static final String messageType = "IdResponseMessage";
    private StringAttribute idAttribute;
    
    public IdResponseMessage(String data){
        super(messageType, data);

    }
    public IdResponseMessage(){
        super(messageType);
    }

    public void setId(String id){
        idAttribute.value = id;
    }

    public String getId(){
        return idAttribute.value;
    }

    @Override
    protected void initAttributes() {
        idAttribute = new StringAttribute("id");
        putAttribute(idAttribute);
    }
}