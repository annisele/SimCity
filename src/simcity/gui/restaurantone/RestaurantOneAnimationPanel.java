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

	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	ImageIcon ii = new ImageIcon(this.getClass().getResource("TableCartoon.png"));
	Image tableimage = ii.getImage();
	ImageIcon cookicon = new ImageIcon(this.getClass().getResource("CookCarton.jpg"));
	Image cookimage = cookicon.getImage();
	
	
	public RestaurantOneAnimationPanel() {
		setBackground(Color.LIGHT_GRAY);
		//setSize(WINDOWX, WINDOWY);
		setVisible(true);


		Timer timer = new Timer(3, this );
		timer.start();

	}

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Color tableColor = new Color(200, 200, 200);

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
		g2.setColor(getBackground());
		g2.fillRect(0, 0, 700, 700);
		g2.setColor(tableColor);
		Color black = new Color(0,0,0);
		g2.setColor(black);
		g2.drawString("Waiting Area", 100, 50);
		//this.g = g;
		Color CookColor = new Color (50, 50, 50);
		//g2.setColor(CookColor);
		//g2.fillRect(300, 100, 25, 25);
		g2.drawImage(cookimage,300, 100, null);
		Color GrillColor = new Color (177, 212, 43);
		g2.setColor(GrillColor);
		/*  g2.fillRect(xGrill, yGrill, 40, 40);
            Color black = new Color(0,0,0);
            g2.setColor(black);
            g2.drawString("Grill", xGrill, yGrill);
            Color PlateColor = new Color (233, 121, 43);
            g2.setColor(PlateColor);
            g2.fillRect(xPlate, yPlate, 40, 40);
            g2.setColor(black);
            g2.drawString("Plate", xPlate, yPlate+42); */


		synchronized(guis) {
			for(Gui gui : guis) {
				if(gui != null) {
					if (gui.isPresent()) {
						gui.updatePosition();
					}
				}
				else{
					System.out.println("Hello");
				}
			}
		}

		synchronized(guis){
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.draw((Graphics2D)g);
				}
			}
		}
		
		repaint();
		//super.paintComponent(g);
		g2.drawImage(tableimage,100, 300, null);
		g2.drawImage(tableimage,200, 300, null);
		g2.drawImage(tableimage,300, 300, null);
	}

	public void addGui(Gui g) {
		guis.add(g);
	}


}