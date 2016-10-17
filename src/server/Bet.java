package server;

import model.MoteurBlackjack;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;


public class Bet implements Runnable {

    private Client client;
	private MoteurBlackjack mBJ;
    
	public Bet(Client client,  MoteurBlackjack mBJ){
        this.client = client; 
        this.mBJ = mBJ;
	}
	
	public void run() {
		
        try {
            System.out.println();
            String message;
            boolean connected = true;
            int bet = 0;
            Thread t1 = new Thread(new SendTo("Tout d'abord misez " + client.getName() + " !", client));
            t1.start();
            while(connected) {
                 
                    String announce = client.getName() + " vous avez " + client.getMoney() + "€ , misez : 0 (FIN) | 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 ||| MISE ACTUEL => " + client.getBet();

                    Thread t2 = new Thread(new SendTo(announce, client));
                    t2.start();
                    t2.join();
                    
                    message = client.getIn().readLine();
                    try {
                        bet = Integer.parseInt(message);
                    } catch (NumberFormatException e) {
                        System.err.println("Vous devez saisir un nombre valide !");
                    }
                
                
                    if(message == null) {
                        connected = false; 
                        break;
                    } 

                    if((message).equals("0")) {
                        System.out.println(client.getName() + " mise " + client.getBet() + "€");
                        return;
                    }

                    try {
                        client.setBet(Integer.parseInt(message));
                    } catch (NumberFormatException e) {
                        System.err.println(e);
                    }
                
            }
            
            System.out.println("\n" + client.getName() + " a quitté le serveur");
            Server.delClient(client);
            
        } catch (IOException e) {   
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
	}

}
