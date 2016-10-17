package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import view.Launcher;

@SuppressWarnings("serial")
public class Start extends JPanel implements MouseListener {

	private Launcher ihm;
	
	public Start(Launcher ihm) {
		this.ihm = ihm;
		
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/30));
		g.drawString("C'est l'heure du du du du branchement du server !!!", 10,getHeight()/2);
	}

	public void mouseClicked(MouseEvent e) {
		this.ihm.menu();
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
