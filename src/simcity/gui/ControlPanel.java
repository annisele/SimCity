package simcity.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import simcity.Clock;
import simcity.Config;

/***************************
 * Control Panel - Entire right side panel. Used to control program.
 *  On bottom half of control panel, will contain tabs to switch between
 *  panel to control people or buildings, panel to control log, and panel
 *  to control general settings.
 *  
 * 
 */
public class ControlPanel extends JPanel implements ActionListener {
	
	private SimCityGui simCityGui;
	private Config config;
	
	//pause and clock elements
	private JPanel pauseAndTime = new JPanel();
	private JButton pauseB = new JButton("Pause");
	private JTextField clockDisplay = new JTextField("", 6);
	private Timer timer = new Timer();

	
	//config panel elements
	private JPanel configPanel = new JPanel();
	private JComboBox configDropdown;
	private String[] configStrings = new String[8];
	private JButton load = new JButton("Load");

	//tab elements
	private JTabbedPane tabPane = new JTabbedPane();
	private JPanel bottomSpace = new JPanel();
	
	//settingsTab (Settings) elements
	private JPanel settingsTab = new JPanel();
	private JSlider globalSpeed = new JSlider();
	private JLabel globalSpeedLabel = new JLabel("Global Speed");

	//logTab (Log) world console elements
	private JPanel logTab = new JPanel();
	private JLabel worldConsoleLabel = new JLabel("<html><u><b>World Console</b></u></html>");
	private JLabel zoomConsoleLabel = new JLabel("<html><u><b>Zoom Console</b></u></html>");
	private JPanel topPanel = new JPanel();
		//logTab: errors elements
	private JLabel errorsLabel = new JLabel("<html><b>Errors:</b></html>");
	private JRadioButton outsideErrors = new JRadioButton("Outside");
	private JRadioButton allErrors = new JRadioButton("All");
	private JRadioButton noErrors = new JRadioButton("None");
	private JPanel errorButtonsPanel = new JPanel();
		//logTab: communication elements
	private JPanel commPanel = new JPanel();
	private JLabel commLabel = new JLabel("<html><b>Communication:</b></html>");
	private JCheckBox pedestrian = new JCheckBox("Pedestrians");
	private JCheckBox busPass = new JCheckBox("Bus Passengers");
	private JCheckBox carPass = new JCheckBox("Car Passengers");
		//logTab: print role switches or not
	private JPanel roleSwitchPanel = new JPanel();
	private JLabel roleSwitch = new JLabel("<html><b>Role Switches:</b></html>");
	private JCheckBox roleSwitchCB = new JCheckBox();
	//logTab (Log) zoom console
	private JPanel zoomPanel = new JPanel();
	private JPanel zoomErrorsPanel = new JPanel();
	private JLabel errors2Label = new JLabel("<html><b>Errors:</b></html>");
	private JCheckBox errorsCB = new JCheckBox();
		//logTab: communication zoom elements
	private JPanel comm2Panel = new JPanel();
	private JLabel comm2Label = new JLabel("<html><b>Communication:</b></html>");
	private JCheckBox commCB = new JCheckBox();
		//logTab: role switching zoom elements
	private JPanel roleSwitch2Panel = new JPanel();
	private JLabel roleSwitch2 = new JLabel("<html><b>Role Switches:</b></html>");
	private JCheckBox roleSwitch2CB = new JCheckBox();
	
	//selectTab - controls the building or person selected
	private JPanel selectTab = new JPanel();
	
