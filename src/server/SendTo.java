package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

public class SendTo implements Runnable {

    private Client client;
    private String message;
	
	public SendTo(String message, Client client) {
        this.message = message;
        this.client = client;
	}

	public void run() {		  
        client.getOut().println(message);
        client.getOut().flush();			  
	}
}
