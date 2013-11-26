package simcity.gui.restauranttwo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.gui.restauranttwo.*;
import simcity.Location;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;

public class RestaurantTwoCustomerGui extends Gui{

	
	private boolean isPresent = false;
	private boolean isHungry = false;
	private final int CASHIER_X = 209;
	private final int CASHIER_Y = 299;

	//private HostAgent host;
	//RestaurantGui gui;
	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image imgu = ii.getImage();
	Image img = imgu.getScaledInstance(30, 35,  java.awt.Image.SCALE_SMOOTH); 

//	private int xPos, yPos;
	//private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, GoToRestaurant,LeaveRestaurant};
	private Command command=Command.noCommand;
	public static final int xTable = 100;
	public static final int yTable = 250;
	//public static final int customer_rectsize =20;
	
	public RestaurantTwoCustomerGui(RestaurantTwoCustomer c){ //HostAgent m) {
		role=c;
		/*role = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
	*/
		
		//maitreD = m;
		//this.gui = gui;
	}
/*
	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) 
				((RestaurantTwoCustomerRole)role).msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				((RestaurantTwoCustomerRole)role).msgPaying();
				System.out.println("customer at cashier");

				//role.msgAnimationFinishedLeaveRestaurant();
				isHungry = false;
				//gui.setCustomerEnabled(role);
			}
			command=Command.noCommand;
		}
	}
*/
	public void draw(Graphics2D g) {
		//g.setColor(Color.GREEN);
		//g.fillRect(xPos, yPos, customer_rectsize, customer_rectsize);
		g.drawImage(img, getX(), getY(), null); 
	}
/*
	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		((RestaurantTwoCustomerRole)role).gotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}*/
	public void DoGoToCashier() {
		//System.out.println("cust gui goes to position");
		DoGoToLocation(60, 100);
	}
	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.
		DoGoToLocation(seatnumber*100, yTable);
		
		command = Command.GoToSeat;
	}
	public void DoGoToRestaurant(int i) {//later you will map seatnumber to table coordinates.
		System.out.println("going to my waiting spot");
		DoGoToLocation( i*25,350);
				
					
					command = Command.GoToRestaurant;
					

	}
	public void DoLeaveToPay() {
		DoGoToLocation( 100,-40);
		
		command = Command.LeaveRestaurant;
	}
	public void DoExitRestaurant() {
		DoGoToLocation( 300,-40);
		command = Command.noCommand;
	}
	
}