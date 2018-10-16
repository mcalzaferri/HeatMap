package mcalzaferri.project.heatmap.messages;

import mcalzaferri.net.http.HttpMessage;

public interface IMessageHandler {
    public static void handleMessage(String message, IMessageHandler handler){
        String messageType = HttpMessage.DecodeMessageType(message);
        switch (messageType) {
            case IdRequestMessage.messageType:
                handler.handleMessage(new IdRequestMessage(message));
                break;
            case IdResponseMessage.messageType:
                handler.handleMessage(new IdResponseMessage(message));
                break;
            case DataPostMessage.messageType:
                handler.handleMessage(new DataPostMessage(message));
                break;
            case DataResponseMessage.messageType:
                handler.handleMessage(new DataResponseMessage(message));
                break;
            default:
                throw new UnsupportedOperationException("Message of type: " + messageType + " is not yet implemented.");
            }
    }
    public void handleMessage(DataPostMessage msg);
    public void handleMessage(DataResponseMessage msg);
    public void handleMessage(IdRequestMessage msg);
    public void handleMessage(IdResponseMessage msg);
}