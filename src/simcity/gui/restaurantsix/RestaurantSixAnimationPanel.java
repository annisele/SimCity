package simcity.gui.restaurantsix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

public class RestaurantSixAnimationPanel extends AnimationPanel implements ActionListener {

	ImageIcon ii = new ImageIcon("res/restaurant/one/tablecartoon.png");
	Image tableimage = ii.getImage();
	ImageIcon cookicon = new ImageIcon("res/restaurant/one/cookcartoon.jpg");
	Image cookimage = cookicon.getImage();
	
	ImageIcon iii = new ImageIcon("res/restaurant/six/restaurantsix.png");
    Image img = iii.getImage();
    Image r2image = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH); 
    
    public RestaurantSixAnimationPanel() {
    	super();
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);
	}
		
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawImage(r2image, 0,0, null);


		super.paintComponent(g);

		//super.guis.add(bob);
	}
	
	public void addGui(Gui g) {
		guis.add(g);
	}
}
