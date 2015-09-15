package eu.motogymkhana.server.ui.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provider for jackson object mapper so we can inject the mapper.
 * 
 * @author christine
 * 
 */
public class ObjectMapperProvider {

	private static ObjectMapper mapper = new ObjectMapper() {
		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
	};

	public ObjectMapper get() {
		return mapper;
	}
}
