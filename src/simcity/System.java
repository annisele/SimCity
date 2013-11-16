package simcity;

import javax.swing.JPanel;

import simcity.gui.AnimationPanel;
import simcity.gui.SimCityGui;

public class System {

	protected AnimationPanel animationPanel;
	protected JPanel controlPanel;
	protected SimCityGui simCityGui;
	
	public System(SimCityGui scg) {
		simCityGui = scg;
	}
	
	public AnimationPanel getAnimationPanel() {
		return animationPanel;
	}
	
	public JPanel getControlPanel() {
		return controlPanel;
	}
	
}
