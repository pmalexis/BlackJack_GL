package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.Launcher;

@SuppressWarnings("serial")
public class Highscore extends JPanel implements ActionListener, MouseListener {

		private Launcher ihm;

		private JButton retourMenu;
		
		private int nb;
		
		public Highscore(Launcher ihm) {
			setLayout(null);
			this.ihm = ihm;
			
			retourMenu = new JButton("Retour menu");
			try {
			    Image img = ImageIO.read(new File("res/img/menu/retour_menu.png"));
			    retourMenu.setIcon(new ImageIcon(img));
			    retourMenu.setBorderPainted(false);
			    retourMenu.setBorder(null);
			    retourMenu.addMouseListener(this);
			    retourMenu.addActionListener(this);
			  } catch (IOException ex) {}
			
			add(retourMenu);
		}
		
		public void paint(Graphics g) { 
			Graphics2D g2 = (Graphics2D) g;
			FontRenderContext context = g2.getFontRenderContext();

			try {
				g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
			} catch (IOException e) { e.printStackTrace(); }
			
			g.setColor(Color.white);
			Font f = new Font("Arial", Font.ITALIC, this.getWidth()/10);
			g.setFont(f);
			String title = "HIGHSCORE";
			TextLayout txt = new TextLayout(title, f, context);
			Rectangle2D bounds = txt.getBounds();
			int xString = (int) ((getWidth() - bounds.getWidth()) / 2.0 );
			g.drawString(title, xString, this.getHeight()/5);

			f = new Font("Arial", Font.PLAIN, this.getWidth()/60);
			g.setFont(f);
			nb = 320;
			ArrayList<String> tempoAl = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
			for(int j=0;j<=2;j++)
				for(int i=0;i<tempoAl.size()-1;i++) {
					if(Integer.parseInt(tempoAl.get(i).split("-")[1].trim()) < Integer.parseInt(tempoAl.get(i+1).split("-")[1].trim())) {
						tempoAl.add(i, tempoAl.remove(i+1));
						i=0;
					}
				}
			
			int i=0;
			for(String s : tempoAl) {
				txt = new TextLayout(s, f, context);
				bounds = txt.getBounds();
				xString = (int) ((getWidth() - bounds.getWidth()) / 2.0 );
				g.drawString(s, xString, nb);
				nb += this.getWidth()/50;
				
				if(++i > 15) break;
			}
			
			retourMenu.setBounds(getWidth()/4 + getWidth()/5, nb, getWidth()/8,getHeight()/20);

			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.PLAIN, getWidth()/60));
			g.drawString("Copyright © 2016 - UFR de Sciences - Universite Caen-Normandie", getWidth()/3 - getWidth()/15, getHeight() - getHeight()/50);
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == this.retourMenu) this.ihm.menu();
		}


		
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() == this.retourMenu) {
				try {
				    Image img = ImageIO.read(new File("res/img/menu/retour_menu_hover.png"));
				    retourMenu.setIcon(new ImageIcon(img));
				  } catch (IOException ex) {}
			}
		}


		public void mouseExited(MouseEvent e) {
			if(e.getSource() == this.retourMenu) {
				try {
				    Image img = ImageIO.read(new File("res/img/menu/retour_menu.png"));
				    retourMenu.setIcon(new ImageIcon(img));
				  } catch (IOException ex) {}
			}
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
}
