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

	 private RestaurantTwoWaiterRole agent = null;
	    //RestaurantGui gui;
	    private int xPos = 0, yPos = 20;//default waiter position
	    private int xDestination = 0, yDestination = 20;//default start position
	    private int tablenum;
	   
	    public static final int xTable = 100;
	    public static final int yTable = 150;
	    //public List<int>[] a= new ArrayList<int>[5]();
	    int[] tablelist = new int[3];
	    private boolean isPresent = false;
		private boolean onBreak ;
		ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
		Image img = ii.getImage();

	    public RestaurantTwoWaiterGui(RestaurantTwoWaiter w) {
	    	onBreak = false;
	    	role=w;
	        this.agent = agent;
	        tablelist[0]=100;
	    	tablelist[1]=200;
	    	tablelist[2]=300;
	    	//this.gui=gui;
	    }

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
	           agent.msgAtTable();
	           agent.w_at_table=true;
	        }
	        else
	        	agent.w_at_table=false;
	        
	       if (xPos == xDestination && yPos == yDestination
	       		& (xDestination == agent.spot*25) & (yDestination == 20)) {
	          agent.msgAtLobby();
	      }
	       else
	       	agent.w_at_lobby=false;
	       if (xPos == xDestination && yPos == yDestination
	          		& (xDestination == 330) & (yDestination == 300)) {
	             agent.msgAtKitchen();
	         }
	    }
	    public void Start(int i) {//later you will map seatnumber to table coordinates.
			
			xDestination = i*25;
			yDestination = 20;

	}
	    public void draw(Graphics2D g) {
	      // g.setColor(Color.MAGENTA);
	        //g.fillRect(xPos, yPos, 20, 20);
	        g.drawImage(img, getX(), getY(), null); 
	    }

	    public boolean isPresent() {
	        return true;
	    }
		public void setPresent(boolean p) {
			isPresent = p;
		}
	    public void setBreak(){
			agent.wantsaBreak();
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
	        xDestination = tablelist[0] + 20;
	        yDestination = yTable - 20;
	    	}
	    	if (b==2){
	            xDestination = tablelist[1] + 20;
	            yDestination = yTable - 20;
	        	}
	    	if (b==3){
	            xDestination = tablelist[2] + 20;
	            yDestination = yTable - 20;
	        	}
	    	
	    		
	    }

	    public void DoLeaveCustomer() {
	        xDestination = 0;
	        yDestination = 20;
	    }
	    public void GoToKitchen() {
	        xDestination = 330;
	        yDestination = 300;
	    }

	    public int getXPos() {
	        return xPos;
	    }

	    public int getYPos() {
	        return yPos;
	    }
	
	
}
