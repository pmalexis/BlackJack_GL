package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import model.Player;
import model.bot.Bot;
import model.cards.Carte;
import model.cards.Couleur;
import model.cards.Paquet;

public class Visu extends JPanel implements ActionListener {

	private JLabel bet;
	private JButton parier;
	
	private boolean pari = false;
	
	private Controleur ctrl;
	private Start      start;
	
	private Image[] tabImages;
	private boolean banquier = false;
	private boolean score    = false;
	
	public Visu(Controleur ctrl, Start start) {
		setLayout(null);
		
		this.ctrl  = ctrl;
		this.start = start;
		
		this.tabImages = new Image[53];
		
		this.bet = new JLabel("");
        this.bet.setForeground(Color.WHITE);
        add(this.bet);
        
        this.parier = new JButton("COMMENCER LA PARTIE");
        this.parier.addActionListener(this);
        add(this.parier);
        this.parier.setEnabled(false);
        
        this.chargerImage();
	}
	
	public void chargerImage() {
		
		String[] tabType = {"pique", "coeur","trefle","carreau"};
		
		int cpt = 0;
		int nbCarte = 1;
		
		for(int i=0;i<52;i++) {
			if(nbCarte == 14) {
				nbCarte = 1;
				cpt++;
			}
			this.tabImages[i] = Toolkit.getDefaultToolkit().getImage("res/img/cards/" + tabType[cpt] + "_" + nbCarte + ".png");
			nbCarte++;
		}
		this.tabImages[52] = Toolkit.getDefaultToolkit().getImage("res/img/cards/back.jpg");
	}
	
