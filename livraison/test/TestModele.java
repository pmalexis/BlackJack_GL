package test;

import static org.junit.Assert.*;

import model.MoteurBlackjack;
import model.Player;

public class TestModele {

	@org.junit.Test
	public void addPlayer() {
		
		MoteurBlackjack mBJ = new MoteurBlackjack();
		Player p = new Player("test", 654);
		mBJ.addPlayer(p);		
		assertEquals(p, mBJ.getPlayers().get(0));
	}
	
	
	@org.junit.Test
	public void initAll() {
		
		MoteurBlackjack mBJ = new MoteurBlackjack();
		mBJ.initAll();
		assertNotNull(mBJ.getPlayers().get(0));	
		
	}
	
	

}
