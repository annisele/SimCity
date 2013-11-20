package simcity.buildings.bank;

import java.util.*;

import javax.swing.JPanel;

import simcity.gui.*;
import simcity.gui.bank.*;
import simcity.gui.market.MarketAnimationPanel;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankHostRole.BankWindow;

public class BankSystem extends simcity.SimSystem{


	private BankHostRole bh;
	private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	private BankWindow windowLookup;
	
	private static final int NUM_BANKWINDOWS = 3;
	
	public BankSystem(SimCityGui scg) {
		super(scg);
		
		super.setAnimationPanel(new BankAnimationPanel());
		super.setControlPanel(new BankControlPanel());
		// once a control panel is created, we need to uncomment this!!!
		//controlPanel = new BankControlPanel();
		
		for (int i=1; i<=NUM_BANKWINDOWS; i++) {
			windows.add(new BankWindow(i));
		}
	}

	public BankHostRole getBankHost() {
		return bh;
	}

	public void setBankHost(BankHostRole bh) {
		this.bh = bh;
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
	
}
