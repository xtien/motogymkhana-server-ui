package eu.motogymkhana.server.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

	public static final boolean USE_HTTPS = true;

	public static final String DEFAULT_LOCAL_URL = "http://api.gymcomp.com";
	public static final int DEFAULT_LOCAL_PORT_HTTP = 8085;
	public static final int DEFAULT_LOCAL_PORT_HTTPS = 9005;

	public static final int DEFAULT_LOCAL_PORT = USE_HTTPS ? DEFAULT_LOCAL_PORT_HTTPS
			: DEFAULT_LOCAL_PORT_HTTP;

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	public static final String version = "1.1";

	public static final String TITLE = "Moto Gymkhana riders and results";

}
