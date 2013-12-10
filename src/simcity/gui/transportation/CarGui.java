package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.gui.Gui;
import simcity.buildings.transportation.*;

public class CarGui extends Gui {
	
	
	private boolean atDestNow = false;
	
	ImageIcon ii = new ImageIcon("res/bank/bankgui.png");
	Image img = ii.getImage();
	Image busimage = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); 
	
	public CarGui(CarPassengerRole cpr) {
		role = ((CarPassengerRole) cpr);
		
		
		
		
	}
	
	public void updatePosition() {
		if(xDest+1 > x) {
			x++;
		}
		else {
			x--;
		}
		if(yDest+1 > y) {
			y++;
		}
		else {
			y--;
		}
		if (((x == (xDest)) || (x == (xDest+1))) && ((y == (yDest)) || (y == (yDest+1)))) {
			if (atDestNow == false) {
				role.atDestination();
				atDestNow = true;
			}
		} 
	} 
    
	@Override
	public void draw(Graphics2D g) {
	//	g.drawImage(busimage, x, y, null); //COMMENT this out to get old Gui Back- Josh
		g.drawImage(busimage, x+xOff, y+yOff, null); //COMMENT this out to get old Gui Back- Josh

	}
	
	public void DoGoTo(int x, int y) {
		//atDestNow = false;
		System.out.println("In DoGoTo");
		DoGoToLocation(x, y);
	}


	
/*	public void clear() {
		caragent.clear();
	} */
	


}
