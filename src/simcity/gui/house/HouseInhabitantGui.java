package simcity.gui.house;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.house.HouseInhabitantRole;
import simcity.gui.Gui;

public class HouseInhabitantGui extends Gui {
	
	private static final int LIVING_X = 220;
	private static final int LIVING_Y = 216;
	private static final int BED_X = 380;
	private static final int BED_Y = 108;
	private static final int BEDROOM_X = 380;
	private static final int BEDROOM_Y = 216;
	private static final int KITCHEN_X = 140;
	private static final int KITCHEN_Y = 100;
	
	private static final int FRIDGE_X = 200;
	private static final int FRIDGE_Y = 80;
	private static final int STOVE_X = 70;
	private static final int STOVE_Y = 80;
	private static final int DINING_X = 150;
	private static final int DINING_Y = 250;
	
	private static final int NEARTABLE_X = 50;
	private static final int NEARTABLE_Y = 250;
	private static final int TABLE_X = 50;
	private static final int TABLE_Y = 310;

	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public HouseInhabitantGui(HouseInhabitantRole h) {
		role = h;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, getX(), getY(), null); 
	}

	public void DoGoToBedroom() {
		DoGoToLocation(BEDROOM_X, BEDROOM_Y);
	}
	
	public void DoGoToBed() {
		DoGoToLocation(BED_X, BED_Y);
	}
	
	public void DoGoToLiving() {
		DoGoToLocation(LIVING_X, LIVING_Y);
	}
	
	public void DoGoToKitchen() {
		DoGoToLocation(KITCHEN_X, KITCHEN_Y);
	}
	
	public void DoGoToFridge() {
		DoGoToLocation(FRIDGE_X, FRIDGE_Y);
	}
	
	public void DoGoToStove() {
		DoGoToLocation(STOVE_X, STOVE_Y);
	}
	
	public void DoGoToDining() {
		DoGoToLocation(DINING_X, DINING_Y);
	}
	
	public void DoGoToNearTable() {
		DoGoToLocation(NEARTABLE_X, NEARTABLE_Y);
	}
	
	public void DoGoToTable() {
		DoGoToLocation(TABLE_X, TABLE_Y);
	}

}
