package controler;

import model.MoteurBlackjack;

public class Controleur {

	private MoteurBlackjack moteur;
	
	public Controleur(int nb_players) {
		moteur = new MoteurBlackjack(nb_players);
	}
}
