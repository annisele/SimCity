package simcity.gui.house;

import javax.swing.JLabel;
import javax.swing.JPanel;


/****************
 * Bank Animation Panel - 
 * @author levonne key
 */

public class HouseControlPanel extends JPanel {
	
	private JLabel typeLabel = new JLabel("Type: House");
	
	public HouseControlPanel() {
		add(typeLabel);
	}
	
}