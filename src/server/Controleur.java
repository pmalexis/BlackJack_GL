package server;

import java.util.ArrayList;

import model.MoteurBlackjack;
import model.Player;


/*
 * CLASSE TEST EN ATTENDANT LE SERVER
 */
public class Controleur {

	private MoteurBlackjack moteur;
	public Server server;
	
	public Controleur() {
		this.moteur = new MoteurBlackjack();
		this.server = new Server(1234);
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
	
	public ArrayList<Player> testArrayList() {
		return this.server.getAllPlayer();
	}

	public Player getThisPlayer(int pos) {
		return this.getPlayers().get(pos);
	}

	public void setBetTable(Player player, int bet) {
		this.moteur.setBetTable(player, bet);
	}
	
	
	public static void main(String[] args) {
		Controleur ctrl = new Controleur();
		
		ctrl.server.getMoteur().addPlayer(new Player("monsieur"));
		
		ArrayList<Player> alp = ctrl.testArrayList();
		
		for(Player p : alp)
			System.out.println(p.getName());
	}
}
