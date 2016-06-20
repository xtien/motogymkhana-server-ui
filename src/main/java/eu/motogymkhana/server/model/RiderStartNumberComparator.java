/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.util.Comparator;

public class RiderStartNumberComparator implements Comparator<Rider> {

	@Override
	public int compare(Rider rider1, Rider rider2) {

		if (rider1.getStartNumber() == 0 && rider2.getStartNumber() == 0) {
			return rider1.getRiderNumber() - rider2.getRiderNumber();
		} else {
			return rider1.getStartNumber() - rider2.getStartNumber();
		}
	}
}
