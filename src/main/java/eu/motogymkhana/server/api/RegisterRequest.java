package eu.motogymkhana.server.api;

public class RegisterRequest extends GymkhanaRequest{

	private String userName;
	private String passwordRepeat;

	public RegisterRequest(String userName, String pw, String pwRep) {
		this.userName = userName;
		this.password = pw;
		this.passwordRepeat = pwRep;
	}
}
