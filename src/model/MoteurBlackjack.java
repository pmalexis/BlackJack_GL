package model;

import java.util.ArrayList;
import java.util.Observable;

import model.cards.Carte;
import model.cards.Couleur;
import model.cards.Paquet;



public class MoteurBlackjack extends Observable{
	
	private final int NB_CARDS_BY_HANDS_START = 2;
	private final int NB_PLAYERS_MAX = 5;
	
	private ArrayList<Player> tabPlayers;
	
	private Paquet paquet; //Le paquet de carte
	
	/*
	 * Constructor
	 */
	public MoteurBlackjack(){
        this.initAll();
    }
    
    /*
	 * add a player
	 */
    public boolean addPlayer(Player player) {
        if(this.tabPlayers.size() == NB_PLAYERS_MAX) return false;
        this.tabPlayers.add(player);
        return true;
    }
    
    public boolean delPlayer(Player player) {
        return this.tabPlayers.remove(player);
    }
    
	/*
	 * initialization of the paquet and tabPlayers
	 */
	public boolean initAll() {
		this.tabPlayers = new ArrayList<Player>();
        this.tabPlayers.add(new Player("Croupier", 0));
		this.paquet = new Paquet();
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carreau, Couleur.Coeur};
		
		for(int nb_pack=0;nb_pack<1;nb_pack++) { //nb pack
			for(int i=0;i<4;i++) {// 4 colors
				for(int j=1;j<14;j++) { // 13 cards
					this.paquet.addTop(new Carte(j, tabCouleur[i])); //IZEHPBGZGEZUJBFIPUGGB
				}
            }
        }
			
		return true;
	}
	
	/*
	 * distribution
	 */
	public boolean distribution() {
		this.paquet.mixCards();
		
		for(int i=0;i<this.NB_CARDS_BY_HANDS_START;i++) 
			for(int j=0;j<tabPlayers.size();j++) {
				this.tabPlayers.get(j).getHand().addTop(this.paquet.dropTop());
			}
		
		return true;
	}
	
	/*
	 * a turn 
	 */
	public void hit(Player player, boolean split) {
		
		Paquet alPaquet;
		if(split) alPaquet = player.getSplit();
		else alPaquet = player.getHand();
		
		if(player != null)
			alPaquet.addBot(this.paquet.dropTop());
	}
	
	/*
	 * split 
	 */
	public boolean split(Player player) {
		
		if(this.canSplit(player) ) {	
			player.getSplit().addBot(player.getHand().dropBot());
			player.getSplit().addBot(this.paquet.dropTop());
			player.getHand().addBot(this.paquet.dropTop());
			
			player.setBetSplit(player.getBet());
			player.setMoney(player.getMoney()-player.getBet());
			
			return true;
		}
		
		return false;
	} 
	
	/*
	 * do a insurance
	 * bet half of your actual bet on the next (value 2:1)
	 */
	public boolean insurance(Player player, boolean split) {
		
		if(!split && this.getPlayers().get(0).getHand().getAlCard().get(0).getHauteur() == 1) {
			player.setInsurance(player.getBet()/2);
			player.setMoney(player.getMoney() - player.getBet()/2);
			return true;
		}
		else if(split && this.getPlayers().get(0).getHand().getAlCard().get(0).getHauteur() == 1) {
			player.setInsuranceSplit(player.getBetSplit()/2);
			player.setMoney(player.getMoney() - player.getBetSplit()/2);
			return true;
		}
		return false;
	}
	
	/*
	 * return 21 if blackjack
	 */
	public boolean blackjack(Player player, boolean split) {
		int n = 0;
		
		Paquet alPaquet;
		if(split) alPaquet = player.getSplit();
		else alPaquet = player.getHand();
		
		int ifOne = alPaquet.getAlCard().get(0).getHauteur();
		int ifTwo = alPaquet.getAlCard().get(1).getHauteur();
		
		if(ifOne == 1) n += 11;
		else n += ifOne > 10 ? 10 : ifOne;
		
		if(ifTwo == 1) n += 11;
		else n += ifTwo > 10 ? 10 : ifTwo;
		
		return n == 21;
	}
	
	/*
	 * Say if you can split
	 */
	public boolean canSplit(Player player) {
		
		int one = player.getHand().getAlCard().get(0).getHauteur();
		int two = player.getHand().getAlCard().get(1).getHauteur();
		
		return one == two;
	}
	
	/*
	 * the bank play value card enter 17 and 21
	 * strategie
	 */
	public boolean bankPlay() {
		while (this.tabPlayers.get(0).computeValue(false) < 17) {
			this.hit(tabPlayers.get(0), false);
		} 
		
		return true;
	}
	
	/*
	 * Reset at zero on the position give in the function
	 */
	public void resetBetTable(Player player) {
		player.setBet(0);
		player.setBetSplit(0);
	}
	
	/*
	 * the bet return to the player 
	 */
	public void backBet() {
		for(Player player : tabPlayers) {
			if(player != null) { //ZERGZERGZREGZRZG manque peut-être des accolades
				player.setMoney(player.getMoney() + player.getBetSplit());
				player.setMoney(player.getMoney() + player.getBet());
			}
		}
	}
	
	/*
	 * Add the value n int the betTable at the position i
	 */
	public void addBetTable(Player player, boolean split, int x) {
		if(split) player.setBetSplit(player.getBetSplit()*x);
		else player.setBet(player.getBet()*x);
	}
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public Paquet getPaquet() {
		return this.paquet;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.tabPlayers;
	}
	
	public int getMoney(Player player) {
		return player.getMoney();
	}
    
    public int getNbPlayers() {
        return tabPlayers.size();
    }
	
	/* --------------------------- *
	 *   SET - change the values
	 * --------------------------- */
	public boolean setBetTable(Player player, int n) {
		if(player.getMoney() >= n) {
			player.setBet(n + player.getBet());
			player.setMoney(player.getMoney() - n);
			notifyObservers();
			return true;
		}
		
		return false;
	}
	
	public void distributeBets() {
		for(int i=1;i<this.tabPlayers.size();i++) {
			Player p = this.tabPlayers.get(i);
			p.setMoney(p.getMoney() + p.getBet() + p.getBetSplit());
		}
	}
}
