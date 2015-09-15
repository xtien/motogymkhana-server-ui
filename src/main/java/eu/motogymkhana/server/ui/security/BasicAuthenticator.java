package eu.motogymkhana.server.ui.security;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/**
 * Basic Security Realm implementation
 * 
 * @author karesti
 * @version 1.0
 */
public class BasicAuthenticator implements Authenticator {

	public static final String AUTH_TOKEN = "authToken";

	@Inject
	private Request request;

	@Property
	private String message;

	public void login(String username, String password) throws AuthenticationException {

		if ("admin".equals(username) && "admin".equals(password)) {
			request.getSession(true).setAttribute(AUTH_TOKEN, new Boolean(true));
		} else {
			message = "not logged in";
		}
	}

	public boolean isLoggedIn() {

		Session session = request.getSession(false);

		if (session != null) {
			return session.getAttribute(AUTH_TOKEN) != null;
		}
		return false;
	}

	public void logout() {

		Session session = request.getSession(false);

		if (session != null) {
			session.setAttribute(AUTH_TOKEN, null);
			session.invalidate();
		}
	}
}
