package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.Gui;

public class PedestrianGui extends Gui {
	
	ImageIcon ii = new ImageIcon("res/person/personup.png");
    Image img = ii.getImage();
    Image personimage = img.getScaledInstance(17, 17,  java.awt.Image.SCALE_SMOOTH); 

	public PedestrianGui(PedestrianRole p) {
		role = p;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(personimage, getX(), getY(), null); //COMMENT this out to get old Gui Back- Josh

	}

}
