package simcity.gui.restauranttwo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.two.*;
import simcity.gui.restauranttwo.*;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoWaiterGui extends Gui{

	// private RestaurantTwoWaiterRole agent = null;
	    //RestaurantGui gui;
	
	//    private int xPos = 0, yPos = 20;//default waiter position
	  //  private int xDestination = 0, yDestination = 20;//default start position
	    private int tablenum;
	    private final int HOST_X = 200;
		private final int HOST_Y = 300;
	    public static final int xTable = 100;
	    public static final int yTable = 250;
	    //public List<int>[] a= new ArrayList<int>[5]();
	    int[] tablelist = new int[3];
	    private boolean isPresent = false;
		private boolean onBreak ;
		ImageIcon ii = new ImageIcon("res/restaurant/two/waiterdown.png");
		Image img = ii.getImage();
		Image image = img.getScaledInstance(30, 35,  java.awt.Image.SCALE_SMOOTH); 


	    public RestaurantTwoWaiterGui(RestaurantTwoWaiter w) {
	    	//onBreak = false;
	    	role=w;
	       // this.agent = agent;
	        tablelist[0]=100;
	    	tablelist[1]=200;
	    	tablelist[2]=300;
	    	//this.gui=gui;
	    }
/*
	    public void updatePosition() {
	    	int tempnum = tablenum*100 ;
	    	if(onBreak!=true){
	    	//gui.setWaiterEnabled(agent);
	    	}
	        if (xPos < xDestination)
	            xPos++;
	        else if (xPos > xDestination)
	            xPos--;

	        if (yPos < yDestination)
	            yPos++;
	        else if (yPos > yDestination)
	            yPos--;

	        if (xPos == xDestination && yPos == yDestination
	        		& (xDestination == tempnum + 20) & (yDestination == yTable - 20)) {
	           ((RestaurantTwoWaiter)role).msgAtTable();
	        }
	        else
	        	//agent.w_at_table=false;
	        
	       if (xPos == xDestination && yPos == yDestination
	       		& (xDestination == ((RestaurantTwoWaiterRole)role).spot*25) & (yDestination == 20)) {
	    	   ((RestaurantTwoWaiterRole)role).msgAtLobby();
	      }
	       if (xPos == xDestination && yPos == yDestination
	          		& (xDestination == 330) & (yDestination == 300)) {
	    	   ((RestaurantTwoWaiter)role).msgAtKitchen();
	         }
	    }*/
	    public void Start(int i) {//later you will map seatnumber to table coordinates.
	    	DoGoToLocation(i*25,400);
			

	}
	    public void draw(Graphics2D g) {
	      // g.setColor(Color.MAGENTA);
	        //g.fillRect(xPos, yPos, 20, 20);
	        g.drawImage(image, getX(), getY(), null); 
	    }

	    public boolean isPresent() {
	        return true;
	    }
		public void setPresent(boolean p) {
			isPresent = p;
		}
	    public void setBreak(){
	    	 ((RestaurantTwoWaiter)role).wantsaBreak();
			//setPresent(true);
		}
	    public void reset(){
			onBreak = false;
			//setPresent(true);
		}
	    public void set(){
			onBreak = true;
			//setPresent(true);
		}
		public boolean onBreak() {
		
			return onBreak;
		}
	    public void DoBringToTable( int b){

	    	//agent.print("waiter animation is coming to table!");
	    	tablenum= b;
	    	if (b==1){
	    		DoGoToLocation(tablelist[0] + 20,yTable - 20);
	    	}
	    	if (b==2){
	    		  DoGoToLocation( tablelist[1] + 20,yTable - 20);
	          
	        	}
	    	if (b==3){
	    		DoGoToLocation( tablelist[2] + 20,yTable - 20);
		          
	        	}
	    	
	    		
	    }

	    public void DoLeaveCustomer(int i) {
	    	DoGoToLocation(i*25,400);
	    }
	    public void GoToKitchen() {
	    	DoGoToLocation(300,50);
	    }
/*
	    public int getXPos() {
	        return xPos;
	    }

	    public int getYPos() {
	        return yPos;
	    }
*/
		public void DoGoToWaiterPosition() {
			System.out.println("waiter gui goes to position");
			DoGoToLocation(300, 300);
			
		}
	
	
}
