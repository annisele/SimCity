package simcity.gui.transportation;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.gui.Gui;
import simcity.buildings.transportation.*;






public class BusGui extends Gui {

		private BusAgent busagent;
		private  int BUSSTOPX = 32; //BOGUS
		private  int BUSSTOPY = 67; //BOGUS

		ImageIcon ii = new ImageIcon("res/citygui/markettruck.png");
		Image img = ii.getImage();
		Image busimage = img.getScaledInstance(25, 20,  java.awt.Image.SCALE_SMOOTH); 


	public BusGui(BusAgent bus) {
		busagent = bus;
		
	}
    
	@Override
	public void draw(Graphics2D g) {

		if (BUSSTOPX < 365 ) {
			BUSSTOPX++;
		}
		g.drawImage(busimage, BUSSTOPX, BUSSTOPY, null); //COMMENT this out to get old Gui Back- Josh

	}

	public void DoGoToStop() {
		DoGoToLocation(BUSSTOPX, BUSSTOPY );
	}

}