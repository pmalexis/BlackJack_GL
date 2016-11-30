package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.View;

@SuppressWarnings("serial")
public class Credit extends JPanel implements ActionListener, MouseListener {

	private View ihm;

	private JButton retourMenu;
	
	private int nb;
	
	public Credit(View ihm) {
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
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
		} catch (IOException e) { e.printStackTrace(); }
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/10));
		g.drawString("CREDIT", this.getWidth()/4 + this.getWidth()/20, this.getHeight()/6);

		g.setFont(new Font("Arial", Font.PLAIN, this.getWidth()/70));
		nb = getHeight()/4;
		ArrayList<String> al = Fichier.getLignes(Fichier.getFichier("credit.txt"));
		for( String str : al ) {
			g.drawString(str, getWidth()/4 + getWidth()/20, nb);
			nb += this.getWidth()/70;
		}
		
		retourMenu.setBounds(getWidth()/4 + getWidth()/20, nb, getWidth()/8,getHeight()/20);
		
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
