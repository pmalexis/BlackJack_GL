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

import model.Player;
import server.Controleur;

@SuppressWarnings("serial")
public class DrawToken extends JPanel implements MouseListener {

	private Start  start;
	private Controleur ctrl;
	
	public DrawToken(Start start, Controleur ctrl) {
		this.start = start;
		this.ctrl  = ctrl;
		
		this.addMouseListener(this);
	}
	
	public int[] getNbToken() {
		int[] tabNbJeton = new int[Jeton.values().length];
		int money = this.ctrl.getThisPlayer(Start.identifiant).getMoney();
		int cpt = 0;
		while(money != 0) {
			cpt = 0;
			for(Jeton j : Jeton.values()) {
				if(money >= j.getValeur()) {
					money -= j.getValeur();
					tabNbJeton[cpt] += 1;
				}
				cpt++;
			}
		}
		return tabNbJeton;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.ITALIC, getWidth()/75));
		try {
			Jeton[] tabJeton = Jeton.values();
			int[] tabNbJeton = this.getNbToken();
			int i = 0;
			int cpt = getWidth()/9;
			for(Jeton j : tabJeton) {
 				g.drawImage(ImageIO.read(new File(j.getChemin())), cpt, 5, getWidth()/23, getHeight() - 30, null);
 				g.drawString("x" + tabNbJeton[i], cpt + ((getWidth()/15) / 8), getHeight() - 5);
 				cpt += getWidth()/15;
 				i++;
			}
			g.setFont(new Font("Arial", Font.ITALIC, getWidth()/30));
			g.drawString("=> " + this.ctrl.getThisPlayer(Start.identifiant).getMoney() + "€", cpt, getWidth()/26);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void mouseClicked(MouseEvent e) {
		
		Jeton[] tabJeton = Jeton.values();
		int[] tabNbJeton = this.getNbToken();
		int i = 0;
		int cpt = getWidth()/9;
		for(Jeton j : tabJeton) {
			if(e.getX() >= cpt && e.getX() <= cpt + getWidth()/23) {
				if(tabNbJeton[i] > 0) {
					this.ctrl.setBetTable(this.ctrl.getThisPlayer(Start.identifiant), j.getValeur());
					this.start.getVisu().setText(this.ctrl.getThisPlayer(Start.identifiant).getBet());
				}
			}
			cpt += getWidth()/15;
			i++;
		}
		revalidate();
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e)  {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e){}
}
