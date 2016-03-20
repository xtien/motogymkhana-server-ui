package eu.motogymkhana.server.ui.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;

public class RiderPage {
	
	private String riderName;

	@Property
	private String text = "";

	@Property
	private String title = Constants.TITLE;

	@Property
	private String message;

	@Property
	private String resultCode;

	@Property
	private Rider rider;

	@Inject
	private RidersServiceLocal riderService;
	
	void onActivate(String riderName) {
		this.riderName = riderName;
	}
	
	void setupRender() {
		
		rider = riderService.getRider(riderName);	
	}

}
