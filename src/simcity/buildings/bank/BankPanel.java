/*******************
 * Bank Panel - Controls everything within the panel. Creates the roles and GUI's.
 * @author levonne key
 *
 */
package simcity.buildings.bank;

import java.util.*;
import javax.swing.JPanel;
import simcity.gui.SimCityGui;
import simcity.gui.bank.BankAnimationPanel;
import simcity.gui.bank.BankControlPanel;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankTeller;


public class BankPanel extends JPanel {

	private SimCityGui simCityGui;
	public BankControlPanel controlPanel;
	public BankAnimationPanel animationPanel;
	private BankComputer system = new BankComputer();;
	private List<BankCustomer> customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	private List<BankHost> trucks = Collections.synchronizedList(new ArrayList<BankHost>());
	private List<BankTeller> workers = Collections.synchronizedList(new ArrayList<BankTeller>());

	public BankPanel(SimCityGui scg) {
		super();
		controlPanel = new BankControlPanel();
		animationPanel = new BankAnimationPanel(scg);
	}
	
	
	
	
	
	
	
	
}
