package simcity.gui.house;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.house.HouseInhabitantRole;
import simcity.gui.Gui;

public class HouseInhabitantGui extends Gui {
	
	private final int BED_X = 269;
	private final int BED_Y = 99;


	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public HouseInhabitantGui(HouseInhabitantRole h) {
		role = h;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null); 
	}

	public void DoGoToBed() {
		System.out.println("HouseInhabGui is going to bed!");
		DoGoToLocation(BED_X, BED_Y);
	}

}
