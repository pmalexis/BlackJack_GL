package cartes;

import java.awt.Color;

public class Carte {
	
	private final String[] tabColor = { "noir", "rouge" };
	
	private int hauteur;
	private int couleur;
	
	/*
	 * Constructor
	 */
	public Carte(int hauteur, int couleur) {
		this.hauteur = hauteur;
		this.couleur = couleur;
	}
	
	/* --------------------------- *
	 *   SET - change the values
	 * --------------------------- */
	public void setHauteur(int h) {
		this.hauteur = h;
	}
	
	public void setCouleur(int c) {
		this.couleur = c;
	}
	
	/* --------------------------- *
	 *   GET - return the values
	 * --------------------------- */
	public int getHauteur() {
		return this.hauteur;
	}
	
	public int getCouleur() {
		return this.couleur;
	}
	
	/*
	 * Return string card
	 */
	public String toString() {
		return this.hauteur + " - " + this.tabColor[this.couleur];
	}
}
