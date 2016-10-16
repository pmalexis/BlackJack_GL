package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
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
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Option extends JPanel implements ActionListener, MouseListener {

	private IhmBlackjack ihm;
	
	private JButton ajout;
	private JButton suppr;
	private JButton retourMenu;
	
	private JTextField textAjout;
	
	private List listJoueurs;
	
	public Option(IhmBlackjack ihm) {
		setLayout(null);
		this.ihm = ihm;
		
		textAjout = new JTextField(" NOM_JOUEUR_EN_MAJ (Ex : DRAXXO)");
		
		ajout = new JButton("Ajouter joueur");
		ajout.addActionListener(this);
		
		listJoueurs = new List();
		this.updateList();
		
		suppr = new JButton("Supprimer joueur");
		suppr.addActionListener(this);
		
		retourMenu = new JButton("Retour menu");
		try {
		    Image img = ImageIO.read(new File("res/img/menu/retour_menu.png"));
		    retourMenu.setIcon(new ImageIcon(img));
		    retourMenu.setBorderPainted(false);
		    retourMenu.setBorder(null);
		    retourMenu.addMouseListener(this);
		    retourMenu.addActionListener(this);
		  } catch (IOException ex) {}
		
		add(textAjout);
		add(ajout);
		add(listJoueurs);
		add(suppr);
		add(retourMenu);
	}
	
	public void updateList() {
		this.listJoueurs.removeAll();
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		Collections.sort(al);
		for(String s : al) 
			listJoueurs.add(s.split("-")[0]);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		FontRenderContext context = g2.getFontRenderContext();
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		
		g.setColor(Color.WHITE);
		String title = "OPTION";
		Font f = new Font("Arial", Font.ITALIC, this.getWidth()/10);
		g.setFont(f);
		TextLayout txt = new TextLayout(title, f, context);
		Rectangle2D bounds = txt.getBounds();
		int xString = (int) ((getWidth() - bounds.getWidth()) / 2.0 );
		g.drawString(title, xString, this.getHeight()/5);
		
		textAjout.setBounds(getWidth()/3,getWidth()/7,getWidth()/5 + getWidth()/40, getHeight()/20);
		textAjout.setFont(new Font("Arial", Font.PLAIN, getHeight()/50));
		ajout.setBounds(getWidth()/2 + getWidth()/14, getWidth()/7, getWidth()/9, getHeight()/20);
		listJoueurs.setBounds(getWidth()/3, getWidth()/5, getWidth()/5 + getWidth()/40, getHeight()/3);
		listJoueurs.setFont(new Font("Arial", Font.PLAIN, getHeight()/30));
		suppr.setBounds(getWidth()/2 + getWidth()/14, getWidth()/4 + getWidth()/40, getWidth()/9, getHeight()/20);
		retourMenu.setBounds(getWidth()/4 + getWidth()/5, getHeight() - getHeight()/4, getWidth()/8,getHeight()/20);
		
		g.setFont(new Font("Arial", Font.PLAIN, getWidth()/60));
		g.drawString("Copyright © 2016 - UFR de Sciences - Universite Caen-Normandie", getWidth()/3 - getWidth()/15, getHeight() - getHeight()/50);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.retourMenu) this.ihm.menu();
		else if(e.getSource() == this.ajout) {
			boolean b = false;
			ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
			
			for(String s : al) {
				if(s.split("-")[0].equals(this.textAjout.getText().toUpperCase()))  {
					this.textAjout.setText(this.textAjout.getText() + " : this player is already on the list");
					b = true; 
				}
			}
			
			if(!b && this.textAjout.getText().trim().length() > 10) {
				this.textAjout.setText("NOM_JOUEUR 10 caractere maximum");
				b = true;
			}
			
			if(!b) {
				Fichier.add(this.textAjout.getText());
				this.textAjout.setText(this.textAjout.getText() + " : is add to the list");
			}
		}
		else if(e.getSource() == this.suppr) {
			if(this.listJoueurs.getSelectedIndex() > 0)
				Fichier.remove(this.listJoueurs.getSelectedItem());
		}
		
		this.updateList();
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
}
