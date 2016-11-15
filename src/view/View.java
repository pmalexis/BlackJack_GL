package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controleur.Controleur;
import view.ihm.Credit;
import view.ihm.Highscore;
import view.ihm.Menu;
import view.ihm.Option;
import view.ihm.Start;

@SuppressWarnings("serial")
public class View extends JFrame implements Observer{

	public View(Controleur crtl) {
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
		setVisible(true);
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

	@Override
	public void update(Observable o, Object arg) {
		System.out.println((String) arg);
		
	}
}
