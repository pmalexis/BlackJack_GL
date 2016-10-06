package controler;

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
    
    static ArrayList<Player> allPlayer;
    static List<Thread> allChat;
    static int port;
    static int nbMaxPlayer;
    
    public Server(int port, int nbMaxPlayer) {
        this.port = port;
        this.nbMaxPlayer = nbMaxPlayer;
        this.allChat = Collections.synchronizedList(new ArrayList<Thread>());
        this.allPlayer = new ArrayList<Player>();
    }
    
	public static void main(String[] args) throws IOException {

        Server server = new Server(1234, 2);
		ServerSocket ss = new ServerSocket(1234);
        
		while(allPlayer.size() < nbMaxPlayer) {
			Socket socket = ss.accept();
            Player player = new Player(socket);
            allPlayer.add(player);
			Thread connexion = new Thread(new Connexion(player));
            connexion.start();
            allChat.add(connexion);
		}
                
        sendToAll("Tous les joueurs ont rejoins la partie", null);
        boolean endGame = false;
        //sleepChat();
        for(Player player : allPlayer) {
            Thread bet = new Thread(new Bet(player));
            bet.start();
            try {
                bet.join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        sendToAll("Début de la distribution", null);
        

	}
    
    public static void sendToAll(String message, Socket currentSocket) {
        Thread sendToAll = new Thread(new SendToAll(message, allPlayer, currentSocket));
        sendToAll.start();
    }        
    
    /*public static void sleepChat() {
        for(Thread thread : allChat) {
            try {
                synchronized (thread) {
                    thread.wait();
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }*/
    
    public static ArrayList<Player> getAllPlayer() {
        return allPlayer;
    }
    
    
}