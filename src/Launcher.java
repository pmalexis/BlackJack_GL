import controleur.Controleur;
import model.MoteurBlackjack;
import view.View;

/**
 * 
 * @author Alexis Prevost-Maynen 
 * @author Guillaume 
 * @author Lucas
 * @author Nicolas
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
