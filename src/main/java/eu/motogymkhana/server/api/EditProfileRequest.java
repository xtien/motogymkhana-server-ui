package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;

public class EditProfileRequest extends GymkhanaRequest {

	public EditProfileRequest(Country country, int season) {
		this.country = country;
		this.season = season;
	}

	private Rider rider;
}
