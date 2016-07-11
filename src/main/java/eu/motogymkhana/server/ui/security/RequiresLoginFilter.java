/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.security;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

import eu.motogymkhana.server.ui.annotations.RequiresLogin;

public class RequiresLoginFilter implements ComponentRequestFilter {

	private final PageRenderLinkSource renderLinkSource;

	private final ComponentSource componentSource;

	private final Response response;

	private final Authenticator authService;

	public RequiresLoginFilter(PageRenderLinkSource renderLinkSource,
			ComponentSource componentSource, Response response, Authenticator authService) {
		this.renderLinkSource = renderLinkSource;
		this.componentSource = componentSource;
		this.response = response;
		this.authService = authService;
	}

	public void handleComponentEvent(ComponentEventRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {

		if (dispatchedToLoginPage(parameters.getActivePageName())) {
			return;
		}

		handler.handleComponentEvent(parameters);

	}

	public void handlePageRender(PageRenderRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {

		if (dispatchedToLoginPage(parameters.getLogicalPageName())) {
			return;
		}

		handler.handlePageRender(parameters);
	}

	private boolean dispatchedToLoginPage(String pageName) throws IOException {

		if (authService.isLoggedIn()) {
			return false;
		}

		Component page = componentSource.getPage(pageName);

		if (!page.getClass().isAnnotationPresent(RequiresLogin.class)) {
			return false;
		}

		Link link = renderLinkSource.createPageRenderLink("rider/Signin");

		response.sendRedirect(link);

		return true;
	}
}
