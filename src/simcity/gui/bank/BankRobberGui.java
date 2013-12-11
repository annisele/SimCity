package simcity.gui.bank;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.buildings.bank.BankRobberRole;
import simcity.gui.Gui;
import simcity.interfaces.bank.BankCustomer;

public class BankRobberGui extends Gui{
	private int HOST_X = 230;
	private int HOST_Y = 400;

	ImageIcon ii = new ImageIcon("res/Bank/robber.png");
	Image img = ii.getImage();
	
		Image image = img.getScaledInstance(50, 70,  java.awt.Image.SCALE_SMOOTH); 
	private final int WINDOW1_X = 105;
	private final int WINDOW1_Y = 200;
	
	private final int WINDOW2_X = 200;
	private final int WINDOW2_Y = 200;
	
	private final int WINDOW3_X = 280;
	private final int WINDOW3_Y = 200;
	
	
	public BankRobberGui(BankRobberRole bankRobber) {
		role = bankRobber;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, getX(), getY(), null); 
	}
	
	public void DoGoToHost() {
		
		DoGoToLocation(HOST_X, HOST_Y);
	}

	public void DoGoToBankTeller(int windowNumber) {
		
		if(windowNumber == 1) {
			DoGoToLocation(WINDOW1_X, WINDOW1_Y);
		}
		else if(windowNumber == 2) {
			DoGoToLocation(WINDOW2_X, WINDOW2_Y);
		}
		else if(windowNumber == 3) {
			DoGoToLocation(WINDOW3_X, WINDOW3_Y);
		}

	}
	
}