package simcity.gui.restaurantfour;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;

public class RestaurantFourWaiterGui extends Gui {
        
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public static final int HOST_LOCATION_X = 290;
	public static final int HOST_LOCATION_Y = 370;
	
	public static final int WAITER_STATION_X = 195;
	public static final int WAITER_STATION_Y = 370;
	
	public static final int WAITING_AREA_X = 240;
	public static final int WAITING_AREA_Y = 370;
	
	public static final int TABLE1_X = 80;
	public static final int TABLE1_Y = 300;
	public static final int TABLE2_X = 150;
	public static final int TABLE2_Y = 300;
	public static final int TABLE3_X = 220;
	public static final int TABLE3_Y = 300;
	
	public static final int COOK_LOCATION_X = 385;
	public static final int COOK_LOCATION_Y = 180;
	
	public RestaurantFourWaiterGui(RestaurantFourWaiter waiter) {
		role = waiter;
	}

	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHostLocation() {
		DoGoToLocation(HOST_LOCATION_X, HOST_LOCATION_Y);
	}
	
	public void DoGoToWaiterStation() {
		DoGoToLocation(WAITER_STATION_X, WAITER_STATION_Y);
	}
    
	public void DoGoToWaitingArea() {
		DoGoToLocation(WAITING_AREA_X, WAITING_AREA_Y);
	}
	
	public void DoGoToTable(int tableNumber) {
		if (tableNumber == 1) {
			DoGoToLocation(TABLE1_X, TABLE1_Y);
		}
		else if (tableNumber == 2) {
			DoGoToLocation(TABLE2_X, TABLE2_Y);
		}
		else if (tableNumber == 3) {
			DoGoToLocation(TABLE3_X, TABLE3_Y);
		}
	}
	
	public void DoGoToCookLocation() {
		DoGoToLocation(COOK_LOCATION_X, COOK_LOCATION_Y);
	}
	
}