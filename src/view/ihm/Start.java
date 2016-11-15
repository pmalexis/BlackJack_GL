package view.ihm;

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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.View;

@SuppressWarnings("serial")
public class Start extends JPanel implements ActionListener, MouseListener {

	private View ihm;
	
	private JButton select;
	private JButton skip;
	
	private List listJoueurs;

	private boolean first_step = false;

	private Socket socket;
	
	public Start(View ihm) {
		
		this.ihm = ihm;
		
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
			
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(!first_step) {
			String name = "";
	        if(e.getSource() == this.select) {
				if(this.listJoueurs.getSelectedIndex() > 0)
					name = this.listJoueurs.getSelectedItem();
			}
			else if(e.getSource() == this.skip){
				name = "Joueur";
			}
	 
	        first_step = true;
	        removeAll();
	        repaint();
        }
	}
	
	
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
