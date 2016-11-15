package controleur;

import java.util.ArrayList;

import javax.swing.text.View;

import model.MoteurBlackjack;
import model.Player;


/*
 * CLASSE TEST EN ATTENDANT LE SERVER
 */
public class Controleur {

	private MoteurBlackjack moteur;
	
	public Controleur(MoteurBlackjack moteur) {
		this.moteur = moteur;
	}
	
	public void addPlayer(String name) {
		this.moteur.addPlayer(new Player(name));
	}
	
	public ArrayList<Player> getPlayers() {
		return this.moteur.getPlayers();
	}
	
	public MoteurBlackjack getMoteur() {
		return this.moteur;
	}

	public Player getThisPlayer(int pos) {
		return this.getPlayers().get(pos);
	}

	public void setBetTable(Player player, int bet) {
		this.moteur.setBetTable(player, bet);
	}
}
