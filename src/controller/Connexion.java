package controller;

import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Connexion implements Runnable {

    BufferedReader in;
    PrintWriter out;
    Player player;
    
	public Connexion(Player player) {
        this.player = player;
    }

	public void run() {
		try {
            /* open flux */
            Scanner sc = new Scanner(System.in);
            out = new PrintWriter(player.getSocket().getOutputStream());
            in = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
            String message;
            String clientName;
            
            /* retrieve client name */
            clientName = in.readLine();
            player.setName(clientName);
            System.out.println(player.getName() + " join the server");
            
            Thread clientServer = new Thread(new ClientServer(in, out, player));
            clientServer.start();
            
        } catch (IOException e) {
            System.err.println(e);
        }
	}
}