package eu.motogymkhana.server.api.response;

import eu.motogymkhana.server.model.Rider;

public class GetRiderResponse {

	int status;
	Rider rider;
	
	public boolean isOK() {
		return status == 200;
	}

	public Rider getRider() {
		return rider;
	}
}
