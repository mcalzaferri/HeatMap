package mcalzaferri.net.attributes;

import java.util.Date;

/**
 * TimestampAttribute
 */
public class TimestampAttribute extends Attribute {
    public Date value;

    public TimestampAttribute(String attributeName) {
        super(attributeName);
    }

    public TimestampAttribute(String attributeName, Date value) {
        this(attributeName);
        this.value = value;
    }

    @Override
    public String encode() {
        return Long.toString(value.getTime());
    }

    @Override
    public void decode(String data) {
    	value = new Date(Long.parseLong(data));
    }
}