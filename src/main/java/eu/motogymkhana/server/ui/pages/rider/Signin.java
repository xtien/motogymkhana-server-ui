/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages.rider;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.annotations.AnonymousAccess;
import eu.motogymkhana.server.ui.pages.Index;
import eu.motogymkhana.server.ui.security.AuthenticationException;
import eu.motogymkhana.server.ui.security.Authenticator;

@AnonymousAccess
public class Signin {

	@Property
	private String title = "Sign in";

	@Property
	private String flashmessage;

	@Persist
	@Property
	@Validate("required")
	private String username;

	@Persist
	@Property
	@Validate("required")
	private String password;

	@Inject
	private Authenticator authenticator;

	@Component
	private Form loginForm;

	@Inject
	private Messages messages;

	@InjectPage
	private Index index;

	@InjectPage
	private Profile profilePage;

	@Inject
	private Logger log;

	@Property
	@Persist
	private int season;

	@Property
	@Persist
	private Country country;

	@Property
	@Persist
	private int roundNumber;

	@Log
	public Object onSubmitFromLoginForm() {
		
		try {
			authenticator.login(username, password);
		} catch (AuthenticationException ex) {
			loginForm.recordError("error signing in: wrong email and/or password");
			return null;
		}

		profilePage.onActivate(country.name(), season, username, password,
				roundNumber);
		return profilePage;
	}

	public String getFlashMessage() {
		return flashmessage;
	}

	public void setFlashMessage(String flashmessage) {
		this.flashmessage = flashmessage;
	}

	void onActivate(String countryString, int season) {

		this.season = season;
		country = Country.valueOf(countryString);
		title = Constants.TITLE + " " + this.country.getString() + " " + this.season;
	}

	void onActivate(String countryString, int season, int roundNumber) {

		this.roundNumber = roundNumber;
		this.season = season;
		country = Country.valueOf(countryString);
		title = Constants.TITLE + " " + this.country.getString() + " " + this.season;
	}

	List onPassivate() {

		List returnParams = new ArrayList();
		if (country == null) {
			country = Country.NL;
		}
		returnParams.add(country.name());
		returnParams.add(season);
		returnParams.add(roundNumber);

		return returnParams;
	}
}
