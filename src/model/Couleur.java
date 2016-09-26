package model;

/*
 * couleur = pique, trefle, coeur, carre
 */
public enum Couleur {
	Pique ("pique"),
	Trefle ("trefle"),
	Coeur ("coeur"),
	Carreau ("carreau");
	
	private String name = "";
 
	Couleur(String name){
		this.name = name;
	}
	   
	public String toString(){
		return name;
	}
}
