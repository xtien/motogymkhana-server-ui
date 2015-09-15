package eu.motogymkhana.server.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Round;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRoundsResult extends GymkhanaResult {

	@JsonProperty("rounds")
	private List<Round> rounds;

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

}
