package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * A FAIRE
- MISES / JETONS / ETC...
- CHARGER JOUEURS
- ERREUR ASSURANCE
- ...
 */
public class TestMoteur {
	
	//On ne gere pas les erreurs "humaine" ici car c'est l'ihm qui le fera
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		//ON LANCE LE JEU, LE MENU NOUS PROPOSE DIFFERENT CHOIX
		boolean quitter = false;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("_______________\nMENU  \\o/      |\n0 - START      |\n1 - OPTION     |\n2 - HIGHTSCORE |\n3 - CREDIT     |\n4 - QUITTER    |\n_______________|\n");
			int r = Integer.parseInt(sc.nextLine());
			
			switch (r) {
			
				//LE JEU ETAPE
				//CHARGER UNE FICHE PERSO OU JOUER YOLO
				//JOUER ET RECOMMENCER
				case 0 : {
					
					System.out.println("Avec combien de joueur voulez vous jouer ? (max 5)");
					int nb_players = Integer.parseInt(sc.nextLine());
					
					System.out.println("Voulez vous charger une/plusieurs fiche(s) joueur avant de commencer ? (o / n)");
					//A FAIRE
					
					MoteurBlackjack mBJ = new MoteurBlackjack();
                    
					
					while(true) {
                        //mBJ.createPlayer(nb_players);
						mBJ.initAll();
						mBJ.distribution();
					
						System.out.println("TOUT D'ABORD MISEZ !");
                        for(int i = 1; i < mBJ.getPlayers().size(); i++) {
                            Player player = mBJ.getPlayers().get(i);
                            
							boolean finMise = false;
							do {
								System.out.println(player.getName() + " vous avez " + player.getMoney() + "€ , misez : 0 (FIN) | 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 ||| MISE ACTUEL => " +player.getBet());
								int n = Integer.parseInt(sc.nextLine());
								if(n == 0) finMise = true;
								else mBJ.setBetTable(player, n);
							}
							while(!finMise);
						}
						
						System.out.println("-------------------------------------------------------------------------------------------------------\n");
						
                        for(int i = 1; i < mBJ.getPlayers().size(); i++) {
                            
                            boolean turnDown  = false;
							boolean turnSplit = false;
							boolean assurance = false;
							String s = "";
							char c;
                            
                            Player player = mBJ.getPlayers().get(i);
							
							if(mBJ.blackjack(player)) {
								System.out.println("BLACKJACK pour " + player.getName() + "\nATTENDEZ QUE LE BANQUIER JOUE\n");
								turnDown = true;
							}
							else {
								int cpt = 1;
								do {
									if(player.computeValue(false) >= 21) {
										turnDown = true;
									}
									else {
										System.out.println(mBJ.getPlayers().get(0).getHandString());
										System.out.println(player.getHandString() + " => " + player.computeValue(false));
										s = player.getName() + " | hit (h) | stand (r) | ";
										if(cpt == 1) {
											s += "double (d) | ";
											if(mBJ.canSplit(player)) s += "split (s) |";
										}
										if(player.getHand().getAlCard().get(0).getHauteur() == 1 && !assurance) {
											s += "Assurance (a) |";
											assurance = true;
										}
										System.out.println(s);
										
										c = sc.nextLine().charAt(0);
										switch(c) {
											case 'h' : mBJ.hit(player, false); break;
											case 'r' : turnDown = true; break;
											case 'd' : mBJ.hit(player, false); mBJ.setBetTable(player, player.getBet()); turnDown = true; break;
											case 's' : mBJ.split(player); turnSplit = true; cpt--; break;
											case 'a' : mBJ.insurance(player); cpt--; break;
										}		
										cpt++;
									}
									
									
									if(!player.getSplit().getAlCard().isEmpty() && turnDown && turnSplit) {
										boolean turnDownSplit = false;
										cpt = 1;
										do {
											if(player.computeValue(true) >= 21) {
												turnDownSplit = true;
											}
											else {
												System.out.println(mBJ.getPlayers().get(0).getHandString());
												System.out.println(player.getHandSplitString() + " => " + player.computeValue(true));
												s = player.getName() + " | hit (h) | stand (r) | ";
												if(cpt == 1) s += "double (d) | ";
												System.out.println(s);
												
												c = sc.nextLine().charAt(0);
												switch(c) {
													case 'h' : mBJ.hit(player, true); break;
													case 'r' : turnDownSplit = true; break;
													case 'd' : mBJ.hit(player, true); player.setBetSplit(player.getBetSplit()); turnDownSplit = true; break;
												}	
												cpt++;
											}
										} while (!turnDownSplit); 
										cpt = 0;
										turnSplit = true;
										System.out.println(player.getName() + " VOUS ETES A " + player.computeValue(true));
										if(player.computeValue(true) > 21) System.out.println("VOUS AVEZ PERDU !\n");
										else System.out.println("ATTENDEZ QUE LE BANQUIER JOUE !\n");
									}
								} while (!turnDown);
							}
							if(!mBJ.blackjack(player)) {
								System.out.println(player.getName() + " VOUS ETES A " + player.computeValue(false));
								if(player.computeValue(false) > 21) System.out.println("VOUS AVEZ PERDU !\n");
								else System.out.println("ATTENDEZ QUE LE BANQUIER JOUE !\n");
							}
						}
						
						System.out.println("--------------------------------------------------------------------");
						boolean split = false;
						if(!mBJ.blackjack(mBJ.getPlayers().get(0))) mBJ.bankPlay();

						for(int i = 1; i < mBJ.getPlayers().size(); i++) {
                            
                            Player player = mBJ.getPlayers().get(i);
							
							if(player.computeValue(true) != 0 && !split) {
								split = true;
							}
							else split = false;	
							
							if(player.computeValue(split) <= 21) {
								//tests blackjack 
								if(mBJ.blackjack(mBJ.getPlayers().get(0)) && mBJ.blackjack(player)) {
									//recup mise
									System.out.println("BLACKJACK EGALITE, REDISTRIBUTION DES MISES, JOUEUR " + (split?"BIS ":"") + player.getName());
								}
								else if(mBJ.blackjack(mBJ.getPlayers().get(0)) && player.computeValue(split) == 21) {
									//perd mise
									System.out.println("BLACKJACK POUR LA BANQUE, MISE DONNE A LA BANQUE, JOUEUR " + (split?"BIS ":"") + player.getName());
									mBJ.resetBetTable(player);
								}
								else if(mBJ.blackjack(player) && mBJ.getPlayers().get(0).computeValue(split) == 21) {
									//gagne double mise
									System.out.println("BLACKJACK POUR LE JOUEUR, MISE DONNE A LA BANQUE, JOUEUR " + (split?"BIS ":"") + player.getName());
									mBJ.addBetTable(player, player.getBet());
								}
								else { //puis test classique
									if(mBJ.getPlayers().get(0).computeValue(split) <= 21 && mBJ.getPlayers().get(0).computeValue(split) > player.computeValue(split)) {
										System.out.println("LA BANQUE GAGNE CONTRE LE JOUEUR " + (split?"BIS ":"") + player.getName());
										mBJ.resetBetTable(player);
									}
									else if (mBJ.getPlayers().get(0).computeValue(split) > 21 || mBJ.getPlayers().get(0).computeValue(split) < player.computeValue(split)) {
										System.out.println(player.getName() + (split?"BIS ":"") + " GAGNE CONTRE LA BANQUE");
										mBJ.addBetTable(player, player.getBet());
									}
									else {
										System.out.println("EGALITE POUR " + player.getName() + (split?"BIS ":"") + " ET LA BANQUE");
									}
									System.out.println("BANQUE => " + mBJ.getPlayers().get(0).computeValue(false) + "\nJOUEUR " + (split?"BIS ":"") + player.getName() + " => " + player.computeValue(split) + "\n");
								}
							} else mBJ.resetBetTable(player);
							
							if(split) i--;
						}
						
						//test mise
						System.out.println("------------------------------------------------------------------------");
						mBJ.backBet();
						
						System.out.println("Voulez-vous continuer ou arreter ? (c / a)");
						char c = sc.nextLine().charAt(0);
						if(c == 'a') break;
					}
					break;
				}
				
				//LES OPTIONS - CREER ET/OU SUPPRIMER UNE FICHE JOUEUR
				case 1 : {
					
					while(true) {
						System.out.println("Voulez creer ou supprimer une fiche joueur ? (c / s / q pour revenir au menu)");
						char rep = sc.nextLine().charAt(0);
						
						if(rep == 'q') break;
						
						ArrayList<String> alTempo = new ArrayList<String>();
						try{
							BufferedReader br = new BufferedReader(new FileReader("res/joueurs.txt"));
							String ligne;
							while ( (ligne = br.readLine()) != null ) {
								alTempo.add(ligne);
							}
							br.close(); 
						}		
						catch (Exception e){
							System.out.println("Fichier non trouvé, il a donc ete cree");
							new File("res/joueurs.txt");
						}
						
						String nom = "";
						if(rep == 'c') {
							while(true) {
								System.out.println("Donner le nom de votre joueur (q pour quitter)");
								nom = sc.nextLine();
								nom = nom.toUpperCase();
								
								if(nom.charAt(0) == 'q') break;
								
								boolean deja = false;
								if(alTempo.size() != 0)
									for(int i=0;i<alTempo.size();i++)
										if(nom.equals(alTempo.get(i).split("-")[0])) {
											System.out.println("Ce joueur est deja enregistre");
											deja = true;
										}
								
								if(!deja) {
									System.out.println("AJOUT DE VOTRE NOM DE JOUEUR DANS LE FICHIER");
									
									FileWriter ffw;
									try {
										ffw = new FileWriter("res/joueurs.txt");
										for(int i=0;i<alTempo.size();i++) {
											ffw.write(alTempo.get(i) + " \r\n");
										}
										ffw.write(nom + "-5500");
										ffw.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								break;
							}
						}
						else if(rep == 's') {
							if(alTempo.size() == 0) {
								System.out.println("Il n'y aucune fiche joueur enregistre pour le moment");
							}
							else {
								System.out.println("Donner le nom de l'avatar que vous voulez supprimer parmis cette liste :");
								for(int i=0;i<alTempo.size();i++)
									System.out.println("- " + alTempo.get(i).split("-")[0]);
								
								nom = sc.nextLine();
								nom = nom.toUpperCase();
								
								FileWriter ffw;
								try {
									ffw = new FileWriter("res/joueurs.txt");
									for(int i=0;i<alTempo.size();i++) {
										if(!nom.equals(alTempo.get(i).split("-")[0]))
											ffw.write(alTempo.get(i) + " \r\n");
										else System.out.println("Ce joueur a bien ete supprime");
									}
									
									ffw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					break;
				}
				
				//HIGHTSCORE
				case 2 : {
					try{
						BufferedReader br = new BufferedReader(new FileReader("res/joueurs.txt"));
						String ligne;
						while ( (ligne = br.readLine()) != null ) {
							System.out.println("- " + ligne);
						}
						br.close(); 
					}		
					catch (Exception e){
						System.out.println("Fichier non trouvé, il a donc ete cree");
						new File("res/joueurs.txt");
					}
					System.out.println("\nAPPUYER SUR ENTREE POUR RETOURNER AU MENU");
					while(sc.nextLine() == null) {}
					break;
				}
				
				//CREDIT
				case 3 : {
					System.out.println("Ce jeu a ete developpe par :\n- Alexis\n- Guillaume\n- Lucas\n- Nicolas\n\n");
					System.out.println("Ce blackjack a ete creer pour le projet de genie logiciel en L3 Informatique a l'universite de Caen.");
					System.out.println("\nAPPUYER SUR ENTREE POUR RETOURNER AU MENU");
					while(sc.nextLine() == null) {}
					break;
				}
				
				//QUITTER
				case 4 : {
					System.out.println("Merci d'avoir joue de la part de toute l'equipe, a bientot.");
					quitter = true;
				}
			}
		} while(!quitter); //ON QUITTE LE JEU
		
	}
}
