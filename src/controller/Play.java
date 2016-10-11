package controller;

import model.MoteurBlackjack;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStreamReader;


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

                if(mBJ.blackjack(client.getId())) {
                    message = "BLACKJACK pour " + client.getName() + "\nATTENDEZ QUE LE BANQUIER JOUE\n";
                    t = new Thread(new SendTo(message, client));
                    t.start();
                    turnDown = true;
                }
                else {
                    int cpt = 1;
                    do {
                        if(client.getValue(false) >= 21) {
                            turnDown = true;
                        }
                        else {
                            message = mBJ.getPlayers()[0].getHandString()+"\n";
                            message += client.getHandString() + " => " + client.getValue(false) + "\n";
                            message += client.getName() + " | hit (h) | stand (r) | ";
                            
                            if(cpt == 1) {
                                message += "double (d) | ";
                                if(mBJ.canSplit(client.getId())) message += "split (s) |";
                            }
                            if(mBJ.getPlayers()[0].getHand().getAlCard().get(0).getHauteur() == 1 && !assurance) {
                                message += "Assurance (a) |";
                                assurance = true;
                            }
                            message +="\n";
                            t = new Thread(new SendTo(message, client));
                            t.start();

                            c = client.getIn().readLine().charAt(0);
                            System.out.println();
                            
                            switch(c) {
                                case 'h' : mBJ.hit(client.getId(), false); break;
                                case 'r' : turnDown = true; break;
                                case 'd' : mBJ.hit(client.getId(), false); mBJ.setBetTable(client.getId(), mBJ.getPlayers()[client.getId()-1].getBet()); turnDown = true; break;
                                case 's' : mBJ.split(client.getId()); turnSplit = true; cpt--; break;
                                case 'a' : mBJ.insurance(client.getId()); cpt--; break;
                                default: 
                                    message = "Incorrect";
                                    t = new Thread(new SendTo(message, client));
                                    t.start();
                                    break;

                            }		
                            cpt++;
                        }


                        if(!client.getSplit().getAlCard().isEmpty() && turnDown && turnSplit) {
                            boolean turnDownSplit = false;
                            cpt = 1;
                            do {
                                if(client.getValue(true) >= 21) {
                                    turnDownSplit = true;
                                }
                                else {
                                    message = mBJ.getPlayers()[0].getHandString();
                                    message += client.getHandSplitString() + " => " + client.getValue(true);
                                    message += client.getName() + " | hit (h) | stand (r) | ";
                                    if(cpt == 1) message += "double (d) | ";
                                    t = new Thread(new SendTo(message, client));
                                    t.start();

                                    c = client.getIn().readLine().charAt(0);
                                    switch(c) {
                                        case 'h' : mBJ.hit(client.getId(), true); break;
                                        case 'r' : turnDownSplit = true; break;
                                        case 'd' : mBJ.hit(client.getId(), true); mBJ.getPlayers()[client.getId()-1].setBetSplit(mBJ.getPlayers()[client.getId()-1].getBetSplit()); turnDownSplit = true; break;
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
                            message = client.getName() + " VOUS ETES A " + client.getValue(true);
                            if(client.getValue(true) > 21) message += "VOUS AVEZ PERDU !\n";
                            else message += "\nATTENDEZ QUE LE BANQUIER JOUE !\n";
                            
                            t = new Thread(new SendToAll(message, null));
                            t.start();
                        }
                    } while (!turnDown);
                }
                if(!mBJ.blackjack(client.getId())) {
                    message = client.getName() + " VOUS ETES A " + client.getValue(false);
                    if(client.getValue(false) > 21) message += " VOUS AVEZ PERDU !\n";
                    else message += "\nATTENDEZ QUE LE BANQUIER JOUE !\n";
                    
                    t = new Thread(new SendToAll(message, null));
                    t.start();
                }
            
                System.out.println("--------------------------------------------------------------------");
                boolean split = false;
                if(!mBJ.blackjack(0)) mBJ.bankPlay();
                Thread whoWin = new Thread(new WhoWin(client, mBJ));
                whoWin.start();

            
            /*System.out.println("\n" + client.getName() + " a quitté le serveur");
            Server.delClient(client);*/
            
        } catch (IOException e) {   
            e.printStackTrace();
        }        
	}

}
