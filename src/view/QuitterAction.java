package view;

import java.awt.event.WindowEvent;

public class QuitterAction {
	private final String texte;

	public QuitterAction(String texte){
		this.texte=texte;
	}
	

	public void actionPerformed(WindowEvent evt) {
		System.out.println(texte);
		System.exit(0);
	}

}
