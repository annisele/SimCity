package simcity.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.PersonAgent;

public class IdlePersonGui extends Gui {

	private PersonAgent person;
	private final int SIZE = 20;
	
	ImageIcon ii = new ImageIcon("res/person/persondown.png");
	Image img = ii.getImage();
	Image personimage = img.getScaledInstance(17, 17,  java.awt.Image.SCALE_SMOOTH); 
	
	public IdlePersonGui(PersonAgent p) {
		person = p;
	}
	
	public void setLocation(Location l) {
		x = l.getX();
		y = l.getY();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(personimage, x, y, null); 
	}

	@Override
	public boolean isPresent() {
		return person.isIdle();
	}

}
