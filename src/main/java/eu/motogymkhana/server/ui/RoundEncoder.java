package eu.motogymkhana.server.ui;

import org.apache.tapestry5.ValueEncoder;

import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.ui.web.RoundsServiceLocal;

public class RoundEncoder  implements ValueEncoder<Round> {
	
	private RoundsServiceLocal roundsService;

	public RoundEncoder(RoundsServiceLocal roundsService){
		this.roundsService = roundsService;
	}
	
	@Override
    public String toClient(Round round) {
        return String.valueOf(round.getNumber());
    }

    @Override
    public Round toValue(String id) {
    	
    	Round round = roundsService.getRound(id);
    	return round;
    }
}
