package cartes;

/*
 * couleur = pique, trefle, coeur, carre
 */
public enum Couleur {
	Pique ("pique"),
	Trefle ("trefle"),
	Coeur ("coeur"),
	Carre ("carre");
	
	private String name = "";
 
	Couleur(String name){
		this.name = name;
	}
	   
	public String toString(){
		return name;
	}
}
