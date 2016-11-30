package model.bot;

import model.MoteurBlackjack;

public class BotDefensif extends Bot {

	public BotDefensif() {
		super("bot_def");
	}

	public void bet(MoteurBlackjack moteur) {
		moteur.setBetTable(this, this.getMoney()/10);
	}
	
	public void play(MoteurBlackjack moteur) {
		if(this.computeValue(false) < 12) {
			moteur.hit(this, false);
		}
		else this.setTurnDown(true);
	}

}
