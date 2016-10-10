package controller;

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

public class Server {
    
    static ArrayList<Client> allClient;
    static MoteurBlackjack mBJ;
    static List<Thread> allChat;
    static int port;
    final static int NB_MAX_CLIENT = 5;
    
    public Server(int port) {
        this.port = port;
        this.mBJ = new MoteurBlackjack(NB_MAX_CLIENT);
        this.allClient = new ArrayList<Client>();
    }
    
	public static void main(String[] args) throws IOException {

        Server server = new Server(1234);
		ServerSocket ss = new ServerSocket(1234);
        
        Thread acceptConnexion = new Thread(new AcceptConnexion(ss, NB_MAX_CLIENT));
        acceptConnexion.start();
       
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
                
                //System.out.println(checkBets());
                if(!checkBets()) continue;
                
                /* ----------- GAME ----------- */
                sendToAll("Game started", null);
                /* initialisation */
                mBJ.initAll();
                mBJ.distribution();
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
                
                /* end of the game */
                /*reinitiliasitaion of bets */
                for(Client client : allClient) {
                    if (client == null) {
                        Thread.sleep(10);
                        break;
                    }
                    client.setBet(0);
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
    
    public static ArrayList<Client> getAllClient() {
        return allClient;
    }
    
    public static void addClient(Client c) {
        allClient.add(c);
    }
    
    public static boolean delClient(Client c) {
        return allClient.remove(c);
    }
    
    
}