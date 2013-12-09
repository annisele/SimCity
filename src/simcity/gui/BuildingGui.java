package simcity.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.SimSystem;

public class BuildingGui {
	
	private Location location;
	private static final int SIZE_X = 60;
	private static final int SIZE_Y = 60;
	private int xOff = 0;
	private int yOff = 0;
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


	//System system;
	public BuildingGui(SimSystem s, String t, int xLoc, int yLoc) {
		system = s;
		type = BuildingType.valueOf(t);
		//controlPanel = s.getControlPanel();
		location = new Location(xLoc, yLoc);
		
	}
	
	public void setOffset(int x, int y) {
		xOff = x;
		yOff = y;
	}
	
    public void draw(Graphics2D g) {

    	Image img;
    	if (type == BuildingType.Market)
    		img = finalmarketimage;
    	else if (type == BuildingType.RestaurantOne)
    		img = finalrestaurantimage;
    	else if (type == BuildingType.RestaurantTwo)
    		img = finalrestaurantimage;
    	else if (type == BuildingType.House)
    		img = finalhouseimage;
    	else if (type == BuildingType.Bank)
    		img = finalbankimage;
    	else
    		img = finalrestaurantimage;
    	
    	g.drawImage(img, location.getX()+xOff, location.getY()+yOff, null);
    }
    
    //to check if this building was clicked on
    public boolean contains(Point point) {
    	if(point.x >= location.getX()+xOff && point.x <= location.getX()+xOff + SIZE_X) {
    		if(point.y >= location.getY()+yOff && point.y <= location.getY()+yOff + SIZE_Y) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public SimSystem getSystem() {
    	return system;
    }

}
