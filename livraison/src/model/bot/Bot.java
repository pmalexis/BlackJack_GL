package model.bot;

import model.MoteurBlackjack;
import model.Player;

public abstract class Bot extends Player {
	
	/**
	 * Constructor
	 * @param name
	 */
	public Bot(String name) {
		super(name, 5555);
	}

	/**
	 * The bot bets
	 * @param moteur (needs the moteur to call the moteur's functions)
	 */
	public abstract void bet(MoteurBlackjack moteur);
	
	/**
	 * The bot plays
	 * @param moteur (needs the moteur to call the moteur's functions)
	 */
	public abstract void play(MoteurBlackjack moteur);
}
