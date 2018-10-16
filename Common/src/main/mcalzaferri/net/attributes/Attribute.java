package mcalzaferri.net.attributes;

import mcalzaferri.net.ITransferable;

/**
 * Attribute
 */
public abstract class Attribute implements ITransferable {
    private String attributeName;

    public Attribute(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }

}