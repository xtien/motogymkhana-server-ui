package eu.motogymkhana.server.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

	public static final boolean USE_HTTPS = true;

	private static final String DEFAULT_LOCAL_URL_HTTP = "http://pengo.xs4all.nl";
	private static final int DEFAULT_LOCAL_PORT_HTTP = 8085;

	private static final String DEFAULT_LOCAL_URL_HTTPS = "https://pengo.xs4all.nl";
	private static final int DEFAULT_LOCAL_PORT_HTTPS = 9005;

	public static final String DEFAULT_LOCAL_URL = USE_HTTPS ? DEFAULT_LOCAL_URL_HTTPS
			: DEFAULT_LOCAL_URL_HTTP;
	public static final int DEFAULT_LOCAL_PORT = USE_HTTPS ? DEFAULT_LOCAL_PORT_HTTPS
			: DEFAULT_LOCAL_PORT_HTTP;

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	
	public static final int roundsCountingForSeasonResult = 6;

	public static final String version = "1.0";

}
