package simcity.gui.restaurantone;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;

public class RestaurantOneHostGui extends Gui {

    private RestaurantOneHostRole hostrole = null;

    private int xPos = 200, yPos = 100;//default waiter position
            

    public RestaurantOneHostGui(RestaurantOneHostRole hr) {
        this.hostrole = hr;
    }
    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
            g.setColor(new Color(250,250, 250));
    }

    public boolean isPresent() {
        return true;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
    

        @Override
        public void updatePosition() {
                // TODO Auto-generated method stub
                
        }
}