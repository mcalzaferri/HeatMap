package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.attributes.LongAttribute;
import mcalzaferri.net.http.HttpMessage;

public class IdResponseMessage extends HttpMessage{
    public static final String messageType = "IdResponseMessage";
    private LongAttribute idAttribute;
    
    public IdResponseMessage(String data){
        super(messageType, data);

    }
    public IdResponseMessage(){
        super(messageType);
    }

    public void setId(long id){
        idAttribute.value = id;
    }

    public long getId(){
        return idAttribute.value;
    }

    @Override
    protected void initAttributes() {
        idAttribute = new LongAttribute("id");
        putAttribute(idAttribute);
    }
}