package simcity;

import javax.swing.JPanel;

import simcity.gui.AnimationPanel;
import simcity.gui.ControlPanel;
import simcity.gui.SimCityGui;

public class SimSystem {

	protected AnimationPanel animationPanel;
	protected JPanel controlPanel;
	protected SimCityGui simCityGui;
	protected String name;
	
	public SimSystem(SimCityGui scg) {
		simCityGui = scg;
	}
	
	public void setAnimationPanel(AnimationPanel a) {
		animationPanel = a;
	}
	
	public void setControlPanel(JPanel cp) {
		controlPanel = cp;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public AnimationPanel getAnimationPanel() {
		if (animationPanel == null) {
			System.out.println("Sorry, this SimSystem does not have an animationPanel!");
			return null;
		} else {
			return animationPanel;
		}
	}
	
	public JPanel getControlPanel() {
		return controlPanel;
	}
	
	public String getName() {
		return name;
	}

	/***
	 * msgEnterBuilding - person messages this, should be overwritten in each system class
	 * @param role - Role that wants to enter
	 * @return - true if person can enter building, otherwise false
	 */
	public boolean msgEnterBuilding(Role role) {
		return false;
	}

}
