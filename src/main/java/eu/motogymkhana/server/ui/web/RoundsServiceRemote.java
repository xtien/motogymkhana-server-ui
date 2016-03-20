package eu.motogymkhana.server.ui.web;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;

/**
 * The <code>IAgentServiceRemote</code> bean exposes the business methods in the
 * interface.
 */
public interface RoundsServiceRemote {

	ListRoundsResult getRounds(Country country, int season) throws JsonProcessingException;

	Round getRound(String id);
}
