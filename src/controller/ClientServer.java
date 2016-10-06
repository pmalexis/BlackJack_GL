package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;


public class ClientServer implements Runnable {

	private BufferedReader in;
    private PrintWriter out;
	private String message = null, name = null;
    private ArrayList<Player> allPlayer;
    private Player player;;
	
	public ClientServer(BufferedReader in, PrintWriter out, Player player){
		
		this.in = in;
        this.out = out;
		this.player = player;
        this.allPlayer = Server.getAllPlayer();
	}
	
	public void run() {
		
        try {
            while((message = in.readLine()) != null) {
               
                System.out.println(player.getName()+" : "+message);
                Server.sendToAll(player.getName() + " :" + message, player.getSocket());
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
