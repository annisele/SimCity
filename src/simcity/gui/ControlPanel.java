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
	private JPanel view = new JPanel();
	private JButton pause = new JButton("Pause");
	private JButton start = new JButton("Start");
	private List<JButton> list = new ArrayList<JButton>();
	public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private boolean startIt = false;
	private boolean pauseIt = false;
	private SimCityGui simCityGui;
	
	public ControlPanel(SimCityGui gui) {
		simCityGui = gui;
		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		
		
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
	}
	public void start() {
		
	}
	public void addPerson(String name) {
		
	}
}