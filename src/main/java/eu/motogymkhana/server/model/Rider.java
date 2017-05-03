/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.web.impl.RidersServiceImpl;

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
	public static final String TIMESTAMP = "timestamp";
	public static final String SEASON = "season";
	public static final String BIKE = "bike";
	public static final String IMAGE_URL = "image_url";
	public static final String BIKE_IMAGE_URL = "bike_image_url";
	public static final String NATIONALITY = "nationality";
	public static final String REGISTRATION = "registration";
	public static final String ANDROID_ID = "android_id";
	public static final String HIDE_LASTNAME = "hide_lastname";

	@JsonProperty(ID)
	private int _id;

	@JsonProperty(ANDROID_ID)
	private String androidId;

	@JsonProperty(SEASON)
	private int season;

	@JsonProperty(NATIONALITY)
	private Country nationality;

	@JsonProperty(BIKE)
	private String bike;

	@JsonProperty(IMAGE_URL)
	private String imageUrl;

	@JsonProperty(BIKE_IMAGE_URL)
	private String bikeImageUrl;

	@JsonProperty(TIMESTAMP)
	private long timeStamp;

	@JsonProperty(FIRSTNAME)
	private String firstName;

	@JsonProperty(HIDE_LASTNAME)
	private boolean hideLastname = false;

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

	@JsonProperty(REGISTRATION)
	@JsonManagedReference
	protected Set<Registration> registrations = new HashSet<Registration>();

	@JsonIgnore
	private List<Times> sortedTimesList = new ArrayList<Times>();

	@JsonIgnore
	private long date;

	@JsonIgnore
	private volatile int position;

	@JsonIgnore
	public Integer totalPoints;

	@JsonIgnore
	private Bib newBibColor = Bib.Y;

	@JsonIgnore
	private HashMap<Integer, String> sortedBibList = new HashMap<Integer, String>();

	private static final Log log = LogFactory.getLog(RidersServiceImpl.class);

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

		Registration registration = getRegistration();

		if (registration != null && registration.getBib() != null) {
			switch (registration.getBib()) {
			case B:
				return "#00ccff";
			case G:
				return "#34d561";
			case R:
				return "#ff0404";
			default:
				return "#f5fb51";
			}
		} else {
			return "#f5fb51";
		}
	}

	private Registration getRegistration() {
		for (Registration registration : registrations) {
			if (registration.getCountry() == Constants.country
					&& registration.getSeason() == Constants.season) {
				return registration;
			}
		}
		return new Registration();
	}

	@JsonIgnore
	public String getNewBibColor() {

		switch (newBibColor) {
		case G:
			return "#34d561";
		case B:
			return "#00ccff";
		case R:
			return "#ff0404";
		default:
			return "#000000";
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

	public String newBibColor(int i) {

		if (sortedTimesList.size() == 0) {
			createSortedTimesList();
		}
		if (i < sortedTimesList.size()) {
			String color = sortedTimesList.get(i).getNewBibColor();
			return color;
		} else {
			return "#000000";
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

		return null;
	}

	public Collection<Times> getTimes() {
		return timesList;
	}

	public boolean hasEUTimes() {

		Times euTimes = getEUTimes();
		return euTimes != null && euTimes.isRegistered()
				&& !(euTimes.isDisqualified1() && euTimes.isDisqualified2())
				&& euTimes.getBestTime() > 0;
	}

	public Times getEUTimes() {

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

	public int getTotalPoints(int roundsCountingForSeasonResult) {

		if (totalPoints == null) {

			List<Integer> totalPointsList = new ArrayList<Integer>();

			for (Times times : timesList) {
				totalPointsList.add(times.getPoints());
			}

			Collections.sort(totalPointsList);

			while (totalPointsList.size() > roundsCountingForSeasonResult) {
				totalPointsList.remove(0);
			}

			totalPoints = 0;

			for (Integer i : totalPointsList) {
				totalPoints += i;
			}
		}

		return totalPoints;
	}

	public String getTotalPointsString(int roundsCountingForSeasonResult) {
		return Integer.toString(getTotalPoints(roundsCountingForSeasonResult));
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
		if (rider.hasText()) {
			text = rider.getText();
		}
		if (rider.hasBike()) {
			bike = rider.getBike();
		}

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

	private boolean hasText() {
		return text != null;
	}

	private boolean hasBike() {
		return bike != null;
	}

	@Override
	public String toString() {
		return Integer.toString(_id) + " " + firstName + " " + lastName + " " + getTime1() + " "
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

	public int getSeason() {
		return season;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getText() {
		return text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getBikeImageUrl() {
		return bikeImageUrl;
	}

	public String getBike() {
		return bike;
	}

	public Country getNationality() {
		return nationality;
	}

	public void setBibPointsColor(long bestTime, Settings settings) {
		long myBestTime = getBestTime();
		if (myBestTime <= (bestTime * settings.getPercentageBlue()) / 100) {
			newBibColor = Bib.B;
		} else if (myBestTime <= (bestTime * settings.getPercentageGreen()) / 100) {
			newBibColor = Bib.G;
		}
	}

	public void setImageUrl(String string) {
		imageUrl = string;
	}

	public void setBikeImageUrl(String string) {
		bikeImageUrl = string;
	}

	public void setText(String riderText) {
		this.text = riderText;
	}

	public void setBike(String riderBike) {
		this.bike = riderBike;
	}

	public boolean isHideLastName() {
		return hideLastname;
	}

	public void hideLastName() {
		this.lastName = "";
	}
}
