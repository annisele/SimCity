package simcity.buildings.bank;

import java.util.*;

import javax.swing.JPanel;

import simcity.Role;
import simcity.gui.*;
import simcity.gui.bank.*;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankTeller;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankHostRole.BankWindow;


public class BankSystem extends simcity.SimSystem{

	private BankComputer computer= new BankComputer();
	private BankHost bh;
	private List<BankCustomer> customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	private List<BankTeller> bankTellers = Collections.synchronizedList(new ArrayList<BankTeller>());
	private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	private BankWindow windowLookup;
	private BankWindow availableWindow;
	
	private static final int NUM_BANKWINDOWS = 3;
	
	public BankSystem(SimCityGui scg) {
		super(scg);
		super.setAnimationPanel(new BankAnimationPanel());
		super.setControlPanel(new BankControlPanel());
		
		for (int i=1; i<=NUM_BANKWINDOWS; i++) {
			windows.add(new BankWindow(i));
		}
	}

	public BankHost getBankHost() {
		return bh;
	}
	/*
	public void setBankHost(BankHost c) {
		this.bh = c;
		BankHostGui hostGui = new BankHostGui(c);
		animationPanel.addGui(hostGui);
	}
*/
	public void findAvailableWindow() {
		synchronized(windows) {
			for (BankWindow window : windows) {
				if (!window.isOccupied() && window.isReadyToServe()) {
					availableWindow = window;
				}
			}
		}
	}
	
	public BankWindow getAvailableWindow() {
		return availableWindow;
	}
	
	public void reinitializeAvailableWindow() {
		availableWindow = null;
	}
	
	public void findUnreadyWindowAndSendBankTeller(BankTeller bt) {
		synchronized(windows) {
			for (BankWindow window : windows) {
				if (!window.isReadyToServe()) {
					window.setBankTeller(bt);
					return;
				}
			}
		}
	}
	
	public void setWindowLookup(int windowNumber) {
		synchronized(windows) {
			for (BankWindow window : windows) {
				if (window.getWindowNumber() == windowNumber) {
					windowLookup = window;
				}
			}
		}
	}
	
	public BankWindow getWindowLookup() {
		return windowLookup;
	}
	public boolean msgEnterBuilding(Role role) {
		animationPanel.addGui(role.getGui());
		if(role instanceof BankCustomer) {
			customers.add((BankCustomer) role);
		}
		
		else if(role instanceof BankTeller) {
			bankTellers.add((BankTeller) role);
		}
		else if(role instanceof BankHost) {
			System.out.println(role == null);
			bh = (BankHost) role;
		}
		return true;
	}
	public void exitBuilding(Role role) {
		animationPanel.removeGui(role.getGui());
		if(role instanceof BankCustomer) {
			customers.remove((BankCustomer) role);
		}
		else if(role instanceof BankTeller) {
			bankTellers.remove((BankTeller) role);
		}
	}
	
}
