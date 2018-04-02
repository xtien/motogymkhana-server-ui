/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.result;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Settings;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRidersResult extends GymkhanaResult {

	@JsonProperty("riders")
	private Collection<Rider> riders;
	
	@JsonProperty("settings")
	private Settings settings;
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	@JsonProperty("text")
	private String text;

	public Collection<Rider> getRiders() {
		return riders;
	}

	public void setRiders(Collection<Rider> riders) {
		this.riders = riders;
	}
	
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    public Settings getSettings(){
    	return settings;
    }
}
