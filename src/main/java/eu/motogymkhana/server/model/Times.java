package eu.motogymkhana.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.ui.Constants;

public class Times {

	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String RIDER_NUMBER = "number";
	public static final String ID = "_id";
	public static final String START_NUMBER = "startnumber";
	public static final String TIME1 = "time1";
	public static final String TIME2 = "time2";
	public static final String BEST_TIME = "best_time";
	public static final String REGISTERED = "registered";
	public static final String DATE = "date";;
	public static final String RIDER = "rider_id";
	private static final String PENALTIES1 = "pen1";
	private static final String PENALTIES2 = "pen2";
	private static final String DISQUALIFIED1 = "dis1";
	private static final String DISQUALIFIED2 = "dis2";
	private static final String TIMESTAMP = "timestamp";

	@JsonIgnore
	private volatile int _id;


	@JsonProperty(TIMESTAMP)
	private long timeStamp;

	@JsonIgnore
	private Rider rider;

	@JsonProperty(DATE)
	private Long date;

	@JsonProperty(START_NUMBER)
	private Integer startNumber;

	@JsonProperty(TIME1)
	private Integer time1 = 0;

	@JsonProperty(TIME2)
	private Integer time2 = 0;

	@JsonProperty(PENALTIES1)
	private Integer penalties1 = 0;

	@JsonProperty(PENALTIES2)
	private Integer penalties2 = 0;

	@JsonProperty(DISQUALIFIED1)
	private boolean disqualified1;

	@JsonProperty(DISQUALIFIED2)
	private boolean disqualified2;

	@JsonProperty(BEST_TIME)
	private Integer bestTime = 0;

	@JsonProperty(REGISTERED)
	private boolean registered = true;

	private int points = 0;

	public Times() {
	}

	public Times(long date) {
		this.date = date;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public void setStartNumber(int number) {
		this.startNumber = number;
	}

	public Integer getStartNumber() {
		return this.startNumber;
	}

	public String getStartNumberString() {
		return Integer.toString(startNumber);
	}

	public int getTime1() {
		return time1;
	}

	public int getTime2() {
		return time2;
	}

	public void setTime1(int milliseconds) {
		time1 = milliseconds;
	}

	public void setTime2(int milliseconds) {
		time2 = milliseconds;
	}

	public int getBestTime() {

		int t1 = time1 + (1000 * penalties1);
		int t2 = time2 + (1000 * penalties2);

		if (!disqualified2 && t2 != 0 && (t2 < t1 || t1 == 0 || disqualified1)) {
			bestTime = t2;
			
		} else if (!disqualified1) {
			bestTime = t1;
			
		} else {
			bestTime = 0;
		}

		return bestTime;
	}

	public String getTime1PlusPenaltiesString() {
		return makeString(time1 + (penalties1 * 1000));
	}

	public String getTime2PlusPenaltiesString() {
		return makeString(time2 + (penalties2 * 1000));
	}

	public String getTime1String() {
		return makeString(time1);
	}

	public String getTime2String() {
		return makeString(time2);
	}

	public String getBestTimeString() {
		return makeString(getBestTime());
	}

	private String makeString(int time) {

		if (time == 0) {
			return "";

		} else {

			int minutes = time / 60000;
			int milliseconds = time - (minutes * 60000);
			int seconds = milliseconds / 1000;
			int centiseconds = (milliseconds - (seconds * 1000)) / 10;

			String secondsString = Integer.toString(seconds);
			String centiSecondsString = Integer.toString(centiseconds);

			return Integer.toString(minutes) + ":" + (secondsString.length() == 1 ? "0" : "")
					+ secondsString + "." + (centiSecondsString.length() == 1 ? "0" : "")
					+ centiSecondsString;
		}
	}

	public void setRegistered(boolean isChecked) {
		registered = isChecked;
	}

	public boolean isRegistered() {
		return registered;
	}

	public long getDate() {
		return date;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public Rider getRider() {
		return rider;
	}

	public String getPenalties1String() {
		return penalties1 == 0 ? "" : Integer.toString(penalties1);
	}

	public String getPenalties2String() {
		return penalties2 == 0 ? "" : Integer.toString(penalties2);
	}

	public void setPenalties1String(String string) {
		if (string.length() > 0) {
			penalties1 = Integer.parseInt(string);
		}
	}

	public void setPenalties2String(String string) {
		if (string.length() > 0) {
			penalties2 = Integer.parseInt(string);
		}
	}

	public void setDisqualified1(boolean checked) {
		disqualified1 = checked;
	}

	public void setDisqualified2(boolean checked) {
		disqualified2 = checked;
	}

	public boolean isDisqualified1() {
		return disqualified1;
	}

	public boolean isDisqualified2() {
		return disqualified2;
	}

	public int getPenalties1() {
		return penalties1;
	}

	public int getPenalties2() {
		return penalties2;
	}

	public void setPoints(int p) {
		points = p;
	}

	public int getPoints() {
		return points;
	}

	public void setPenalties1(int penalties1) {
		this.penalties1 = penalties1;
	}

	public void setPenalties2(int penalties2) {
		this.penalties2 = penalties2;
	}

	public void merge(Times times) {

		time1 = times.getTime1();
		time2 = times.getTime2();
		penalties1 = times.getPenalties1();
		penalties2 = times.getPenalties2();
		points = times.getPoints();
		date = times.getDate();
		disqualified1 = times.isDisqualified1();
		disqualified2 = times.isDisqualified2();
	}
	
	@Override
	public String toString(){
		return Constants.dateFormat.format(date);
	}
}
