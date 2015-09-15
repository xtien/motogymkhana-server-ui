package eu.motogymkhana.server.ui.api;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.ui.Constants;

public class URLHelper {

	public static String getListRidersUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_GET_RIDERS;
	}

	public static String getListRoundsUrl() {
		return getBaseUrl() + ServerConstants.MOTOGYMKHANA + ServerConstants.UI_GET_ROUNDS;
	}

	private static String getBaseUrl() {
		return Constants.DEFAULT_LOCAL_URL + ":" + Constants.DEFAULT_LOCAL_PORT;
	}
}
