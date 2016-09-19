package cartes;

import java.util.ArrayList;
import java.util.Collections;

public class Paquet {

	private ArrayList<Carte> alCard;
	
	/*
	 * Constructor
	 */
	public Paquet() {
		this.alCard =  new ArrayList<Carte>();
	}
	
	/*
	 * Add card top
	 */
	public void addTop(Carte c) {
		this.alCard.add(c);
	}
	
	/*
	 * Add card bottom
	 */
	public void addBot(Carte c) {
		if(this.alCard.isEmpty()) this.addTop(c);
		else this.alCard.add(0, c);
	}
	
	/*
	 * remove card top
	 */
	public boolean dropTop() {
		if(this.alCard.isEmpty()) return false;
			
		this.alCard.remove(this.alCard.size());
		return true;
	}
	
	/*
	 * remove card bot
	 */
	public boolean dropBot() {
		if(this.alCard.isEmpty()) return false;
		
		this.alCard.remove(0);
		return true;
	}
	
	/*
	 * Draw a card random
	 */
	public Carte drawRandom() {
		if(this.alCard.isEmpty()) return null;
		
		int index = (int) (Math.random() * ( this.alCard.size() - 0 ));
		return this.alCard.get(index);
	}
	
	/*
	 * Mix cards
	 */
	public boolean mixCards() {
		if(this.alCard.isEmpty()) return false;
		
		Collections.shuffle(this.alCard);
		return true;
	}
	
	/*
	 * Cut pack card
	 * VOIR SI REMVOIE DE ALTEMPO !!!
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
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public ArrayList<Carte> getAlCard() {
		return this.alCard;
	}
	
	/*
	 * return string of alCard
	 */
	public String toString() {
		String s = "";
		
		for(int i=0;i<this.alCard.size();i++) {
			s += this.alCard.get(i).toString() + "\n";
		}
		return s;
	}
}
