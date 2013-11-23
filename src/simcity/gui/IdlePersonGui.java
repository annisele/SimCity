package simcity.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.PersonAgent;

public class IdlePersonGui extends Gui {

	private PersonAgent person;
	private int x = 0;
	private int y = 0;
	private final int SIZE = 20;
	
	public IdlePersonGui(PersonAgent p) {
		person = p;
	}
	
	public void setLocation(Location l) {
		x = l.getX();
		y = l.getY();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, SIZE, SIZE);
	}

	@Override
	public boolean isPresent() {
		return person.isIdle();
	}

}
