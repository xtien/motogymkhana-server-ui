package eu.motogymkhana.server.ui.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.EditProfileResult;
import eu.motogymkhana.server.model.Rider;

public interface EditProfileServiceLocal {

	EditProfileResult editProfile(Rider rider) throws JsonProcessingException;
}
