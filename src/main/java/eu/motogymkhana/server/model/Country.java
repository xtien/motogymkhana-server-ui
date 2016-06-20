/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

/**
 * Created by christine on 29-5-15.
 */
public enum Country {

	/*
	 * 0 NL 1 BE 2 UK 3 FR 4 DE 5 PL 6 RU 7 CZ 8 UA 9 EU 10 LT 11 JP
	 */

	NL("Netherlands"), BE("Belgium"), UK("UK"), FR("France"), DE("Deutschland"), PL("Poland"), RU(
			"Russia"), CZ("Czech Republic"), UA("Ukrain"), EU("European Championship"), LT(
			"Lithuania"), JP("Japan");

	private String string;

	Country(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
