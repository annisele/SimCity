package simcity.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;

public class Building {
	private Location location;
	private static final int SIZE_X = 60;
	private static final int SIZE_Y = 60;
	private String name;
	
	//System system;
	public Building(String n, int xLoc, int yLoc) {
		name = n;
		location = new Location(xLoc, yLoc);
	}
	
	public String getName() {
		return name;
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
	
}
