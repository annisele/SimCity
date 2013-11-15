package simcity.gui;

import javax.swing.*;

import simcity.buildings.Building;
import simcity.buildings.market.MarketPanel;
import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.transportation.PedestrianGui;

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
	//	private JPanel viewWorldPanel = new WorldAnimationPanel();
	//	private JPanel viewDetailPanel = new MarketAnimationPanel(); //new AnimationPanel();
	
	// This isn't really a panel
	private WorldPanel wp = new WorldPanel(this);
	private MarketPanel mp = new MarketPanel(this);
	// But this is really a panel
	private AnimationPanel viewWorldPanel = wp.getAnimationPanel();
	private AnimationPanel viewDetailPanel = mp.getAnimationPanel();
	private JTextArea consoleWorld = new JTextArea();
	private JTextArea consoleDetail = new JTextArea();
	private JSplitPane splitPaneWorld;
	private JSplitPane splitPaneDetail;
	
	// Panel for everything
	private JPanel fullPane = new JPanel();
	GridLayout twoGridLayout = new GridLayout(0,2);
	
	private ControlPanel controlPanel = new ControlPanel(this);

	
	//TEST
	private PedestrianGui testPed = new PedestrianGui();
	
	public SimCityGui() {

		menuPanel.setPreferredSize(new Dimension(300, 700));
		controlPanel.setPreferredSize(new Dimension(300, 700));
		menuPanel.add(controlPanel);
		
		// Setup the two views and consoles
		consoleWorld.setEnabled(false);
		consoleDetail.setEnabled(false);
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
        viewWorldPanel.addBuilding(new Building("Market1", 30, 10));
        viewWorldPanel.addBuilding(new Building("Bank1", 110, 10));
        viewWorldPanel.addGui(testPed);
        
        
	}
	public void updateInfoPanel(Object person) {
		
	}
	public void actionPerformed(ActionEvent e) {
	
	}
	  
	public static void main(String[] args) {
	  SimCityGui gui = new SimCityGui();
      gui.setTitle("SimCity Team 14");
      
      gui.setResizable(true);
      gui.setSize(1250, 700);
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setVisible(true);
	}
  
	  
}