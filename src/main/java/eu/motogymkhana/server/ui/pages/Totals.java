package eu.motogymkhana.server.ui.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.RoundComparator;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.ui.RoundEncoder;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;

/**
 * Start page of application MotoGymkhana UI.
 */
public class Totals {

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
	private String title = "Moto Gymkhana Competition Nederland";

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
	private Round round;

	@Property
	private List<Round> rounds;

	@Property
	private SelectModel roundsModel;

	@Inject
	private SelectModelFactory selectModelFactory;

	@Inject
	private RoundsServiceLocal roundsService;

	@Inject
	private RidersServiceLocal riderService;

	private int[] points = { 20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

	@InjectPage
	private Index indexPage;

	@InjectPage
	private Results resultsPage;
	
	public void afterRender() {
		String eventURL = refreshZone.getLink().toAbsoluteURI();
		javaScriptSupport.require("periodic-zone-updater").with(resultsZone.getClientId(),
				eventURL, 5, 100);
	}

	void onRefreshZone() {

		setupRender();

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(resultsZone);
		}
	}

	void onPrepare() {

		ListRoundsResult roundsResult = roundsService.getRounds();

		if (roundsResult.getResultCode() == 200) {
			setRounds(roundsResult);
			roundsModel = selectModelFactory.create(rounds, "dateString");
		}
	}

	void setupRender() {

		ListRoundsResult roundsResult = roundsService.getRounds();

		if (roundsResult.getResultCode() == 200) {
			setRounds(roundsResult);

			roundsModel = selectModelFactory.create(rounds, "number");
		}

		ListRidersResult result = riderService.getRiders();

		if (result.getResultCode() == 200) {

			riders.clear();

			if (result.getRiders().size() > 0) {

				for(Rider r : result.getRiders()){
					if(!r.isDayRider()){
						riders.add(r);
					}
				}

				getTotals();

				Collections.sort(riders, new Comparator<Rider>() {

					@Override
					public int compare(Rider lhs, Rider rhs) {
						return rhs.getTotalPoints() - lhs.getTotalPoints();
					}

				});
				
				int i = 0;
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

	void onActivate(String roundNumber) {
		this.roundNumber = Integer.parseInt(roundNumber);
	}

	Integer onPassivate() {
		return roundNumber;
	}

	private void setRounds(ListRoundsResult roundsResult) {

		rounds = roundsResult.getRounds();

		Collections.sort(rounds, new RoundComparator());

		if (rounds != null) {

			if (roundNumber != -1) {
				round = rounds.get(roundNumber - 1);

			} else {
				for (Round r : rounds) {

					if (r.isCurrent()) {
						round = r;
						roundNumber = r.getNumber();
					}
				}
			}

		} else {
			rounds = new ArrayList<Round>();
			round = new Round();
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

	public void getTotals() {

		HashMap<Long, List<Times>> timesMap = new HashMap<Long, List<Times>>();

		Iterator<Rider> riderIterator = riders.iterator();

		while (riderIterator.hasNext()) {
			Rider rider = riderIterator.next();

			if (rider.hasTimes()) {

				Iterator<Times> timesIterator = rider.getTimes().iterator();

				while (timesIterator.hasNext()) {
					Times times = timesIterator.next();

					if (timesMap.get(times.getDate()) == null) {
						timesMap.put(times.getDate(), new ArrayList<Times>());
					}

					timesMap.get(times.getDate()).add(times);
				}
			}
		}

		for (long key : timesMap.keySet()) {

			List<Times> list = new ArrayList<Times>();
			list.addAll(timesMap.get(key));

			Collections.sort(list, new Comparator<Times>() {

				@Override
				public int compare(Times lhs, Times rhs) {
					return lhs.getBestTime() - rhs.getBestTime();
				}

			});

			int pointsPointer = 0;
			for (Times times : list) {
				if (times.getBestTime() > 0 && pointsPointer < points.length) {

					int p = points[pointsPointer++];

					times.setPoints(p);
				}
			}
		}
		Collections.sort(riders, new Comparator<Rider>() {

			@Override
			public int compare(Rider lhs, Rider rhs) {
				return rhs.getTotalPoints() - lhs.getTotalPoints();
			}

		});
	}
}
