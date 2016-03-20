package eu.motogymkhana.server.ui.web.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.http.client.ClientProtocolException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.motogymkhana.server.api.EditProfileRequest;
import eu.motogymkhana.server.api.EditProfileResult;
import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.LoginResult;
import eu.motogymkhana.server.api.RegisterResult;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.ui.api.URLHelper;
import eu.motogymkhana.server.ui.api.URLHelperImpl;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;
import eu.motogymkhana.server.ui.services.EditProfileServiceLocal;
import eu.motogymkhana.server.ui.web.EditProfileServiceRemote;
import eu.motogymkhana.server.ui.web.RegisterServiceLocal;
import eu.motogymkhana.server.ui.web.RegisterServiceRemote;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;
import eu.motogymkhana.server.ui.web.RidersServiceRemote;

public class EditProfileServiceImpl implements EditProfileServiceLocal, EditProfileServiceRemote {

	@Inject
	private Logger logger;

	@Inject
	private MyHttpClient client;

	@Inject
	private ObjectMapper mapper;
	
	@Inject
	private URLHelper urlHelper;

	@Override
	public EditProfileResult editProfile(Rider rider) throws JsonProcessingException {

		EditProfileResult result = new EditProfileResult();

		GymkhanaRequest request = new EditProfileRequest(Country.NL, 2016);
		String input = mapper.writeValueAsString(request);

		try {
			HttpResultWrapper httpResult = client.postStringFromUrl(urlHelper.getEditProfileUrl(),
					input);

			result.setResultCode(httpResult.getStatusCode());

			if (httpResult.getStatusCode() == 200) {

				EditProfileResult profileResult = mapper.readValue(httpResult.getString(),
						EditProfileResult.class);
				if (profileResult.getResultCode() == 0) {
					result.setRider(profileResult.getRider());
					result.setMessage("");

				} else {
					result.setResultCode(profileResult.getResultCode());
					result.setMessage(profileResult.getMessage());
				}

			} else {
				result.setMessage(httpResult.getErrorMessage());
				result.setResultCode(httpResult.getStatusCode());
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
