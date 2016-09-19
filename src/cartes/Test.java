package cartes;

import java.awt.Color;

public class Test {
	
	public static void main(String[] args) {
		
		Paquet paquet = new Paquet();
		
		for(int i=0;i<4;i++)
			for(int j=1;j<14;j++) {
				if(i < 2) paquet.addTop(new Carte(j, 0));
				else paquet.addTop(new Carte(j, 1));
			}
		
		System.out.println(paquet.toString());
	}
}
