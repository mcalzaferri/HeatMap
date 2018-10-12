package mcalzaferri.net.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
    private Socket socket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    public TcpClient(String targetHostName, int port) throws Exception{
        socket = new Socket(targetHostName, port);
        outToServer = new DataOutputStream(socket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void sendObject(Object o) throws Exception{
    	ObjectOutputStream oos = new ObjectOutputStream(outToServer);
    	oos.writeObject(o);
    }

    public void sendPacket(String sentence) throws Exception{
        outToServer.writeBytes(sentence + '\n');
    }
    public void receivePacket() throws Exception{
        String modifiedSentence = inFromServer.readLine();
        System.out.println("From Server:" + modifiedSentence);
    }

    public void close() throws Exception{
    	inFromServer.close();
    	outToServer.close();
        socket.close();
    }
}
