/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Rider;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateRiderRequest {

	private Rider rider;

	public UpdateRiderRequest(){
		
	}
	
	public UpdateRiderRequest(Rider rider) {
		this.rider = rider;
	}

	public Rider getRider() {
		return rider;
	}
}
