/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages.rider;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	@Property
	private String username;

	@Property
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

	@Log
	public Object onSubmitFromLoginForm() {
		try {
			authenticator.login(username, password);
		} catch (AuthenticationException ex) {
			loginForm.recordError(messages.get("error.login"));
			return null;
		}

		return profilePage;
	}

	public String getFlashMessage() {
		return flashmessage;
	}

	public void setFlashMessage(String flashmessage) {
		this.flashmessage = flashmessage;
	}

}
