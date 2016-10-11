package model;

import java.util.ArrayList;

import model.cards.Carte;
import model.cards.Couleur;
import model.cards.Paquet;

public class MoteurBlackjack {

	private final int NB_CARDS_BY_HANDS_START = 2;
	private final int NB_PLAYERS_MAX = 5; 
	
	private int nb_players;
	
	private Player[] tabPlayers;
	
	private Paquet paquet; //Le paquet de carte
	
	/*
	 * Constructor
	 */
	public MoteurBlackjack(int nb_players) {
		this.nb_players = nb_players;
        
        //MODIF LUCAS
        //this.initAll();
	}
    
    
	
	/*
	 * initialization of the paquet and tabPlayers
	 */
	public boolean initAll(ArrayList<Player> allPlayers) {
		this.paquet = new Paquet();
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carreau, Couleur.Coeur};
		
		for(int nb_pack=0;nb_pack<1;nb_pack++) //nb pack
			for(int i=0;i<4;i++) // 4 colors
				for(int j=1;j<14;j++) { // 13 cards
					this.paquet.addTop(new Carte(j, tabCouleur[i]));
				}
		
        /* MODIFICATION LUCAS */
        /* Permet la compatibilité entre le serveur et le moteur */
        if(allPlayers == null) {
            this.tabPlayers = new Player[this.NB_PLAYERS_MAX + 1];
            for(int i=0;i<this.nb_players+1;i++)
                this.tabPlayers[i] = new Player((i>0?"Joueur "+i:"Banquier"));
        }
        else {
            allPlayers.add(0, new Player("Banquier"));
            this.tabPlayers = new Player[this.nb_players + 1];
            for(int i=0;i<tabPlayers.length;i++) {
                this.tabPlayers[i] = allPlayers.get(i);
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
			for(int j=0;j<this.nb_players+1;j++) {
				this.tabPlayers[j].getHand().addTop(this.paquet.dropTop());
			}
		
		return true;
	}
	
	/*
	 * a turn 
	 */
	public void hit(int player_now, boolean split) {
		
		Paquet alPaquet;
		if(split) alPaquet = this.tabPlayers[player_now].getSplit();
		else alPaquet = this.tabPlayers[player_now].getHand();
		
		if(this.tabPlayers[player_now] != null)
			alPaquet.addBot(this.paquet.dropTop());
	}
	
	/*
	 * split 
	 */
	public boolean split(int player_now) {
		
		if(this.canSplit(player_now) ) {	
			this.tabPlayers[player_now].getSplit().addBot(this.tabPlayers[player_now].getHand().dropBot());
			this.tabPlayers[player_now].getSplit().addBot(this.paquet.dropTop());
			this.tabPlayers[player_now].getHand().addBot(this.paquet.dropTop());
					
			return true;
		}
		
		return false;
	} 
	
	/*
	 * do a insurance
	 * bet half of your actual bet on the next (value 2:1)
	 */
	public boolean insurance(int player_now) {
		
		if(this.tabPlayers[player_now].getHand().getAlCard().get(0).getHauteur() == 1) {
			this.tabPlayers[player_now].setInsurance(this.tabPlayers[player_now].getBet()/2);
			return true;
		}
		return false;
	}
	
	/*
	 * return 21 if blackjack
	 */
	public boolean blackjack(int player_now) {
		int n = 0;
		
		int ifOne = this.tabPlayers[player_now].getHand().getAlCard().get(0).getHauteur();
		int ifTwo = this.tabPlayers[player_now].getHand().getAlCard().get(1).getHauteur();
		
		if(ifOne == 1) n += 11;
		else n += ifOne > 10 ? 10 : ifOne;
		
		if(ifTwo == 1) n += 11;
		else n += ifTwo > 10 ? 10 : ifTwo;
		
		return n == 21;
	}
	
	/*
	 * Say if you can split
	 */
	public boolean canSplit(int player_now) {
		
		int one = this.tabPlayers[player_now].getHand().getAlCard().get(0).getHauteur();
		int two = this.tabPlayers[player_now].getHand().getAlCard().get(1).getHauteur();
		
		return (one>10?10:one) == (two>10?10:two);
	}
	
	/*
	 * the bank play value card enter 17 and 21
	 * strategie
	 */
	public boolean bankPlay() {
		
		while (this.tabPlayers[0].getValue(false) < 17) {
			this.hit(0, false);
		} 
		
		return true;
	}
	
	/*
	 * Reset at zero on the position give in the function
	 */
	public void resetBetTable(int player_now) {
		this.tabPlayers[player_now].setBet(0);
	}
	
	/*
	 * the bet return to the player 
	 */
	public void backBet() {
		for(int i=1;i<this.nb_players+1;i++) {
			if(this.tabPlayers[i] != null)
				this.tabPlayers[i].setMoney(this.tabPlayers[i].getMoney() + this.tabPlayers[i].getBetSplit());
				this.tabPlayers[i].setMoney(this.tabPlayers[i].getMoney() + this.tabPlayers[i].getBet());
		}
	}
	
	/*
	 * Add the value n int the betTable at the position i
	 */
	public void addBetTable(int player_now, int n) {
		this.tabPlayers[player_now].setBet(this.tabPlayers[player_now].getBet() + n);
	}
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public Paquet getPaquet() {
		return this.paquet;
	}
	
	public Player[] getPlayers() {
		return this.tabPlayers;
	}
	
	public int getMoney(int player_now) {
		return this.tabPlayers[player_now].getMoney();
	}
    
    public int getNbPlayers() {
        return nb_players;
    }
	
	/* --------------------------- *
	 *   SET - change the values
	 * --------------------------- */
	public boolean setBetTable(int player_now, int n) {
		if(this.tabPlayers[player_now].getMoney() >= n) {
			this.tabPlayers[player_now].setBet(n + this.tabPlayers[player_now].getBet());
			this.tabPlayers[player_now].setMoney(this.tabPlayers[player_now].getMoney() - n);
			
			return false;
		}
		
		return false;
	}
    
    public void setNbPlayers(int nb_players) {
        this.nb_players = nb_players;
    }
}
