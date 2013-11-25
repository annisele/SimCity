package simcity.gui.bank;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import simcity.buildings.bank.BankHostRole;
import simcity.gui.Gui;
import simcity.interfaces.bank.BankHost;

public class BankHostGui extends Gui {
	
	private final int HOST_X = 300;
	private final int HOST_Y = 400;
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();

	public BankHostGui (BankHost c) {
		role = c;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null); 
	}

	public void DoGoToHostPosition() {
		DoGoToLocation(HOST_X, HOST_Y);
	}
}
