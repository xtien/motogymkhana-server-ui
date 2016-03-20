package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Rider;

public class EditProfileResult extends GymkhanaResult{

	private Rider rider;

	public Rider getRider() {
		return rider;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}
}
