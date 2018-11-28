package mcalzaferri.geo;

public class IdentifiedCountryCapital extends CountryCapital{
	private long id;
	
	public IdentifiedCountryCapital(CountryCapital capital, long id) {
		this.CapitalLatitude = capital.CapitalLatitude;
		this.CapitalLongitude = capital.CapitalLongitude;
		this.CapitalName = capital.CapitalName;
		this.ContinentName = capital.ContinentName;
		this.CountryName = capital.CountryName;
		this.CountryCode = capital.CountryCode;
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
