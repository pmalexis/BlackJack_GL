package view.ihm;

public enum Jeton {

	WHITE (1,    "res/img/tokens/white.png" ),
	RED   (5,    "res/img/tokens/red.png"   ),
	BLUE  (10,   "res/img/tokens/blue.png"  ),
	YELLOW(25,   "res/img/tokens/yellow.png"),
	GREEN (50,   "res/img/tokens/green.png" ),
	BLACK (100,  "res/img/tokens/black.png" ),
	PURPLE(500,  "res/img/tokens/purple.png"),
	ORANGE(1000, "res/img/tokens/orange.png"),
	GRAY  (5000, "res/img/tokens/gray.png"  );
	
	private int valeur;
	private String chemin; 
	
	Jeton(int v, String s) {
		this.valeur = v;
		this.chemin = s;
	}
	   
	public int getValeur(){
		return this.valeur;
	}
	
	public String getChemin() {
		return this.chemin;
	}
}
