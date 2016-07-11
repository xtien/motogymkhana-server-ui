package eu.motogymkhana.server.api.request;

public class TokenRequest extends GymkhanaRequest {

	private String email;
	private String token;

	public TokenRequest() {
	}

	public TokenRequest(String userName, String password, String token) {
		this.email = userName;
		this.password = password;
		this.token = token;
	}
}
