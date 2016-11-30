package model.cards;

public class Carte {
	
	private int hauteur;
	private Couleur couleur;
	
	/*
	 * Constructor
	 */
	public Carte(int hauteur, Couleur couleur) {
		this.hauteur = hauteur;
		this.couleur = couleur;
	}
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public int getHauteur() {
		return this.hauteur;
	}
	
	public Couleur getCouleur() {
		return this.couleur;
	}
}
