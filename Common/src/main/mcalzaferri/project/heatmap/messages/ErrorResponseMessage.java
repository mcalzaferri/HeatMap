package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.attributes.StringAttribute;
import mcalzaferri.net.http.HttpMessage;

public class ErrorResponseMessage extends HttpMessage{
    public static final String messageType = "ErrorResponseMessage";
    private StringAttribute errorDescriptionAttribute;
    
    public ErrorResponseMessage(String data){
        super(messageType, data);

    }
    public ErrorResponseMessage(){
        super(messageType);
    }

    public void setErrorDescription(String errorDescription){
        errorDescriptionAttribute.value = errorDescription;
    }
    public String getErrorDescription(){
        return errorDescriptionAttribute.value;
    }
    @Override
    protected void initAttributes() {
        errorDescriptionAttribute = new StringAttribute("errorDescription");
        putAttribute(errorDescriptionAttribute);
    }
}