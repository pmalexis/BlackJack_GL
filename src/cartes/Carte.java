package cartes;

import java.awt.Color;

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
	 *   SET - change the values
	 * --------------------------- */
	public void setHauteur(int h) {
		this.hauteur = h;
	}
	
	public void setCouleur(Couleur c) {
		this.couleur = c;
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
	
	/*
	 * Return string card
	 */
	public String toString() {
		return this.hauteur + " - " + this.couleur;
	}
}
