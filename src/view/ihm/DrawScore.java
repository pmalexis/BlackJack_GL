package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import model.Player;
import view.View;

public class DrawScore extends JPanel implements ActionListener {

	private Controleur ctrl;
	private View       view;
	
	private JLabel message;
	
	private JButton again;
	private JButton stop;
	
	public DrawScore(View view, Controleur ctrl) {
		setLayout(null);
		this.ctrl = ctrl;
		this.view = view;
		
		this.message = new JLabel("");
		this.message.setForeground(Color.WHITE);
		add(this.message);
		
		this.again = new JButton("Oui");
		this.again.addActionListener(this);
		add(this.again);
		
		this.stop = new JButton("Non");
		this.stop.addActionListener(this);
		add(this.stop);
		
		boolean split = false;
		for(int i = 0; i <= 1; i++) {
			
            Player player = this.ctrl.getThisPlayer(Start.identifiant);
			
			if(i==1 && player.computeValue(true) != 0 && !split) {
				split = true;
			}
			else split = false;	
			
			if((!split && i==0) || (split && i==1)) {
				
				if(player.computeValue(true) != 0) {
					if(!split) this.message.setText(this.message.getText() + "LEFT HAND : ");
					else this.message.setText(this.message.getText() + ", RIGHT HAND : ");
				}
				
				if(player.computeValue(split) <= 21) {
					//tests blackjack 
					if(this.ctrl.blackjack(this.ctrl.getPlayers().get(0), false) && this.ctrl.blackjack(player, split)) {
						//recup mise
						this.message.setText(this.message.getText() + "BLACKJACK EGALITE, REDISTRIBUTION DES MISES");
						this.ctrl.addBetTable(player, split, 1);
					}
					else if(this.ctrl.blackjack(this.ctrl.getPlayers().get(0), false) && player.computeValue(split) == 21) {
						//perd mise
						this.message.setText(this.message.getText() +"BLACKJACK POUR LA BANQUE, MISE DONNE A LA BANQUE");
						this.ctrl.resetBetTable(player);
					}
					else if(this.ctrl.blackjack(player, split) && this.ctrl.getPlayers().get(0).computeValue(split) == 21) {
						//gagne double mise
						this.message.setText(this.message.getText() + "BLACKJACK POUR LE JOUEUR, VOUS REMPORTEZ LA MISE");
						this.ctrl.addBetTable(player, split, 2);
					}
					else { //puis test classique
						if(this.ctrl.getPlayers().get(0).computeValue(split) <= 21 && this.ctrl.getPlayers().get(0).computeValue(false) > player.computeValue(split)) {
							this.message.setText(this.message.getText() + "LA BANQUE GAGNE CONTRE VOUS");
							this.ctrl.resetBetTable(player);
						}
						else if (this.ctrl.getPlayers().get(0).computeValue(split) > 21 || this.ctrl.getPlayers().get(0).computeValue(false) < player.computeValue(split)) {
							this.message.setText(this.message.getText() + "VOUS GAGNER CONTRE LA BANQUE");
							this.ctrl.addBetTable(player, split, 2);
						}
						else {
							this.message.setText(this.message.getText() + "EGALITE POUR VOUS ET LA BANQUE");
						}
						//this.message.setText("BANQUE => " + this.ctrl.getPlayers().get(0).getValue(false) + "\nJOUEUR " + (split?"BIS ":"") + player.getName() + " => " + player.getValue(split) + "\n");
					}
					//this.message.setText(this.message.getText() + ", VOUS " + player.computeValue(split) + " | BANQUIER " + this.ctrl.getPlayers().get(0).computeValue(false));
				} else {
					this.message.setText(this.message.getText() + "VOUS AVEZ FAIT PLUS DE 21, VOUS AVEZ PERDU");
					this.ctrl.resetBetTable(player);
				}
			}
		}
		
		this.message.setText(this.message.getText() + ", ENCORE ? ");
	}

	public void paintComponent(Graphics g) {
		this.message.setFont(new Font("Arial", Font.ITALIC, getHeight()/5));
		this.message.setBounds(10, getHeight()/25, getWidth(), getHeight());
		
		this.again.setBounds(getWidth() - 300, getHeight()/5, 100, 50);
		this.stop.setBounds(getWidth() - 150, getHeight()/5, 100, 50);
	}
	
	public void actionPerformed(ActionEvent e) {
		Player player = this.ctrl.getThisPlayer(Start.identifiant);
		if(e.getSource() == this.again) {
			this.ctrl.distributeBets();
			Fichier.setMoney(player.getName(), player.getMoney());
			this.ctrl.init();
			this.view.start(player.getName());
		}
		else if(e.getSource() == this.stop) {
			this.ctrl.distributeBets();
			Fichier.setMoney(player.getName(), player.getMoney());
			this.ctrl.init();
			this.view.menu();
		}
	}
}
