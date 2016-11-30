package model.bot;

import model.MoteurBlackjack;

public class BotAgressif extends Bot {
	
	public BotAgressif() {
		super("bot_atk");
	}

	public void bet(MoteurBlackjack moteur) {
		moteur.setBetTable(this, this.getMoney()/2);
	}
	
	public void play(MoteurBlackjack moteur) {
		if(this.computeValue(false) < 18) {
			moteur.hit(this, false);
		}
		else this.setTurnDown(true);
	}
}
