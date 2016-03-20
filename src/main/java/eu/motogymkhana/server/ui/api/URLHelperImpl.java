package eu.motogymkhana.server.ui.api;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.properties.GymkhanaUIProperties;
import eu.motogymkhana.server.ui.Constants;

public class URLHelperImpl implements URLHelper {

	private String server = Constants.DEFAULT_LOCAL_URL;
	private String localhost = Constants.DEFAULT_LOCAL_URL;
	private boolean useHttps = true;
	private int portHttps = Constants.DEFAULT_LOCAL_PORT_HTTPS;
	private int portHttp = Constants.DEFAULT_LOCAL_PORT;

	public URLHelperImpl() {

		GymkhanaUIProperties.init();
		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.SERVER_PROPERTY)) {
			String str = GymkhanaUIProperties.getProperty(GymkhanaUIProperties.SERVER_PROPERTY);
			if (str != null) {
				server = "https://" + str;
			}
		}

		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.HOST_PROPERTY)) {
			String str = GymkhanaUIProperties.getProperty(GymkhanaUIProperties.HOST_PROPERTY);
			if (str != null) {
				localhost = "http://" + str;
			}
		}

		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.USE_HTTPS)) {
			useHttps = GymkhanaUIProperties.getBooleanProperty(GymkhanaUIProperties.HOST_PROPERTY);
		}

		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.PORT_HTTPS_PROPERTY)) {
			portHttps = GymkhanaUIProperties
					.getIntProperty(GymkhanaUIProperties.PORT_HTTPS_PROPERTY);
		}

		if (GymkhanaUIProperties.hasProperty(GymkhanaUIProperties.PORT_HTTPS_PROPERTY)) {
			portHttp = GymkhanaUIProperties.getIntProperty(GymkhanaUIProperties.PORT_HTTP_PROPERTY);
		}

	}

	@Override
	public String getListRidersUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_GET_RIDERS;
	}

	@Override
	public String getListRoundsUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_GET_ROUNDS;
	}

	@Override
	public String getRegisterUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_REGISTER;
	}

	@Override
	public String getLoginUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_LOGIN;
	}

	@Override
	public String getEditProfileUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_EDIT_PROFILE;
	}

	private String getBaseUrl() {
		return server + ":" + (useHttps ? portHttps : portHttp);
	}
}
