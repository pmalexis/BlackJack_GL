package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseMotionListener, MouseListener {
	
	private IhmBlackjack ihm;
	
	private int x = 0;
	private int y = 0;
	
	public Menu(IhmBlackjack ihm) {
		this.ihm = ihm;
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/10));
		g.drawString("BLACKJACK", this.getWidth()/5, this.getHeight()/5);

		g.setFont(new Font("Arial", Font.PLAIN, getWidth()/20)); 
		if(this.x >= getWidth()/3 + getWidth()/12 && this.x <= this.getWidth()/3 + getWidth()/4 
		   && this.y <= getHeight()/3 && this.y >= getHeight()/3 - getHeight()/15) g.setColor(Color.yellow);
		g.drawString("START", getWidth()/3 + getWidth()/12, getHeight()/3); g.setColor(Color.white);
		
		if(this.x >= getWidth()/3 && this.x <= getWidth()/3 + getWidth()/3
		   && this.y <= getHeight()/2 - getHeight()/18 && this.y >= getHeight()/2 - getHeight()/8) g.setColor(Color.yellow);
		g.drawString("HIGHTSCORE", getWidth()/3, getHeight()/2 - getHeight()/18); g.setColor(Color.white);
		
		if(this.x >= getWidth()/3 + getWidth()/14 && this.x <= getWidth()/3 + getWidth()/4 + getWidth()/100
		   && this.y <= getHeight()/2 + getHeight()/18 && this.y >= getHeight()/2 - getHeight()/65) g.setColor(Color.yellow);
		g.drawString("OPTION", getWidth()/3 + getWidth()/14, getHeight()/2 + getHeight()/18); g.setColor(Color.white);
		
		if(this.x >= getWidth()/3 + getWidth()/13 && this.x <= getWidth()/3 + getWidth()/4 + getWidth()/90
		   && this.y <= getHeight()/2 + getHeight()/6 && this.y >= getHeight()/2 + getHeight()/10) g.setColor(Color.yellow);
		g.drawString("CREDIT", getWidth()/3 + getWidth()/13, getHeight()/2 + getHeight()/6); g.setColor(Color.white);
		
		if(this.x >= getWidth()/3 + getWidth()/12 && this.x <= getWidth()/3 + getWidth()/4
           && this.y <= getHeight()/2 + getHeight()/4 + getHeight()/30 && this.y >= getHeight()/2 + getHeight()/5 + getHeight()/50) g.setColor(Color.yellow);
		g.drawString("CLOSE", getWidth()/3 + getWidth()/12, getHeight()/2 + getHeight()/4 + getHeight()/30); g.setColor(Color.white);
		
		g.setFont(new Font("Arial", Font.PLAIN, getWidth()/60));
		g.drawString("Copyright © 2016 - UFR de Sciences - Universite Caen-Normandie", getWidth()/3 - getWidth()/15, getHeight() - getHeight()/50);
	}

	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		
		//START
		if(this.x >= getWidth()/3 + getWidth()/12 && this.x <= this.getWidth()/3 + getWidth()/4 
			       && this.y <= getHeight()/3 && this.y >= getHeight()/3 - getHeight()/15) {
			System.out.println("Bientot bientot du calme ^^");
		}
		
		//HIGHTSCORE
		if(this.x >= getWidth()/3 && this.x <= getWidth()/3 + getWidth()/3
			      && this.y <= getHeight()/2 - getHeight()/18 && this.y >= getHeight()/2 - getHeight()/8) {
			System.out.println("coming soon");
		}
		
		//OPTION
		if(this.x >= getWidth()/3 + getWidth()/14 && this.x <= getWidth()/3 + getWidth()/4 + getWidth()/100
			      && this.y <= getHeight()/2 + getHeight()/18 && this.y >= getHeight()/2 - getHeight()/65) {
			System.out.println("chut tu l'auras mais avant la detente ok ;)");
		}
		
		//CREDIT
		if(this.x >= getWidth()/3 + getWidth()/13 && this.x <= getWidth()/3 + getWidth()/4 + getWidth()/90
				&& this.y <= getHeight()/2 + getHeight()/6 && this.y >= getHeight()/2 + getHeight()/10) {
			this.ihm.credit();
		}
		
		//CLOSE
		if(this.x >= getWidth()/3 + getWidth()/12 && this.x <= getWidth()/3 + getWidth()/4
		   && this.y <= getHeight()/2 + getHeight()/4 + getHeight()/30 && this.y >= getHeight()/2 + getHeight()/5 + getHeight()/50) {
			System.exit(0);
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e)  {}
	
	public void update(Graphics g) {
        Graphics offgc;
        Image offscreen = null;
        Dimension d = getSize();

        offscreen = createImage(d.width, d.height);
        offgc = offscreen.getGraphics();
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, d.width, d.height);
        offgc.setColor(getForeground());
        paint(offgc);
        g.drawImage(offscreen, 0, 0, this);
    }
}
