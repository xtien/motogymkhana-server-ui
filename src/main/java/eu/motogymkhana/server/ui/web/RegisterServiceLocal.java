/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.LoginResult;
import eu.motogymkhana.server.api.RegisterResult;



/**
 * The <code>IPersonServiceLocal</code> bean exposes the business methods in the
 * interface.
 */
public interface RegisterServiceLocal extends RegisterServiceRemote {

	RegisterResult register(String userName, String pw, String pwRep) throws JsonProcessingException;
	
	LoginResult login(String userName, String pw) throws JsonProcessingException;
}
