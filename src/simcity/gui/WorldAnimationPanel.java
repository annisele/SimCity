package simcity.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class WorldAnimationPanel extends AnimationPanel implements ActionListener {

	public WorldAnimationPanel() {//SimCityGui sc) {
		super();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public void paintComponent(Graphics g) {
		//System.out.println("We're in paint component");
		Graphics2D g2 = (Graphics2D)g;

		//super.paintComponent(g);
		
        //Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
	    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw((Graphics2D)g);
            }
        }
        
        for(BuildingGui b : buildingGuis) {
        	b.draw((Graphics2D)g);
        }
        
        repaint();
		
		//super.guis.add(bob);
	}
	
	public void addBuilding(BuildingGui g) {
		buildingGuis.add(g);
	}

}