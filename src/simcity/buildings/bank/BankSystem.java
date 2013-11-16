package simcity.buildings.bank;

import java.util.*;
import simcity.gui.*;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankHostRole.BankWindow;

public class BankSystem extends simcity.System{


	private BankHostRole bh;
	private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	private BankWindow windowLookup;
	
	private static final int NUM_BANKWINDOWS = 3;
	
	public BankSystem(SimCityGui scg) {
		super(scg);
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
