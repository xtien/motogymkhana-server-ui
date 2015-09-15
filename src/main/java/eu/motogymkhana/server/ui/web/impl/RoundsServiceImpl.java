package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.inject.Singleton;

import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;
import eu.motogymkhana.server.ui.web.RoundsServiceRemote;

@Singleton
public class RoundsServiceImpl implements RoundsServiceLocal, RoundsServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;

	private HashMap<Integer, Round> roundsMap = new HashMap<Integer, Round>();

	@Override
	public ListRoundsResult getRounds() {

		ListRoundsResult result = new ListRoundsResult();

		try {
			HttpResultWrapper httpResult = client.getStringFromUrl(URLHelper.getListRoundsUrl());

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				ListRoundsResult roundsResult = mapper.readValue(httpResult.getString(),
						ListRoundsResult.class);
				if (roundsResult.getResultCode() == 0) {

					if (roundsResult.getRounds() != null) {
						for (Round r : roundsResult.getRounds()) {
							roundsMap.put(r.getNumber(), r);
						}
					}

					result.setRounds(roundsResult.getRounds());
					result.setMessage("");

				} else {
					result.setResultCode(roundsResult.getResultCode());
					result.setMessage(roundsResult.getMessage());
				}

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
				result.setRounds(new LinkedList<Round>());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		} catch (IOException e) {
			e.printStackTrace();
			result.setResultCode(-1);
		}

		return result;
	}

	@Override
	public Round getRound(String id) {
		
		int number = Integer.parseInt(id);
		return roundsMap.get(number);
	}
}
