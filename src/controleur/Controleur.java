package controleur;

import java.util.ArrayList;

import model.MoteurBlackjack;
import model.Player;
import model.bot.BotAgressif;
import model.bot.BotDefensif;


/*
 * CLASSE TEST EN ATTENDANT LE SERVER
 */
public class Controleur {

	private MoteurBlackjack moteur;
	
	public Controleur(MoteurBlackjack moteur) {
		this.moteur = moteur;
	}
	
	public void addPlayer(String name, int money) {
		this.moteur.addPlayer(new Player(name, money));
	}
	
	public void addBetTable(Player player, boolean split, int x) {
		this.moteur.addBetTable(player, split, x);
	}
	
	public void addBot(String name) {
		Player player;
		if(name.equals("atk")) player = new BotAgressif();
		else player = new BotDefensif();
		
		this.moteur.addPlayer(player);
	}
	
	public void distribution() {
		this.moteur.distribution();
	}

	public void hit(Player player, boolean split) {
		this.moteur.hit(player, split);
	}

	public boolean canSplit(Player player) {
		return this.moteur.canSplit(player);
	}
	
	public void insurance(Player player, boolean split) {
		this.moteur.insurance(player, split);
	}

	public void bankPlay() {
		this.moteur.bankPlay();
	}

	public boolean blackjack(Player player, boolean split) {
		return this.moteur.blackjack(player, split);
	}

	public void resetBetTable(Player player) {
		this.moteur.resetBetTable(player);
	}

	public void init() {
		this.moteur.initAll();
	}

	public void split(Player player) {
		this.moteur.split(player);
	}
	
	public void distributeBets() {
		this.moteur.distributeBets();
	}
	
	public void setBetTable(Player player, int bet) {
		this.moteur.setBetTable(player, bet);
	}
	
	public void setTurnDown(Player player, boolean turnDown) {
		player.setTurnDown(turnDown);
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
}
