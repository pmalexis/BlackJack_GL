package view;

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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel implements ActionListener, MouseListener {
	
	private IhmBlackjack ihm;
	
	//voir pour image jeton
	private final JButton JETON_DROIT  = new JButton("");
	private final JButton JETON_GAUCHE = new JButton("");
	
	private JButton start     = new JButton("START");
	private JButton highscore = new JButton("HIGHSCORE");
	private JButton option    = new JButton("OPTION");
	private JButton credit    = new JButton("CREDIT");
	private JButton close     = new JButton("CLOSE");
	
	private JButton[] tabButton = { start, highscore, option, credit, close };
	
	public Menu(IhmBlackjack ihm) {
		setLayout(null);
		this.ihm = ihm;
		
		try {
			JETON_DROIT.setIcon(new ImageIcon(ImageIO.read(new File("res/img/menu/jeton.png"))));
			JETON_DROIT.setBorderPainted(false);
		    JETON_DROIT.setBorder(null);
		    
			JETON_GAUCHE.setIcon(new ImageIcon(ImageIO.read(new File("res/img/menu/jeton.png"))));
			JETON_GAUCHE.setBorderPainted(false);
		    JETON_GAUCHE.setBorder(null);
			
			add(JETON_DROIT);
			add(JETON_GAUCHE);
		} catch (IOException e) {}
		
		for(JButton b : tabButton) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/" + b.getText() + ".png"));
			    b.setIcon(new ImageIcon(img));
			    b.setBorderPainted(false);
			    b.setBorder(null);
			    b.addMouseListener(this);
			    b.addActionListener(this);
			} catch (IOException ex) {}
			add(b);
		}
	}
	
	public void paint(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("res/img/carpet.png")),0,0,this.getWidth(),this.getHeight(),null);
			g.drawImage(ImageIO.read(new File("res/img/menu/cartes.png")), getWidth()/2 + getWidth()/6, getHeight()/4, getHeight()/2, getHeight()/2,null);
		} catch (IOException e) { e.printStackTrace(); }
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/10));
		g.drawString("BLACKJACK", this.getWidth()/5, this.getHeight()/5);

		start.setBounds(getWidth()/3 + getWidth()/12, getHeight()/3, getWidth()/8,getHeight()/20);
		highscore.setBounds(getWidth()/3 + getWidth()/20, getHeight()/3 + getHeight()/12, getWidth()/5,getHeight()/20);
		option.setBounds(getWidth()/3 + getWidth()/12, getHeight()/2, getWidth()/8,getHeight()/20);
		credit.setBounds(getWidth()/3 + getWidth()/12, getHeight()/2 + getHeight()/12, getWidth()/8,getHeight()/20);
		close.setBounds(getWidth()/3 + getWidth()/12, getHeight()/2 + getHeight()/6, getWidth()/8,getHeight()/20);		
		
		g.setFont(new Font("Arial", Font.PLAIN, getWidth()/60));
		g.drawString("Copyright © 2016 - UFR de Sciences - Universite Caen-Normandie", getWidth()/3 - getWidth()/15, getHeight() - getHeight()/50);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.start) this.ihm.start();
		else if(e.getSource() == this.highscore) this.ihm.highscore();
		else if(e.getSource() == this.option) this.ihm.option();
		else if(e.getSource() == this.credit) this.ihm.credit();
		else if(e.getSource() == this.close) System.exit(0);
	}


	
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == this.start) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/START_BACK.png"));
			    start.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.highscore) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/HIGHSCORE_BACK.png"));
			    highscore.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.option) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/OPTION_BACK.png"));
			    option.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.credit) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/CREDIT_BACK.png"));
			    credit.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.close) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/CLOSE_BACK.png"));
			    close.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
	}


	public void mouseExited(MouseEvent e) {
		if(e.getSource() == this.start) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/START.png"));
			    start.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.highscore) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/HIGHSCORE.png"));
			    highscore.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.option) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/OPTION.png"));
			    option.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.credit) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/CREDIT.png"));
			    credit.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
		else if(e.getSource() == this.close) {
			try {
			    Image img = ImageIO.read(new File("res/img/menu/CLOSE.png"));
			    close.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {}
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
