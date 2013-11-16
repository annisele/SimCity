package simcity.gui;

import javax.swing.*;

import simcity.Config;
import simcity.SystemManager;
import simcity.WorldSystem;
import simcity.buildings.market.MarketSystem;
import simcity.buildings.restaurant.one.RestaurantOneSystem;
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
	
	// But this is really a panel
	private AnimationPanel viewWorldPanel;
	private AnimationPanel viewDetailPanel;
	private JTextArea consoleWorld = new JTextArea();
	private JTextArea consoleDetail = new JTextArea();
	private JSplitPane splitPaneWorld;
	private JSplitPane splitPaneDetail;
	
	private Config config;
	private SystemManager systemManager;
	
	// Panel for everything
	private JPanel fullPane = new JPanel();
	GridLayout twoGridLayout = new GridLayout(0,2);
	
	private ControlPanel controlPanel;
	
	public SimCityGui() {
		systemManager = new SystemManager(this);
        config = new Config(systemManager);
                
        viewDetailPanel = new EmptyAnimationPanel();
        viewWorldPanel = systemManager.getWorld().getAnimationPanel();
        //
        // I'm thinking we don't need to initialize this DetailPane.  Maybe we'll initialize it to some default thing
        // When someone clicks on a building (after loading them), this DetailPane will show that building
        //
        //viewDetailPanel = systemManager.getRestaurantOne(0).getAnimationPanel();
        
		controlPanel  = new ControlPanel(this, config);
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

       
        
	}
	public ControlPanel getControlPanel() {
		return controlPanel;
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
  
	/*public WorldSystem getWorld() {
		return wp;
	}*/
	
	  
}