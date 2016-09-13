/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
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
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.annotations.AnonymousAccess;
import eu.motogymkhana.server.ui.pages.Index;
import eu.motogymkhana.server.ui.web.RegisterServiceLocal;

@AnonymousAccess
public class Register {

	@Property
	private String title = "Register";
	
	@Property
	private String text = "";

	@Property
	private String flashmessage;

	@Property
	private String username;
	
	@Property
	private String password;

	@Component
	private Form registerForm;

	@Inject
	private Messages messages;

	@InjectPage
	private Index index;

	@Property
	private String message;

	private int minimumPasswordLength = 6;

	@Inject
	private RegisterServiceLocal registerService;

	@InjectPage
	private Signin loginPage;

	@Inject
	private Logger log;

	@Property
	private int season;

	@Property
	private Country country;

	@Log
	public Object onSubmitFromRegisterForm() {

		if (username.length() >= minimumPasswordLength) {

			try {
				RegisterRiderResult result = registerService.register(username,country,season);

				if (result.getResultCode() == 200) {
					message = "register ok";
					return loginPage;

				} else {
					message = result.getResultCode() + " " + result.getMessage();
				}
			} catch (JsonProcessingException e) {
				message = e.getMessage();
				e.printStackTrace();
			}

		} else {
			message = "user name too short";
		}

		return this;
	}
	
	void onActivate(String countryString, int season) {

		log.debug("onActivate " + countryString + " " + season);

		this.season =season;
		country = Country.valueOf(countryString);

		title = Constants.TITLE + " " + this.country.getString() + " " + this.season;
	}
	
	List<String> onPassivate() {

		List returnParams = new ArrayList();
		returnParams.add(country.name());
		returnParams.add(String.valueOf(season));

		return returnParams;
	}

	public String getFlashMessage() {
		return flashmessage;
	}

	public void setFlashMessage(String flashmessage) {
		this.flashmessage = flashmessage;
	}
}
