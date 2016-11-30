package model.cards;

/**
 * Cette classe créée une carte qui est créer à partir de la classe Paquet
 */
public class Carte {
	
	private int hauteur;
	private Couleur couleur;
	
	/**
	 * Constructeur
	 * @param hauteur (entre 1 et 13)
	 * @param couleur (enum)
	 */
	public Carte(int hauteur, Couleur couleur) {
		this.hauteur = hauteur;
		this.couleur = couleur;
	}

	/**
	 * @return hauteur
	 */
	public int getHauteur() {
		return this.hauteur;
	}
	
	/**
	 * @return couleur
	 */
	public Couleur getCouleur() {
		return this.couleur;
	}
}
