package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import javax.swing.*;

/****************
 * Bank Animation Panel - 
 * @author levonne key
 */
public class BankAnimationPanel extends AnimationPanel implements ActionListener {
	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());

	public BankAnimationPanel() {
		setBackground(Color.YELLOW);
		setVisible(true);	

    	Timer timer = new Timer(2, this );
    	timer.start();
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		//bank window gui
		Color windowColor = new Color (220,220,220);
		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
        g2.fillRect(0, 0, 700, 700);
        g2.setColor(windowColor);
        g2.fillRect(100, 350, 50, 20);
        g2.fillRect(200, 350, 50, 20);
        g2.fillRect(300, 350, 50, 20);

		Color BankHostColor = new Color (50, 50, 50);
			g2.setColor(BankHostColor);
			g2.fillRect(30, 55, 25, 25);

		//Here is the waiting area
        g2.setColor(Color.BLUE);		       
        for (int i = 0; i < 18; i++){
        	g2.fillRect(30+(i*25),10 ,20 ,20 );
        }
		        	
		
			 synchronized(guis) {
			        for(Gui gui : guis) {
			            if (gui.isPresent()) {
			                gui.updatePosition();
			            }
			        }
			 }
			  
		        synchronized(guis){
		        for(Gui gui : guis) {
		            if (gui.isPresent()) {
		                gui.draw(g2);
		            }
		        }
		        }
		   repaint();
	}

	public void addGui(Gui g) {
		guis.add(g);
	}
}