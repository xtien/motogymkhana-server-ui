/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.result.LoginResult;
import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.api.result.SigninRiderResult;
import eu.motogymkhana.server.api.result.TokenResult;
import eu.motogymkhana.server.model.Country;



/**
 * The <code>IPersonServiceLocal</code> bean exposes the business methods in the
 * interface.
 */
public interface RegisterServiceLocal extends RegisterServiceRemote {

	RegisterRiderResult register(String userName, Country country, int season) throws JsonProcessingException;
	
	SigninRiderResult login(String userName, String pw) throws JsonProcessingException;

	TokenResult sendToken(String userName, String password, String token) throws JsonProcessingException;
}
