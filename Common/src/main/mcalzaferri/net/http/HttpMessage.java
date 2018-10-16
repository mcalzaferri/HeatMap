package mcalzaferri.net.http;

import java.util.HashMap;
import java.util.Map;

import mcalzaferri.net.ITransferable;
import mcalzaferri.net.attributes.Attribute;


public abstract class HttpMessage implements ITransferable {
	public static final String typeKey = "messageType";
	private String messageType;
	protected Map<String,Attribute> attributes; 

	public HttpMessage(String messageType, String data){
		this(messageType);
		decode(data);
	}

	public HttpMessage(String messageType){
		attributes = new HashMap<String,Attribute>();
		setMessageType(messageType);
		initAttributes();
	}

	protected abstract void initAttributes();

	private void setMessageType(String messageType) {
		if (messageType == null || messageType.isEmpty()) {
			throw new IllegalArgumentException("Data does not contain a message type");
		} else {
			this.messageType = messageType;
		}
	}

	public String getMessageType() {
		return messageType;
	}

	protected Attribute getAttribute(String key){
		return attributes.get(key);
	}

	protected void putAttribute(Attribute attribute){
		attributes.put(attribute.getAttributeName(), attribute);
	}

	@Override
	public final String encode() {
		HttpKeyValueString data = new HttpKeyValueString();
		data.putAttribute(typeKey, messageType);
		for(String key : attributes.keySet()){
			data.putAttribute(key, attributes.get(key).encode());
		}
		return data.toString();
	}

	@Override
	public final void decode(String data) {
		HttpKeyValueString message = new HttpKeyValueString(data);
		Map<String,String> attributes = message.getAttributes();
		for(String key : attributes.keySet()){
			if(key.equals(typeKey)){
				if(!attributes.get(key).equals(messageType)){
					throw new IllegalArgumentException("Message type of received data does not match. Expected: " + messageType + " Received: " + attributes.get(key));
				}
			}else{
				getAttribute(key).decode(attributes.get(key));
			}
		}
	}

	public static String DecodeMessageType(String message){
		HttpKeyValueString decodedMessage = new HttpKeyValueString(message);
		if(decodedMessage.getAttributes().containsKey(typeKey)){
			return decodedMessage.getAttributes().get(typeKey);
		}else{
			throw new IllegalArgumentException("Data does not contain a message type key. Message: " + message + " TypeKey: " + typeKey);
		}
		
	}
}
