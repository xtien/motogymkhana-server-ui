package eu.motogymkhana.server.api;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Settings;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRidersResult extends GymkhanaResult {

	@JsonProperty("riders")
	private Collection<Rider> riders;
	
	@JsonProperty("settings")
	private Settings settings;
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	@JsonProperty("text")
	private String text;

	public Collection<Rider> getRiders() {
		return riders;
	}

	public void setRiders(Collection<Rider> riders) {
		this.riders = riders;
	}
	
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    public Settings getSettings(){
    	return settings;
    }
}
