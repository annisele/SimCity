package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.AnimationPanel;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;

/****************
 * Bank Animation Panel - 
 * @author levonne key
 */

public class BankControlPanel extends JPanel {
	
	private JLabel typeLabel = new JLabel("Type: Bank");
	//int windows = 3;
	public BankControlPanel() {
		add(typeLabel);
	}
	/*
	private Vector<BankCustomerRole> bankCustomers = new Vector<BankCustomerRole>();
	private ArrayList<BankTellerRole> bankTeller = new ArrayList<BankTellerRole>();
	private BankHostRole bankHost = new BankHostRole("Bank Host", windows);
	*/
	
	
}