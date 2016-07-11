/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.security;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.result.LoginResult;
import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.api.result.SigninRiderResult;
import eu.motogymkhana.server.ui.web.RegisterServiceLocal;

/**
 * Basic Security Realm implementation
 * 
 * @author karesti
 * @version 1.0
 */
public class BasicAuthenticator implements Authenticator {

	public static final String AUTH_TOKEN = "authToken";

	@Inject
	private Request request;

	@Property
	private String message;

	@Inject
	private RegisterServiceLocal registerService;

	public void login(String username, String password) throws AuthenticationException {

		try {
			SigninRiderResult loginResult = registerService.login(username, password);

			if (loginResult.getResultCode() == 0
					&& loginResult.getResultCode() == RegisterRiderResult.OK) {
				request.getSession(true).setAttribute(AUTH_TOKEN, new Boolean(true));
			} else {
				message = "not logged in";
				throw new AuthenticationException(message);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = e.getMessage();
		}
	}

	public boolean isLoggedIn() {

		Session session = request.getSession(false);

		if (session != null) {
			return session.getAttribute(AUTH_TOKEN) != null;
		}
		return false;
	}

	public void logout() {

		Session session = request.getSession(false);

		if (session != null) {
			session.setAttribute(AUTH_TOKEN, null);
			session.invalidate();
		}
	}
}
