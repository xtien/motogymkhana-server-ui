package eu.motogymkhana.server.ui.httpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;

public class HttpClientProvider {

	private HttpClient client;

	public HttpClient get() {

		if (client == null) {
			client = createClient();
		}
		return client;
	}

	private HttpClient createClient() {

		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", createAdditionalCertsSSLSocketFactory()).build();

		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host) throws UnknownHostException {
				if (host.equalsIgnoreCase("pengo.xs4all.nl")) {
					return new InetAddress[] { InetAddress
							.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry, connFactory, dnsResolver);

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
				.build();

		return httpclient;
	}

	protected SSLConnectionSocketFactory createAdditionalCertsSSLSocketFactory() {
		try {

			final KeyStore ks = KeyStore.getInstance("JKS");

			final InputStream in = new FileInputStream(new File(
					"/home/christine/gossipServer/pengo.jks"));
			try {
				ks.load(in, "smndbvr".toCharArray());
			} finally {
				in.close();
			}

			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(ks, new TrustSelfSignedStrategy()).build();

			return new SSLConnectionSocketFactory(sslContext);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
