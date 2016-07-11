/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

import eu.motogymkhana.server.model.Country;

public class RegisterRequest extends GymkhanaRequest{

	private String email;

	public RegisterRequest(String email,Country country, int season) {
		this.email = email;
		this.country = country;
		this.season = season;
	}
}
