/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;


/**
 * The <code>IPersonServiceLocal</code> bean exposes the business methods in the
 * interface.
 */
public interface RidersServiceLocal extends RidersServiceRemote {

	ListRidersResult getRiders(Country country, int season) throws JsonProcessingException;

	Rider getRider(String riderName);
}
