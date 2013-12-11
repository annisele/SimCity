package simcity.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import simcity.Clock;
import simcity.Config;

/***************************
 * Control Panel - Entire right side panel. Used to control program.
 *  On bottom half of control panel, will contain tabs to switch between
 *  panel to control people or buildings, panel to control log, and panel
 *  to control general settings.d
 *  
 *  
 */
public class ControlPanel extends JPanel implements ActionListener {

	private SimCityGui simCityGui; 
	private Config config;

	//pause and clock elements
	private JPanel pauseAndTime = new JPanel();
	private JButton pauseB = new JButton("Pause");
	private JTextField clockDisplay = new JTextField("", 10);
	private Timer timer = new Timer();


	//config panel elements
	private JPanel configPanel = new JPanel();
	private JComboBox configDropdown;
	private String[] configStrings = new String[24];
	private JButton load = new JButton("Load");

	//tab elements
	private JTabbedPane tabPane = new JTabbedPane();
	private JPanel bottomSpace = new JPanel();

	//Add tab - Allows user to add people to simcity
	private JPanel addTab = new JPanel();
	private JTextField nameField = new JTextField("", 10);
	private JButton addPerson = new JButton("Add Person");
	private JComboBox typeDropdown;
	private String[] typeStrings = new String[2];
	private JComboBox placeDropdown;
	private String[] placeStrings = new String[1];
	
	//log tab - controls what trace panels show
	private JPanel logTab = new JPanel();

	//selectTab - controls the building or person selected
	private JPanel selectTab = new JPanel();


	//NEW log tab stuff
	private JCheckBox worldErrors = new JCheckBox("Show World Errors");
	private JCheckBox worldMessages = new JCheckBox("Show World Messages");
	private JCheckBox buildingErrors = new JCheckBox("Show Building Errors");
	private JCheckBox buildingMessages = new JCheckBox("Show Building Messages");
	private JCheckBox worldInfo = new JCheckBox("Show World Info");
	private JCheckBox debug = new JCheckBox("Show Debug Statements");

