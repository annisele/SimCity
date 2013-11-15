package simcity.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import simcity.buildings.Building;

public class AnimationPanel extends JPanel {
	
	SimCityGui simCityGui;
	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	private List<BuildingGui> buildingGuis = Collections.synchronizedList(new ArrayList<BuildingGui>());
	 
	protected AnimationPanel(){//SimCityGui sc) {
		
		//simCityGui = sc;
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {

			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent me) {
               // super.mouseClicked(me);
                for (Gui g : guis) {
                    if (g.contains(me.getPoint())) {//check if mouse is clicked within shape

                        //we can either just print out the object class name
                        System.out.println("Clicked a "+g.getClass().getName());

                        //or check the shape class we are dealing with using instance of with nested if
                        if (g instanceof Building) {
                            System.out.println("Clicked a building");
                        }
                    }
                }
                for (BuildingGui g : buildingGuis) {
                    if (g.contains(me.getPoint())) {//check if mouse is clicked within shape

                        //we can either just print out the object class name
                        System.out.println("Clicked a "+"building");

                        //or check the shape class we are dealing with using instance of with nested if

                    }

               }
            }
		});
	}
	
	//what makes it so that this will be called over and over? it's not right now
	public void paintComponent(Graphics g) {
		
		
		
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
        for(BuildingGui buildingGui : buildingGuis) {
                buildingGui.draw((Graphics2D)g);
        }
	}
	
	public void addGui(Gui gui) {
		guis.add(gui);
	}
	
	/*public void addGui(gui) {
        guis.add(gui);
    }
    
    public void setRestaurantPanel(RestaurantPanel restPanel) {
    	xTables = restPanel.getTableXPos();
        yTables = restPanel.getTableYPos();
    }*/
	
}
