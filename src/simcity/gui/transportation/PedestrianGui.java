package simcity.gui.transportation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.Gui;

public class PedestrianGui extends Gui{

	public PedestrianGui() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, SIZE, SIZE);
	}

}
