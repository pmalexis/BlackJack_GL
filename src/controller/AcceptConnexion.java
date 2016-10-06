package controller;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.InterruptedException;

public class AcceptConnexion implements Runnable {
    
    static ServerSocket ss;
    static int nbMaxClient;
    
    public AcceptConnexion(ServerSocket ss, int nbMaxClient) {
        this.ss = ss;
        this.nbMaxClient = nbMaxClient;
    }
    
	public void run() {

        try {
            while(true) {
                if(Server.getAllClient().size() < nbMaxClient) {
                    Socket socket = ss.accept();
                    Client client = new Client(socket);
                    Server.addClient(client);
                    Thread connexion = new Thread(new Connexion(client));
                    connexion.start();
                }   
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
      
}