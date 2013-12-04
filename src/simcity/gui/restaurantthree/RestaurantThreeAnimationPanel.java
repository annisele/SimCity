package simcity.gui.restaurantthree;
import javax.swing.*;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/****************
 * restaurant 3 Animation Panel - 
 * @author levonne key
 */
public class RestaurantThreeAnimationPanel extends AnimationPanel implements ActionListener {

	ImageIcon ii = new ImageIcon("res/restaurant/three/res3gui.png");
	Image img = ii.getImage();
	Image res3image = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH);
	
	
	public RestaurantThreeAnimationPanel() {
		super();
	}

	public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);		
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawImage(res3image, 0,0, null);

		super.paintComponent(g);
	}

	public void addGui(Gui g) {
		guis.add(g);
	}


}