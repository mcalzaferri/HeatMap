package mcalzaferri.net.http;

import java.util.HashMap;
import java.util.Map;

import mcalzaferri.net.ITransferable;

public class HttpKeyValueString implements ITransferable{
    public static final String pairSeparator = "&";
    public static final String valueAllocator = "=";
    private Map<String, String> attributes;
    private StringBuilder sb;

    public HttpKeyValueString() {
        attributes = new HashMap<>();
    }

    public HttpKeyValueString(String data) {
        this();
        decode(data);
    }

    @Override
    public void decode(String data) {
        attributes.clear();
        appendData(data);
    }
    public void appendData(String data){
        String[] pairs = data.split(pairSeparator);
        if (pairs.length > 0) {
            for (String pair : pairs) {
                putAttribute(pair);
            }
        } else {
            throw new IllegalArgumentException(
                    "Cant decode data because of illegal argument: " + data + "(Argument has no pairs)");
        }
    }

    private void putAttribute(String pair) {
        String[] values = pair.split(valueAllocator);
        if (values.length == 2) {
            putAttribute(values[0], values[1]);
        } else {
            throw new IllegalArgumentException(
                    "Cant decode pair because of illegal argument: " + pair + "(Attribute is not of type key=value");
        }
    }

    public void putAttribute(String key, String value) {
        attributes.put(key, value);
    }

    @Override
    public String encode() {
        sb = new StringBuilder();
        appendAttributes();
        return sb.toString();
    }

    private void appendAttributes() {
        boolean first = true;
        for (String key : attributes.keySet()) {
            if (!first)
                sb.append(pairSeparator);
            appendAttribute(key);
            first = false;
        }
    }

    private void appendAttribute(String attribute) {
        sb.append(attribute);
        sb.append(valueAllocator);
        sb.append(attributes.get(attribute));
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}