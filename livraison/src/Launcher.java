import controleur.Controleur;
import model.MoteurBlackjack;
import view.View;

/**
 * 
 * @author Alexis Prevost-Maynen - 21511372
 * @author Guillaume Drouart - 21009341 
 * @author Lucas Gouédard - 21506676
 * @author Nicolas Vatel - 21410740
 *
 */
public class Launcher {
	
	/**
	 * Permet de lancer toute les classes 
	 * necessaire au lancement du programme
	 */
	public static void main(String[] args) {
		MoteurBlackjack moteur = new MoteurBlackjack();
		Controleur ctrl = new Controleur(moteur);
		View view = new View(ctrl);	
		moteur.addObserver(view);
	}
}
