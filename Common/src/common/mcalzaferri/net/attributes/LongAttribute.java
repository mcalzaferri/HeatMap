package mcalzaferri.net.attributes;

public class LongAttribute extends Attribute {
    public long value;

    public LongAttribute(String attributeName) {
        super(attributeName);
    }

    public LongAttribute(String attributeName, long value) {
        this(attributeName);
        this.value = value;
    }

    @Override
    public String encode() {
        return Long.toString(value);
    }

    @Override
    public void decode(String data) {
        value = Long.parseLong(data);
    }

}