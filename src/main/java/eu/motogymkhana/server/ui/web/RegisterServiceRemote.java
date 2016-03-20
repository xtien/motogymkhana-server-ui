package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.LoginResult;
import eu.motogymkhana.server.api.RegisterResult;

/**
 * The <code>IAgentServiceRemote</code> bean exposes the business methods in the
 * interface.
 */
public interface RegisterServiceRemote {

	RegisterResult register(String userName, String pw, String pwRep) throws JsonProcessingException;
	
	LoginResult login(String userName, String pw) throws JsonProcessingException;
}
