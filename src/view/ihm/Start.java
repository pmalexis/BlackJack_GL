package view.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;
import controleur.Controleur;
import view.View;

@SuppressWarnings("serial")
public class Start extends JPanel implements ActionListener, MouseListener {

	public static int identifiant = 1;
	
	private View launcher;
	private Controleur ctrl;
	
	private JButton select;
	private JButton skip;
	private JButton botAtk;
	private JButton botDef;
	
	private JPanel actionClient;
	private Visu visu;
	
	private List listJoueurs;

	private boolean first_step = false;
	private boolean miser      = false;
	
	private Socket socket;
	
	public Start(View launcher, Controleur ctrl, String name) {
		
		this.launcher = launcher;
		this.ctrl     = ctrl;
		
		listJoueurs = new List();
		this.updateList();
		
		select = new JButton("Selectionner joueur");
		select.addActionListener(this);

		skip = new JButton("Skip");
		skip.addActionListener(this);
		
		botAtk = new JButton("Look bot atk");
		botAtk.addActionListener(this);
		
		botDef = new JButton("Look bot def");
		botDef.addActionListener(this);
		
		add(listJoueurs);
		add(select);
		add(skip);
		add(botAtk);
		add(botDef);
		
		if(name != null) this.initVisu(name);
	}
	
	public void updateList() {
		this.listJoueurs.removeAll();
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		Collections.sort(al);
		for(String s : al) 
			listJoueurs.add(s.split("-")[0]);
	}
	
	public void paintComponent(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		
		if(!first_step) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/30));
			g.drawString("Select your player's file or juste skip", getWidth()/4, getHeight()/5);
			listJoueurs.setBounds(getWidth()/3, getWidth()/6, getWidth()/5 + getWidth()/40, getHeight()/3);
			listJoueurs.setFont(new Font("Arial", Font.PLAIN, getHeight()/30));
			select.setBounds(getWidth()/2 + getWidth()/14, getWidth()/4, getWidth()/9, getHeight()/20);
			skip.setBounds(getWidth()/2 + getWidth()/14, getHeight()/2 + getHeight()/5, getWidth()/9, getHeight()/20);
			botAtk.setBounds(getWidth()/2 - getWidth()/6, getHeight()/2 + getHeight()/5, getWidth()/9, getHeight()/20);
			botDef.setBounds(getWidth()/2 - getWidth()/21, getHeight()/2 + getHeight()/5, getWidth()/9, getHeight()/20);
		}
		else {
			this.actionClient.setBounds(0, getHeight() - getHeight()/10, getWidth(), getHeight()/10);
			this.visu.setBounds(0, 0, getWidth(), getHeight() - getHeight()/10);
		}
	}

	public void actionPerformed(ActionEvent e) {
		
		if(!this.first_step) {
			String name = "";
            
            if(e.getSource() == this.select) {
				if(this.listJoueurs.getSelectedIndex() > 0)
					name = this.listJoueurs.getSelectedItem();
			}
			else if(e.getSource() == this.skip) 
				name = "Joueur";
			else if(e.getSource() == botAtk) {
				name = "bot_atk";
	   		}
	   		else if(e.getSource() == botDef) {
	   			name = "bot_def";
	   		}
		
            this.initVisu(name);
		}
	}
	
	public void initVisu(String name) {
		
		if(name.split("_")[0].equals("bot"))
			this.ctrl.addBot(name.split("_")[1]);
		else {
			int money = Fichier.getMoney(name);
			this.ctrl.addPlayer(name, money);
		}
        
        //On ajoute un JPanel pour l'utiliser avec miser (pour afficher les jetons)
        //et avec partieEnCour pour afficher les boutons d action
        
        removeAll();
        this.first_step = true;
        
        this.visu = new Visu(this.ctrl, this);
        this.visu.setBackground(new Color(0,0,0,0));
        add(this.visu);
        
        this.actionClient = new DrawToken(this, this.ctrl);
        this.actionClient.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.actionClient.setBackground(new Color(0,0,0,0));
        add(this.actionClient);
        
        revalidate();
        repaint();
	}
	
	public Visu getVisu() {
		return this.visu;
	}
	
	public void startDrawButton() {
		this.actionClient.setBorder(null);
		this.actionClient = new DrawButton(this.ctrl);
		this.actionClient.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.actionClient.setBackground(new Color(0,0,0,0));
        add(this.actionClient);
		revalidate();
        repaint();
	}
	
	public void startDrawScore() {
		this.actionClient.setBorder(null);
		this.actionClient = new DrawScore(this.launcher, this.ctrl);
		this.actionClient.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.actionClient.setBackground(new Color(0,0,0,0));
        add(this.actionClient);
		revalidate();
        repaint();
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
