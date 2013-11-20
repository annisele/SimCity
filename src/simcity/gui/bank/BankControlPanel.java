package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;

/****************
 * Bank Animation Panel - 
 * @author levonne key
 */

public class BankControlPanel extends JPanel {
	
	private JLabel typeLabel = new JLabel("Type: Bank");
	
	public BankControlPanel() {
		add(typeLabel);
	}
	
}