package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.EditProfileResult;
import eu.motogymkhana.server.model.Rider;

public interface EditProfileServiceRemote {

	EditProfileResult editProfile(Rider rider) throws JsonProcessingException;
}
