package simcity.gui.restaurantone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;

public class RestaurantOneCookGui extends Gui {

    private RestaurantOneCookRole cookrole = null;
    
    private Graphics2D g = null;

    private int xPos = 550, yPos = 120; // cook position
    private int xGrill = 500, yGrill = 130;
    private int xPlate = 550, yPlate = 175;
            

    public RestaurantOneCookGui(RestaurantOneCookRole cr) {
                this.cookrole = cr;
        }

    
    // retrieve table location information from the animation panel
    // pass the table location information to the Customer GUI (the customer cannot access the info, but the GUI can)
    // pass the table location information to the WAITER Gui


    public void draw(Graphics2D g) {
            this.g = g;
            Color CookColor = new Color (50, 50, 50);
                g.setColor(CookColor);
                g.fillRect(xPos, yPos, 50, 50);
                Color GrillColor = new Color (177, 212, 43);
                g.setColor(GrillColor);
                g.fillRect(xGrill, yGrill, 40, 40);
                Color black = new Color(0,0,0);
                g.setColor(black);
                g.drawString("Grill", xGrill, yGrill);
                Color PlateColor = new Color (233, 121, 43);
                g.setColor(PlateColor);
                g.fillRect(xPlate, yPlate, 40, 40);
                g.setColor(black);
                g.drawString("Plate", xPlate, yPlate+42);
    }
    
    public void putongrill() {
            Color foodongrill = new Color (20, 100, 220);
            g.setColor(foodongrill);
            g.fillRect(xGrill, yGrill, 17, 17);
            
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