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
	private JPanel tab1 = new JPanel();
	private JPanel tab2 = new JPanel();
	
	//tab1 elements
	private JSlider globalSpeed = new JSlider();
	private JLabel globalSpeedLabel = new JLabel("Global Speed");
	
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
		
		tab1.setLayout(new FlowLayout());
		globalSpeedLabel.setAlignmentX(CENTER_ALIGNMENT);
		tab1.add(globalSpeedLabel);
		tab1.add(globalSpeed);
		
		tab2.setLayout(new FlowLayout());
		
		
		tabPane.addTab("Settings", tab1);
		tabPane.addTab("Log", tab2);
		add(tabPane);
		
		bottomSpace.setPreferredSize(new Dimension(this.getWidth(), 50));
		add(bottomSpace);
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pauseB) {
			if(pauseB.getText().equals("Pause")) {
				pauseB.setText("Resume");
			}
			else {
				pauseB.setText("Pause");
			}
		}
	}
	public void start() {
		
	}
	public void addPerson(String name) {
		
	}
}