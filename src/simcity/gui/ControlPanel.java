package simcity.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***************************
 * Control Panel - Entire right side panel. Used to control program.
 *  On bottom half of control panel, will contain tabs to switch between
 *  panel to control people or buildings, panel to control log, and panel
 *  to control general settings.
 *  
 *  NOTES - need to find shorter tab names or it looks bad
 * @author rebeccahao
 * 
 */
public class ControlPanel extends JPanel implements ActionListener {
	
	private SimCityGui simCityGui;
	
	//pause and clock elements
	private JPanel pauseAndTime = new JPanel();
	private JButton pauseB = new JButton("Pause");
	private JTextField clock = new JTextField();
	private String time;
	private String ampm;
	
	//config panel elements
	private JPanel configPanel = new JPanel();
	private JComboBox configDropdown;
	private String[] configStrings = new String[2];
	private JButton load = new JButton("Load");

	//tab elements
	private JTabbedPane tabPane = new JTabbedPane();
	private JPanel bottomSpace = new JPanel();
	
	//tab1 (Settings) elements
	private JPanel tab1 = new JPanel();
	private JSlider globalSpeed = new JSlider();
	private JLabel globalSpeedLabel = new JLabel("Global Speed");

	//tab2 (Log) world console elements
	private JPanel tab2 = new JPanel();
	private JLabel worldConsoleLabel = new JLabel("<html><u><b>World Console</b></u></html>");
	private JLabel zoomConsoleLabel = new JLabel("<html><u><b>Zoom Console</b></u></html>");
	private JPanel topPanel = new JPanel();
		//tab2: errors elements
	private JLabel errorsLabel = new JLabel("<html><b>Errors:</b></html>");
	private JRadioButton outsideErrors = new JRadioButton("Outside");
	private JRadioButton allErrors = new JRadioButton("All");
	private JRadioButton noErrors = new JRadioButton("None");
	private JPanel errorButtonsPanel = new JPanel();
		//tab2: communication elements
	private JPanel commPanel = new JPanel();
	private JLabel commLabel = new JLabel("<html><b>Communication:</b></html>");
	private JCheckBox pedestrian = new JCheckBox("Pedestrians");
	private JCheckBox busPass = new JCheckBox("Bus Passengers");
	private JCheckBox carPass = new JCheckBox("Car Passengers");
		//tab2: print role switches or not
	private JPanel roleSwitchPanel = new JPanel();
	private JLabel roleSwitch = new JLabel("<html><b>Role Switches:</b></html>");
	private JCheckBox roleSwitchCB = new JCheckBox();
	//tab2 (Log) zoom console
	private JPanel zoomPanel = new JPanel();
	private JPanel zoomErrorsPanel = new JPanel();
	private JLabel errors2Label = new JLabel("<html><b>Errors:</b></html>");
	private JCheckBox errorsCB = new JCheckBox();
		//tab2: communication zoom elements
	private JPanel comm2Panel = new JPanel();
	private JLabel comm2Label = new JLabel("<html><b>Communication:</b></html>");
	private JCheckBox commCB = new JCheckBox();
		//tab2: role switching zoom elements
	private JPanel roleSwitch2Panel = new JPanel();
	private JLabel roleSwitch2 = new JLabel("<html><b>Role Switches:</b></html>");
	private JCheckBox roleSwitch2CB = new JCheckBox();
	
	//tab3 - controls the building or person selected
	private JPanel tab3 = new JPanel();
	private JLabel nameLabel = new JLabel("Name: Ralph's");
	private JLabel typeLabel = new JLabel("Type: Market");
	
	//tab4 - global events
	private JPanel tab4 = new JPanel();
	private JButton earthquake = new JButton("Earthquake");
	private JButton fire = new JButton("Fire (In Selected Building)");
	
	
	public ControlPanel(SimCityGui gui) {
		simCityGui = gui;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		
		//config panel
		configStrings[0] = "One building, one person";
		configStrings[1] = "One building, two people";
		configDropdown = new JComboBox(configStrings);
		configPanel.setLayout(new FlowLayout());
		configPanel.add(configDropdown);
		load.addActionListener(this);
		configPanel.add(load);
		configPanel.setPreferredSize(new Dimension(this.getWidth(), 100));
		add(configPanel);
		
		//pause and time panel
		pauseB.addActionListener(this);
		time = "10:10";
		ampm = " AM";
		clock.setText(time + ampm);
		clock.setEnabled(false);
		pauseAndTime.setLayout(new FlowLayout());
		pauseAndTime.add(clock);
		pauseAndTime.add(pauseB);
		pauseAndTime.setPreferredSize(new Dimension(this.getWidth(), 100));
		add(pauseAndTime);
		
		tabPane.setPreferredSize(new Dimension(300, 650));
		
		//tab1 (Settings)
		tab1.setLayout(new FlowLayout());
		globalSpeedLabel.setAlignmentX(CENTER_ALIGNMENT);
		tab1.add(globalSpeedLabel);
		tab1.add(globalSpeed);
		
		//Log tab world section
		tab2.setLayout(new FlowLayout());
		topPanel.setLayout(new GridLayout(3, 1));
		worldConsoleLabel.setAlignmentX(CENTER_ALIGNMENT);
		worldConsoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		errorsLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(worldConsoleLabel);
		topPanel.add(new JLabel());
		topPanel.add(errorsLabel);
		tab2.add(topPanel);
		
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
		tab2.add(errorButtonsPanel);
		
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
		tab2.add(commPanel);
		
		//log tab role switching
		roleSwitchCB.setSelected(true);
		roleSwitchCB.addActionListener(this);
		roleSwitchPanel.add(roleSwitch);
		roleSwitchPanel.add(roleSwitchCB);
		tab2.add(roleSwitchPanel);
		
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
		tab2.add(zoomPanel);
		
		//tab3
		tab3.setLayout(new GridLayout(15, 1));
		tab3.add(nameLabel);
		tab3.add(typeLabel);
		
		//tab4
		tab4.setLayout(new GridLayout(15, 1));
		earthquake.addActionListener(this);
		fire.addActionListener(this);
		tab4.add(earthquake);
		tab4.add(fire);
		
		
		
		tabPane.addTab("Control", tab3);
		tabPane.addTab("Settings", tab1);
		tabPane.addTab("Log", tab2);
		tabPane.addTab("Events", tab4);
		
		
		
		add(tabPane);
		
		bottomSpace.setPreferredSize(new Dimension(this.getWidth(), 50));
		add(bottomSpace);
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		//when load button is pressed
		if(e.getSource() == load) {
			
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
}