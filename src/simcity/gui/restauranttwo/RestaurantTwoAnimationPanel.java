package simcity.gui.restauranttwo;
import javax.swing.*;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RestaurantTwoAnimationPanel extends AnimationPanel implements ActionListener {

	ImageIcon ii = new ImageIcon("res/restaurant/one/tablecartoon.png");
	Image tableimage = ii.getImage();
	ImageIcon cookicon = new ImageIcon("res/restaurant/one/cookcartoon.jpg");
	Image cookimage = cookicon.getImage();
	
	ImageIcon iii = new ImageIcon("res/restaurant/two/restauranttwo.png");
    Image img = iii.getImage();
    Image r2image = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH); 
	public RestaurantTwoAnimationPanel() {
		setBackground(Color.LIGHT_GRAY);
		//setSize(WINDOWX, WINDOWY);
		setVisible(true);

		Timer timer = new Timer(3, this );
		timer.start();

	}

	
    public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);
		
	}


	

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawImage(r2image, 0,0, null); 
		g2.setColor(Color.BLACK);
        g2.fillRect(380, 380, 20,60);
        g2.setColor(Color.BLACK);
        g2.fillRect(50, 100, 60,20);
		/*
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

/*
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
		*/
        
		super.paintComponent(g);
	//	 super.paintComponent(g2);
	}

	public void addGui(Gui g) {
		System.out.println("here: "+g);
		guis.add(g);
	}

   
    
   
}