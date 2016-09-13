/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.inject.Singleton;

import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.ListRoundsResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;
import eu.motogymkhana.server.ui.web.RoundsServiceRemote;

@Singleton
public class RoundsServiceImpl implements RoundsServiceLocal, RoundsServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;
	
	@Inject
	private URLHelper urlHelper;

	private HashMap<Integer, Round> roundsMap = new HashMap<Integer, Round>();

	@Override
	public ListRoundsResult getRounds(Country country, int season) throws JsonProcessingException {

		ListRoundsResult result = new ListRoundsResult();
		GymkhanaRequest request = new GymkhanaRequest(country, season);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getListRoundsUrl(),input);
			logger.debug("getRounds " + httpResult.getStatusCode());

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {
				
				ListRoundsResult roundsResult = mapper.readValue(httpResult.getString(),
						ListRoundsResult.class);
				if (roundsResult.getResultCode() == 0) {

					if (roundsResult.getRounds() != null) {
						for (Round r : roundsResult.getRounds()) {
							roundsMap.put(r.getNumber(), r);
						}
					}

					result.setRounds(roundsResult.getRounds());
					result.setMessage("");

				} else {
					result.setResultCode(roundsResult.getResultCode());
					result.setMessage(roundsResult.getMessage());
				}

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
				result.setRounds(new LinkedList<Round>());
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
	public Round getRound(String id) {
		
		int number = Integer.parseInt(id);
		return roundsMap.get(number);
	}
}
