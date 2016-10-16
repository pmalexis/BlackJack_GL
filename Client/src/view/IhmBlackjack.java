package view;

import java.awt.Cursor;

import javax.swing.JFrame;

import controller.Controleur;

@SuppressWarnings("serial")
public class IhmBlackjack extends JFrame {

	public IhmBlackjack(Controleur crtl) {
		setTitle("BLACKJACK");
		setLocation(100, 100);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setSize(1024, 768);
		
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
		add(new Start(this));
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
		IhmBlackjack ihm = new IhmBlackjack(new Controleur());
		ihm.setVisible(true); //bug avec linux ???
	}
}
