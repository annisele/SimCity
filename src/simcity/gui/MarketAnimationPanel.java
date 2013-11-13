package simcity.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TEST - this is just a test to put into zoom panel

public class MarketAnimationPanel extends AnimationPanel implements ActionListener  {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
		
		g2.setColor(Color.LIGHT_GRAY);
	    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	    g2.setColor(Color.DARK_GRAY);
	    for(int i = 0; i < 8; i++) {
	    	g2.fillRect(50 + (50*i), 100, 20, 200);
	    }
	    
		super.paintComponent(g);
		
		//super.guis.add(bob);
	}

}
