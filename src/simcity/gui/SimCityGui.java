package simcity.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import simcity.Config;
import simcity.SystemManager;
import simcity.gui.trace.AlertLevel;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.trace.TracePanel;

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
	private TracePanel worldTracePanel = new TracePanel();
	private TracePanel detailTracePanel = new TracePanel();

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
                
        viewDetailPanel = new JPanel(new CardLayout());
        viewWorldPanel = systemManager.getWorld().getAnimationPanel();
  
		controlPanel  = new ControlPanel(this, config);
		menuPanel.setPreferredSize(new Dimension(300, 700));
		controlPanel.setPreferredSize(new Dimension(300, 700));
		menuPanel.add(controlPanel);
		
		// Setup the two views and consoles
		
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
		
		// Trace panels
		AlertLog.getInstance().addAlertListener(worldTracePanel);
		AlertLog.getInstance().addAlertListener(detailTracePanel);
		worldTracePanel.showAlertsWithTag(AlertTag.WORLD);

		// Add it all to the main pane
		fullPane.setLayout(twoGridLayout);
		fullPane.add(splitPaneWorld);
		fullPane.add(splitPaneDetail);
		
		setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.EAST);
        add(fullPane, BorderLayout.CENTER);
	}
	
	public void setWorldErrorTrace(boolean set) {
		if(set) {
			worldTracePanel.showAlertsWithLevel(AlertLevel.ERROR);
		}
		else {
			worldTracePanel.hideAlertsWithLevel(AlertLevel.ERROR);
		}
	}
	
	public void setWorldMessageTrace(boolean set) {
		if(set) {
			worldTracePanel.showAlertsWithLevel(AlertLevel.MESSAGE);
		}
		else {
			worldTracePanel.hideAlertsWithLevel(AlertLevel.MESSAGE);
		}
	}
	
	public void setWorldInfoTrace(boolean set) {
		if(set) {
			worldTracePanel.showAlertsWithLevel(AlertLevel.INFO);
		}
		else {
			worldTracePanel.hideAlertsWithLevel(AlertLevel.INFO);
		}
	}
	
	public void setDetailErrorTrace(boolean set) {
		if(set) {
			detailTracePanel.showAlertsWithLevel(AlertLevel.ERROR);
		}
		else {
			detailTracePanel.hideAlertsWithLevel(AlertLevel.ERROR);
		}
	}
	
	public void setDetailMessageTrace(boolean set) {
		if(set) {
			detailTracePanel.showAlertsWithLevel(AlertLevel.MESSAGE);
		}
		else {
			detailTracePanel.hideAlertsWithLevel(AlertLevel.MESSAGE);
		}
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	
	public TracePanel getDetailTracePanel() {
		return detailTracePanel;
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
			viewDetailPanel.removeAll();
			viewDetailPanel.updateUI();
			viewDetailPanel.add(a, "name");
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
}
