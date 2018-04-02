package eu.motogymkhana.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Registration {

	public static final String ID = "_id";
	public static final String SEASON = "season";
	public static final String COUNTRY = "country";
	public static final String RIDER = "rider";
	public static final String NUMBER = "number";
	private static final String REGISTERED = "registered";
	private static final String BIB = "bib";
	private static final String DAY_RIDER = "day_rider";

	@JsonProperty(ID)
	private int _id;

	@JsonProperty(SEASON)
	protected int season;

	@JsonProperty(COUNTRY)
	protected Country country;

	@JsonProperty(NUMBER)
	protected int number;

	@JsonProperty(BIB)
	protected Bib bib;

	@JsonProperty(DAY_RIDER)
	protected boolean dayRider;

	@JsonBackReference
	protected Rider rider;

	@JsonProperty(REGISTERED)
	protected boolean registered;

	public Registration() {

	}

	public Registration(Rider rider, Country country, int season, int startNumber, Bib bib) {
		this.rider = rider;
		this.country = country;
		this.season = season;
		this.number = startNumber;
		this.bib = bib;
	}

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	public boolean isRegistered() {
		return registered;
	}

	public Rider getRider() {
		return rider;
	}

	public String toString() {
		return country.name() + " " + season + " " + number;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public int get_id() {
		return _id;
	}

	public void setDayRider(boolean dayRider) {
		this.dayRider = dayRider;
	}
	
	public boolean isDayRider(){
		return dayRider;
	}

	public Bib getBib() {
		return bib;
	}
}
