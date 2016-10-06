package model;

import java.util.ArrayList;

import model.cards.Carte;
import model.cards.Paquet;

public class Player {

	private String name;
	
	private Paquet hand;
	private Paquet handSplit;
	
	private int bet;
	private int betSplit;
	
	private int money;
	private int insurance;
	
	public Player(String name) {
		this.name = name;
		
		this.hand      = new Paquet();
		this.handSplit = new Paquet();
		
		this.money     = 5555;
		this.bet       = 0;
		this.betSplit  = 0;
		this.insurance = 0;
	}
	
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public String getName() {
		return this.name;
	}
	
	public Paquet getHand() {
		return this.hand;
	}
	
	public Paquet getSplit() {
		return this.handSplit;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public int getBet() {
		return this.bet;
	}
	
	public int getBetSplit() {
		return this.betSplit;
	}
	
	public int getInsurance() {
		return this.insurance;
	}

	public int getValue(boolean split) {
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
	
	/* --------------------------- *
	 *   SET - change the values
	 * --------------------------- */
	public void setMoney(int n) {
		this.money = n;
	}
	
	public void setBetSplit(int n) {
		this.betSplit = n;
	}
	
	public void setBet(int n) {
		this.bet = n;
	}
	
	public void setInsurance(int n) {
		this.insurance = n;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	/* STRING */
	public String getHandString() {
		String s = "";
		s += this.name + " : ";
		
		for(int j=0;j<this.hand.getAlCard().size();j++)
			s += (this.name.equals("Banquier") && j > 0 ? "--------- | " : this.hand.getAlCard().get(j) + " | " ) ;
		
		return s;
	}
	
	public String getHandSplitString() {
		String s = "";
		s += this.name + " BIS : ";
		
		for(int j=0;j<this.handSplit.getAlCard().size();j++)
			s += this.handSplit.getAlCard().get(j) + " | ";
		
		return s;
	}
}
