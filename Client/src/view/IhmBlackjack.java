package view;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import controller.Controleur;

public class IhmBlackjack extends JFrame implements KeyListener {

	public IhmBlackjack(Controleur crtl) {
		setTitle("BLACKJACK");
		setLocation(100, 100);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setUndecorated(true);
		setExtendedState(this.MAXIMIZED_BOTH); 
		setSize(1024, 768);
		setResizable(false);
		
		add(new Menu(this));

		addKeyListener(this);
		this.setVisible(true); 
	}
	
	public void menu() {
		getContentPane().removeAll();
		repaint();
		add(new Menu(this));
		this.setVisible(true); 
	}
	
	public void credit() {
		getContentPane().removeAll();
		repaint();
		add(new Credit(this));
		this.setVisible(true); 
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public static void main(String[] args) {
		IhmBlackjack ihm = new IhmBlackjack(new Controleur(2));
	}
}
