package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.gui.Gui;
import simcity.buildings.transportation.*;






public class BusGui extends Gui {

		private BusAgent busagent;
		private final int BUSSTOPX = 10; //BOGUS
		private final int BUSSTOPY = 10; //BOGUS

		ImageIcon ii = new ImageIcon("res/citygui/markettruck.png");
		Image img = ii.getImage();
		


	public BusGui(BusAgent bus) {
		busagent = bus;
		
	}
    
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, 100, 400, null); //COMMENT this out to get old Gui Back- Josh
	}

	public void DoGoToStop() {
		DoGoToLocation(BUSSTOPX, BUSSTOPY );
	}

}