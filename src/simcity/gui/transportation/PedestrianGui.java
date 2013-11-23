package simcity.gui.transportation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.Gui;
import simcity.interfaces.transportation.Pedestrian;

public class PedestrianGui extends Gui{

	public PedestrianGui(PedestrianRole p) {
		role = p;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, SIZE, SIZE);
	}

}
