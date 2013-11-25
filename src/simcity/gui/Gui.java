package simcity.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.interfaces.GuiPartner;

public class Gui {

	protected GuiPartner role;
	private int x = 240;
	private int y = 460;
	private int xDest = 240;
	private int yDest = 460;
	protected int size_x = 40;
	protected int size_y = 40;
	private final int EXIT_X = 235;
	private final int EXIT_Y = 454;
	private boolean atDestNow = false;

	public void updatePosition() {
		//role is null when idle gui should be drawn
		if(role == null) {
			x = xDest;
			y = yDest;
		}
 		if(xDest > x) {
			x++;
		}
		else {
			x--;
		}
		if(yDest > y) {
			y++;
		}
		else {
			y--;
		}
		if(((x == xDest) || (x == xDest + 1) || (x == xDest - 1)) && ((y == yDest) || (y == yDest + 1) || (y == yDest - 1))) {
			if(!atDestNow && role != null) {
				role.atDestination();
				atDestNow = true;
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, size_x, size_y);
	}
	public boolean isPresent() {
		return true;
	}

	public boolean contains(Point point) {
		if(point.getX() >= x && point.getX() <= x + size_x) {
			if(point.y >= y && point.y <= y + size_y) {
				return true;
			}
		}
		return false;
	}

	public void DoGoToLocation(int x, int y) {
		xDest = x;
		yDest = y;
		atDestNow = false;
	}

	public Location getLocation() {
		return new Location(x, y);
	}

	public void DoExitBuilding() {
		DoGoToLocation(EXIT_X, EXIT_Y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}