package simcity.gui;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.PersonAgent;

public class IdlePersonGui extends Gui {

	private PersonAgent person;
	
	ImageIcon ii = new ImageIcon("res/person/persondown.png");
	Image img = ii.getImage();
	Image personimage = img.getScaledInstance(17, 17,  java.awt.Image.SCALE_SMOOTH); 
	
	public IdlePersonGui(PersonAgent p) {
		person = p;
	}
	
	public void setLocation(Location l) {
		DoGoToLocation(l.getX(), l.getY());
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(personimage, getX(), getY(), null); 
	}

	@Override
	public boolean isPresent() {
		return person.isIdle();
	}

}
