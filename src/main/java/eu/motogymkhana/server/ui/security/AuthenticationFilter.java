/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.security;

import java.io.IOException;

import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

import eu.motogymkhana.server.ui.pages.Index;

/**
 * Intercepts the current page to redirect through the requested page or to the
 * authentication page if login is required. For more understanding read the
 * following tutorial <a href=
 * "http://tapestryjava.blogspot.com/2009/12/securing-tapestry-pages-with.html">
 * Securing Tapestry Pages with annotations </a>
 * 
 * @author karesti
 * @version 1.0
 */
public class AuthenticationFilter implements ComponentRequestFilter {

	private final PageRenderLinkSource renderLinkSource;

	private final ComponentSource componentSource;

	private final Response response;

	private final Authenticator authenticator;

	private String defaultPage = Index.class.getSimpleName();

	public AuthenticationFilter(PageRenderLinkSource renderLinkSource,
			ComponentSource componentSource, Response response, Authenticator authenticator) {
		this.renderLinkSource = renderLinkSource;
		this.componentSource = componentSource;
		this.response = response;
		this.authenticator = authenticator;
	}

	public void handleComponentEvent(ComponentEventRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {

		handler.handleComponentEvent(parameters);

	}

	public void handlePageRender(PageRenderRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {


		handler.handlePageRender(parameters);
	}


}
