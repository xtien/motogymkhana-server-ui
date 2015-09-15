package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.RidersServiceRemote;

public class RidersServiceImpl implements RidersServiceLocal, RidersServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;

	@Override
	public ListRidersResult getRiders() {
		
		ListRidersResult result = new ListRidersResult();

		try {
			HttpResultWrapper httpResult = client.getStringFromUrl(URLHelper.getListRidersUrl());

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				ListRidersResult ridersResult = mapper.readValue(httpResult.getString(),
						ListRidersResult.class);
				if (ridersResult.getResultCode() == 0) {
					result.setRiders(ridersResult.getRiders());
					result.setMessage("");
					result.setText(ridersResult.getText());
				} else {
					result.setResultCode(ridersResult.getResultCode());
					result.setMessage(ridersResult.getMessage());
					result.setText(ridersResult.getText());
				}

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
				result.setRiders(new LinkedList<Rider>());
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

}
