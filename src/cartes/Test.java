package cartes;

import java.awt.Color;

public class Test {
	
	public static void main(String[] args) {
		
		Paquet paquet = new Paquet();
		
		Couleur[] tabCouleur = {Couleur.Pique, Couleur.Trefle, Couleur.Carre, Couleur.Coeur};
		
		for(int i=0;i<4;i++) // 4 type
			for(int j=1;j<14;j++) { // 13 carte
				paquet.addTop(new Carte(j, tabCouleur[i]));
			}
		
		System.out.println(paquet.toString());
		
		paquet.cutPack();
		
		System.out.println(paquet.toString());
	}
}
