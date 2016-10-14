package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credit extends JPanel implements MouseMotionListener, MouseListener {

	private IhmBlackjack ihm;
	
	private int x;
	private int y;

	private int nb;
	
	public Credit(IhmBlackjack ihm) {
		this.ihm = ihm;
		
		this.x = -1;
		this.y = -1;
		
		JLabel l = new JLabel("grrr gr gregjreo gjejr");
		add(l, BorderLayout.NORTH);
		
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paint(Graphics g) { 
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		
		/*
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("CREDIT", 320, 120);

		g.setFont(new Font("Arial", Font.PLAIN, 20));
		BufferedReader br = Fichier.getFichier("credit.txt");
		String str = "";
		nb = 160;
		try {
			while( (str = br.readLine()) != null ) {
				g.drawString(str, 80, nb);
				nb += 22;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		nb += 44;
		if(this.x >= 80 && this.x <= 120 && this.y >= nb - 20 && this.y <= nb) g.setColor(Color.yellow);
		g.drawString("RETOUR MENU", 80, nb);
		*/
	}

	public void mouseMoved(MouseEvent e) {
		/*this.x = e.getX();
		this.y = e.getY();
		
		if(this.x >= 70 && this.x <= 130 && this.y >= nb - 30 && this.y <= nb) repaint();*/
	}
	
	public void mouseClicked(MouseEvent e) {
		this.ihm.menu();
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
