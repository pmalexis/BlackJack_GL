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
import server.AcceptConnexion;
import server.Controleur;
import server.Server;
import view.Launcher;
import view.client.Emission;
import view.client.Reception;
import view.client.ReceptionPlayer;

@SuppressWarnings("serial")
public class Start extends JPanel implements ActionListener, MouseListener {

	public static int identifiant = 1;
	
	private Launcher launcher;
	private Controleur ctrl;
	
	private JButton select;
	private JButton skip;
	
	private JPanel actionClient;
	private Visu visu;
	
	private List listJoueurs;

	private boolean first_step = false;
	private boolean miser      = false;
	
	private Socket socket;
	
	public Start(Launcher launcher, Controleur ctrl) {
		
		/*
		Thread server = new Thread(new Server(1234));
		server.start();
		*/
		
		this.launcher = launcher;
		this.ctrl     = ctrl;
		
		listJoueurs = new List();
		this.updateList();
		
		select = new JButton("Selectionner joueur");
		select.addActionListener(this);

		skip = new JButton("Skip");
		skip.addActionListener(this);
		
		add(listJoueurs);
		add(select);
		add(skip);
	}
	
	public void updateList() {
		this.listJoueurs.removeAll();
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("joueurs.txt"));
		Collections.sort(al);
		for(String s : al) 
			listJoueurs.add(s.split("-")[0]);
	}
	
	public void launch_game() {
		
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
			skip.setBounds(getWidth()/2 - getWidth()/15, getHeight()/2 + getHeight()/5, getWidth()/9, getHeight()/20);
		}
		else {
			this.actionClient.setBounds(0, getHeight() - getHeight()/10, getWidth(), getHeight()/10);
			this.visu.setBounds(0, 0, getWidth(), getHeight() - getHeight()/10);
		}
	}

	public void actionPerformed(ActionEvent e) {
		/*
		if(!first_step) { 
			try {
				System.out.println("toto");
				socket = new Socket("localhost", 1234);
				System.out.println("tata");
	            PrintWriter out = new PrintWriter(socket.getOutputStream());
	            System.out.println("tato");
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            System.out.println("tota");
				String name = "";
	            
	            if(e.getSource() == this.select) {
					if(this.listJoueurs.getSelectedIndex() > 0)
						name = this.listJoueurs.getSelectedItem();
				}
				else if(e.getSource() == this.skip) 
					name = "Joueur";
			
	            if(name.length() != 0) {
					/* send client name *
			        out.println(name);
			        out.flush();
			        
			        /* retrieve allPlayer *
		            Thread receptionPlayer = new Thread(new ReceptionPlayer(socket));
		            receptionPlayer.start();
			        
			        Thread emission = new Thread(new Emission(out));
			        Thread reception = new Thread(new Reception(in));
			        emission.start();
			        reception.start();
			        
			        
			        first_step = true; 
			        removeAll();
			        repaint();
	            }
			} catch (IOException ev) {
	            System.err.println("cafe " + ev);
	        }
		}
		*/
		if(!this.first_step) {
			String name = "";
            
            if(e.getSource() == this.select) {
				if(this.listJoueurs.getSelectedIndex() > 0)
					name = this.listJoueurs.getSelectedItem();
			}
			else if(e.getSource() == this.skip) 
				name = "Joueur";
		
            this.ctrl.addPlayer(name);
            
            //On ajoute un JPanel pour l'utiliser avec miser (pour afficher les jetons)
            //et avec partieEnCour pour afficher les boutons d action
            
	        removeAll();
	        this.first_step = true;
	        
	        this.visu = new Visu();
	        this.visu.setBackground(new Color(0,0,0,0));
	        add(this.visu);
	        
	        this.actionClient = new DrawToken(this, this.ctrl);
	        this.actionClient.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            this.actionClient.setBackground(new Color(0,0,0,0));
            add(this.actionClient);
	        
            revalidate();
	        repaint();
		}
		else {
			if(!this.miser) {
				
			}
			else {
				
			}
		}
	}
	
	public Visu getVisu() {
		return this.visu;
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
