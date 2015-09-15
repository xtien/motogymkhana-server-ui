package eu.motogymkhana.server.model;

import java.util.Comparator;

public class RiderBestTimeComparator implements Comparator<Rider> {

	@Override
	public int compare(Rider rider1, Rider rider2) {
		return rider1.getBestTime() - rider2.getBestTime();
	}
}
