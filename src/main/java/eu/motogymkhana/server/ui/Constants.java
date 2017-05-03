/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import eu.motogymkhana.server.model.Country;

public class Constants {

	public static final boolean USE_HTTPS = true;

	public static final String DEFAULT_LOCAL_URL = "http://api.gymcomp.com";
	public static final int DEFAULT_LOCAL_PORT_HTTP = 8085;
	public static final int DEFAULT_LOCAL_PORT_HTTPS = 9005;

	public static final int DEFAULT_LOCAL_PORT = USE_HTTPS ? DEFAULT_LOCAL_PORT_HTTPS
			: DEFAULT_LOCAL_PORT_HTTP;

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	public static final String version = "1.1";

	public static final String TITLE = "Moto Gymkhana riders and results";
	public static final String PROFILE_TITLE = "Moto Gymkhana rider profile";
	
	public static Country country;
	public static int season;

}
