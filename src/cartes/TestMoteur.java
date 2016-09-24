package cartes;

import java.util.Scanner;

/*
 * A FAIRE

- Doubler la mise (en anglais : double) => Ajouter les mises, enum jeton
- Assurance => si banquier à as

- GERER CORRECTEMENT LES AS (ENTRE 1 ET 11)
- GERER "BLACKJACK" 
- BJ > 21 / BJ = BJ  / 21 = 21
 */
public class TestMoteur {
	
	public static void main(String[] args) {
		
		int nb_players = 2; //valeur à changer pour tester le nombre de joueur
		
		Scanner sc = new Scanner(System.in);
		
		MoteurBlackjack mBJ = new MoteurBlackjack(nb_players);
		mBJ.distribution();
	
		//On ne gere pas les erreurs "humaine" ici car c'est l'ihm qui le fera
		
		for(int i=1;i<=nb_players;i++) {
			System.out.println("Joueur " + i + ", choississez votre mise de départ : 1 | 5 | 10 | 25 | 50 | 100 | 500 | 1000 | 5000 |  ??? ");
			int n = Integer.parseInt(sc.nextLine());
			Jeton jeton = null;
			switch(n) {
				case 1    : jeton = Jeton.BLANC;  break;
				case 5    : jeton = Jeton.ROUGE;  break;
				case 10   : jeton = Jeton.BLEU;   break;
				case 25   : jeton = Jeton.JAUNE;  break;
				case 50   : jeton = Jeton.VERT;   break;
				case 100  : jeton = Jeton.NOIR;   break;
				case 250  : jeton = Jeton.VIOLET; break;
				case 1000 : jeton = Jeton.ORANGE; break;
				case 5000 : jeton = Jeton.GRIS;   break;
			}
			mBJ.setJetonTable(i, jeton);
		}
		
		for(int i=1;i<=nb_players;i++) {
			boolean turnDown  = false;
			boolean turnSplit = false;
			
			if(mBJ.blackjack(i)) {
				System.out.println("BLACKJACK pour le joueur " + i);
				turnDown = true;
			}
			else {
				int cpt = 1;
				do {
					System.out.println(mBJ.getHand(0));
					System.out.println(mBJ.getHand(i) + " => " + mBJ.getValeurJoueur(i));
					String s = "";
					s += "Joueur " + i + " | hit (h) | stand (r) | ";
					if(cpt == 1) {
						s += "double (d) | ";
						if(mBJ.canSplit(i)) s += "split (s) |;";
					}
					if(mBJ.getHands()[0][0].getHauteur() == 1) {
						cpt--;
						s += "Assurance (a) |";
					}
					System.out.println(s);
					
					char c = sc.nextLine().charAt(0);
					switch(c) {
						case 'h' : mBJ.hit(i); break;
						case 'r' : turnDown = true; break;
						case 'd' : mBJ.hit(i); mBJ.double_(i); break;
						case 's' : mBJ.split(i); turnSplit = true; break;
						case 'a' : mBJ.assurance(i); break;
					}		
					cpt++;
					
					if(mBJ.getHands()[i + nb_players][0] != null && turnDown && turnSplit) {
						boolean turnDownSplit = false;
						cpt = 1;
						do {
							System.out.println(mBJ.getHand(0));
							System.out.println(mBJ.getHand(i + nb_players) + " => " + mBJ.getValeurJoueur(i + nb_players));
							s = "";
							s += "Joueur " + i + " | hit (h) | stand (r) | ";
							if(cpt == 1) s += "double (d) | ";
							System.out.println(s);
							
							c = sc.nextLine().charAt(0);
							switch(c) {
								case 'h' : mBJ.hit(i + nb_players); break;
								case 'r' : turnDownSplit = true; break;
								case 'd' : mBJ.hit(i + nb_players); mBJ.double_(i + nb_players); break;
							}	
							cpt++;
						} while (!turnDownSplit);  //BOOLEAN !!!
						cpt = 0;
						turnSplit = true;
					}
				} while (!turnDown);
			}
			
			System.out.println("La banque est à " + mBJ.getValeurJoueur(0));
			System.out.println("Vous avez : " + mBJ.getValeurJoueur(i));
		}
	}
}
