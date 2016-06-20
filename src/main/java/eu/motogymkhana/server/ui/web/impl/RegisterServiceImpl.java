/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.LoginRequest;
import eu.motogymkhana.server.api.LoginResult;
import eu.motogymkhana.server.api.RegisterRequest;
import eu.motogymkhana.server.api.RegisterResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.api.URLHelperImpl;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.web.RegisterServiceLocal;
import eu.motogymkhana.server.ui.web.RegisterServiceRemote;

public class RegisterServiceImpl implements RegisterServiceLocal, RegisterServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;
	
	@Inject
	private URLHelper urlHelper;

	@Override
	public RegisterResult register(String userName, String pw, String pwRep)
			throws JsonProcessingException {

		RegisterResult result = new RegisterResult();

		RegisterRequest request = new RegisterRequest(userName, pw, pwRep);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getListRidersUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				result = mapper.readValue(httpResult.getString(), RegisterResult.class);

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		} catch (IOException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		}

		return result;
	}

	@Override
	public LoginResult login(String userName, String pw) throws JsonProcessingException {
		
		LoginResult result = new LoginResult();

		LoginRequest request = new LoginRequest(userName, pw);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getListRidersUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				result = mapper.readValue(httpResult.getString(), LoginResult.class);

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		} catch (IOException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		}

		return result;
	}
}
