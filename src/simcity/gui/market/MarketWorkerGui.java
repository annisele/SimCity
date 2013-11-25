package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketWorkerRole;
import simcity.gui.Gui;

public class MarketWorkerGui extends Gui {

	private final int SHELF_ONE_X = 400;
	private final int SHELF_TWO_X = 90;
	private final int SHELF_ONE_Y = 225;
	private final int SHELF_TWO_Y = 340;
	private final int COUNTER_X = 20;
	private final int COUNTER_Y = 200;
	private final int HOME_X = -50;
	private final int HOME_Y = 225;
	private final int CENTER_X = 115;	
	private final int CENTER_Y = 225;
	
	ImageIcon ii = new ImageIcon("res/market/cashier.png");
	Image img = ii.getImage();
	Image workerimage = img.getScaledInstance(70, 62,  java.awt.Image.SCALE_SMOOTH); 

	public MarketWorkerGui(MarketWorkerRole m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(workerimage, x, y, null);

	}
	
	public void DoGoToShelfOne() {
		DoGoToLocation(SHELF_ONE_X, SHELF_ONE_Y);
	}
	
	public void DoGoToShelfTwo() {
		DoGoToLocation(SHELF_TWO_X, SHELF_TWO_Y);
	}
	
	public void DoGoToDropOffItems() {
		DoGoToLocation(COUNTER_X, COUNTER_Y);
	}
	
	public void DoGoToHomePosition() {
		DoGoToLocation(HOME_X, HOME_Y);
	}
	
	public void DoGoToCenter() {
		DoGoToLocation(CENTER_X, CENTER_Y);
	}


}