	public ControlPanel(SimCityGui gui, Config c) {
		simCityGui = gui;
		config = c;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));

		//config panel
		configStrings[0] = "Full City";
		configStrings[1] = "Market, House, Bank";
		configStrings[2] = "Bus to Market";
		configStrings[3] = "Restaurant, Market, Bank";
		configStrings[4] = "One Market, One House";
		configStrings[5] = "One Market";
		configStrings[6] = "One Restaurant";
		configStrings[7] = "One Bank";
		configStrings[8] = "RestaurantOne";
		configStrings[9] = "RestaurantThree";
		configStrings[10] = "RestaurantFour";
		configStrings[11] = "RestaurantFive";
		configStrings[12] = "RestaurantSix";
		configStrings[13] = "Full Market";
		configStrings[14] = "Bus Intersection Test";
		configStrings[15] = "Full Markets and Banks";
		configStrings[16] = "Scenario A";
		configStrings[17] = "Scenario B";
		configStrings[18] = "Scenario C";
		configStrings[19] = "Scenario E";
		configStrings[20] = "Scenario F";
		configStrings[21] = "Scenario G";
		configStrings[22] = "Scenario J";
		configStrings[23] = "OneBankShitty";


		configDropdown = new JComboBox(configStrings);
		configPanel.setLayout(new FlowLayout());
		configPanel.add(configDropdown);
		load.addActionListener(this);
		configPanel.add(load);
		configPanel.setPreferredSize(new Dimension(this.getWidth(), 100));
		add(configPanel);

		//pause and time panel
		pauseB.addActionListener(this);
		clockDisplay.setText("0:00");
		//clockDisplay.
		clockDisplay.setBackground(Color.WHITE);
		clockDisplay.setDisabledTextColor(Color.BLACK);
		clockDisplay.setEnabled(false);

		pauseAndTime.setLayout(new FlowLayout());
		pauseAndTime.add(clockDisplay);
		//pauseAndTime.add(pauseB);
		pauseAndTime.setPreferredSize(new Dimension(this.getWidth(), 100));
		add(pauseAndTime);
		timer.scheduleAtFixedRate((new TimerTask() {
			public void run() {
				//clockDisplay.setText(Clock.getDisplayTime());
				clockDisplay.setText("" + Clock.getDebugTime());
			}
		}), 0, 1000);

		tabPane.setPreferredSize(new Dimension(300, 650));
		
		//setting tab layouts
		addTab.setLayout(new GridLayout(15, 1));
		logTab.setLayout(new FlowLayout());
		//selectTab.setLayout(new GridLayout(15, 1));
		selectTab.setLayout(new FlowLayout());
		selectTab.setPreferredSize(new Dimension(this.getWidth(), 600));


		//Log tab section
		worldErrors.addActionListener(this);
		worldMessages.addActionListener(this);
		worldInfo.addActionListener(this);
		debug.addActionListener(this);
		buildingErrors.addActionListener(this);
		buildingMessages.addActionListener(this);
		//setting start mode for trace panel checkboxes
		worldInfo.setSelected(true);
		worldErrors.setSelected(true);
		worldMessages.setSelected(true);
		debug.setSelected(false);
		buildingErrors.setSelected(true);
		buildingMessages.setSelected(true);
		//adding options for what to show in log
		logTab.add(worldErrors);
		logTab.add(worldMessages);
		logTab.add(worldInfo);
		logTab.add(debug);
		logTab.add(buildingErrors);
		logTab.add(buildingMessages);
		
		//Add tab section
		typeStrings[0] = "No job";
		typeStrings[1] = "Market Worker";
		//jobStrings[12] = "";


		typeDropdown = new JComboBox(typeStrings);
		
		placeStrings[0] = "MARKET1";
		placeDropdown = new JComboBox(placeStrings);
		
		
		addPerson.addActionListener(this);
		addPerson.setPreferredSize(new Dimension(100, 30));
		addPerson.setEnabled(false);
		nameField.setPreferredSize(new Dimension(100, 30));
		nameField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(nameField.getText().length() == 0) { // || name already exists) {
					addPerson.setEnabled(false);
				}
				else {
					addPerson.setEnabled(true);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {				
		
			}

		});
		
		addTab.add(nameField);
		addTab.add(typeDropdown);
		addTab.add(placeDropdown);
		addTab.add(addPerson);

		tabPane.addTab("Selected", selectTab);
		tabPane.addTab("Add People", addTab);
		tabPane.addTab("Log", logTab);

		add(tabPane);

		bottomSpace.setPreferredSize(new Dimension(this.getWidth(), 50));
		add(bottomSpace);
	}




	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == load) {
			String selection = (String)configDropdown.getSelectedItem();
			if(selection.equals(configStrings[0])) {
				config.fullCity();
			} else if(selection.equals(configStrings[1])) {
				config.marketHouseBank();
			} else if(selection.equals(configStrings[2])) {
				config.busToMarket();
			} else if(selection.equals(configStrings[3])) {
				config.MarketBankRestaurant();
			} else if(selection.equals(configStrings[4])) {
				config.oneMarketOneHouse();
			} else if(selection.equals(configStrings[5])) {
				config.oneMarket();
			} else if(selection.equals(configStrings[6])) {
				config.oneRestaurant();
			} else if(selection.equals(configStrings[7])) {
				config.oneBank();
			} else if(selection.equals(configStrings[8])) {
				config.restaurantOne();
			} else if(selection.equals(configStrings[9])) {
				config.restaurantThree();
			} else if(selection.equals(configStrings[10])) {
				config.restaurantFour();
			} else if(selection.equals(configStrings[11])) {
				config.restaurantFive();
			} else if(selection.equals(configStrings[12])) {
				config.restaurantSix();
			}else if(selection.equals(configStrings[13])) {
				config.fullMarket();
			}else if (selection.equals(configStrings[14])) {
				config.busIntersection();
			} else if(selection.equals(configStrings[15])) {
				config.fullMarketAndBank();
			} else if(selection.equals(configStrings[16])) {
				config.scenarioA();
			} else if(selection.equals(configStrings[17])) {
				config.scenarioB();
			} else if(selection.equals(configStrings[18])) {
				config.scenarioC();
			} else if(selection.equals(configStrings[19])) {
				config.scenarioE();
			} else if(selection.equals(configStrings[20])) {
				config.scenarioF();
			} else if(selection.equals(configStrings[21])) {
				config.scenarioG();
			} else if(selection.equals(configStrings[22])) {
				config.scenarioJ();
			} else if(selection.equals(configStrings[23])) {
				config.OneBankShitty();
			}
			
		}

		else if(e.getSource() == worldErrors) {
			//turn on world errors based on if checkbox is selected
			simCityGui.setWorldErrorTrace(worldErrors.isSelected());
		}
		else if(e.getSource() == worldMessages) {
			simCityGui.setWorldMessageTrace(worldMessages.isSelected());
		}
		else if(e.getSource() == worldInfo) {
			simCityGui.setWorldInfoTrace(worldInfo.isSelected());
		}
		else if(e.getSource() == debug) {
			simCityGui.setWorldDebugTrace(debug.isSelected());
			simCityGui.setDetailDebugTrace(debug.isSelected());
		}
		else if(e.getSource() == buildingErrors) {
			simCityGui.setDetailErrorTrace(buildingErrors.isSelected());
		}
		else if(e.getSource() == buildingMessages) {
			simCityGui.setDetailMessageTrace(buildingMessages.isSelected());
		}

		//when pause button is pressed
		else if(e.getSource() == pauseB) {
			if(pauseB.getText().equals("Pause")) {
				pauseB.setText("Resume");
				
			}
			else {
				pauseB.setText("Pause");
			}
		}
		else if(e.getSource() == addPerson) {
			String selection = (String)typeDropdown.getSelectedItem();
			if(selection.equals(typeStrings[0])) {
				config.fullCity();
			}
		}

	}

	public void setSelectedPanel(JPanel panel) {
		selectTab.removeAll();
		selectTab.updateUI();
		selectTab.add(panel);
	}

	public void updateSelected(BuildingGui b) {


	}

}