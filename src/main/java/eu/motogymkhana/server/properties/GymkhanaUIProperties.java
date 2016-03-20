package eu.motogymkhana.server.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.motogymkhana.server.properties.util.Util;

public class GymkhanaUIProperties {

	public static final String HOST_PROPERTY = "localhost";
	public static final String SERVER_PROPERTY = "server";
	public static final String PORT_HTTP_PROPERTY = "port_http";
	public static final String PORT_HTTPS_PROPERTY = "port_https";
	public static final String GUI_REFRESH_SECONDS = "gui_refresh_rate";
	public static final String USE_HTTPS = "use_https";

	private static final Log log = LogFactory.getLog(GymkhanaUIProperties.class);

	private static Properties properties;
	private static String path;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");

	private static boolean propertiesRead = false;

	public static void init() {
		if (!propertiesRead) {
			readProperties();
			propertiesRead = true;
		}
	}

	private static void readProperties() {

		path = "/home/christine" + File.separator + UIConstants.settings_properties_file;

		File settingsFile = new File(path);

		properties = new Properties();

		if (settingsFile.exists()) {

			try {

				InputStream is = new FileInputStream(settingsFile);

				properties.load(is);

			} catch (IOException e) {
				Util.stacktraceToString(e);
				e.printStackTrace();
			}
		}
		for (Object key : properties.keySet()) {
			log.debug("prop: " + (String) key + " " + properties.getProperty((String) key));
		}
	}

	public static boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	public static boolean hasProperty(Object key) {
		return containsKey(key);
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static int getIntProperty(String key) {
		String stringProp = getProperty(key);
		int result = -1;
		if (NumberUtils.isNumber(stringProp)) {
			result = NumberUtils.toInt(stringProp);
		}
		return result;
	}

	public static boolean getBooleanProperty(String key) {
		String stringProp = getProperty(key);
		return Boolean.parseBoolean(stringProp);
	}

	public static void save() {

		try {

			FileOutputStream fos = new FileOutputStream(new File(path));

			properties.store(fos, "** " + dateFormat.format(new Date()));

		} catch (FileNotFoundException e) {
			Util.stacktraceToString(e);
			e.printStackTrace();
		} catch (IOException e) {
			Util.stacktraceToString(e);
			e.printStackTrace();
		}
	}
}
