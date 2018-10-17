package mcalzaferri.net.https;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import mcalzaferri.net.http.HttpMessage;

public class HttpsClient {
	private String url;
	private HttpsURLConnection con;
	
	public HttpsClient(String url) throws IOException {
		this.url = url;
		URL obj = new URL(url);
		con = (HttpsURLConnection) obj.openConnection();
	}
	public void connect() throws IOException {
		con.connect();
	}
	public void disconnect() {
		con.disconnect();
	}
	public String post(HttpMessage message) throws IOException {
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		//Send post
		con.setDoOutput(true);
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
		return response.toString();
	}
	
	public void setConnectTimeout(int timeoutMilis){
		con.setConnectTimeout(timeoutMilis);
	}
	
	
	public String getUrl() {
		return url;
	}
}
