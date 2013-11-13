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
	private JPanel pauseAndTime = new JPanel();
	private JPanel configPanel = new JPanel();
	private JTabbedPane tabPane = new JTabbedPane();
	private JPanel bottomSpace = new JPanel();
	private JButton pauseB = new JButton("Pause");
	private JTextField clock = new JTextField();
	private String time;
	private String ampm;
	
	
	//tab1 (Settings) elements
	private JPanel tab1 = new JPanel();
	private JSlider globalSpeed = new JSlider();
	private JLabel globalSpeedLabel = new JLabel("Global Speed");


	

	
	//tab2 (Log) elements
	private JPanel tab2 = new JPanel();
	private JLabel worldConsoleLabel = new JLabel("<html><u><b>World Console</b></u></html>");
	private JLabel zoomConsoleLabel = new JLabel("Zoom Console");
	private JPanel topPanel = new JPanel();
	private JLabel errorsLabel = new JLabel("Errors: ");
	private JRadioButton outsideErrors = new JRadioButton("Outside");
	private JRadioButton allErrors = new JRadioButton("All");
	private JRadioButton noErrors = new JRadioButton("None");
	private JPanel errorButtonsPanel = new JPanel();
	
	
	private JButton start = new JButton("Start");
	private List<JButton> list = new ArrayList<JButton>();
	public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private boolean startIt = false;
	private boolean pauseIt = false;
	private SimCityGui simCityGui;
	private JComboBox configDropdown;
	private String[] configStrings = new String[2];
	
	public ControlPanel(SimCityGui gui) {
		simCityGui = gui;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		
		//config panel
		configStrings[0] = "One building, one person";
		configStrings[1] = "One building, two people";
		configDropdown = new JComboBox(configStrings);
		configPanel.setLayout(new FlowLayout());
		configPanel.add(configDropdown);
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
		topPanel.setLayout(new GridLayout(2, 1));
		worldConsoleLabel.setAlignmentX(CENTER_ALIGNMENT);
		//tab2.add(worldConsoleLabel);
		errorsLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(worldConsoleLabel);
		topPanel.add(errorsLabel);
		tab2.add(topPanel);
		//tab2.add(errorsLabel);
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
		
		
		//tab2 (Log) zoom section
		zoomConsoleLabel.setAlignmentX(CENTER_ALIGNMENT);

		
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
	public void start() {
		
	}
	public void addPerson(String name) {
		
	}
}