package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.Gui;
import simcity.interfaces.bank.BankTeller;

public class BankTellerGui extends Gui {
	private final int WINDOW1_X = 300;
	private final int WINDOW1_Y = 200;
	
	private final int WINDOW2_X = 300;
	private final int WINDOW2_Y = 300;
	
	private final int WINDOW3_X = 300;
	private final int WINDOW3_Y = 400;

	private final int HOST_X = 300;
	private final int HOST_Y = 400;
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public BankTellerGui(BankTellerRole b) {
		role = b;
	}
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null);
	}
	public void DoGoToWindow1() {
		DoGoToLocation(WINDOW1_X, WINDOW1_Y);
	}
	public void DoGoToWindow2() {
		DoGoToLocation(WINDOW2_X, WINDOW2_Y);
	}
	public void DoGoToWindow3() {
		DoGoToLocation(WINDOW3_X, WINDOW3_Y);
	}
	public void DoGoToHost() {
		DoGoToLocation(HOST_X, HOST_Y);
	}
	
}
