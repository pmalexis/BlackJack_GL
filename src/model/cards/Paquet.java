package model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Paquet {

	private ArrayList<Carte> alCard;
	
	/**
	 * Constructor
	 */
	public Paquet() {
		this.alCard =  new ArrayList<Carte>();
	}
	
	/**
	 * Add card top
	 * @param Carte
	 */
	public void addTop(Carte c) {
		this.alCard.add(c);
	}
	
	/**
	 * Add card bottom
	 * @param Carte
	 */
	public void addBot(Carte c) {
		if(this.alCard.isEmpty()) this.addTop(c);
		else this.alCard.add(0, c);
	}
	
	/**
	 * remove card top
	 * @return Carte (droped card)
	 */
	public Carte dropTop() {
		if(this.alCard.isEmpty()) return null;
			
		return this.alCard.remove(this.alCard.size()-1);
	}
	
	/**
	 * remove card bot
	 * @return Carte (droped card)
	 */
	public Carte dropBot() {
		if(this.alCard.isEmpty()) return null;
		
		return this.alCard.remove(0);
	}
	
	/**
	 * Draw a card random
	 * @return Carte
	 */
	public Carte drawRandom() {
		if(this.alCard.isEmpty()) return null;
		
		int index = (int) (Math.random() * ( this.alCard.size() - 0 ));
		return this.alCard.remove(index);
	}
	
	/**
	 * Mix cards
	 * @return boolean true if mix are ok
	 */
	public boolean mixCards() {
		if(this.alCard.isEmpty()) return false;
		
		Collections.shuffle(this.alCard);
		return true;
	}
	
	/**
	 * Cut pack card
	 * @return boolean true if cut are ok
	 */
	public boolean cutPack() {
		if(this.alCard.isEmpty()) return false;
		
		ArrayList<Carte> alTempo = new ArrayList<Carte>();
		
		int index = (int) (Math.random() * ( (this.alCard.size()-3) - 3 ));
		for(int i=0;i<index-1;i++) {
			alTempo.add(this.alCard.get(0));
			this.alCard.remove(0);
		}
		
		for(int i=0;i<alTempo.size();i++) 
			this.alCard.add(alTempo.get(i));
			
		return true;
	}
	
	/**
	 * @return ArrayList<Carte>
	 */
	public ArrayList<Carte> getAlCard() {
		return this.alCard;
	}
}
