package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;


public class Bet implements Runnable {

    private Client client;
	
	public Bet(Client client){
        this.client = client;        
	}
	
	public void run() {
		
        try {
            String message;
            while((client.getIn().read() != -1) && (message = client.getIn().readLine()) != null) {
                String announce = client.getName() + ", misez : 0 (FIN) | 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 ||| MISE ACTUEL => ";// + mBJ.getBetClient(i));
                System.out.println(client.getName()+" : "+announce);
                Thread t = new Thread(new SendTo(client.getName() + " :" + announce, client));
                t.start();
                t.join();
                
                if(message == null) continue;
                
                if((message).equals("0")) {
                    System.out.println("Fin des mises pour " + client.getName());
                    return;
                }
                
                try {
                    client.setBet(Integer.parseInt(message));
                } catch (NumberFormatException e) {
                    System.err.println(e);
                }
            }
            
            System.out.println(client.getName() + " left the server");
            Server.delClient(client);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
	}

}
