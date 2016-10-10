package controller;

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
    static List<Thread> allChat;
    static int port;
    final static int NB_MAX_CLIENT = 5;
    
    public Server(int port) {
        this.port = port;
        this.allChat = Collections.synchronizedList(new ArrayList<Thread>());
        this.allClient = new ArrayList<Client>();
    }
    
	public static void main(String[] args) throws IOException {

        Server server = new Server(1234);
		ServerSocket ss = new ServerSocket(1234);
        
        Thread acceptConnexion = new Thread(new AcceptConnexion(ss, NB_MAX_CLIENT));
        acceptConnexion.start();
       
		while(true) {   
            System.out.println(allClient.size());
            //if(allClient.size() == 0) { continue; }
            for(int i = 0; i < allClient.size(); i++) {
                Client client = allClient.get(i);
                //System.out.println(allClient.size());
                Thread bet = new Thread(new Bet(client));
                bet.start();
                
                try {
                    bet.join();
                } catch (InterruptedException e) {
                    //System.err.println(e);
                }
            }
        
		}       

	}
    
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