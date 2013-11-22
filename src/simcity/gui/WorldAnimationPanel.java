package simcity.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WorldAnimationPanel extends AnimationPanel implements ActionListener {

	public WorldAnimationPanel() {//SimCityGui sc) {
		super();
		Timer timer = new Timer(8, this );
    	timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(getBackground());
		//super.paintComponent(g);
		
        //Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
	    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
        //here is the building gui
	    g2.setColor(Color.BLUE);
        for(BuildingGui b : buildingGuis) {
        	b.draw((Graphics2D)g);
        }
        
        super.paintComponent(g);
		
       
	}
	
	public void addGui(Gui g) {
		guis.add(g);
	}
	
	public void addBuilding(BuildingGui g) {
		buildingGuis.add(g);
	}
	

}