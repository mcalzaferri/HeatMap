package mcalzaferri.net.https;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import mcalzaferri.net.IHttpClient;
import mcalzaferri.net.http.HttpMessage;

public class HttpsClient implements IHttpClient{
	private HttpsURLConnection con;
	private URL url;
	private int connectTimeout;
	
	public HttpsClient(String url) throws IOException {
		this.url = new URL(url);
		connectTimeout = 0;
	}
	public void connect() throws IOException {
		con = (HttpsURLConnection) this.url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);
		con.setConnectTimeout(connectTimeout);
		con.connect();
	}
	public void disconnect() {
		con.disconnect();
	}
	public String post(HttpMessage message) throws IOException {
		//Send post
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		System.out.println("Sending message: " + message.encode());
		wr.writeBytes(message.encode());
		wr.flush();
		wr.close();
		
		//Wait for response
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
				
		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		disconnect();
		connect();
		System.out.println("Received message " + response.toString());
		return response.toString();
	}
	
	public void setConnectTimeout(int timeoutMilis){
		connectTimeout = timeoutMilis;
	}
}
