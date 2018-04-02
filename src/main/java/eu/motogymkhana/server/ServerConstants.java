/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server;

public class ServerConstants {

	public static final int HTTPS_PORT = 9005;
	public static final int HTTP_PORT = 8085;
	
	public static final String HTTPS = "https://";
	public static final String HTTP = "http://";
	public static final String UPDATE_RIDER = "/updateRider/";
	public static final String GET_RIDERS = "/getRiders/";
	public static final String UPLOAD_RIDERS = "/uploadRiders/";
	
	public static final String UI_GET_RIDERS = "/ui/getRiders/";
	public static final String UI_GET_ROUNDS = "/ui/getRounds/";	
	public static final String UI_REGISTER = "/ui/registerRider/";	
	public static final String UI_LOGIN = "/ui/signinRider/";	
	public static final String UI_EDIT_PROFILE = "/ui/editProfile/";	
	public static final String UI_SEND_TOKEN = "/ui/sendToken/";	
	public static final String UI_GET_RIDER = "/ui/getRider/";
	public static final String UI_UPDATE_RIDER = "/ui/updateRider/";

	public static final String MOTOGYMKHANA = "/motogymkhana";

	public static String digestAlgorithm = "SHA-256";
}
