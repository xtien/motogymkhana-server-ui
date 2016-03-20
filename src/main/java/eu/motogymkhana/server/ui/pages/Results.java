package eu.motogymkhana.server.ui.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.RiderBestTimeComparator;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.RoundComparator;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.RoundEncoder;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;

/**
 * Start page of application MotoGymkhana UI.
 */
public class Results {

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
	private int season = 2015;

	@Property
	private int otherSeason = 2016;

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

	@Property
	private Boolean hasTotals = true;

	void onPrepare() {

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
		
		this.roundNumber = Integer.parseInt(roundNumber);
		this.season = Integer.parseInt(season);
		otherSeason = (this.season == 2015) ? 2016 : 2015;

		for (Country c : Country.values()) {
			if (c.name().equals(country)) {
				this.country = Country.valueOf(country);
			}
		}
		
		title = Constants.TITLE + " " +  this.country.getString();
	}

	List<String> onPassivate() {

		List<String> returnParams = new ArrayList<String>();
		returnParams.add(country.name());
		returnParams.add(String.valueOf(season));
		returnParams.add(String.valueOf(roundNumber));

		return returnParams;
	}

	void onValidateFromForm() {
		roundNumber = round == null ? null : round.getNumber();
	}

	void setupRender() {

		loadRiders();
		hasTotals = rounds.size() > 1;
	}

	public void afterRender() {
		String eventURL = refreshZone.getLink().toAbsoluteURI();
		javaScriptSupport.require("periodic-zone-updater").with(resultsZone.getClientId(),
				eventURL, 10, 100);
	}

	void onRefreshZone() {

		loadRiders();

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(resultsZone);
		}
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

		if (roundsResult != null && roundsResult.getResultCode() == 200) {
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

		text = result.getText();

		if (result.getResultCode() == 200) {

			riders.clear();

			if (text != null) {
				text = result.getText();
			}

			if (result.getRiders().size() > 0) {

				int i = 0;

				for (Rider rider : result.getRiders()) {

					if (round != null) {
						rider.setDate(round.getDate());
					}

					if (rider.hasEUTimes()) {
						riders.add(rider);
					}
				}

				if (riders.size() > 0) {
					Collections.sort(riders, new RiderBestTimeComparator());
				}

				for (Rider rider : riders) {
					rider.setPosition(++i);
				}

			} else {
				message = "no riders on server";
			}

			resultCode = "";

		} else {
			message = "no connection with server";
		}
	}

	private void setRounds(ListRoundsResult roundsResult) {

		rounds = roundsResult.getRounds();

		Collections.sort(rounds, new RoundComparator());

		if (rounds != null) {

			if (roundNumber > 0) {
				round = rounds.get(roundNumber - 1);

			} else {
				for (Round r : rounds) {

					if (r.isCurrent()) {
						round = r;
						roundNumber = r.getNumber();
						break;
					}
				}
			}

		} else {
			rounds = new ArrayList<Round>();
			round = new Round();
			roundNumber = 1;
		}
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
