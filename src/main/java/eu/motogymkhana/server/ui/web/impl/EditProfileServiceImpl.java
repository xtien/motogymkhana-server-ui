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

import eu.motogymkhana.server.api.request.EditProfileRequest;
import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.EditProfileResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.services.EditProfileServiceLocal;
import eu.motogymkhana.server.ui.web.remote.EditProfileServiceRemote;

public class EditProfileServiceImpl implements EditProfileServiceLocal, EditProfileServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;
	
	@Inject
	private URLHelper urlHelper;

	@Override
	public EditProfileResult editProfile(Rider rider) throws JsonProcessingException {

		EditProfileResult result = new EditProfileResult();

		GymkhanaRequest request = new EditProfileRequest(Country.NL, 2016);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getEditProfileUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				EditProfileResult profileResult = mapper.readValue(httpResult.getString(),
						EditProfileResult.class);
				if (profileResult.getResultCode() == 0) {
					result.setRider(profileResult.getRider());
					result.setMessage("");

				} else {
					result.setResultCode(profileResult.getResultCode());
					result.setMessage(profileResult.getMessage());
				}

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
