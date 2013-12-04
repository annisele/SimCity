package simcity.gui.restaurantthree;

import javax.swing.JLabel;
import javax.swing.JPanel;
/******************
 * Restaurant 3 Control Panel - When market is selected, this will show up under Select tab in the control panel.
 * @author levonne key
 *
 */
public class RestaurantThreeControlPanel extends JPanel {
	private JLabel typeLabel = new JLabel("Type: Restaurant 3");
        public RestaurantThreeControlPanel() {
               add(typeLabel); 
        }
        
}