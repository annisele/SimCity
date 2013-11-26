package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.gui.Gui;
import simcity.buildings.transportation.*;

public class BusGui extends Gui {

	private BusAgent busagent;

	private int x = 10;
	private int y = 67;
	private int xDest;
	private int yDest;
	
	private boolean atDestNow = false;
	
	ImageIcon ii = new ImageIcon("res/citygui/markettruck.png");
	Image img = ii.getImage();
	Image busimage = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); 

	public BusGui(BusAgent bus) {
		this.busagent = bus;
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
		/*
		if (((x == (xDest)) || (x == (xDest+1)) || (x == (xDest-1)) && ((y == (yDest)) || (y == (yDest+1)) || (y == (yDest-1))))) {
			if (atDestNow == false) {
				busagent.atDestination();
				atDestNow = true;
			}
		} */
		if (((x == (xDest)) || (x == (xDest+1))) && ((y == (yDest)) || (y == (yDest+1)))) {
			if (atDestNow == false) {
				System.out.println("gui says at destination");
				busagent.atDestination();
				atDestNow = true;
			}
		} 
	}
    
	@Override
	public void draw(Graphics2D g) {

		g.drawImage(busimage, x, y, null); //COMMENT this out to get old Gui Back- Josh
	}

	public void DoGoToStop(int x, int y) {
		//atDestNow = false;
		xDest = x;
		yDest = y;
		atDestNow = false; 
	}

	public void setBusAgent(BusAgent bus) {
		this.busagent = bus;
	}
	
	public void clear() {
		busagent.clear();
	}
	
}