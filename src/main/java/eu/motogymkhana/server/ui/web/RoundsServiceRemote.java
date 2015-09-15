package eu.motogymkhana.server.ui.web;

import eu.motogymkhana.server.api.ListRoundsResult;

/**
 * The <code>IAgentServiceRemote</code> bean exposes the business methods in the
 * interface.
 */
public interface RoundsServiceRemote {

	ListRoundsResult getRounds();
}
