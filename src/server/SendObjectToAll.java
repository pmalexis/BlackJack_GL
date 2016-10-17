package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

import model.Player;

public class SendObjectToAll implements Runnable {

    private Socket currentSocket;
    private ArrayList<Player> allPlayer;
    private Client client;
	
	public SendObjectToAll(ArrayList<Player> allPlayer, Socket currentSocket) {
        this.allPlayer = allPlayer;
        this.currentSocket = currentSocket;
	}

	public void run() {		  
		 
       try {
            //if(currentSocket == null) System.out.println(message);
            for(Client client : Server.getAllClient()) {
                Socket socket = client.getSocket();
                if(currentSocket != null && currentSocket.equals(socket)) continue;
                ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
                oout.writeObject(allPlayer);
                oout.flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
			  
	}
}
