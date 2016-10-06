package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

public class SendToAll implements Runnable {

    private ArrayList<Player> allPlayer;
    private Socket currentSocket;
    private String message;
	
	public SendToAll(String message, ArrayList<Player> allPlayer, Socket currentSocket) {
        this.message = message;
        this.allPlayer = allPlayer;
        this.currentSocket = currentSocket;
	}

	public void run() {		  
		 
        try {
            if(currentSocket == null) System.out.println(message);
            for(Player player : allPlayer) {
                Socket socket = player.getSocket();
                if(currentSocket != null && currentSocket.equals(socket)) continue;
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
			  
	}
}
