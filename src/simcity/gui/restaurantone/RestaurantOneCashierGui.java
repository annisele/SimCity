package simcity.gui.restaurantone;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.one.RestaurantOneCashier;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

public class RestaurantOneCashierGui extends Gui {

    
    private ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
    private Image hostimage = ii.getImage();

    
    public static final int CASHX = 100;
    public static final int CASHY = 400;
            

    public RestaurantOneCashierGui(RestaurantOneCashier cr) {
        role = cr;
    }
    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
    	g.drawImage(hostimage,getX(), getY(), null);
    }
    
    public void DoGoToStand() {
    	DoGoToLocation(CASHX, CASHY);
    	DoGoToLocation(300, 200);

    	//DoGoToLocation(100, 100);
    	
    }

    
}