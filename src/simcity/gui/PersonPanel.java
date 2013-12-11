package simcity.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simcity.buildings.restaurant.two.RestaurantTwoComputer;

public class PersonPanel extends JPanel {
        
	private JLabel typeLabel = new JLabel("Type: Person");
//	private JButton drainstock = new JButton("Remove Food Stock");
	//private JButton AlexSmith = new JButton("Alex Smith");

	
	private JLabel foodLabel = new JLabel();
	private JLabel setfoodLabel = new JLabel();
	private JLabel setchickenLabel = new JLabel();
	private JLabel setsteakLabel = new JLabel();
	private JLabel setpizzaLabel = new JLabel();
	private JLabel setsaladLabel = new JLabel();

	 JPanel opanel = new JPanel();
	 
	public PersonPanel(RestaurantTwoComputer c) {
		
		setLayout(new GridLayout(0, 1, 2, 0));
		foodLabel.setText("<html>Food Quantities:\n<br/>"+"<br/>salad: "+0
				+"<br/>chicken: "+0+"<br/>steak: "+0+"<br/>pizza: "+0+"</html>");
		//setfoodLabel.setText("<html><br/>Set Food Quantities:\n<br/></html>");
		setsaladLabel.setText("<html>salad: </html>");
		setchickenLabel.setText("<html>chicken: </html>");
		setsteakLabel.setText("<html>steak: </html>");
		setpizzaLabel.setText("<html>pizza: </html>");
		
			add(typeLabel);
		add(foodLabel);
		
}

	
	
		
	}
