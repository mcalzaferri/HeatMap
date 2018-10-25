package mcalzaferri.net.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpPostClient{
	private HttpURLConnection con; //Can be Https or Http as HttpsURLConnection simply extends HttpURLConnection
	private URL url;
	private int connectTimeout;
	public HttpPostClient(String url) throws IOException {
		this.url = new URL(url);
		connectTimeout = 0;
	}
	public void connect() throws IOException {
		con = (HttpURLConnection) this.url.openConnection(); //Will automaticly use Https if required
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setConnectTimeout(connectTimeout);
		con.connect();
	}
	
	public void disconnect() {
		con.disconnect();
	}
	
	public <T> T post(Object data, Class<T> expectedResponseData) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(data);
		return gson.fromJson(post(json), expectedResponseData);
	}
	
	public void post(Object data) throws IOException {
		//Send post
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(data);
		post(json);
	}
	
	private String post(String data) throws IOException {
		//Send post
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		System.out.println("Sending post: " + data);
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		
		//Wait for response
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
				
		StringBuffer response = new StringBuffer();
		String responsBuffer;
		while ((responsBuffer = in.readLine()) != null) {
			response.append(responsBuffer);
		}
		System.out.println("Received post response: " + response.toString());
		in.close();
		disconnect();
		connect();
		return response.toString();
	}
	
	public void setConnectTimeout(int timeoutMilis){
		connectTimeout = timeoutMilis;
	}
}
