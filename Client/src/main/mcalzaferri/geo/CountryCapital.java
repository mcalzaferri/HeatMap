package mcalzaferri.geo;

public class CountryCapital {
	protected String CountryName;
	protected String CapitalName;
	protected String CapitalLatitude;
	protected String CapitalLongitude;
	protected String CountryCode;
	protected String ContinentName;
	
	public GeoLocation getLocation() {
		return new GeoLocation(Double.parseDouble(CapitalLatitude), Double.parseDouble(CapitalLongitude));
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CountryCapital) {
			return this.CapitalName.equals(((CountryCapital)obj).CapitalName);
		}else {
			return super.equals(obj);
		}
	}
}
