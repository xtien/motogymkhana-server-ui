package eu.motogymkhana.server.ui.pages.rider;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.RegisterResult;
import eu.motogymkhana.server.ui.annotations.AnonymousAccess;
import eu.motogymkhana.server.ui.pages.Index;
import eu.motogymkhana.server.ui.web.RegisterServiceLocal;

@AnonymousAccess
public class Register {

	@Property
	private String title = "Register";

	@Property
	private String flashmessage;

	@Property
	private String username;

	@Property
	private String password;

	@Property
	private String passwordRepeat;

	@Component
	private Form registerForm;

	@Inject
	private Messages messages;

	@InjectPage
	private Index index;

	@Property
	private String message;

	private int minimumPasswordLength = 6;

	@Inject
	private RegisterServiceLocal registerService;
	
	@InjectPage
	private Signin loginPage;

	@Log
	public Object onSubmitFromRegisterForm() {

		if (username.length() >= minimumPasswordLength
				&& password.length() >= minimumPasswordLength && passwordRepeat.length() > 5
				&& password.equals(passwordRepeat)) {

			try {
				RegisterResult result = registerService
						.register(username, password, passwordRepeat);
				
				if (result.getResultCode() == 200) {
					message = "register ok";
					return loginPage;
					
				} else {
					message = result.getResultCode() + " " + result.getMessage();
				}
			} catch (JsonProcessingException e) {
				message = e.getMessage();
				e.printStackTrace();
			}

		} else {
			if (username.length() < minimumPasswordLength) {
				message = "user name too short";
			} else if (password.length() < minimumPasswordLength
					|| passwordRepeat.length() < minimumPasswordLength) {
				message = "password too short";
			} else {
				message = "passwords don't match";
			}
		}

		return this;
	}

	public String getFlashMessage() {
		return flashmessage;
	}

	public void setFlashMessage(String flashmessage) {
		this.flashmessage = flashmessage;
	}
}