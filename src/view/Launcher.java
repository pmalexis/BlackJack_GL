package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import server.Controleur;
import view.ihm.Credit;
import view.ihm.Highscore;
import view.ihm.Menu;
import view.ihm.Option;
import view.ihm.Start;

@SuppressWarnings("serial")
public class Launcher extends JFrame {

	private Controleur ctrl;
	
	public Launcher(Controleur ctrl) {
		
		this.ctrl = ctrl;
		
		setTitle("BLACKJACK");
		setLocation(100, 100);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
		setSize(width, height);
		
		setUndecorated(true);
		this.menu();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setResizable(false);
	}
	
	public void menu() {
		getContentPane().removeAll();
		repaint();
		add(new Menu(this));
		this.setVisible(true); 
	}
	
	public void start() {
		getContentPane().removeAll();
		repaint();
		add(new Start(this, this.ctrl));
		this.setVisible(true);
	}
	
	public void highscore() {
		getContentPane().removeAll();
		repaint();
		add(new Highscore(this));
		this.setVisible(true);
	}
	
	public void option() {
		getContentPane().removeAll();
		repaint();
		add(new Option(this));
		this.setVisible(true);
	}
	
	public void credit() {
		getContentPane().removeAll();
		repaint();
		add(new Credit(this));
		this.setVisible(true); 
	}
	
	public static void main(String[] args) {
		Launcher ihm = new Launcher(new Controleur());
		ihm.setVisible(true);
	}
}
