package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;


public class Bet implements Runnable {

    private Player player;
	
	public Bet(Player player){
        this.player = player;        
	}
	
	public void run() {
		
        try {
            String message = "1";
            BufferedReader in = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
            while(!(message).equals("0")) {
                String announce = player.getName() + ", misez : 0 (FIN) | 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 ||| MISE ACTUEL => ";// + mBJ.getBetPlayer(i));
                System.out.println(player.getName()+" : "+message);
                Thread t = new Thread(new SendTo(player.getName() + " :" + announce, player.getSocket()));
                t.start();
                t.join();
                message = in.readLine();
                System.out.println(message);
            }
            System.out.println("Fin des mises pour " + player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
	}

}
