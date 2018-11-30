package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

@SuppressWarnings("deprecation")
public class SimulatedTemperatureSensorClient extends TemperatureSensorClient{
	

	private static final Date endDate = new Date(117, 11, 1);
	private Random rm = new Random();

	public SimulatedTemperatureSensorClient(String host, CapitalManager manager) throws IOException {
		super(host, manager);
	}

	@Override
	public TemperatureSensorData getTemperatureSensorData(GeoLocation location) {
		Date date = getRandomDate();
		return new TemperatureSensorData(getTemperature(location, date), date);
	}
	
	private Date getRandomDate() {
		Date now = new Date();
		long time = now.getTime() - (long)(((double)(now.getTime() - endDate.getTime())) * rm.nextDouble());
		System.out.println(time);
		return new Date(time);
	}
	
	public double getTemperature(GeoLocation location, Date date) {
		double temperatureOffset = getAverageYearTemperature(location);
		temperatureOffset += (getWinterSummerDifference(location) / 2) * getScaledSinValue(getDayOfYear(date), 365.0, 135.0);
		return getTemperature(temperatureOffset, location, date);
	}
	
	private double getTemperature(double dayOffset, GeoLocation location, Date date) {
		double hourOfDay = date.getHours() + (double)date.getMinutes() / 60.0 + (double)date.getSeconds() / 3600.0;
		return dayOffset + (getDayNightDifference(location) / 2) * getScaledSinValue(hourOfDay, 24.0, 9.0);
	}
	
	private double getAverageYearTemperature(GeoLocation location) {
		return getScaledValue(location, 30.0, -25.0, true, false);
	}
	
	private double getWinterSummerDifference(GeoLocation location) {
		return getScaledValue(location, 0.0, 40.0, false, true);
	}
	
	private double getDayNightDifference(GeoLocation location) {
		return 10.0;
	}
	
	private double getLinearLatitudeFactor(GeoLocation location) {
		return Math.abs(location.getLatitude() / 90.0);
	}
	
	private double getSinLatitudeFactor(GeoLocation location) {
		return 1 - Math.abs(getScaledSinValue(location.getLatitude() * 0.9, 360.0, -90.0));
	}
	
	private double getScaledValue(GeoLocation location,double valueAtEquator, double valueAtPole, boolean abs, boolean linear) {
		double value;
		if(linear)
			value = valueAtEquator - (valueAtEquator - valueAtPole) * getLinearLatitudeFactor(location);
		else
			value = valueAtEquator - (valueAtEquator - valueAtPole) * getSinLatitudeFactor(location);
		if(abs)
			return value;
		else
			return Math.signum(location.getLatitude()) * value;
	}
	
	private double getScaledSinValue(double x, double xMax, double xZero) {
		double angle = 2 * Math.PI * ((x - xZero) / xMax);
		return Math.sin(angle);
	}
	
	private double getDayOfYear(Date date) {
		double day = 0;
		switch(date.getMonth()) {
		case 11:
			day += 30.0;
		case 10:
			day += 31.0;
		case 9:
			day += 30.0;
		case 8:
			day += 31.0;
		case 7:
			day += 31.0;
		case 6:
			day += 30.0;
		case 5:
			day += 31.0;
		case 4:
			day += 30.0;
		case 3:
			day += 31.0;
		case 2:
			day += 28.0;
		case 1:
			day += 31.0;
			break;
		default:
			break;
		}
		day += date.getDate() - 1;
		return day;
	}
}
