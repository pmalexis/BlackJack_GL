import controleur.Controleur;
import model.MoteurBlackjack;
import view.View;

public class Launcher {
	
	public static void main(String[] args) {
		MoteurBlackjack moteur = new MoteurBlackjack();
		Controleur ctrl = new Controleur(moteur);
		ctrl.addPlayer("monsieur");
		View view = new View(ctrl);	
		moteur.addObserver(view);
	}
}
