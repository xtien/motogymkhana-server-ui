/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui;

import org.apache.tapestry5.ValueEncoder;

import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.ui.web.local.RoundsServiceLocal;

public class RoundEncoder  implements ValueEncoder<Round> {
	
	private RoundsServiceLocal roundsService;

	public RoundEncoder(RoundsServiceLocal roundsService){
		this.roundsService = roundsService;
	}
	
	@Override
    public String toClient(Round round) {
        return String.valueOf(round.getNumber());
    }

    @Override
    public Round toValue(String id) {
    	
    	Round round = roundsService.getRound(id);
    	return round;
    }
}
