package simcity.gui.restaurantone;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

public class RestaurantOneHostGui extends Gui {

    
    private ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
    private Image hostimage = ii.getImage();

    
    public static final int HOSTX = 300;
    public static final int HOSTY = 300;
            

    public RestaurantOneHostGui(RestaurantOneHost hr) {
        role = hr;
    }
    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
    	g.drawImage(hostimage,getX(), getY(), null);
    }
    
    public void DoGoToStand() {
    	DoGoToLocation(HOSTX, HOSTY);
    	//DoGoToLocation(100, 100);
    	
    }

    
}