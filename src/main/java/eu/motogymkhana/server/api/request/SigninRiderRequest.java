/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

public class SigninRiderRequest extends GymkhanaRequest{

	private String email;
	private String password;

	public SigninRiderRequest(String email, String pw) {
		this.email = email;
		this.password = pw;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}