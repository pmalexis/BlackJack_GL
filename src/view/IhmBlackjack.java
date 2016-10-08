package view;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IhmBlackjack extends JFrame {

	public IhmBlackjack() {
		
		this.setTitle("BLACKJACK");
		this.setLocation(100, 100);
		this.setSize(600, 640);
		
		//gerer la fermeture fenetre
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		} );

		this.setVisible(true); 
	}
	
	public void initMenu() {}
	
	public void initVisuIhm() {}
	
	public void initOption() {}
	
	public void initCredit() {}
	
	public static void main(String[] args) {
		IhmBlackjack ihm = new IhmBlackjack();
	}
}
