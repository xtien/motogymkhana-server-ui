/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages.rider;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.result.TokenResult;
import eu.motogymkhana.server.ui.annotations.AnonymousAccess;
import eu.motogymkhana.server.ui.pages.Index;
import eu.motogymkhana.server.ui.web.local.RegisterServiceLocal;

@AnonymousAccess
public class SendToken {

	@Property
	private String title = "Token Registration";

	@Property
	private String flashmessage;

	@Property
	private String username;

	@Property
	private String password;

	@Property
	private String passwordRepeat;

	@Component
	private Form registerForm;

	@Property
	private String text;

	@Persist
	private String token;

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

	@Log
	public Object onSubmitFromRegisterForm() {

		if (username.length() >= minimumPasswordLength
				&& password.length() >= minimumPasswordLength && passwordRepeat.length() > 5
				&& password.equals(passwordRepeat)) {

			try {
				TokenResult result = registerService.sendToken(username, password, token);

				if (result.getResultCode() == 0) {
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
			if (username.length() < minimumPasswordLength) {
				message = "user name too short";
			} else if (password.length() < minimumPasswordLength
					|| passwordRepeat.length() < minimumPasswordLength) {
				message = "password too short";
			} else {
				message = "passwords don't match";
			}
		}

		return this;
	}

	public String getFlashMessage() {
		return flashmessage;
	}

	public void setFlashMessage(String flashmessage) {
		this.flashmessage = flashmessage;
	}

	void onActivate(String token) {
		this.token = token;
	}

	void onPassivate() {

	}

}
