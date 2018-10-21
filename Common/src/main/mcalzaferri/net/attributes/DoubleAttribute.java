package mcalzaferri.net.attributes;
public class DoubleAttribute extends Attribute {
    public double value;

    public DoubleAttribute(String attributeName) {
        super(attributeName);
    }

    public DoubleAttribute(String attributeName, double value) {
        this(attributeName);
        this.value = value;
    }

    @Override
    public String encode() {
        return Double.toString(value);
    }

    @Override
    public void decode(String data) {
        value = Double.parseDouble(data);
    }

}