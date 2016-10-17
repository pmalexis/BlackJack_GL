package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

public class SendToAll implements Runnable {

    private Socket currentSocket;
    private String message;
    private Client client;
	
	public SendToAll(String message, Socket currentSocket) {
        this.message = message;
        this.currentSocket = currentSocket;
	}

	public void run() {		  
		 
       // try {
            if(currentSocket == null) System.out.println(message);
            for(Client client : Server.getAllClient()) {
                Socket socket = client.getSocket();
                if(currentSocket != null && currentSocket.equals(socket)) continue;
                client.getOut().println(message);
                client.getOut().flush();
            }
        /*} catch (IOException e) {
            System.err.println(e);
        }
		*/	  
	}
}
