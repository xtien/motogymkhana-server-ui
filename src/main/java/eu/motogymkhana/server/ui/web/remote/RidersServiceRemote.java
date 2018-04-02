/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web.remote;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.response.UpdateRiderResponse;
import eu.motogymkhana.server.api.result.ListRidersResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;

/**
 * The <code>IAgentServiceRemote</code> bean exposes the business methods in the
 * interface.
 */
public interface RidersServiceRemote {

	ListRidersResult getRiders(Country country, int season) throws JsonProcessingException;

	Rider getRider(int riderNumber);
	
	Rider getRider(String email, String password, Country country, int season) throws JsonProcessingException;
	
	UpdateRiderResponse updateRider(Rider rider, String email, String password) throws JsonProcessingException;
}
