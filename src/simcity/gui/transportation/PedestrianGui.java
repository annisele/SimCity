package simcity.gui.transportation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.Gui;
import simcity.interfaces.transportation.Pedestrian;

public class PedestrianGui extends Gui{
	
	ImageIcon ii = new ImageIcon("res/Person/PersonUp.png");
    Image img = ii.getImage();
    Image personimage = img.getScaledInstance(17, 17,  java.awt.Image.SCALE_SMOOTH); 

	public PedestrianGui(PedestrianRole p) {
		role = p;
	}

	@Override
	public void draw(Graphics2D g) {
		//g.setColor(Color.MAGENTA);
		//g.fillRect(x, y, SIZE, SIZE);
		g.drawImage(personimage, x,y, null); //COMMENT this out to get old Gui Back- Josh

	}

}
