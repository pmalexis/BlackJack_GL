package model;

public enum Jeton {

	BLANC(1),
	ROUGE(5),
	BLEU(10),
	JAUNE(25),
	VERT(50),
	NOIR(100),
	VIOLET(500),
	ORANGE(1000),
	GRIS(5000);
	
	private int valeur = 0;
	 
	Jeton(int v) {
		this.valeur = v;
	}
	   
	public int getValeur(){
		return this.valeur;
	}
}
