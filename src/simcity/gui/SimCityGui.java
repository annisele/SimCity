package simcity.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import simcity.Config;
import simcity.SystemManager;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.trace.TracePanel;

/***
 * TRACE PANEL ISSUES - take out transparency, all guis need to override size (default
 * is 20)
 */


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
	TracePanel worldTracePanel = new TracePanel();
	TracePanel detailTracePanel = new TracePanel();

	
	// But this is really a panel
	private AnimationPanel viewWorldPanel;
	private JPanel viewDetailPanel;
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
                
       // viewDetailPanel = new EmptyAnimationPanel();
        viewDetailPanel = new JPanel(new CardLayout());
        viewWorldPanel = systemManager.getWorld().getAnimationPanel();
        //systemManager.getWorld().setAnimationPanel();
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
		
		//viewWorldPanel.setMinimumSize(minimumSize);
		splitPaneWorld = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                viewWorldPanel, worldTracePanel);
		splitPaneWorld.setResizeWeight(0.83);
		splitPaneDetail = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                viewDetailPanel, detailTracePanel);
		splitPaneDetail.setResizeWeight(0.83);
		viewWorldPanel.setMaximumSize(new Dimension(400, 400));
		viewDetailPanel.setMaximumSize(new Dimension(400, 400));
		
		worldTracePanel.setMinimumSize(new Dimension(200, 208));
		detailTracePanel.setMinimumSize(new Dimension(200, 208));
		//splitPaneWorld.setResizeWeight(0.0);
		//splitPaneDetail.setResizableWithParent(viewDetailPanel, Boolean.FALSE);
		
		//trace panels
		AlertLog.getInstance().addAlertListener(worldTracePanel);
		AlertLog.getInstance().addAlertListener(detailTracePanel);
		detailTracePanel.showAlertsWithTag(AlertTag.MARKET_CASHIER);
		detailTracePanel.showAlertsWithTag(AlertTag.MARKET_WORKER);
		detailTracePanel.showAlertsWithTag(AlertTag.MARKET_CUSTOMER);
		worldTracePanel.showAlertsWithTag(AlertTag.PEDESTRIAN);
		worldTracePanel.showAlertsWithTag(AlertTag.WORLD);
		worldTracePanel.showAlertsWithTag(AlertTag.IDLE_PERSON);

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
	
	public void setControlPanel(JPanel cp) {
		controlPanel.setSelectedPanel(cp);
		//System.out.println("Changing the control panel");
	}
	
	public JPanel getDetailPane() {
		return viewDetailPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
	
	}
	
	public void clearDetailPane() {
		viewDetailPanel.removeAll();
		viewDetailPanel.updateUI();
	}
	
	public void changeDetailPane(AnimationPanel a) {
		if (a == null) {
			System.out.println("You clicked on something that made us want to change the DetailPane, but the supplied panel is null!");
		} else {
			//viewDetailPanel = a;
			//CardLayout c = (CardLayout)(((Container) cards).getLayout());
		  //  c.show((Container) cards, a.getName());
			//viewDetailPanel = new JPanel(new CardLayout());
			
			// This works, but is hack-ish
			viewDetailPanel.removeAll();
			viewDetailPanel.updateUI();
			viewDetailPanel.add(a, "name");

			//CardLayout c = (CardLayout) viewDetailPanel.getLayout();
			//c.show(viewDetailPanel, a.getName());			
			//System.out.println("Changing the detail panel to "+a.getClass().getName());
		}
	}
	
	  
	public static void main(String[] args) {
	  SimCityGui gui = new SimCityGui();
      gui.setTitle("SimCity Team 14");
      
      
      gui.setResizable(false);
      gui.setSize(1250, 700);
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setVisible(true);
	}
  
	/*public WorldSystem getWorld() {
		return wp;
	}*/
	
	  
}