/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.request.GetRiderRequest;
import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.GetRiderResponse;
import eu.motogymkhana.server.api.response.UpdateRiderResponse;
import eu.motogymkhana.server.api.result.ListRidersResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.web.local.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.remote.RidersServiceRemote;

public class RidersServiceImpl implements RidersServiceLocal, RidersServiceRemote {

	private static final Log log = LogFactory.getLog(RidersServiceImpl.class);

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;

	@Inject
	private URLHelper urlHelper;

	private Collection<Rider> riders;

	@Override
	public ListRidersResult getRiders(Country country, int season) throws JsonProcessingException {

		Constants.country = country;
		Constants.season = season;
		
		ListRidersResult result = new ListRidersResult();

		GymkhanaRequest request = new GymkhanaRequest(country, season);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getListRidersUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());
			log.debug("getRiders " + httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				ListRidersResult ridersResult = mapper.readValue(httpResult.getString(),
						ListRidersResult.class);
				if (ridersResult.getResultCode() == 0) {
					
					for(Rider rider:ridersResult.getRiders()){
						if(rider.isHideLastName()){
							rider.hideLastName();
						}
						Iterator<Times> iterator =rider.getTimes().iterator();
						while(iterator.hasNext()){
							Times times = iterator.next();
							if(!times.isFor(country,season)){
								iterator.remove();
							}
						}
					}
					
					result.setRiders(ridersResult.getRiders());
					result.setSettings(ridersResult.getSettings());
					result.setMessage("");
					result.setText(ridersResult.getText());

					this.riders = result.getRiders();

				} else {
					result.setResultCode(ridersResult.getResultCode());
					result.setMessage(ridersResult.getMessage());
					result.setText(ridersResult.getText());
				}

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
				result.setRiders(new LinkedList<Rider>());
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
	public Rider getRider(int riderNumber) {

		if (riders == null) {
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (riders != null) {
			for (Rider rider : riders) {
				if (rider.getRiderNumber() == riderNumber) {
					return rider;
				}
			}
		}

		return null;
	}

	@Override
	public Rider getRider(String email, String password, Country country, int season) throws JsonProcessingException {

		GetRiderRequest request = new GetRiderRequest(email,password, country, season);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getGetRiderUrl(),
					input);

			GetRiderResponse response = mapper.readValue(httpResult.getString(),
					GetRiderResponse.class);

			if (response.isOK()) {
				return response.getRider();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UpdateRiderResponse updateRider(Rider rider, String email, String password)
			throws JsonProcessingException {

		UpdateRiderResponse response = new UpdateRiderResponse();
		UpdateRiderRequest request = new UpdateRiderRequest(rider, email,password);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getUpdateRiderUrl(),
					input);

			response = mapper.readValue(httpResult.getString(), UpdateRiderResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
