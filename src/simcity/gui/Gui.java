package simcity.gui;

import java.awt.*;

import simcity.Location;
import simcity.Role;

public class Gui {

	protected Role role;
	protected int x = 236;
	protected int y = 454;
	protected int xDest = 0;
	protected int yDest = 0;
	protected int SIZE = 20;
	protected int EXIT_X = 235;
	protected int EXIT_Y = 454;
	protected boolean atDestNow = false;

	public void updatePosition() {
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
			if(!atDestNow) {
				role.atDestination();
				atDestNow = true;
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, SIZE, SIZE);
	}
	public boolean isPresent() {
		return true;
	}

	public boolean contains(Point point) {
		if(point.getX() >= x && point.getX() <= x + SIZE) {
			if(point.y >= y && point.y <= y + SIZE) {
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

}