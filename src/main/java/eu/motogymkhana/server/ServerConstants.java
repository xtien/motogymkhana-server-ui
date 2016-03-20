package eu.motogymkhana.server;

public class ServerConstants {

	public static final int HTTPS_PORT = 9005;
	public static final int HTTP_PORT = 8085;
	
	public static final String HTTPS = "https://";
	public static final String HTTP = "http://";
	public static final String UPDATE_RIDER = "/updateRider/";
	public static final String GET_RIDERS = "/getRiders/";
	public static final String UPLOAD_RIDERS = "/uploadRiders/";
	
	public static final String UI_GET_RIDERS = "/ui/getRiders/";
	public static final String UI_GET_ROUNDS = "/ui/getRounds/";	
	public static final String UI_REGISTER = "/ui/register/";	
	public static final String UI_LOGIN = "/ui/login/";	
	public static final String UI_EDIT_PROFILE = "/ui/editProfile/";	
	
	public static final String MOTOGYMKHANA = "/motogymkhana";

	public static String digestAlgorithm = "SHA-256";
}
