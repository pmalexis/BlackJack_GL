package model.bot;

import model.MoteurBlackjack;
import model.Player;

public abstract class Bot extends Player {
	
	public Bot(String name) {
		super(name, 5555);
	}

	public abstract void bet(MoteurBlackjack moteur);
	
	public abstract void play(MoteurBlackjack moteur);
}
