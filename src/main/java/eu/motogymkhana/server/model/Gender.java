package eu.motogymkhana.server.model;

public enum Gender {

	F("F"), M("");

	private String str;

	Gender(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}
