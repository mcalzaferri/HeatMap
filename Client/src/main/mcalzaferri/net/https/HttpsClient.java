package mcalzaferri.net.https;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import mcalzaferri.net.http.HttpMessage;

public class HttpsClient {
	private HttpURLConnection con;
	private URL url;
	private int connectTimeout;
	
	public HttpsClient(String url) throws IOException {
		this.url = new URL(url);
		connectTimeout = 0;
	}
	public void connect() throws IOException {
		con = (HttpURLConnection) this.url.openConnection();
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
		return response.toString();
	}
	
	public void setConnectTimeout(int timeoutMilis){
		connectTimeout = timeoutMilis;
	}
}
