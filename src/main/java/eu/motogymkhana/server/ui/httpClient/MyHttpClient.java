package eu.motogymkhana.server.ui.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import eu.motogymkhana.server.http.HttpResultWrapper;

public interface MyHttpClient {

	public HttpResultWrapper getStringFromUrl(String url) throws ClientProtocolException,
			IOException;

	public HttpResultWrapper postStringFromUrl(String url, String input)
			throws UnsupportedEncodingException, ClientProtocolException, IOException;
}
