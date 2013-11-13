package simcity.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ControlPanel extends JPanel implements ActionListener {
	private JPanel view = new JPanel();
	private JButton pause = new JButton("Pause");
	private JButton start = new JButton("Start");
	private List<JButton> list = new ArrayList<JButton>();
	private JPanel view = new JPanel();
	public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private boolean startIt = false;
	private boolean pauseIt = false;
	private SimCityGui simGui;
	private String type;
	
	public ControlPanel(SimCityGui gui, String type) {
		simGui = gui;
		this.type = type;
		
		add(pane);
	}
	public void actionPerformed(ActionEvent e) {
		
	}
	public void start() {
		
	}
	public void addPerson(String name) {
		
	}
}