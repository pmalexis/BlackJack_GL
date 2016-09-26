package model;

import java.util.Scanner;

/*
 * A FAIRE
- MISES / JETONS / ETC...
- GERER AFFICHAGE RESULTAT DES MAINS SLIPTTER A LA FIN
- ...
 */
public class TestMoteur {
	
	public static void main(String[] args) {
		
		int nb_players = 2; //valeur à changer pour tester le nombre de joueur
		
		Scanner sc = new Scanner(System.in);
		
		MoteurBlackjack mBJ = new MoteurBlackjack(nb_players);
		mBJ.distribution();
	
		//On ne gere pas les erreurs "humaine" ici car c'est l'ihm qui le fera
		System.out.println("TOUT D'ABORD MISEZ !");
		for(int i=1;i<=nb_players;i++) {
			boolean finMise = false;
			do {
				System.out.println("Joueur " + i + ", misez : 0 (FIN) | 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 ||| MISE ACTUEL => " + mBJ.getBetPlayer(i));
				int n = Integer.parseInt(sc.nextLine());
				if(n == 0) finMise = true;
				else mBJ.setBetTable(i, n);
			}
			while(!finMise);
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------\n");
		
		for(int i=1;i<=nb_players;i++) {
			boolean turnDown  = false;
			boolean turnSplit = false;
			boolean assurance = false;
			String s = "";
			char c;
			
			if(mBJ.blackjack(i)) {
				System.out.println("BLACKJACK pour le joueur " + i + "\nATTENDEZ QUE LE BANQUIER JOUE\n");
				turnDown = true;
			}
			else {
				int cpt = 1;
				do {
					if(mBJ.getValeurJoueur(i) >= 21) {
						turnDown = true;
					}
					else {
						System.out.println(mBJ.getHand(0));
						System.out.println(mBJ.getHand(i) + " => " + mBJ.getValeurJoueur(i));
						s = "Joueur " + i + " | hit (h) | stand (r) | ";
						if(cpt == 1) {
							s += "double (d) | ";
							if(mBJ.canSplit(i)) s += "split (s) |";
						}
						if(mBJ.getHands()[0][0].getHauteur() == 1 && !assurance) {
							s += "Assurance (a) |";
							assurance = true;
						}
						System.out.println(s);
						
						c = sc.nextLine().charAt(0);
						switch(c) {
							case 'h' : mBJ.hit(i); break;
							case 'r' : turnDown = true; break;
							case 'd' : mBJ.hit(i); mBJ.setBetTable(i, mBJ.getBetPlayer(i)); turnDown = true; break;
							case 's' : mBJ.split(i); turnSplit = true; cpt--; break;
							case 'a' : mBJ.insurance(i); cpt--; break;
						}		
						cpt++;
					}
					
					if(mBJ.getHands()[i + nb_players][0] != null && turnDown && turnSplit) {
						boolean turnDownSplit = false;
						cpt = 1;
						do {
							if(mBJ.getValeurJoueur(i+nb_players) >= 21) {
								turnDownSplit = true;
							}
							else {
								System.out.println(mBJ.getHand(0));
								System.out.println(mBJ.getHand(i + nb_players) + " => " + mBJ.getValeurJoueur(i + nb_players));
								s = "Joueur BIS " + i + " | hit (h) | stand (r) | ";
								if(cpt == 1) s += "double (d) | ";
								System.out.println(s);
								
								c = sc.nextLine().charAt(0);
								switch(c) {
									case 'h' : mBJ.hit(i + nb_players); break;
									case 'r' : turnDownSplit = true; break;
									case 'd' : mBJ.hit(i + nb_players); mBJ.setBetTable(i+nb_players, mBJ.getBetPlayer(i)); turnDownSplit = true; break;
								}	
								cpt++;
							}
						} while (!turnDownSplit); 
						cpt = 0;
						turnSplit = true;
						System.out.println("JOUEUR BIS " + i + " VOUS ETES A " + mBJ.getValeurJoueur(i+nb_players));
						if(mBJ.getValeurJoueur(i+nb_players) > 21) System.out.println("VOUS AVEZ PERDU !\n");
						else System.out.println("ATTENDEZ QUE LE BANQUIER JOUE !\n");
					}
				} while (!turnDown);
			}
			System.out.println("JOUEUR " + i + " VOUS ETES A " + mBJ.getValeurJoueur(i));
			if(mBJ.getValeurJoueur(i) > 21) System.out.println("VOUS AVEZ PERDU !\n");
			else System.out.println("ATTENDEZ QUE LE BANQUIER JOUE !\n");
		}
		
		System.out.println("--------------------------------------------------------------------");
		if(!mBJ.blackjack(0)) mBJ.bankPlay();
		for(int i=1;i<nb_players+1;i++) {
			if(mBJ.getValeurJoueur(i) <= 21) {
				//tests blackjack 
				if(mBJ.blackjack(0) && mBJ.blackjack(i)) {
					//separer mise
					System.out.println("BLACKJACK EGALITE, REDISTRIBUTION DES MISES, JOUEUR " + i);
				}
				else if(mBJ.blackjack(0) && mBJ.getValeurJoueur(i) == 21) {
					//mise pour banque
					System.out.println("BLACKJACK POUR LA BANQUE, MISE DONNE A LA BANQUE, JOUEUR " + i);
				}
				else if(mBJ.blackjack(i) && mBJ.getValeurJoueur(0) == 21) {
					//mise pour joueur
					System.out.println("BLACKJACK POUR LE JOUEUR, MISE DONNE A LA BANQUE, JOUEUR " + i);
				}
				else { //puis test classique
					if(mBJ.getValeurJoueur(0) <= 21 && mBJ.getValeurJoueur(0) > mBJ.getValeurJoueur(i)) System.out.println("LA BANQUE GAGNE CONTRE LE JOUEUR " + i);
					else if (mBJ.getValeurJoueur(0) > 21 || mBJ.getValeurJoueur(0) < mBJ.getValeurJoueur(i)) System.out.println("LE JOUEUR " + i + " GAGNE CONTRE LA BANQUE"); 
					else System.out.println("EGALITE POUR LE JOUEUR " + i + " ET LA BANQUE");
					System.out.println("BANQUE => " + mBJ.getValeurJoueur(0) + "\nJOUEUR " + i + " => " + mBJ.getValeurJoueur(i) + "\n");
				}
			}
		}
	}
}
