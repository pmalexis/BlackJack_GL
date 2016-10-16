package controller;

import model.MoteurBlackjack;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;
import model.Player;


public class WhoWin implements Runnable {

    private Client client;
    private static MoteurBlackjack mBJ;
	
	public WhoWin(Client client, MoteurBlackjack mBJ){
        this.client = client;     
        this.mBJ = mBJ;
	}
	
	public void run() {
			
            Player player = (Player) client;
            Player banquier = mBJ.getPlayers().get(0);
            String message = "";
							
            boolean split = false;
            if(player.getValue(true) != 0 && !split) {
                split = true;
            }
            else split = false;	
        
            if(player.getValue(split) <= 21) {
                //tests blackjack 
                if(mBJ.blackjack(banquier) && mBJ.blackjack(player)) {
                    //recup mise
                    message += "BLACKJACK EGALITE, REDISTRIBUTION DES MISES, JOUEUR " + (split?"BIS ":"") + player.getName();
                    client.setOver(true);
                }
                else if(mBJ.blackjack(banquier) && player.getValue(split) == 21) {
                    //perd mise
                    message += "BLACKJACK POUR LA BANQUE, MISE DONNE A LA BANQUE, JOUEUR " + (split?"BIS ":"") + player.getName();
                    mBJ.resetBetTable(player);
                    client.setOver(true);
                }
                else if(mBJ.blackjack(player) && banquier.getValue(split) == 21) {
                    //gagne double mise
                    message +="BLACKJACK POUR LE JOUEUR, MISE DONNE A LA BANQUE, JOUEUR " + (split?"BIS ":"") + player.getName();
                    mBJ.addBetTable(player, player.getBet());
                    client.setOver(true);
                }
                else { //puis test classique
                    if(banquier.getValue(split) <= 21 && banquier.getValue(split) > player.getValue(split)) {
                        message += "LA BANQUE GAGNE CONTRE LE JOUEUR " + (split?"BIS ":"") + player.getName();
                        mBJ.resetBetTable(player);
                        client.setOver(true);
   
                    }
                    else if (banquier.getValue(split) > 21 || banquier.getValue(split) < player.getValue(split)) {
                        message += (split?"BIS ":"") + player.getName() + " GAGNE CONTRE LA BANQUE";
                        mBJ.addBetTable(player,player.getBet());
                        client.setOver(true);
   
                    }
                    else {
                        message += "EGALITE POUR " + (split?"BIS ":"") + player.getName() + " ET LA BANQUE";
                        client.setOver(true);
   
                    }
                    message += "\nBANQUE => " + banquier.getValue(false) + "\n" + (split?"BIS ":"") + player.getName() + " => " + player.getValue(split) + "\n";                    
                }
            } else mBJ.resetBetTable(player);

            //if(split) player.setId(player);


        //test mise
        message += "------------------------------------------------------------------------";
        mBJ.backBet();
                                                             
        Thread t = new Thread(new SendToAll(message, null));
        t.start();  
            
            /*System.out.println("\n" + client.getName() + " a quitté le serveur");
            Server.delClient(client);*/
                    
	}

}
