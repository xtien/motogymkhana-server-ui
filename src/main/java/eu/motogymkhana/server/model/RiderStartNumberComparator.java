package eu.motogymkhana.server.model;

import java.util.Comparator;

public class RiderStartNumberComparator implements Comparator<Rider> {

	@Override
	public int compare(Rider rider1, Rider rider2) {

		if (rider1.getStartNumber() == 0 && rider2.getStartNumber() == 0) {
			return rider1.getRiderNumber() - rider2.getRiderNumber();
		} else {
			return rider1.getStartNumber() - rider2.getStartNumber();
		}
	}
}
