package eu.motogymkhana.server.ui.web;

import eu.motogymkhana.server.api.ListRidersResult;


/**
 * The <code>IPersonServiceLocal</code> bean exposes the business methods in the
 * interface.
 */
public interface RidersServiceLocal extends RidersServiceRemote {

	ListRidersResult getRiders();
}
