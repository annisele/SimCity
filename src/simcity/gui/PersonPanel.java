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

	
	private JLabel nameLabel = new JLabel();
	private JLabel setfoodLabel = new JLabel();
	private JLabel setchickenLabel = new JLabel();
	private JLabel setsteakLabel = new JLabel();
	private JLabel setpizzaLabel = new JLabel();
	private JLabel setsaladLabel = new JLabel();
	String name,role;

	 JPanel opanel = new JPanel();
	 
	public PersonPanel() {
		
		setLayout(new GridLayout(0, 1, 2, 0));
		//setfoodLabel.setText("<html><br/>Set Food Quantities:\n<br/></html>");
		
		setchickenLabel.setText("<html>chicken: </html>");
		setsteakLabel.setText("<html>steak: </html>");
		setpizzaLabel.setText("<html>pizza: </html>");
		
			add(typeLabel);
	
		
}
	public void setInfo(Gui g){
		
		name=g.getClass().getName();
		nameLabel.setText("<html>Name: </html>"+name);
		add(nameLabel);
	//	role=g.getClass().getCurrentRole();
		
	}

	
	
		
	}
