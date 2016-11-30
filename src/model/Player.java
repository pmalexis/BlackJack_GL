package model;

import java.util.ArrayList;

import model.cards.Carte;
import model.cards.Paquet;

/**
 * Cette classe permet de stoker toutes les informations du joueur / bot
 */
public class Player {

	private String name;
	
	private Paquet hand;
	private Paquet handSplit;
	
	private int bet;
	private int betSplit;
	
	private int money;
	
	private int insurance;
	private int insuranceSplit;
	
	private boolean turnDown = false;
	
	/**
	 * Constructeur
	 * @param name of player
	 * @param money of player (5555 by default)
	 */
	public Player(String name, int money) {
		this.name = name;
		
		this.hand      = new Paquet();
		this.handSplit = new Paquet();
		
		this.money     = money;
		
		this.bet       = 0;
		this.betSplit  = 0;
		
		this.insurance      = 0;
		this.insuranceSplit = 0;
	}
    
	/**
	 * reset hands
	 */
    public void resetHand() {
        this.hand      = new Paquet();
		this.handSplit = new Paquet(); 
    }
	
    /**
     * @param split (if you want the value of the hand split or not
     * @return the value of your hand
     */
    public int computeValue(boolean split) {
		int n  = 0;
		int as = 0;
		
		ArrayList<Carte> alTempo;
		if(split) alTempo = this.handSplit.getAlCard();
		else alTempo = this.hand.getAlCard();
		
		for(int i=0;i<alTempo.size();i++) {
			if(alTempo.get(i).getHauteur() == 1) as++;
			else n += (alTempo.get(i).getHauteur()>10?10:alTempo.get(i).getHauteur());
		}
		
		for(int j=1;j<=as;j++)
			if(n + 11 <= 21) {
				if(as >= 2 && alTempo.size() > 2 && j > 1) n++;
				else n += 11;
			}
			else n++;
		
		return n;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return hand
	 */
	public Paquet getHand() {
		return this.hand;
	}
	
	/**
	 * @return hand split
	 */
	public Paquet getSplit() {
		return this.handSplit;
	}
	
	/**
	 * @return money
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * @return bet
	 */
	public int getBet() {
		return this.bet;
	}
	
	/**
	 * @return bet split
	 */
	public int getBetSplit() {
		return this.betSplit;
	}
	
	/**
	 * @return insurance
	 */
	public int getInsurance() {
		return this.insurance;
	}
	
	/**
	 * @return insurance split
	 */
	public int getInsuranceSplit() {
		return this.insuranceSplit;
	}
	
	/**
	 * @return turnDown
	 */
	public boolean getTurnDown() {
		return turnDown;
	}

	
	/**
	 * @param n (change value of money)
	 */
	public void setMoney(int n) {
		this.money = n;
	}
	
	/**
	 * @param n (change value of betSplit)
	 */
	public void setBetSplit(int n) {
		this.betSplit = n;
	}
	
	/**
	 * @param n (change value of bet)
	 */
	public void setBet(int n) {
		this.bet = n;
	}
	
	/**
	 * @param n (change value of insurance)
	 */
	public void setInsurance(int n) {
		this.insurance = n;
	}
	
	/**
	 * @param n (change value of insuranceSplit)
	 */
	public void setInsuranceSplit(int n) {
		this.insuranceSplit = n;
	}
	
	/**
	 * @param s (change value of Name)
	 */
	public void setName(String s) {
		this.name = s;
	}
	
	/**
	 * @param n (change value of turnDown)
	 */
	public void setTurnDown(boolean turnDown) {
		this.turnDown = turnDown;
	}
}
