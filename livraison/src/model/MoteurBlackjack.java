package model;

import java.util.ArrayList;
import java.util.Observable;

import model.cards.Carte;
import model.cards.Couleur;
import model.cards.Paquet;

/**
 * Classe qui permet de gérer toute les actions faite sur le model
 * Toutes les méthodes ci-dessous sont appellé par le Controleur
 */
public class MoteurBlackjack extends Observable{
	
	private final int NB_CARDS_BY_HANDS_START = 2;
	private final int NB_PLAYERS_MAX = 5;
	
	private ArrayList<Player> tabPlayers;
	
	private Paquet paquet; //Le paquet de carte
	
	/**
	 * Constructor
	 */
	public MoteurBlackjack(){
        this.initAll();
    }
    
    /**
     * add a player
     * @param player
     * @return boolean
     */
    public boolean addPlayer(Player player) {
        if(this.tabPlayers.size() == NB_PLAYERS_MAX) return false;
        this.tabPlayers.add(player);
        return true;
    }
    
	/**
	 * initialization of the paquet and tabPlayers
	 */
	public void initAll() {
		this.tabPlayers = new ArrayList<Player>();
        this.tabPlayers.add(new Player("Croupier", 0));
		this.paquet = new Paquet();
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carreau, Couleur.Coeur};
		
		for(int nb_pack=0;nb_pack<5;nb_pack++) { //nb pack
			for(int i=0;i<4;i++) {// 4 colors
				for(int j=1;j<14;j++) { // 13 cards
					this.paquet.addTop(new Carte(j, tabCouleur[i])); 
				}
            }
        }
	}
	
	/**
	 * distribution of cards for all players / bots
	 */
	public void distribution() {
		this.paquet.mixCards();
		
		for(int i=0;i<this.NB_CARDS_BY_HANDS_START;i++) 
			for(int j=0;j<tabPlayers.size();j++) {
				this.tabPlayers.get(j).getHand().addTop(this.paquet.dropTop());
			}
	}
	
	/**
	 * Give the won bets to the players
	 */
	public void distributeBets() {
		for(int i=1;i<this.tabPlayers.size();i++) {
			Player p = this.tabPlayers.get(i);
			p.setMoney(p.getMoney() + p.getBet() + p.getBetSplit());
		}
	}
	
	/**
	 * a turn, the player hits depending on the value of split
	 */
	public void hit(Player player, boolean split) {
		
		Paquet alPaquet;
		if(split) alPaquet = player.getSplit();
		else alPaquet = player.getHand();
		
		if(player != null)
			alPaquet.addBot(this.paquet.dropTop());
	}
	
	/**
	 * split the hand of the current player
	 */
	public void split(Player player) {
		
		if(this.canSplit(player) ) {	
			player.getSplit().addBot(player.getHand().dropBot());
			player.getSplit().addBot(this.paquet.dropTop());
			player.getHand().addBot(this.paquet.dropTop());
			
			player.setBetSplit(player.getBet());
			player.setMoney(player.getMoney()-player.getBet());
		}
	} 
	
	/**
	 * @param player
	 * @param boolean split
	 * 
	 * do an insurance on the current player
	 * bet half of your actual bet on the next (value 2:1)
	 */
	public void insurance(Player player, boolean split) {
		
		if(!split && this.getPlayers().get(0).getHand().getAlCard().get(0).getHauteur() == 1) {
			player.setInsurance(player.getBet()/2);
			player.setMoney(player.getMoney() - player.getBet()/2);
		}
		else if(split && this.getPlayers().get(0).getHand().getAlCard().get(0).getHauteur() == 1) {
			player.setInsuranceSplit(player.getBetSplit()/2);
			player.setMoney(player.getMoney() - player.getBetSplit()/2);
		}
	}
	
	/**
	 * @param player
	 * @param boolean split
	 * Return 21 if blackjack
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
	
	/**
	 * @param player
	 * Say if you can split
	 */
	public boolean canSplit(Player player) {
		
		int one = player.getHand().getAlCard().get(0).getHauteur();
		int two = player.getHand().getAlCard().get(1).getHauteur();
		
		return one == two;
	}
	
	/**
	 * The bank play value card between 17 and more
	 */
	public void bankPlay() {
		while (this.tabPlayers.get(0).computeValue(false) < 17) {
			this.hit(tabPlayers.get(0), false);
		}
	}
	
	/**
	 * @param player
	 * Reset to zero on the position give in the function
	 */
	public void resetBetTable(Player player) {
		player.setBet(0);
		player.setBetSplit(0);
	}
	
	/**
	 * The bets return to the players
	 */
	public void backBet() {
		for(Player player : tabPlayers) {
			if(player != null) { //ZERGZERGZREGZRZG manque peut-être des accolades
				player.setMoney(player.getMoney() + player.getBetSplit());
				player.setMoney(player.getMoney() + player.getBet());
			}
		}
	}
	
	/**
	 * @param player
	 * @param boolean split
	 * @param int x
	 * Add the value bet*x in the betTable for the current player
	 */
	public void addBetTable(Player player, boolean split, int x) {
		if(split) player.setBetSplit(player.getBetSplit()*x);
		else player.setBet(player.getBet()*x);
	}
	
	/**
	 * @return Paquet
	 */
	public Paquet getPaquet() {
		return this.paquet;
	}
	
	/**
	 * @return ArrayList<Player>
	 */
	public ArrayList<Player> getPlayers() {
		return this.tabPlayers;
	}
	
	/**
	 * @param player
	 * @return money of the current player
	 */
	public int getMoney(Player player) {
		return player.getMoney();
	}
    
	/**
	 * @return nb player
	 */
    public int getNbPlayers() {
        return tabPlayers.size();
    }
	
	/**
	 * Change the value of the bet and the money of the current player
	 * @param player
	 * @param n
	 */
	public void setBetTable(Player player, int n) {
		if(player.getMoney() >= n) {
			player.setBet(n + player.getBet());
			player.setMoney(player.getMoney() - n);
			notifyObservers();
		}
	}
}
