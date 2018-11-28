package mcalzaferri.geo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mcalzaferri.geo.CountryCapital;
import mcalzaferri.geo.IdentifiedCountryCapital;

public class CapitalManager {
	private static CapitalManager instance;
	private List<CountryCapital> capitals;
	private List<CountryCapital> usedCapitals;
	private List<IdentifiedCountryCapital> identifiedCapitals;
	private CapitalJsonEditor reader;
	
	private CapitalManager() {
		reader = new CapitalJsonEditor();
		usedCapitals = new ArrayList<>();
		try {
			capitals = reader.getCapitals();
			identifiedCapitals = reader.getIdentifiedCapitals();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public synchronized static CapitalManager getInstance() {
		if(instance == null) {
			instance = new CapitalManager();
		}
		return instance;
	}
	
	public synchronized CountryCapital getNextCapital() {
		CountryCapital unusedCapital = null;
		for(CountryCapital capital : capitals) {
			if(!usedCapitals.contains(capital)) {
				if(!identifiedCapitals.contains(capital)) {
					usedCapitals.add(capital);
					return capital;
				}else {
					unusedCapital = identifiedCapitals.get(identifiedCapitals.indexOf(capital));
				}
			}
		}
		return unusedCapital;
	}
	
	public synchronized void identifyCapital(CountryCapital capital, long id) {
		if(!identifiedCapitals.contains(capital)) {
			identifiedCapitals.add(new IdentifiedCountryCapital(capital, id));
		}
		reader.saveIdentifiedCapitals(identifiedCapitals);
	}
	
}
