package simcity.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/***************************
 * Control Panel - Entire right side panel. Used to control program.
 *  On bottom half of control panel, will contain tabs to switch between
 *  panel to control people or buildings, panel to control log, and panel
 *  to control general settings.
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
	private JPanel middlePanel = new JPanel();
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
	public ControlPanel(SimCityGui gui) {
		simCityGui = gui;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		
		//config panel
		configStrings[0] = "One building, one person";
		configStrings[1] = "One building, two people";
		configDropdown = new JComboBox(configStrings);
		configPanel.setLayout(new FlowLayout());
		configPanel.add(configDropdown);
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
		
		tabPane.setPreferredSize(new Dimension(300, 1000));
		
		//tab1 (Settings)
		tab1.setLayout(new FlowLayout());
		globalSpeedLabel.setAlignmentX(CENTER_ALIGNMENT);
		tab1.add(globalSpeedLabel);
		tab1.add(globalSpeed);
		
		//tab2 (Log) world section
		tab2.setLayout(new FlowLayout());
		topPanel.setLayout(new GridLayout(3, 1));
		worldConsoleLabel.setAlignmentX(CENTER_ALIGNMENT);
		worldConsoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		errorsLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(worldConsoleLabel);
		topPanel.add(new JLabel());
		topPanel.add(errorsLabel);
		tab2.add(topPanel);
		//tab2 log world errors section
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
		//tab2 log world communications section
		commPanel.setLayout(new GridLayout(4, 1));
		commPanel.add(commLabel);
		pedestrian.setSelected(true);
		busPass.setSelected(true);
		carPass.setSelected(true);
		commPanel.add(pedestrian);
		commPanel.add(busPass);
		commPanel.add(carPass);
		tab2.add(commPanel);
		//role switching
		roleSwitchCB.setSelected(true);
		roleSwitchPanel.add(roleSwitch);
		roleSwitchPanel.add(roleSwitchCB);
		tab2.add(roleSwitchPanel);
		
		//tab2 (Log) zoom section
		zoomPanel.setLayout(new GridLayout(5, 1));
		zoomConsoleLabel.setAlignmentX(CENTER_ALIGNMENT); //someone help me center this!!
		zoomConsoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		zoomPanel.add(zoomConsoleLabel);
			//tab2 zoom errors
		errorsCB.setSelected(true);
		zoomErrorsPanel.add(errors2Label);
		zoomErrorsPanel.add(errorsCB);
		zoomPanel.add(zoomErrorsPanel);
			//tab2 (Log) zoom communication section
		commCB.setSelected(true);
		comm2Panel.add(comm2Label);
		comm2Panel.add(commCB);
		zoomPanel.add(comm2Panel);
			//tab2 role switching
		roleSwitch2CB.setSelected(true);
		roleSwitch2Panel.add(roleSwitch2);
		roleSwitch2Panel.add(roleSwitch2CB);
		zoomPanel.add(roleSwitch2Panel);
		
		
		tab2.add(zoomPanel);
		
		tabPane.addTab("Settings", tab1);
		tabPane.addTab("Log", tab2);
		add(tabPane);
		
		bottomSpace.setPreferredSize(new Dimension(this.getWidth(), 50));
		add(bottomSpace);
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		//when pause button is pressed
		if(e.getSource() == pauseB) {
			if(pauseB.getText().equals("Pause")) {
				pauseB.setText("Resume");
			}
			else {
				pauseB.setText("Pause");
			}
		}
		//when errors radio buttons are pressed
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
	}
}