	//eventsTab - global events
	private JPanel eventsTab = new JPanel();
	private JButton earthquake = new JButton("Earthquake");
	private JButton fire = new JButton("Fire (In Selected Building)");
	
	
	public ControlPanel(SimCityGui gui, Config c) {
		simCityGui = gui;
		config = c;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		
		//config panel
		configStrings[0] = "Three Buildings";
		configStrings[1] = "One Market";
		configStrings[2] = "One Restaurant";
		configStrings[3] = "One Bank";
		configStrings[4] = "One House";
		configStrings[5] = "Full City";
		configStrings[6] = "One Market, One House";
		configStrings[7] = "Bus to Market";
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
		clockDisplay.setEnabled(false);
		//.addActionListener(this);
		pauseAndTime.setLayout(new FlowLayout());
		pauseAndTime.add(clockDisplay);
		pauseAndTime.add(pauseB);
		pauseAndTime.setPreferredSize(new Dimension(this.getWidth(), 100));
		add(pauseAndTime);
		timer.scheduleAtFixedRate((new TimerTask() {
			public void run() {
				clockDisplay.setText(Clock.getDisplayTime());
			}
		}), 0, 1000);
		
		tabPane.setPreferredSize(new Dimension(300, 650));
		
		//settingsTab (Settings)
		settingsTab.setLayout(new FlowLayout());
		globalSpeedLabel.setAlignmentX(CENTER_ALIGNMENT);
		settingsTab.add(globalSpeedLabel);
		settingsTab.add(globalSpeed);
		
		//Log tab world section
		logTab.setLayout(new FlowLayout());
		topPanel.setLayout(new GridLayout(3, 1));
		worldConsoleLabel.setAlignmentX(CENTER_ALIGNMENT);
		worldConsoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		errorsLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(worldConsoleLabel);
		topPanel.add(new JLabel());
		topPanel.add(errorsLabel);
		logTab.add(topPanel);
		
		//log tab world errors section
		allErrors.setSelected(false);
		allErrors.addActionListener(this);
		outsideErrors.setSelected(true);
		outsideErrors.addActionListener(this);
		noErrors.setSelected(false);
		noErrors.addActionListener(this);
		errorButtonsPanel.add(outsideErrors);
		errorButtonsPanel.add(allErrors);
		errorButtonsPanel.add(noErrors);
		logTab.add(errorButtonsPanel);
		
		//log tab world communications section
		commPanel.setLayout(new GridLayout(4, 1));
		commPanel.add(commLabel);
		pedestrian.setSelected(true);
		pedestrian.addActionListener(this);
		busPass.setSelected(true);
		busPass.addActionListener(this);
		carPass.setSelected(true);
		carPass.addActionListener(this);
		commPanel.add(pedestrian);
		commPanel.add(busPass);
		commPanel.add(carPass);
		logTab.add(commPanel);
		
		//log tab role switching
		roleSwitchCB.setSelected(true);
		roleSwitchCB.addActionListener(this);
		roleSwitchPanel.add(roleSwitch);
		roleSwitchPanel.add(roleSwitchCB);
		logTab.add(roleSwitchPanel);
		
		//log tab zoom section
		zoomPanel.setLayout(new GridLayout(5, 1));
		zoomConsoleLabel.setAlignmentX(CENTER_ALIGNMENT); //someone help me center this!!
		zoomConsoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		zoomPanel.add(zoomConsoleLabel);
		
		//log tab zoom errors
		errorsCB.setSelected(true);
		errorsCB.addActionListener(this);
		zoomErrorsPanel.add(errors2Label);
		zoomErrorsPanel.add(errorsCB);
		zoomPanel.add(zoomErrorsPanel);
		
		//log tab zoom communication section
		commCB.setSelected(true);
		commCB.addActionListener(this);
		comm2Panel.add(comm2Label);
		comm2Panel.add(commCB);
		zoomPanel.add(comm2Panel);
		
		//log tab zoom console role switching
		roleSwitch2CB.setSelected(true);
		roleSwitch2Panel.add(roleSwitch2);
		roleSwitch2Panel.add(roleSwitch2CB);
		zoomPanel.add(roleSwitch2Panel);
		logTab.add(zoomPanel);
		
		//selectTab
		selectTab.setLayout(new GridLayout(15, 1));
		
		//eventsTab
		eventsTab.setLayout(new GridLayout(15, 1, 50, 500));
		earthquake.addActionListener(this);
		fire.addActionListener(this);
		eventsTab.add(earthquake);
		eventsTab.add(fire);
		
		tabPane.addTab("Selected", selectTab);
		tabPane.addTab("Settings", settingsTab);
		tabPane.addTab("Log", logTab);
		tabPane.addTab("Events", eventsTab);
		
		add(tabPane);
		
		bottomSpace.setPreferredSize(new Dimension(this.getWidth(), 50));
		add(bottomSpace);
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		//NEED TO RESET WORLD WHEN LOAD IS PRESSED
		//when load button is pressed

		if(e.getSource() == load) {
			String selection = (String)configDropdown.getSelectedItem();
			if(selection.equals(configStrings[0])) {
				config.threeBuildings();
			} else if(selection.equals(configStrings[1])) {
				config.oneMarket();
			} else if(selection.equals(configStrings[2])) {
				config.oneRestaurant();
			} else if(selection.equals(configStrings[3])) {
				config.oneBank();
			} else if(selection.equals(configStrings[4])) {
				config.oneHouse();
			} else if(selection.equals(configStrings[5])) {
				config.fullCity();
			} else if(selection.equals(configStrings[6])) {
				config.oneMarketOneHouse();
			} else if(selection.equals(configStrings[7])) {
				config.busToMarket();
			}
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
		//when errors radio buttons are pressed in world console
		else if(e.getSource() == outsideErrors) {
			if(!outsideErrors.isSelected()) {
				outsideErrors.setSelected(true);
			}
			allErrors.setSelected(false);
			noErrors.setSelected(false);
		}
		else if(e.getSource() == allErrors) {
			if(!allErrors.isSelected()) {
				allErrors.setSelected(true);
			}
			outsideErrors.setSelected(false);
			noErrors.setSelected(false);
		}
		else if(e.getSource() == noErrors) {
			if(!noErrors.isSelected()) {
				noErrors.setSelected(true);
			}
			allErrors.setSelected(false);
			outsideErrors.setSelected(false);
		}
		//world console communication check boxes
		else if(e.getSource() == pedestrian) {
			
		}
		else if(e.getSource() == busPass) {
			
		}
		else if(e.getSource() == carPass) {
			
		}
		//world role switch check box
		else if(e.getSource() == roleSwitchCB) {
			
		}
		//zoom role switch check box
		else if(e.getSource() == roleSwitch2CB) {
			
		}
		//zoom errors check box
		else if(e.getSource() == errorsCB) {
			
		}
		//zoom communication check box
		else if(e.getSource() == commCB) {
			
		}
		//earthquake button in events tab
		else if(e.getSource() == earthquake) {
			
		}
		//fire button in events tab
		else if(e.getSource() == fire) {
			
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