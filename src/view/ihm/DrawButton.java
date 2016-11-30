package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controleur.Controleur;
import model.Player;

public class DrawButton extends JPanel implements ActionListener {

	private Controleur ctrl;
	
	private JButton hit;
	private JButton stand;
	private JButton doubles;
	private JButton split;
	private JButton insurance;
	
	private boolean isSplit = false;
	
	public DrawButton(Controleur ctrl) {
		this.ctrl = ctrl;
		
		setLayout(null);
		
		this.hit       = new JButton("Hit");
		this.stand     = new JButton("Stand");
		this.doubles   = new JButton("Double");
		this.split     = new JButton("Split");
		this.insurance = new JButton("Assurance");
		
		this.hit.addActionListener(this);
		this.stand.addActionListener(this);
		this.doubles.addActionListener(this);
		this.split.addActionListener(this);
		this.insurance.addActionListener(this);
		
		add(this.hit);
		add(this.stand);
		add(this.doubles);
		add(this.split);
		add(this.insurance);
		
		Player player = ctrl.getThisPlayer(Start.identifiant);
		
		if(!this.ctrl.canSplit(player)) this.split.setEnabled(false);
		if(ctrl.getThisPlayer(0).getHand().getAlCard().get(0).getHauteur() != 1) this.insurance.setEnabled(false);
		if(player.getMoney() < player.getBet()) {
			this.doubles.setEnabled(false);
			this.split.setEnabled(false);
		}
		if(player.computeValue(false) >= 21) {
			this.hit.setEnabled(false);
			this.stand.setEnabled(false);
			this.doubles.setEnabled(false);
			this.split.setEnabled(false);
			this.insurance.setEnabled(false);
			
			this.ctrl.setTurnDown(player, true);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cpt = 10;
		this.hit.setBounds(cpt, 10, this.getWidth()/8, this.getHeight()/2);
		cpt += this.getWidth()/8 + 10;
		this.stand.setBounds(cpt, 10, this.getWidth()/8, this.getHeight()/2);
		cpt += this.getWidth()/8 + 10;
		this.doubles.setBounds(cpt, 10, this.getWidth()/8, this.getHeight()/2);
		cpt += this.getWidth()/8 + 10;
		this.split.setBounds(cpt, 10, this.getWidth()/8, this.getHeight()/2);
		cpt += this.getWidth()/8 + 10;
		this.insurance.setBounds(cpt, 10, this.getWidth()/8, this.getHeight()/2);
		cpt += this.getWidth()/8 + 10;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/40));
		
		String texte = "Take your choice";
		if(ctrl.getThisPlayer(Start.identifiant).getBetSplit() > 0) {
			if(!this.isSplit) texte = "LEFT HAND";
			else texte = "RIGHT HAND";
		}
				
		g.drawString(texte, cpt, this.getWidth()/40);
	}

	public void actionPerformed(ActionEvent e) {
		
		boolean hit, stand, doubles, split, insurance, isSplit;
		hit = stand = doubles = split = insurance = isSplit = false;
		
		int cpt = 0;
		Player player = ctrl.getThisPlayer(Start.identifiant);
		
		if(e.getSource() == this.hit) { //PIOCHE NORMALE
			this.ctrl.hit(player, this.isSplit);
			cpt++;
		}
		else if(e.getSource() == this.stand) { //S ARRETE
			if(player.getBetSplit() == 0 || this.isSplit) {
				this.ctrl.setTurnDown(player, true);
				cpt++;
			}
			else isSplit = true;
			
			//System.out.println(player.getTurnDown());
		} 
		else if(e.getSource() == this.doubles) { //PIOCHE + DOUBLER LA MISE
			this.ctrl.hit(player, this.isSplit); 
			this.ctrl.addBetTable(player, this.isSplit, 2);
			cpt++;
			if(player.getBetSplit() == 0 || this.isSplit)
				this.ctrl.setTurnDown(player, true);
			else isSplit = true;
		}
		else if(e.getSource() == this.split) { //DIVISER SA MAIN
			this.ctrl.split(player);
			split = true;
		}
		else if(e.getSource() == this.insurance) { //ASSURANCE
			insurance = true;
			this.ctrl.insurance(player, this.isSplit);
		}
		
		if(cpt >= 1) doubles = true;
		if(player.computeValue(this.isSplit) >= 21 || isSplit) {
			hit = true;
			stand = true;
			doubles = true;
			split = true;
			insurance = true;
			
			if(player.getBetSplit() == 0 || this.isSplit)
				this.ctrl.setTurnDown(player, true);
			else {
				hit       = false;
				stand     = false;
				split     = true;
				
				if(player.getMoney() >= player.getBetSplit()) 
					doubles = false;
				
				if(ctrl.getThisPlayer(0).getHand().getAlCard().get(0).getHauteur() == 1) {
					insurance = false;
					this.insurance.setEnabled(true);
				}
					
				this.isSplit = true;
			}
		}
		
		//passer les boutons a false
		if(hit)       this.hit.setEnabled(false);
		if(stand)     this.stand.setEnabled(false);
		if(doubles)   this.doubles.setEnabled(false);
		if(split)     this.split.setEnabled(false);
		if(insurance) this.insurance.setEnabled(false);
	}
}
