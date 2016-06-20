/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.RiderNumberComparator;
import eu.motogymkhana.server.model.RiderStartNumberComparator;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.RoundComparator;
import eu.motogymkhana.server.properties.GymkhanaUIProperties;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.RoundEncoder;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;

/**
 * Start page of application MotoGymkhana UI.
 */
public class Index {

	@InjectComponent
	private Zone resultsZone;

	@InjectComponent
	private EventLink refreshZone;

	@Inject
	private Request request;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private ComponentResources componentResources;

	private Integer roundNumber = -1;

	@Property
	private String title = Constants.TITLE;

	@Property
	private List<Rider> riders = new ArrayList<Rider>();

	@Property
	private Rider rider;

	@Property
	private String message;

	@Property
	private String text = "";

	@Property
	private String resultCode;

	@Property
	@Validate("required")
	private Round round;

	@Property
	private List<Round> rounds;

	@Property
	private SelectModel roundsModel;

	@Property
	private int season = 2016;

	@Property
	private int otherSeason = 2015;

	@Property
	private Country country = Country.NL;

	@Inject
	private SelectModelFactory selectModelFactory;

	@Inject
	private RoundsServiceLocal roundsService;

	@Inject
	private RidersServiceLocal riderService;

	@InjectPage
	private Index indexPage;

	@InjectPage
	private Totals totalsPage;

	@Inject
	private Logger log;

	@Property
	private Boolean hasTotals = true;

	void onPrepare() {

		log.debug("onPrepare ");
		log.debug("onPrepare " + country + " " + season);

		ListRoundsResult roundsResult = null;
		try {
			roundsResult = roundsService.getRounds(country, season);
		} catch (JsonProcessingException e) {
			message = e.getClass().getSimpleName() + " " + e.getMessage() != null ? e.getMessage()
					: "";
			e.printStackTrace();
		}

		if (roundsResult != null && roundsResult.getResultCode() == 200) {

			setRounds(roundsResult);

			roundsModel = selectModelFactory.create(rounds, "dateString");
		}
	}

	void onActivate(String country, String season, String roundNumber) {

		log.debug("onActivate " + country + " " + season + " " + roundNumber);

		this.roundNumber = Integer.parseInt(roundNumber);
		this.season = Integer.parseInt(season);
		otherSeason = (this.season == 2015) ? 2016 : 2015;

		for (Country c : Country.values()) {
			if (c.name().equals(country)) {
				this.country = Country.valueOf(country);
			}
		}

		title = Constants.TITLE + " " + this.country.getString() + " " + this.season;
	}

	List<String> onPassivate() {

		List<String> returnParams = new ArrayList<String>();
		returnParams.add(country.name());
		returnParams.add(String.valueOf(season));
		returnParams.add(String.valueOf(roundNumber));

		return returnParams;
	}

	void onValidateFromForm() {
		log.debug("onvalidatefromform " + round);
		log.debug("onvalidatefromform " + round.getNumber());

		roundNumber = (round == null) ? null : round.getNumber();
	}

	void setupRender() {
		loadRiders();
		hasTotals = rounds !=null && rounds.size() > 1;
	}

	public void afterRender() {

		int refreshRate = 60;

		GymkhanaUIProperties.init();
		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.GUI_REFRESH_SECONDS)) {
			String str = GymkhanaUIProperties.getProperty(GymkhanaUIProperties.GUI_REFRESH_SECONDS);
			if (NumberUtils.isNumber(str)) {
				refreshRate = Integer.parseInt(str);
			}
		}

		String eventURL = refreshZone.getLink().toAbsoluteURI();
		javaScriptSupport.require("periodic-zone-updater").with(resultsZone.getClientId(),
				eventURL, refreshRate, 100);
	}

	private void loadRiders() {

		ListRoundsResult roundsResult = null;
		try {
			roundsResult = roundsService.getRounds(country, season);
		} catch (JsonProcessingException e) {
			message = e.getClass().getSimpleName() + " " + e.getMessage() != null ? e.getMessage()
					: "";
			e.printStackTrace();
		}

		log.debug("roundsResult "
				+ ((roundsResult == null) ? "null" : (roundsResult.getResultCode())));

		if (roundsResult != null && roundsResult.getResultCode() == 200) {
			log.debug("roundsResult size" + roundsResult.getRounds().size());

			setRounds(roundsResult);
			roundsModel = selectModelFactory.create(rounds, "dateString");
		}

		ListRidersResult result = null;
		try {
			result = riderService.getRiders(country, season);
		} catch (JsonProcessingException e) {
			message = e.getClass().getSimpleName() + " " + e.getMessage() != null ? e.getMessage()
					: "";
			e.printStackTrace();
		}

		if (result != null && result.getResultCode() == 200) {

			riders.clear();

			if (text != null) {
				text = result.getText();
			}

			if (result.getRiders().size() > 0) {

				for (Rider rider : result.getRiders()) {

					if (round != null) {
						rider.setDate(round.getDate());
					}

					if (rider.isRegistered()) {
						riders.add(rider);
					}
				}

				Collections.sort(riders, new RiderStartNumberComparator());

			} else {
				message = "no riders on server";
			}

			resultCode = "";

		} else {
			message = "no connection with server";
		}
	}

	void onRefreshZone() {

		loadRiders();

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(resultsZone);
		}
	}

	private void setRounds(ListRoundsResult roundsResult) {

		rounds = roundsResult.getRounds();

		Collections.sort(rounds, new RoundComparator());

		if (rounds != null && rounds.size() >= roundNumber) {

			if (roundNumber > 0) {
				round = rounds.get(roundNumber - 1);
				return;
			} else {

				long now = System.currentTimeMillis();
				Round r = null;
				long oneWeek = 7 * 24 * 3600 * 1000l;

				for (Round rr : rounds) {
					if (r == null || ((rr.getDate() > now - oneWeek)
							&& rr.getDate() < r.getDate())) {
						r = rr;
					}
				}
				round = r;
				roundNumber = r.getNumber();
				return;
			}
		}

		rounds = new ArrayList<Round>();
		round = new Round();
		roundNumber = 1;
	}

	public RoundEncoder getRoundEncoder() {
		return new RoundEncoder(roundsService);
	}

	public Date getCurrentTime() {
		return new Date();
	}

	public void set(String string) {
		roundNumber = Integer.parseInt(string);
	}
}
