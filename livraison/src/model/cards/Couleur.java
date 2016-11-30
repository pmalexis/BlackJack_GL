package model.cards;

/**
 * Cet enum permet de connaître tous les types des cartes
 * Il ne peut y avoir que les types présents ci-dessous
 */
public enum Couleur {
	Pique ("pique"),
	Trefle ("trefle"),
	Coeur ("coeur"),
	Carreau ("carreau");
	
	private String name = "";
 
	/**
	 * Constructeur
	 * @param name
	 */
	Couleur(String name){
		this.name = name;
	}
	
	/**
	 * @return name
	 */
	public String toString(){
		return name;
	}
}
