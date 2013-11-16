package simcity.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class AnimationPanel extends JPanel {
	
	SimCityGui simCityGui;
	ControlPanel controlPanel;
	
	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	//private List<BuildingGui> buildingGuis = Collections.synchronizedList(new ArrayList<BuildingGui>());
	
	// 
	// have a timer that calls repaint() on every panel
	//private Timer repaintTimer = new Timer();
	//
	// keep track of: how fast was I going, how much time has passed since the last update
	//
	// If we have a base System class, then we can link up the ControlPanel to the animationPanel.  The system
	// class would call AnimationPanel.setControlPanel() and give the animationpanel a controlpanel.
	// Then the animationPanel would call controlPanel.update() passing a building or person
	//
	private List<BuildingGui> buildingGuis = Collections.synchronizedList(new ArrayList<BuildingGui>());
	private int px; //x where mouse was pressed
	private int py;
	
	protected AnimationPanel(){//SimCityGui sc) {
		
		//simCityGui = sc;
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				px = e.getXOnScreen();
				py = e.getYOnScreen();
				System.out.print("There are "+buildingGuis.size());
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
                        
                        //controlPanel.updateSelected(g.getPerson());

                    }
                }
               // for (BuildingGui g : buildingGuis) {
                for(BuildingGui g : buildingGuis) {
                    if (g.contains(me.getPoint())) {//check if mouse is clicked within shape

                        //we can either just print out the object class name
                        System.out.println("Clicked a "+"building: " + g.getName());

                        //controlPanel.updateSelected(g);
                        
                    }

               }
            }
		});
		
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				//added this to try to set up dragging world map
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
		});
	}
	
	//what makes it so that this will be called over and over? it's not right now
	public void paintComponent(Graphics g) {
		// compute dt, then send dt to every gui for updatePosition
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
      //  for(BuildingGui buildingGui : buildingGuis) {
        //System.out.println("There are "+buildingGuis.size()+" buildings");
        for(BuildingGui b : buildingGuis) {
              //  buildingGui.draw((Graphics2D)g);
        	System.out.println("We're drawing a building");
        	b.draw((Graphics2D)g);
        }
        repaint();
	}
	
	public void addGui(Gui gui) {
		guis.add(gui);
	}
	
	public void addBuilding(BuildingGui b) {
		
		buildingGuis.add(b);
		System.out.println("A building is added.  There are "+buildingGuis.size());
	}
	
	public void setControlPanel(ControlPanel cp) {
		controlPanel = cp;
	}
	
	/*public void addGui(gui) {
        guis.add(gui);
    }
    
    public void setRestaurantPanel(RestaurantPanel restPanel) {
    	xTables = restPanel.getTableXPos();
        yTables = restPanel.getTableYPos();
    }*/
	
}
