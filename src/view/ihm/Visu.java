package view.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Visu extends JPanel {

	private JLabel bet;
	
	public Visu() {
		setLayout(null);
		
		this.bet = new JLabel("");
        this.bet.setForeground(Color.WHITE);
        add(this.bet);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.bet.setFont(new Font("Arial", Font.ITALIC, this.getWidth()/20));
		this.bet.setBounds(getWidth()/2,getHeight()/2,1000,this.getWidth()/20);
	}
	
	public void setText(int bet) {
		this.bet.setText(""+bet);
		revalidate();
		repaint();
	}
}
