package model.cards;

public enum Jeton {

	WHITE(1),
	RED(5),
	BLUE(10),
	YELLOW(25),
	GREEN(50),
	BLACK(100),
	PURPLE(500),
	ORANGE(1000),
	GRAY(5000);
	
	private int valeur = 0;
	 
	Jeton(int v) {
		this.valeur = v;
	}
	   
	public int getValeur(){
		return this.valeur;
	}
}
