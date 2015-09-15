package eu.motogymkhana.server.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.ui.Constants;

public class Round {

	private static final String ID = "id";
	public static final String DATE = "date";
	private static final String SORTED = "sorted";
	public static final String NUMBER = "number";
	private static final String TIMESTAMP = "timestamp";
	private static final String CURRENT = "current";
	private static final String COUNTRY = "country";

	@JsonIgnore
	private int _id;

	@JsonProperty(NUMBER)
	private int number;

	@JsonProperty(TIMESTAMP)
	private long timeStamp;

	@JsonProperty(CURRENT)
	private boolean current;

	@JsonProperty(COUNTRY)
	private Country country;

	private long date;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	public Round() {

	}

	public Round(long date) {
		this.date = date;
	}

	public long getDate() {
		return date;
	}
	
	public String getDateString(){
		return Constants.dateFormat.format(date);
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public int getNumber() {
		return number;
	}

	public boolean isCurrent() {
		return current;
	}

	public String toString() {
		return "Round " + number + " on " + dateFormat.format(date);
	}

	public boolean equals(Object other) {

		if (!(other instanceof Round)) {
			return false;
		}

		return ((Round) other).getNumber() == number;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(number).hashCode();
	}
}
