package simcity.gui.market;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simcity.gui.ControlPanel;

/******************
 * Market Control Panel - When market is selected, this will show up under Select tab in the control panel.
 * @author rebeccahao
 *
 */
public class MarketControlPanel extends JPanel {
	
	private JLabel typeLabel = new JLabel("Type: Market");
	
	public MarketControlPanel() {
		add(typeLabel);
		
	}
	
}
