package simcity.gui.transportation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.gui.Gui;

public class PedestrianGui implements Gui{

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(60, 100, 20, 20);
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean contains(Point point) {
		if(point.getX() >= 60 && point.getX() <= 80) {
			if(point.y >= 100 && point.y <= 120) {
				return true;
			}
		}
		return false;
	}

}
