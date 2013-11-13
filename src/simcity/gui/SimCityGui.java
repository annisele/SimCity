package simcity.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/****************************
 * SimCityGui - The entire window which contains a world panel, detail panel
 *  to display what the user is zoomed in on, consoles for both, and a 
 *  control panel.
 * @author rebeccahao
 *
 */
public class SimCityGui extends JFrame implements ActionListener {
	
	// Panel on the right
	private JPanel menuPanel = new JPanel();
	
	// Panels for each view and console
	private JPanel viewWorldPanel = new JPanel();
	private JPanel viewDetailPanel = new AnimationPanel();
	private JTextArea consoleWorld = new JTextArea();
	private JTextArea consoleDetail = new JTextArea();
	private JSplitPane splitPaneWorld;
	private JSplitPane splitPaneDetail;
	
	// Panel for everything
	private JPanel fullPane = new JPanel();
	GridLayout twoGridLayout = new GridLayout(0,2);
	
	// This is just to see the size of the right panel
	private ControlPanel controlPanel = new ControlPanel(this); // will be replaced with a panel

	
	public SimCityGui() {

		// testing code for the right panel
		menuPanel.setPreferredSize(new Dimension(250, 700));
		controlPanel.setPreferredSize(new Dimension(250, 700));
		menuPanel.add(controlPanel); // testing only
		
		// Setup the two views and consoles
		splitPaneWorld = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                viewWorldPanel, consoleWorld);
		splitPaneWorld.setResizeWeight(0.7);
		splitPaneDetail = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                viewDetailPanel, consoleDetail);
		splitPaneDetail.setResizeWeight(0.7);
		
		// Add it all to the main pane
		fullPane.setLayout(twoGridLayout);
		fullPane.add(splitPaneWorld);
		fullPane.add(splitPaneDetail);
		
		setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.EAST);
        add(fullPane, BorderLayout.CENTER);
        
	}
	public void updateInfoPanel(Object person) {
	
	}
	public void actionPerformed(ActionEvent e) {
	
	}
	  
	public static void main(String[] args) {
	  SimCityGui gui = new SimCityGui();
      gui.setTitle("SimCity Team 14");
      gui.setVisible(true);
      gui.setResizable(true);
      gui.setSize(1250, 700);
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
  
	  
}