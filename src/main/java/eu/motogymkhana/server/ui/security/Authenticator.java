/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.security;

/**
 * Basic security interface
 * 
 * @author karesti
 */
public interface Authenticator {

	/**
	 * Checks if the current user is logged in
	 * 
	 * @return true if the user is logged in
	 */
	boolean isLoggedIn();

	/**
	 * Logs the user.
	 * 
	 * @param username
	 * @param password
	 * @throws AuthenticationException
	 *             throw if an error occurs
	 */
	void login(String username, String password) throws AuthenticationException;

	/**
	 * Logs out the user
	 */
	void logout();
}
