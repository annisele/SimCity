package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.gui.Gui;
import simcity.buildings.transportation.*;

public class BusGui extends Gui {

	private BusAgent busagent;

	private int x = 40;
	private int y = 50;
	private int xDest;
	private int yDest;
	
	private boolean atDestNow = false;
	
	ImageIcon ii = new ImageIcon("res/transportation/bus.png");
	Image img = ii.getImage();
	Image busimage = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); 

	public BusGui(BusAgent bus) {
		this.busagent = bus;
		if (busagent.getName().equals("Buster")) {
			x = 10;
			y = 67;
		}
		if (busagent.getName().equals("Busta")) {
			x = 20;
			y = 108;
		}
		if (busagent.getName().equals("Kipling")) {
			x = 1370;
			y = 800;
				
		}
		
	}
	
	public void updatePosition() {
		if(xDest+1 > x) {
			x = x+2;
		}
		else {
			x = x-2;
		}
		if(yDest+1 > y) {
			y = y+2;
		}
		else {
			y = y-2;
		}
		/*
		if (((x == (xDest)) || (x == (xDest+1)) || (x == (xDest-1)) && ((y == (yDest)) || (y == (yDest+1)) || (y == (yDest-1))))) {
			if (atDestNow == false) {
				busagent.atDestination();
				atDestNow = true;
			}
		} */
		if (((x == (xDest)) || (x == (xDest+1)) || (x == (xDest+2))) && ((y == (yDest)) || (y == (yDest+1)) || (y == (yDest+2)))) {
			if (atDestNow == false) {
				busagent.atDestination();
				atDestNow = true;
			}
		} 
	}
    
	@Override
	public void draw(Graphics2D g) {

		g.drawImage(busimage, x+xOff, y+yOff, null); //COMMENT this out to get old Gui Back- Josh
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