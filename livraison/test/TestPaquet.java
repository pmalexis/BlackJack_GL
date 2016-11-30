package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import model.cards.*;
import org.junit.Test;

public class TestPaquet {

	@Test
	public void addBot() {
		
		Paquet paquet = new Paquet();
		Carte card = new Carte(1, Couleur.Pique);
		Carte otherCard = new Carte(3, Couleur.Coeur);
		paquet.addBot(card);
		paquet.addBot(otherCard);

		//on compare la premiere carte du paquet de base apres en avoir ajoute une autre
		assertNotSame(card, paquet.getAlCard().get(0));
		
	}
	
	@Test
	public void addTop() {
		
		Paquet paquet = new Paquet();
		Carte card = new Carte(1, Couleur.Pique);
		Carte middleCard = new Carte(3, Couleur.Coeur);
		Carte lastCard = new Carte(4, Couleur.Trefle);
		paquet.addTop(card);
		paquet.addTop(middleCard);
		paquet.addTop(lastCard);

		//on compare la derniere carte du paquet apres en avoir ajoute deux autres
		assertNotSame(card, paquet.getAlCard().get(2));
		
	}
	
	@Test
	public void dropTop(){
		
		Paquet paquet = new Paquet();
		Carte card1 = new Carte(1, Couleur.Coeur);
		Carte card2 = new Carte(2, Couleur.Coeur);
		Carte card3 = new Carte(3, Couleur.Coeur);
		paquet.addTop(card1);
		paquet.addTop(card2);
		paquet.addTop(card3);
		paquet.dropTop();
		assertNotEquals(card3, paquet.getAlCard().get(paquet.getAlCard().size()-1));
		
	}
	
	@Test
	public void dropBot(){
		
		Paquet paquet = new Paquet();
		Carte card1 = new Carte(1, Couleur.Coeur);
		Carte card2 = new Carte(2, Couleur.Coeur);
		paquet.addTop(card1);
		paquet.addTop(card2);
		paquet.dropBot();
		assertNotEquals(card1, paquet.getAlCard().get(0));
	}
	
	@Test
	public void mixCards() {
		Paquet paquet = makePaquet();
		Paquet mixedPaquet = makePaquet();
		mixedPaquet.mixCards();
		
		assertNotEquals(paquet.getAlCard(), mixedPaquet.getAlCard());
		
	}
	
	@Test
	public void drawRandom() {
		Paquet paquet = makePaquet();
		Carte one = paquet.drawRandom();
		Carte two = paquet.drawRandom();
		
		assertNotEquals(one, two);
		
	}
	
	@Test
	public void cutPack() {
		Paquet paquet = makePaquet();
		Paquet cutPaquet = makePaquet();
		
		cutPaquet.cutPack();
		
		assertNotEquals(paquet.getAlCard(), cutPaquet.getAlCard());

	}
	
	private Paquet makePaquet() {
		Paquet paquet = new Paquet();
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carreau, Couleur.Coeur};
		
		for(int nb_pack=0;nb_pack<1;nb_pack++) { //nb pack
			for(int i=0;i<4;i++) {// 4 colors
				for(int j=1;j<14;j++) { // 13 cards
					paquet.addTop(new Carte(j, tabCouleur[i]));
				}
            }
        }
		
		return paquet;
	}

}
