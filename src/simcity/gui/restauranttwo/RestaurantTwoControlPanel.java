package simcity.gui.restauranttwo;

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

public class RestaurantTwoControlPanel extends JPanel implements ActionListener  {
        
	private JLabel typeLabel = new JLabel("Type: American Haute Cuisine");
//	private JButton drainstock = new JButton("Remove Food Stock");
	//private JButton AlexSmith = new JButton("Alex Smith");

	private RestaurantTwoComputer comp;
	private JLabel foodLabel = new JLabel();
	private JLabel setfoodLabel = new JLabel();
	private JLabel setchickenLabel = new JLabel();
	private JLabel setsteakLabel = new JLabel();
	private JLabel setpizzaLabel = new JLabel();
	private JLabel setsaladLabel = new JLabel();
	 private JButton setInventory = new JButton("Set Inventory");
	 private JTextField chbox; 
	 private JTextField stbox; 
	 private JTextField pibox; 
	 private JTextField sabox; 
	 JPanel opanel = new JPanel();
	 
	public RestaurantTwoControlPanel(RestaurantTwoComputer c) {
		this.comp=c;
		setLayout(new GridLayout(0, 1, 2, 0));
		foodLabel.setText("<html>Food Quantities:\n<br/>"+"<br/>salad: "+0
				+"<br/>chicken: "+0+"<br/>steak: "+0+"<br/>pizza: "+0+"</html>");
		//setfoodLabel.setText("<html><br/>Set Food Quantities:\n<br/></html>");
		setsaladLabel.setText("<html>salad: </html>");
		setchickenLabel.setText("<html>chicken: </html>");
		setsteakLabel.setText("<html>steak: </html>");
		setpizzaLabel.setText("<html>pizza: </html>");
		
			stbox = new JTextField("");
	        stbox.setPreferredSize( new Dimension(100,24)); 
	        chbox = new JTextField("");
	        chbox.setPreferredSize( new Dimension(100,24));
	        pibox = new JTextField("");
	        pibox.setPreferredSize( new Dimension(100,24));
	        sabox = new JTextField("");
	        sabox.setPreferredSize( new Dimension(100,24));
		add(typeLabel);
		add(foodLabel);
		opanel.setLayout(new GridLayout(0, 2));
		
	      
		opanel.add(setsaladLabel);
		opanel.add(sabox);
		
		opanel.add(setchickenLabel);
		opanel.add(chbox);
		opanel.add(setsteakLabel);
		opanel.add(stbox);
		opanel.add(setpizzaLabel);
		opanel.add(pibox);
		 setInventory.addActionListener(this);
		 opanel.add(setInventory);
		 add(opanel);
}
	public void updateFoodDisplay(Map<String, Integer> foodStock) {
		String foodText = "<html>Food Quantities:\n<br/>";
		for (String food : foodStock.keySet()) {
			foodText += ("<br/>" + food + ": " + foodStock.get(food));
		}
		foodText += "</html>";
		foodLabel.setText(foodText);
		//this.invalidate();
		//this.validate();
		//this.updateUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == setInventory) {
	         	// Chapter 2.19 describes showInputDialog()
	            setInventory( stbox.getText(), chbox.getText(),pibox.getText(),sabox.getText());
	           
	             stbox.setText("");
	             pibox.setText("");
	             sabox.setText("");
	             chbox.setText("");
	             
	             		
	         }
	}
	public void setInventory(String steak,String chicken,String pizza,String salad){
		int st = Integer.parseInt(steak);
		int ch = Integer.parseInt(chicken);
		int pi = Integer.parseInt(pizza);
		int sa = Integer.parseInt(salad);
		comp.setInventory(st,ch,sa,pi);
	}
		// TODO Auto-generated method stub
		
	}
