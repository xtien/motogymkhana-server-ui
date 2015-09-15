package eu.motogymkhana.server.ui.httpClient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.ui.httpClient.MyHttpClient;

public class MyHttpClientImpl implements MyHttpClient {

	@Inject
	private HttpClient client;

	@Inject
	private Logger log;

	public MyHttpClientImpl() {

	}

	@Override
	public HttpResultWrapper getStringFromUrl(String url) throws ClientProtocolException,
			IOException {

		log.debug(url);

		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in), 512);
		return new HttpResultWrapper(response.getStatusLine().getStatusCode(), response
				.getStatusLine().getReasonPhrase(), br.readLine());
	}

	@Override
	public HttpResultWrapper postStringFromUrl(String url, String input)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(input.replaceAll("\"", "\\\""));
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		HttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in), 512);
		return new HttpResultWrapper(response.getStatusLine().getStatusCode(), response
				.getStatusLine().getReasonPhrase(), br.readLine());
	}

}
