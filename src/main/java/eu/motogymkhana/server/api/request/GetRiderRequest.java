package eu.motogymkhana.server.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Country;

public class GetRiderRequest extends GymkhanaRequest {
	
	@JsonProperty("email")
	private String email;

	public GetRiderRequest(String email, String password,Country country, int season) {
		this.season = season;
		this.country = country;
		this.email = email;
		this.password = password;
	}
}
