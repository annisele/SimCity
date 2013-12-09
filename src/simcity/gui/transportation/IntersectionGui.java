package simcity.gui.transportation;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.CarAgent;
import simcity.gui.Gui;

public class IntersectionGui extends Gui {
	
	private int x;
	private int y;
	private Rectangle box;
	
	public static final int intersectionWidth = 100;
	public static final int intersectionHeight = 100;
	
	// Constructor
	public IntersectionGui(int x, int y) {
		this.x = x;
		this.y = y;
		box = new Rectangle(x, y, intersectionWidth, intersectionHeight);
	}
	
	// Accessors
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	// Functions
}