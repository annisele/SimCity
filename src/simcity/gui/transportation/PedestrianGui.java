package simcity.gui.transportation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.Gui;

public class PedestrianGui implements Gui{

	private PedestrianRole role;
	private int x = 0;
	private int y = 0;
	private int xDest = 100;
	private int yDest = 100;
	private final int SIZE = 20;
	
	@Override
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
		if(x == xDest && y == yDest) {
			xDest = (int) (Math.random() * 400);
			yDest = (int) (Math.random() * 400);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, SIZE, SIZE);
	}

	@Override
	public boolean isPresent() {
		return true;
	}

	@Override
	public boolean contains(Point point) {
		if(point.getX() >= x && point.getX() <= x + SIZE) {
			if(point.y >= y && point.y <= y + SIZE) {
				return true;
			}
		}
		return false;
	}

}
