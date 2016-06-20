/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

public enum Gender {

	F("F"), M("");

	private String str;

	Gender(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}
