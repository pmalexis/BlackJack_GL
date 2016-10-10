package view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controller.Server;

public class IhmBlackjack extends JFrame {

	public IhmBlackjack(/*Server server*/) {
		
		this.setTitle("BLACKJACK");
		this.setLocation(100, 100);
		this.setSize(800, 600);
		
		//gerer la fermeture fenetre
		addWindowListener(new WindowAdapter() {
                    
                    public view.QuitterAction quitterAction = new view.QuitterAction(" Au revoir ! ");

                    public void windowClosing(WindowEvent evt) {
                        this.quitterAction.actionPerformed(evt);
                    }
                    
//                  public void windowClosing(WindowEvent evt) {
//			System.exit(0);
//                  }
		} );

		this.setVisible(true); 
	}
	
	public void initMenu() {}
	
	public void initVisuIhm() {}
	
	public void initOption() {}
	
	public void initCredit() {}
	
	public static void main(String[] args) {
		//IhmBlackjack ihm = new IhmBlackjack(new Server(1234));
        IhmBlackjack ihm = new IhmBlackjack();
	}
}
