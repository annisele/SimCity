package simcity.gui.restauranttwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.two.RestaurantTwoCookRole;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;

public class RestaurantTwoCookGui extends Gui {

	   private RestaurantTwoCookRole agent = null;

	   private int xPos = 370, yPos = 300;//default waiter position
	   private int xDestination = 370, yDestination = 300;//default start position
	   private String text=""; 

	   ImageIcon ii = new ImageIcon("res/restaurant/one/cookcartoon.jpg");
		Image img = ii.getImage();
		Image image = img.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); 

	    public RestaurantTwoCookGui(RestaurantTwoCook c) {
	    	role=c;
	       // this.agent = agent;
	    }

	   /* public void updatePosition() {
	    	
	        if (xPos < xDestination)
	            xPos++;
	        else if (xPos > xDestination)
	            xPos--;

	        if (yPos < yDestination)
	            yPos++;
	        else if (yPos > yDestination)
	            yPos--;

	        if (xPos == xDestination && yPos == yDestination
	        		& (xDestination == 350) & (yDestination == 290)) {
	           Cook();
	        }
	    }*/
	 public void setText(String t){
		 text=t;
	 }
	    public void draw(Graphics2D g) {
	       /* g.setColor(Color.BLUE);
	        g.fillRect(xPos, yPos, 20, 20);
	        g.setFont(new Font("Serif", Font.BOLD,12));
	        g.setColor(Color.RED);
	        g.drawString(text, xPos-20, yPos+10);
	  */
	    	g.drawImage(image, getX(), getY(), null); 
	    }

	    public boolean isPresent() {
	        return true;
	    }

	    public void Prep(){

	    	DoGoToLocation( 370,100);
	     
	    			
	    }

	    public void Cook() {
	    	DoGoToLocation( 360,60);
	     
	    }
	    
	    public void Plate() {
	    	DoGoToLocation( 350,100);
	   
	    }

	

		public void DoGoToPosition() {
			DoGoToLocation( 350,100);
			
		}
	
	
}