	public Image getImage(Couleur couleur, int valeur) {
		if(valeur > 52) return this.tabImages[52];
	
		//pique += 0  --  coeur += 13 -- trefle += 26 -- carreau += 39
		int ajout = 0;
		if(couleur == Couleur.Coeur) ajout = 13;
		if(couleur == Couleur.Trefle) ajout = 26;
		if(couleur == Couleur.Carreau) ajout = 39;
		
		return this.tabImages[valeur + ajout -1];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int betWidth = getWidth()/2-getWidth()/25;
		g.setColor(Color.WHITE);
		if(!pari) {
			this.parier.setBounds(getWidth()/2-getWidth()/20, getHeight()-getHeight()/8, this.getWidth()/10, this.getHeight()/20);
		}
		else {
			//on dessine les cartes du banquier + pioche
			int cpt = 0;
			for(int i=0;i<10;i++) {
				g.drawImage(this.getImage(null, 53), 10 + cpt, 10 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);
				cpt += 3;
			}

			Paquet test = this.ctrl.getThisPlayer(0).getHand();
			cpt = 0;
			int nbCarte = 1;
			for(Carte c : test.getAlCard()) {
				if(!this.ctrl.getThisPlayer(0).getTurnDown() && nbCarte == 2) 
					g.drawImage(this.getImage(null, 53), getWidth()/2-getWidth()/15 + cpt, 10 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);	
				else g.drawImage(this.getImage(c.getCouleur(), c.getHauteur()), getWidth()/2-getWidth()/15 + cpt, 10 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);
				cpt += getWidth()/50;
				nbCarte++;
			}
			
			g.setFont(new Font("Arial", Font.ITALIC, getWidth()/40));
			//On dessine les cartes du joueurs principal
			if(this.ctrl.getThisPlayer(Start.identifiant).computeValue(true) > 0) {
				if(this.ctrl.getThisPlayer(Start.identifiant).getInsurance() > 0)
					g.drawString("Insurance : " + this.ctrl.getThisPlayer(Start.identifiant).getInsurance() + " | " + this.ctrl.getThisPlayer(Start.identifiant).getInsuranceSplit(), getWidth()/2 - getWidth()/7, getHeight()/2 - 20);
				
				test = this.ctrl.getThisPlayer(Start.identifiant).getHand();
				cpt = 0;
				for(Carte c : test.getAlCard()) {
					//System.out.println(c.getCouleur() + "_" + c.getHauteur());
					try {
						g.drawImage(ImageIO.read(new File("res/img/cards/"+c.getCouleur() + "_" + c.getHauteur()+".png")), getWidth()/2-getWidth()/7 + cpt, getHeight() - getHeight()/2 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);
						cpt += getWidth()/50;
					} catch (IOException e) { e.printStackTrace(); }
				}
				
				test = this.ctrl.getThisPlayer(Start.identifiant).getSplit();
				cpt = 0;
				for(Carte c : test.getAlCard()) {
					//System.out.println(c.getCouleur() + "_" + c.getHauteur());
					try {
						g.drawImage(ImageIO.read(new File("res/img/cards/"+c.getCouleur() + "_" + c.getHauteur()+".png")), getWidth()/2 + cpt, getHeight() - getHeight()/2 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);
						cpt += getWidth()/50;
					} catch (IOException e) { e.printStackTrace(); }
				}
				betWidth = getWidth()/2-getWidth()/16;
				this.bet.setText(this.ctrl.getThisPlayer(Start.identifiant).getBet() + " | " + this.ctrl.getThisPlayer(Start.identifiant).getBetSplit());
			}
			else {
				if(this.ctrl.getThisPlayer(Start.identifiant).getInsurance() > 0 || this.ctrl.getThisPlayer(Start.identifiant).getInsuranceSplit() > 0)
					g.drawString("Insurance : " + this.ctrl.getThisPlayer(Start.identifiant).getInsurance(), getWidth()/2 - getWidth()/10, getHeight()/2 - 20);
				
				test = this.ctrl.getThisPlayer(Start.identifiant).getHand();
				cpt = 0;
				for(Carte c : test.getAlCard()) {
					//System.out.println(c.getCouleur() + "_" + c.getHauteur());
					try {
						g.drawImage(ImageIO.read(new File("res/img/cards/"+c.getCouleur() + "_" + c.getHauteur()+".png")), getWidth()/2-getWidth()/15 + cpt, getHeight() - getHeight()/2 + cpt, getWidth()/10, getHeight()/4 + getHeight()/15, null);
						cpt += getWidth()/50;
					} catch (IOException e) { e.printStackTrace(); }
				}
				this.bet.setText(""+this.ctrl.getThisPlayer(Start.identifiant).getBet());
			}
			this.parier.setBounds(-500,-500,0,0);
			
		}
		this.bet.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/40));
		this.bet.setBounds(betWidth,getHeight()-getHeight()/11,1000,this.getWidth()/20);
		
		//PARTIE BOT
		
		
		//PARTIE AFFICHAGE RESULTAT
		boolean fini = true;
		int cpt = 0;
		for(Player p : this.ctrl.getPlayers()) {
			if(cpt != 0)
				if(!p.getTurnDown()) fini = false;
			cpt++;
			
			System.out.println("test " + cpt);
		}
		
		if(fini) {
			if((this.ctrl.getThisPlayer(Start.identifiant).computeValue(false) > 21 
			    && this.ctrl.getThisPlayer(Start.identifiant).getSplit() == null) 
				|| (this.ctrl.getThisPlayer(Start.identifiant).computeValue(false) > 21 
			    && this.ctrl.getThisPlayer(Start.identifiant).getSplit() != null
				&& this.ctrl.getThisPlayer(Start.identifiant).computeValue(false) > 21) ) {
				this.banquier = true;
			}
			
			if(!this.banquier) {
				this.banquier = true;
				this.ctrl.bankPlay();
				this.ctrl.setTurnDown(this.ctrl.getThisPlayer(0), true);
				repaint();
			}
			else 
				if(!score) {
					score = true;
					this.start.startDrawScore();
				}
		}
	}
	
	public void setText(int bet) {
		this.bet.setText(""+bet);
		this.parier.setEnabled(true);
		revalidate();
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.parier) {
			this.ctrl.distribution();
			this.pari = true;
			this.parier.setEnabled(false);
			this.start.startDrawButton();
			
			revalidate();
	        repaint();
		}
	}
}
