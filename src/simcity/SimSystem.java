package simcity;

import javax.swing.JPanel;

import simcity.gui.AnimationPanel;
import simcity.gui.SimCityGui;

public class SimSystem {

	protected AnimationPanel animationPanel;
	protected JPanel controlPanel;
	protected SimCityGui simCityGui;
	
	public SimSystem(SimCityGui scg) {
		simCityGui = scg;
	}
	
	public void setAnimationPanel(AnimationPanel a) {
		animationPanel = a;
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
	
}
