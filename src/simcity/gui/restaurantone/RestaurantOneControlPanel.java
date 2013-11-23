package simcity.gui.restaurantone;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RestaurantOneControlPanel extends JPanel {
        
	private JLabel typeLabel = new JLabel("Type: American Haute Cuisine");
	private JButton drainstock = new JButton("Remove Food Stock");
	private JButton AlexSmith = new JButton("Alex Smith");
	
	



   
	public RestaurantOneControlPanel() {
		add(typeLabel);
		add(drainstock);
		add(AlexSmith);
		


		
    }
        
}