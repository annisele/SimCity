package simcity.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.SimSystem;
import simcity.PersonAgent.EventType;

public class BuildingGui {
	
	private Location location;
	private static final int SIZE_X = 60;
	private static final int SIZE_Y = 60;
	private SimSystem system;
	private enum BuildingType { House, Market, Bank, Apartment, RestaurantOne, RestaurantTwo, RestaurantThree, RestaurantFour, RestaurantFive, RestaurantSix };
	private BuildingType type;
	//private ControlPanel controlPanel;
	
	ImageIcon restauranticon = new ImageIcon("res/citygui/restaurantone.png");
	Image restaurantimage = restauranticon.getImage();
    Image finalrestaurantimage = restaurantimage.getScaledInstance(65, 65,  java.awt.Image.SCALE_SMOOTH); 

	ImageIcon marketicon = new ImageIcon("res/citygui/squaremarket.png");
	Image marketimage = marketicon.getImage();
	Image finalmarketimage = marketimage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	ImageIcon houseicon = new ImageIcon("res/citygui/home1.png");
	Image houseimage = houseicon.getImage();
	Image finalhouseimage = houseimage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon bankicon = new ImageIcon("res/citygui/bank.png");
    Image bankimage = bankicon.getImage();
    Image finalbankimage = bankimage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon ii = new ImageIcon("res/citygui/basicroad.png");
    Image img = ii.getImage();
    Image roadimage = img.getScaledInstance(388, 400,  java.awt.Image.SCALE_SMOOTH); 
	
	
	

	
	//System system;
	public BuildingGui(SimSystem s, String t, int xLoc, int yLoc) {
		system = s;
		type = BuildingType.valueOf(t);
		//controlPanel = s.getControlPanel();
		location = new Location(xLoc, yLoc);
		
	}
	
    public void draw(Graphics2D g) {
    //	g.setColor(Color.BLUE);
    	//g.fillRect(location.getX(), location.getY(), SIZE_X, SIZE_Y);
	    g.drawImage(roadimage, 40,32, null);

    	if (type == BuildingType.Market)
    		g.drawImage(finalmarketimage,location.getX(), location.getY(), null);
    	else if (type == BuildingType.RestaurantOne)
    		g.drawImage(finalrestaurantimage, location.getX(), location.getY(), null);
    	else if (type == BuildingType.House)
    		g.drawImage(finalhouseimage, location.getX(), location.getY(), null);
    	else if (type == BuildingType.Bank)
    		g.drawImage(finalbankimage, location.getX(), location.getY(), null);
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
