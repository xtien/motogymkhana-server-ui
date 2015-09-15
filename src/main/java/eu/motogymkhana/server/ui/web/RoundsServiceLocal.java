package eu.motogymkhana.server.ui.web;

import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Round;

/**
 * The <code>IPersonServiceLocal</code> bean exposes the business methods in the
 * interface.
 */
public interface RoundsServiceLocal extends RoundsServiceRemote {

	ListRoundsResult getRounds();

	Round getRound(String id);
}
