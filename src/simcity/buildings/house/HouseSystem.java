package simcity.buildings.house;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.Role;
import simcity.gui.SimCityGui;
import simcity.gui.house.*;
import simcity.interfaces.house.HouseInhabitant;


public class HouseSystem extends simcity.SimSystem {
	
	private List<HouseInhabitant> residents = Collections.synchronizedList(new ArrayList<HouseInhabitant>());
	
	public HouseSystem(SimCityGui scg) {
		super(scg);
		super.setControlPanel(new HouseControlPanel());
		super.setAnimationPanel(new HouseAnimationPanel());
	}
	
	public void updateFoodDisplay(HouseInhabitant h) {
		((HouseControlPanel)controlPanel).updateFoodDisplay(h.getFoodStock());
	}
	
	@Override
	public boolean msgEnterBuilding(Role role) {
		animationPanel.addGui(role.getGui());
		if(role instanceof HouseInhabitant) {
			residents.add((HouseInhabitant) role);
			updateFoodDisplay((HouseInhabitant) role);
		}
		return true;
	}

	public void exitBuilding(Role role) {
		animationPanel.removeGui(role.getGui());
		if(role instanceof HouseInhabitant) {
			residents.remove((HouseInhabitant) role);
		}
	}
	
	
}
