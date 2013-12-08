package simcity.gui.restaurantone;
import javax.swing.*;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

//import restaurant.gui.WaiterGui;
//import restaurant.gui.WaiterGui.Coordinates;

public class RestaurantOneAnimationPanel extends AnimationPanel implements ActionListener {

	//private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	ImageIcon ii = new ImageIcon("res/restaurant/one/tablecartoon.png");
	Image tableimage = ii.getImage();
	ImageIcon cookicon = new ImageIcon("res/restaurant/one/cookcartoon.jpg");
	Image cookimage = cookicon.getImage();
	
	ImageIcon iii = new ImageIcon("res/restaurant/two/restauranttwo.png");
    Image img = iii.getImage();
    Image r1image = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH); 
	
	
	public RestaurantOneAnimationPanel() {
		//setSize(WINDOWX, WINDOWY);
		super();

	}

	public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);
		
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(r1image, 0,0, null); 

		Color tableColor = new Color(200, 200, 200);

		


		
		super.paintComponent(g);
		//g2.drawImage(tableimage,100, 300, null);
		//g2.drawImage(tableimage,200, 300, null);
		//g2.drawImage(tableimage,300, 300, null);
	}

	public void addGui(Gui g) {
		guis.add(g);
	}


}