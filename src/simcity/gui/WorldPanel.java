package simcity.gui;

public class WorldPanel {
	
	private SimCityGui simCityGui;
	private WorldAnimationPanel wap = new WorldAnimationPanel();
	
	WorldPanel(SimCityGui sc) {
		simCityGui = sc;
	}
	
	AnimationPanel getAnimationPanel() {
		return wap;
	}
}
