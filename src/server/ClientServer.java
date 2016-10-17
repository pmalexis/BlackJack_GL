package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;


public class ClientServer implements Runnable {

	private String message = null, name = null;
    private Client client;
	
	public ClientServer(Client client){
		this.client = client;
	}
	
	public void run() {
		
        try {
            while((message = client.getIn().readLine()) != null && client.getIn().read() != -1) {
                System.out.println(client.getName()+" : "+message);
                Server.sendToAll(client.getName() + " :" + message, client.getSocket());
            }
            
            System.out.println(client.getName() + " left the server");
            Server.delClient(client);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
