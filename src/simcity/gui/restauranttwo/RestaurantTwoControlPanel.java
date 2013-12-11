package simcity.gui.restauranttwo;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RestaurantTwoControlPanel extends JPanel {
        
	private JLabel typeLabel = new JLabel("Type: American Haute Cuisine");
//	private JButton drainstock = new JButton("Remove Food Stock");
	//private JButton AlexSmith = new JButton("Alex Smith");

	
	private JLabel foodLabel = new JLabel();


   
	public RestaurantTwoControlPanel() {
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