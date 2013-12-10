package simcity.gui.restaurantone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.one.RestaurantOneCook;

public class RestaurantOneCookGui extends Gui {

    
    private Graphics2D g = null;

   // private int xPos = 150, yPos = 120; // cook position
    private int xGrill = 500, yGrill = 130;
    private int xPlate = 550, yPlate = 175;
            
    private ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
    private Image hostimage = ii.getImage();

    public RestaurantOneCookGui(RestaurantOneCook cr) {
                role = cr;
        }

    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
    	g.drawImage(hostimage,getX(), getY(), null);

    }
    
    public void putongrill() {
            Color foodongrill = new Color (20, 100, 220);
            g.setColor(foodongrill);
            g.fillRect(xGrill, yGrill, 17, 17);
            
    }

  

    public void DoGoToKitchen() {
    	DoGoToLocation(180, 180);
    }


        
}