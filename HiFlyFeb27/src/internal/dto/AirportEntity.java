package internal.dto;

public class AirportEntity {

	String airportCode;
	String airportType;
	String city;

	String airportName;
	String latitude;
	String longitude;
	String gpsCode;
	String iataCode;
	String localCode;

	public String getAirportCode() {
		return airportCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAirportType() {
		return airportType;
	}

	public void setAirportType(String airportType) {
		this.airportType = airportType;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getGpsCode() {
		return gpsCode;
	}

	public void setGpsCode(String gpsCode) {
		this.gpsCode = gpsCode;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

}
