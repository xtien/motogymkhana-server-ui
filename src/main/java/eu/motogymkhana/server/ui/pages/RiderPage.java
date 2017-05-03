/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.web.local.RidersServiceLocal;

public class RiderPage {

	private int riderNumber;

	@Property
	private String text = "";

	@Property
	private String title = Constants.PROFILE_TITLE;

	@Property
	private String message;

	@Property
	private String resultCode;

	@Property
	private Rider rider;
	
	@Property
	private Country country;
	
	@Property
	private int season;

	@Inject
	private RidersServiceLocal riderService;

	void onActivate(String countryString, int season, String email, String password, int riderNumber) {
		this.riderNumber = riderNumber;
		this.season = season;
		this.country = Country.valueOf(countryString);
	}

	void onActivate(String countryString, int season, int riderNumber) {
		this.riderNumber = riderNumber;
		this.season = season;
		this.country = Country.valueOf(countryString);
	}
	
	List onPassivate(){
		
		List returnParams = new ArrayList();
		if (country == null) {
			country = Country.NL;
		}
		returnParams.add(country.name());
		returnParams.add(season);

		return returnParams;
	}

	void setupRender() {
		rider = riderService.getRider(riderNumber);
		if (rider == null) {
			title = Constants.PROFILE_TITLE + " " + rider.getFullName();
			rider = new Rider();
			text = "Rider not found";
		}
	}
}
