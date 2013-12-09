package simcity.gui.restaurantfour;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.four.RestaurantFourCook;

public class RestaurantFourCookGui extends Gui {
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public static final int HOST_LOCATION_X = 290;
	public static final int HOST_LOCATION_Y = 370;
	
	public static final int COOK_STATION_SINK_X = 290;
	public static final int COOK_STATION_SINK_Y = 100;
	
	public static final int COOK_STATION_X = 380;
	public static final int COOK_STATION_Y = 100;
	
	public RestaurantFourCookGui(RestaurantFourCook cook) {
		role = cook;
	}
 
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHostLocation() {
		DoGoToLocation(HOST_LOCATION_X, HOST_LOCATION_Y);
	}
	
	public void DoGoToCookStationSink() {
		DoGoToLocation(COOK_STATION_SINK_X, COOK_STATION_SINK_Y);
	}
	
	public void DoGoToCookStation() {
		DoGoToLocation(COOK_STATION_X, COOK_STATION_Y);
	}
	
}