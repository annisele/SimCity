package simcity.gui.market;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simcity.buildings.market.MarketComputer;

/******************
 * Market Control Panel - When market is selected, this will show up under Select tab in the control panel.
 * @author rebeccahao
 *
 */
public class MarketControlPanel extends JPanel implements ActionListener{
	private MarketComputer comp;
	public Map<String,Integer> in= new HashMap<String, Integer>();

	private JLabel typeLabel = new JLabel("Type: Market");
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
	public MarketControlPanel(MarketComputer c) {
		this.comp=c;
		setLayout(new GridLayout(10, 1, 2, 0));
		foodLabel.setText("<html>Food Quantities:\n<br/>"+"<br/>salad: "+0
				+"<br/>chicken: "+0+"<br/>steak: "+0+"<br/>pizza: "+0+"</html>");
		//setfoodLabel.setText("<html><br/>Set Food Quantities:\n<br/></html>");
		setsaladLabel.setText("<html>salad: </html>");
		setchickenLabel.setText("<html>chicken: </html>");
		setsteakLabel.setText("<html>steak: </html>");
		setpizzaLabel.setText("<html>pizza: </html>");
		setInventory.setEnabled(false);
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
		add(setfoodLabel);
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
		chbox.addActionListener(this);
		stbox.addActionListener(this);
		pibox.addActionListener(this);
		sabox.addActionListener(this);
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
		else if(e.getSource() == stbox) {
			if(stbox.getText() != "" && chbox.getText() != "" && pibox.getText() != "" && sabox.getText() != "") {
				setInventory.setEnabled(true);
			}
			else {
				setInventory.setEnabled(false);
			}
		}
		else if(e.getSource() == chbox) {
			if(stbox.getText() != "" && chbox.getText() != "" && pibox.getText() != "" && sabox.getText() != "") {
				setInventory.setEnabled(true);
			}
			else {
				setInventory.setEnabled(false);
			}
		}
		else if(e.getSource() == sabox) {
			if(stbox.getText() != "" && chbox.getText() != "" && pibox.getText() != "" && sabox.getText() != "") {
				setInventory.setEnabled(true);
			}
			else {
				setInventory.setEnabled(false);
			}
		}
		else if(e.getSource() == pibox) {
			if(stbox.getText() != "" && chbox.getText() != "" && pibox.getText() != "" && sabox.getText() != "") {
				setInventory.setEnabled(true);
			}
			else {
				setInventory.setEnabled(false);
			}
		}
	}
	public void setInventory(String steak,String chicken,String pizza,String salad){
		int st = Integer.parseInt(steak);
		int ch = Integer.parseInt(chicken);
		int pi = Integer.parseInt(pizza);
		int sa = Integer.parseInt(salad);
		in.put("steak",st);
		in.put("chicken",ch);
		in.put("pizza",pi);
		in.put("salad",sa);
		comp.setInventory(in);
	}

}
