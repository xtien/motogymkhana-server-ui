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

import eu.motogymkhana.server.api.request.RegisterRequest;
import eu.motogymkhana.server.api.request.SigninRiderRequest;
import eu.motogymkhana.server.api.request.TokenRequest;
import eu.motogymkhana.server.api.result.LoginResult;
import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.api.result.SigninRiderResult;
import eu.motogymkhana.server.api.result.TokenResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.api.impl.URLHelperImpl;
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
	public RegisterRiderResult register(String userName, Country country, int season)
			throws JsonProcessingException {

		RegisterRiderResult result = new RegisterRiderResult();

		RegisterRequest request = new RegisterRequest(userName, country, season);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getRegisterUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				result = mapper.readValue(httpResult.getString(), RegisterRiderResult.class);

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
	public SigninRiderResult login(String userName, String pw) throws JsonProcessingException {

		SigninRiderResult result = new SigninRiderResult();

		SigninRiderRequest request = new SigninRiderRequest(userName, pw);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getLoginUrl(), input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				result = mapper.readValue(httpResult.getString(), SigninRiderResult.class);

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
	public TokenResult sendToken(String userName, String password, String token)
			throws JsonProcessingException {

		TokenResult result = new TokenResult();
		TokenRequest request = new TokenRequest(userName, password, token);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getSendTokenUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				result = mapper.readValue(httpResult.getString(), TokenResult.class);

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
