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

public class Play implements Runnable {

    private Client client;
    private static MoteurBlackjack mBJ;
	
	public Play(Client client, MoteurBlackjack mBJ){
        this.client = client;     
        this.mBJ = mBJ;
	}
	
	public void run() {
		
        try {
                boolean turnDown  = false;
                boolean turnSplit = false;
                boolean assurance = false;
                String message = "";
                Thread t;
                char c;
            
                Player player = (Player) client;
                Player banquier = mBJ.getPlayers().get(0);

                if(mBJ.blackjack(player)) {
                    message = "BLACKJACK pour " + player.getName() + "\nATTENDEZ QUE LE BANQUIER JOUE\n";
                    t = new Thread(new SendTo(message, client));
                    t.start();
                    turnDown = true;
                }
                else {
                    int cpt = 1;
                    do {
                        if(player.getValue(false) >= 21) {
                            turnDown = true;
                        }
                        else {
                            message = banquier.getHandString()+"\n";
                            message += player.getHandString() + " => " + player.getValue(false) + "\n";
                            t = new Thread(new SendToAll(message, client.getSocket()));
                            t.start();
                            message += player.getName() + " | hit (h) | stand (r) | ";
                            
                            if(cpt == 1) {
                                message += "double (d) | ";
                                if(mBJ.canSplit(player)) message += "split (s) |";
                            }
                            if(banquier.getHand().getAlCard().get(0).getHauteur() == 1 && !assurance) {
                                message += "Assurance (a) |";
                                assurance = true;
                            }
                            message +="\n";
                            t = new Thread(new SendTo(message, client));
                            t.start();

                            c = client.getIn().readLine().charAt(0);
                            
                            switch(c) {
                                case 'h' : mBJ.hit(player, false); break;
                                case 'r' : turnDown = true; break;
                                case 'd' : mBJ.hit(player, false); mBJ.setBetTable(player, player.getBet()); turnDown = true; break;
                                case 's' : mBJ.split(player); turnSplit = true; cpt--; break;
                                case 'a' : mBJ.insurance(player); cpt--; break;
                                default: 
                                    message = "Incorrect";
                                    t = new Thread(new SendTo(message, client));
                                    t.start();
                                    break;

                            }		 
                            cpt++;
                        }


                        if(!player.getSplit().getAlCard().isEmpty() && turnDown && turnSplit) {
                            boolean turnDownSplit = false;
                            cpt = 1;
                            do {
                                if(player.getValue(true) >= 21) {
                                    turnDownSplit = true;
                                }
                                else {
                                    message = banquier.getHandString();
                                    message += player.getHandSplitString() + " => " + player.getValue(true);
                                    t = new Thread(new SendToAll(message, null));
                                    t.start();
                                    message = player.getName() + " | hit (h) | stand (r) | ";
                                    if(cpt == 1) message += "double (d) | ";
                                    t = new Thread(new SendTo(message, client));
                                    t.start();

                                    c = client.getIn().readLine().charAt(0);
                                    switch(c) {
                                        case 'h' : mBJ.hit(player, true); break;
                                        case 'r' : turnDownSplit = true; break;
                                        case 'd' : mBJ.hit(player, true); player.setBetSplit(player.getBetSplit()); turnDownSplit = true; break;
                                        default: 
                                            message = "Incorrect";
                                            t = new Thread(new SendTo(message, client));
                                            t.start();
                                            break;
                                    }	
                                    cpt++;
                                }
                            } while (!turnDownSplit); 
                            cpt = 0;
                            turnSplit = true;
                            message = player.getName() + " VOUS ETES A " + player.getValue(true);
                            if(player.getValue(true) > 21) {
                                message += "VOUS AVEZ PERDU !\n";
                                client.setOver(true);
                            }
                            else message += "\nATTENDEZ QUE LE BANQUIER JOUE !\n";
                            
                            t = new Thread(new SendToAll(message, null));
                            t.start();
                        }
                    } while (!turnDown);
                }
                if(!mBJ.blackjack(player)) {
                    message = player.getName() + " VOUS ETES A " + player.getValue(false);
                    if(player.getValue(false) > 21) {
                        message += " VOUS AVEZ PERDU !\n";
                        client.setOver(true);
                    }
                    else message += "\nATTENDEZ QUE LE BANQUIER JOUE !\n";
                    
                    t = new Thread(new SendToAll(message, null));
                    t.start();
                }
            
                boolean split = false;
                if(!mBJ.blackjack(banquier)) mBJ.bankPlay();
                Thread whoWin = new Thread(new WhoWin(client, mBJ));
                whoWin.start();

            
        } catch (IOException e) {   
            e.printStackTrace();
        }        
	}

}
