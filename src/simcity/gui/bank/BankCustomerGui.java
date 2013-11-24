package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import simcity.Location;
import simcity.buildings.bank.BankCustomerRole;
import simcity.gui.Gui;
import simcity.interfaces.bank.BankCustomer;

public class BankCustomerGui extends Gui{
	private int HOST_X = 250;
	private int HOST_Y = 420;
	private int xDestination, yDestination;
	int xWindow[] = {300, 300, 300};
	int yWindow[] = {200, 300, 400};
	private enum Command {noCommand, GoToWindow, LeaveBank};
	private Command command = Command.noCommand;
	public BankCustomerGui(BankCustomerRole b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, SIZE, SIZE);
	}
	public void DoGoToHost() {
		DoGoToLocation(HOST_X, HOST_Y);
	}

	public void DoGoToBankTeller(int windowNumber) {
	
	}
	
}