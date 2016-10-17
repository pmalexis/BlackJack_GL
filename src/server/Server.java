package server;

import model.MoteurBlackjack;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.InterruptedException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import model.Player;

public class Server implements Runnable {
    
    static ArrayList<Client> allClient;
    static MoteurBlackjack mBJ;
    static int port;
    public final static int NB_MAX_CLIENT = 5;
    final static int NB_CLIENT = 1;
   
    
    public Server(int port) {
        this.port = port;
        this.mBJ = new MoteurBlackjack();
        this.allClient = new ArrayList<Client>();

    }
    
	public void run() {

        Server server = new Server(1234);
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1234);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        Thread acceptConnexion = new Thread(new AcceptConnexion(ss, NB_MAX_CLIENT));
        System.out.println("wait for client");
        acceptConnexion.start();
        
        mBJ.addPlayer(new Player("Banquier"));
        
       
		while(true) {   
            //System.out.println(allClient.size());
            try {
                Thread.sleep(10);
                
                /* bet */
                for(int i = 0; i < allClient.size(); i++) {
                    Client client = allClient.get(i);
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    
                    //System.out.println(allClient.size());
                    Thread bet = new Thread(new Bet(client, mBJ));
                    bet.start();
                    bet.join();
                } 
                
                if(!checkBets()) continue;
                /* ----------- GAME ----------- */
                sendToAll("Game started", null);
                /* initialisation */              
                mBJ.initAll();
                mBJ.distribution();
                System.out.println("Taille de allClient " + allClient.size());
                for(int i = 0; i < allClient.size();i++) {
                    mBJ.setBetTable(allClient.get(i), allClient.get(i).getBet());
                }
                
                while(inGame()) {
                
                    for(int i = 0; i < allClient.size(); i++) {
                        Client client = allClient.get(i);
                        if (client == null) {
                            Thread.sleep(10);
                            break;
                        }

                        Thread play = new Thread(new Play(client, mBJ));
                        play.start();
                        play.join();
                    }
                }
                
                /* end of the game */
                
                /*reinitialisation */
                for(Client client : allClient) {
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    client.setOver(false);
                    client.setBet(0);
                    client.resetHand();
                    mBJ.getPlayers().get(0).resetHand();
                }
                
             } catch (InterruptedException e) {
                System.err.println(e);
            }
        
		}       

	}
    
    public static boolean checkBets() {
        if(allClient.size() == 0) return false;
        
        for(Client client: allClient) {
            if(client.getBet() == 0) return false;
        }      
        return true;
    }
    
    public static MoteurBlackjack getMoteur() { return mBJ; }
    
    public static void sendToAll(String message, Socket currentSocket) {
        Thread sendToAll = new Thread(new SendToAll(message, currentSocket));
        sendToAll.start();
    }      
    
    public static void sendAllPlayer() {
    	Thread sendObjectToAll = new Thread(new SendObjectToAll(mBJ.getPlayers(), null));
    	sendObjectToAll.start();
    }
    
    public static ArrayList<Client> getAllClient() {
        return allClient;
    }
    
    public static void addClient(Client c) {
        allClient.add(c);
        mBJ.addPlayer(c);
    }
    
    public static boolean delClient(Client c) {
        return mBJ.delPlayer(c) && allClient.remove(c);
    }
    
    public static ArrayList<Player> clientToPlayer() {
        ArrayList<Player> allPlayer = new ArrayList<Player>();
        for(Client client : allClient) {
            allPlayer.add((Player) client);
        }
        
        return allPlayer;
    }   
    
    public static boolean inGame() {
        if(allClient.size() == 0) return false;
        for(Client client : allClient) {
            if(!client.getOver()) return true;
        }
        
        return false;
    }
}