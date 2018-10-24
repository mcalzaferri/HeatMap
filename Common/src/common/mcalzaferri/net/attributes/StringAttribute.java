package mcalzaferri.net.attributes;

/**
 * GeoLocationAttribute
 */
public class StringAttribute extends Attribute {
    public String value;
    public StringAttribute(String attributeName){
        super(attributeName);
    }
    public StringAttribute(String attributeName, String value){
        this(attributeName);
        this.value = value;
    }
    @Override
    public String encode() {
        return value;
    }

    @Override
    public void decode(String data) {
        this.value = data;
    }

    
}