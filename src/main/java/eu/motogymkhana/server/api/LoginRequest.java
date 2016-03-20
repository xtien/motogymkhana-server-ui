package eu.motogymkhana.server.api;

public class LoginRequest extends GymkhanaRequest{

	private String userName;

	public LoginRequest(String userName, String pw) {
		this.userName = userName;
		this.password = pw;
	}

}
