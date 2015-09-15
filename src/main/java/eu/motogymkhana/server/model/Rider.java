package eu.motogymkhana.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.ui.Constants;

public class Rider {

	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String RIDER_NUMBER = "number";
	public static final String DAY_RIDER = "day_rider";
	public static final String POINTS = "points";
	public static final String ID = "_id";
	public static final String GENDER = "gender";
	public static final String DATE_OF_BIRTH = "dob";
	public static final String COUNTRY = "country";
	public static final String BIB = "bib";
	public static final String TEXT = "text";
	public static final String TIMES = "times";
	private static final String TIMESTAMP = "timestamp";


	@JsonIgnore
	private int _id;

	@JsonProperty(TIMESTAMP)
	private long timeStamp;

	@JsonProperty(FIRSTNAME)
	private String firstName;

	@JsonProperty(LASTNAME)
	private String lastName;

	@JsonProperty(RIDER_NUMBER)
	private int riderNumber;

	@JsonProperty(DAY_RIDER)
	private boolean dayRider;

	@JsonProperty(GENDER)
	private Gender gender;

	@JsonProperty(DATE_OF_BIRTH)
	private String dateOfBirth;

	@JsonProperty(COUNTRY)
	private Country country;

	@JsonProperty(BIB)
	private Bib bib;

	@JsonProperty(TEXT)
	private String text;

	@JsonProperty(TIMES)
	private Set<Times> timesList = new HashSet<Times>();

	@JsonIgnore
	private List<Times> sortedTimesList = new ArrayList<Times>();

	private long date;

	private volatile int position;

	private Integer totalPoints;

	public Rider() {

	}

	public Rider(String riderString) {

		if (riderString.length() > 0) {

			riderString = riderString.replaceAll("\t", " ");
			riderString = riderString.replaceAll("  ", " ");
			String[] splitString = riderString.split(" ");

			if (splitString.length > 0) {
				firstName = splitString[1];

				if (splitString.length > 1) {
					lastName = splitString[2];
				}
			}
			for (int i = 3; i < splitString.length; i++) {
				lastName = lastName + " " + splitString[i];
			}

			riderNumber = Integer.parseInt(splitString[0]);
		}
	}

	public Rider(int number, String firstName, String lastName) {
		riderNumber = number;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@JsonIgnore
	public String getBibColor() {
		switch (bib) {
		case G:
			return "#34d561";
		case R:
			return "#ff0404";
		default:
			return "#f5fb51";
		}
	}

	public int getStartNumber() {
		return getTimes(date).getStartNumber();
	}

	public String points(int i) {

		if (sortedTimesList.size() == 0) {
			createSortedTimesList();
		}

		if (i < sortedTimesList.size()) {
			int points = sortedTimesList.get(i).getPoints();
			return points == 0 ? "" : Integer.toString(points);
		} else {
			return "";
		}
	}

	private void createSortedTimesList() {

		sortedTimesList.addAll(timesList);

		Collections.sort(sortedTimesList, new Comparator<Times>() {

			@Override
			public int compare(Times lhs, Times rhs) {
				return (lhs.getDate() - rhs.getDate() < 0 ? -1 : +1);
			}

		});
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public int getRiderNumber() {
		return riderNumber;
	}

	public String getBibString() {
		if (bib == null) {
			bib = Bib.Y;
		}
		return bib.toString();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRiderNumberString() {
		return Integer.toString(riderNumber);
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public Times getTimes(long date) {

		if (timesList != null) {

			Iterator<Times> iterator = timesList.iterator();

			while (iterator.hasNext()) {
				Times t = iterator.next();
				if (date == t.getDate()) {
					return t;
				}
			}
		}

		Times times = new Times(date);
		timesList.add(times);

		return times;
	}

	public Collection<Times> getTimes() {
		return timesList;
	}

	public boolean hasEUTimes() {

		Times euTimes = getEUTimes();
		return euTimes != null && !(euTimes.isDisqualified1() && euTimes.isDisqualified2())
				&& euTimes.getBestTime() > 0;
	}

	private Times getEUTimes() {

		Iterator<Times> iterator = timesList.iterator();

		while (iterator.hasNext()) {
			Times t = iterator.next();
			if (t.getDate() == date) {
				return t;
			}
		}

		return null;
	}

	public String getTime1() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			if (euTimes.isDisqualified1()) {
				return "X";
			} else {
				return euTimes.getTime1String();
			}
		} else {
			return "";
		}
	}

	public String getTime2() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			if (euTimes.isDisqualified2()) {
				return "X";
			} else {
				return euTimes.getTime2String();
			}
		} else {
			return "";
		}
	}

	public String getPenalty1() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			if (euTimes.getPenalties1() == 0) {
				return "";
			} else {
				return Integer.toString(euTimes.getPenalties1());
			}
		} else {
			return "";
		}
	}

	public String getPenalty2() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			if (euTimes.getPenalties2() == 0) {
				return "";
			} else {
				return Integer.toString(euTimes.getPenalties2());
			}
		} else {
			return "";
		}
	}

	public String getBestTimeString() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getBestTimeString();
		} else {
			return "";
		}
	}

	public int getBestTime() {

		if (getEUTimes() != null) {
			return getEUTimes().getBestTime();
		} else {
			return 0;
		}

	}

	public void addTimes(Times times) {
		timesList.add(times);
	}

	public int getTotalPoints() {

		if (totalPoints == null) {

			List<Integer> totalPointsList = new ArrayList<Integer>();

			for (Times times : timesList) {
				totalPointsList.add(times.getPoints());
			}

			Collections.sort(totalPointsList);

			while (totalPointsList.size() > Constants.roundsCountingForSeasonResult) {
				totalPointsList.remove(0);
			}

			totalPoints = 0;

			for (Integer i : totalPointsList) {
				totalPoints += i;
			}
		}

		return totalPoints;
	}

	public String getTotalPointsString() {
		return Integer.toString(getTotalPoints());
	}

	public boolean hasTimes() {
		return timesList != null && timesList.size() > 0;
	}

	public int getFirstTimeForSort(long date) {

		Times times = getTimes(date);

		return times.getPenalties1() + (times.getTime1() != 0 ? times.getTime1() : 360000);
	}

	public boolean isDayRider() {
		return dayRider;
	}

	public void setDayRider(boolean dayRider) {
		this.dayRider = dayRider;
	}

	public boolean isValid() {
		return firstName != null && firstName.length() > 0 && lastName != null
				&& lastName.length() > 0 && riderNumber > 0;
	}

	@Override
	public boolean equals(Object other) {

		if (other != null && other instanceof Rider) {

			Rider otherRider = (Rider) other;
			return otherRider.getRiderNumber() == riderNumber;

		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder().append(riderNumber).append(firstName).append(lastName)
				.toHashCode();
	}

	public void setPosition(int i) {
		position = i;
	}

	public int getPosition() {
		return position;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void merge(Rider rider) {

		firstName = rider.getFirstName();
		lastName = rider.getLastName();
		riderNumber = rider.getRiderNumber();
		dayRider = rider.isDayRider();

		for (Times tRider : rider.getTimes()) {

			for (Times t : timesList) {

				if (t.getDate() == tRider.getDate()) {
					t.merge(tRider);
					break;
				}

				timesList.add(tRider);
			}
		}
	}

	@Override
	public String toString() {
		return Integer.toString(riderNumber) + " " + firstName + " " + lastName + getTime1() + " "
				+ getTime2();
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isRegistered() {

		Times times = getEUTimes();
		return times != null && times.isRegistered();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
