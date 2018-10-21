package mcalzaferri.net;

import java.io.IOException;

import mcalzaferri.net.http.HttpMessage;

public interface IHttpClient {
	public void connect() throws IOException;
	
	public void disconnect();
	
	public String post(HttpMessage message) throws IOException;
	
	public void setConnectTimeout(int timeoutMilis);
}
