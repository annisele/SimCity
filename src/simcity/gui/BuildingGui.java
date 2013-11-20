package simcity.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.Location;
import simcity.SimSystem;

public class BuildingGui {
	private Location location;
	private static final int SIZE_X = 60;
	private static final int SIZE_Y = 60;
	private SimSystem system;
	//private ControlPanel controlPanel;
	
	//System system;
	public BuildingGui(SimSystem s, int xLoc, int yLoc) {
		system = s;
		//controlPanel = s.getControlPanel();
		location = new Location(xLoc, yLoc);
		
	}
	
    public void draw(Graphics2D g) {
    	g.setColor(Color.BLUE);
    	g.fillRect(location.getX(), location.getY(), SIZE_X, SIZE_Y);
    }
    
    //to check if this building was clicked on
    public boolean contains(Point point) {
    	if(point.x >= location.getX() && point.x <= location.getX() + SIZE_X) {
    		if(point.y >= location.getY() && point.y <= location.getY() + SIZE_Y) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public SimSystem getSystem() {
    	return system;
    }
	
   /* public ControlPanel getControlPanel() {
    	return controlPanel;
    }*/
}
