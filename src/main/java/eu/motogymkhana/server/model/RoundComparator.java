package eu.motogymkhana.server.model;

import java.util.Comparator;

public class RoundComparator implements Comparator<Round> {

	@Override
	public int compare(Round lhs, Round rhs) {
		return lhs.getNumber() - rhs.getNumber();
	}
}
