package simcity.gui.house;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


/****************
 * Bank Animation Panel - 
 * @author levonne key
 */

public class HouseControlPanel extends JPanel {
	
	private JLabel typeLabel = new JLabel("<html>Type: House<br/><br/>");
	
	private JLabel foodLabel = new JLabel();

	
	public HouseControlPanel() {
		setLayout(new GridLayout(0, 1, 5, 0));
		foodLabel.setText("<html>Food Quantities:\n<br/>"+"<br/>salad: "+0
				+"<br/>chicken: "+0+"<br/>steak: "+0+"<br/>pizza: "+0+"</html>");
		add(typeLabel);
		add(foodLabel);
	}
	
	public void updateFoodDisplay(Map<String, Integer> foodStock) {
		String foodText = "<html>Food Quantities:\n<br/>";
		for (String food : foodStock.keySet()) {
			foodText += ("<br/>" + food + ": " + foodStock.get(food));
		}
		foodText += "</html>";
		foodLabel.setText(foodText);
	}
	
	
}