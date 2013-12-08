package simcity.gui.restaurantone;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;

public class RestaurantOneHostGui extends Gui {

    private RestaurantOneHostRole hostrole = null;
    
    private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
    private Image hostimage = ii.getImage();

    
    public static final int HOSTX = 220;
    public static final int HOSTY = 150;
            

    public RestaurantOneHostGui(RestaurantOneHostRole hr) {
        this.hostrole = hr;
    }
    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
    	g.drawImage(hostimage,getX(), getY(), null);
    }

    
}