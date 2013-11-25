package simcity.gui.house;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.house.HouseInhabitantRole;
import simcity.gui.Gui;

public class HouseInhabitantGui extends Gui {
	
	private final int BED_X = 380;
	private final int BED_Y = 108;
	private final int BEDROOM_X = 380;
	private final int BEDROOM_Y = 216;
	private final int LIVING_X = 220;
	private final int LIVING_Y = 216;
	private final int KITCHEN_X = 140;
	private final int KITCHEN_Y = 100;

	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public HouseInhabitantGui(HouseInhabitantRole h) {
		role = h;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null); 
	}

	public void DoGoToBedroom() {
		System.out.println("HouseInhabGui is going to bed!");
		DoGoToLocation(BEDROOM_X, BEDROOM_Y);
	}
	
	public void DoGoToBed() {
		System.out.println("HouseInhabGui is going to bed!");
		DoGoToLocation(BED_X, BED_Y);
	}
	
	public void DoGoToLiving() {
		System.out.println("HouseInhabGui is going to bed!");
		DoGoToLocation(LIVING_X, LIVING_Y);
	}
	
	public void DoGoToKitchen() {
		System.out.println("HouseInhabGui is going to bed!");
		DoGoToLocation(KITCHEN_X, KITCHEN_Y);
	}

}
