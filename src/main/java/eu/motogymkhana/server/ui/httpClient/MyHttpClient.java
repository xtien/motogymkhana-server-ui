/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import eu.motogymkhana.server.http.HttpResultWrapper;

public interface MyHttpClient {

	public HttpResultWrapper getStringFromUrl(String url) throws ClientProtocolException,
			IOException;

	public HttpResultWrapper postStringFromUrl(String url, String input)
			throws UnsupportedEncodingException, ClientProtocolException, IOException;
}
