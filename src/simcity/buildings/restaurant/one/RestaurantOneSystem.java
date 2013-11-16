package simcity.buildings.restaurant.one;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import java.awt.*;

import simcity.gui.SimCityGui;
import simcity.gui.restaurant.RestaurantAnimationPanel;
import simcity.interfaces.restaurant.one.*;


/* public class RestaurantOneSystem extends simcity.System {
	
	import javax.swing.JPanel;
	import simcity.gui.SimCityGui;
	import simcity.gui.restaurant.RestaurantAnimationPanel; */

	public class RestaurantOneSystem extends JPanel {

	        private SimCityGui scg;
	       // public RestaurantControlPanel controlPanel;
	        private RestaurantAnimationPanel animationPanel;
	        private List<RestaurantOneCustomer> customers = new ArrayList<RestaurantOneCustomer>();
	        private List<RestaurantOneWaiter> waiters = new ArrayList<RestaurantOneWaiter>();
	        private RestaurantOneHost host;
	        private RestaurantOneCook cook;
	        private RestaurantOneCashier cashier;

	        public RestaurantOneSystem(SimCityGui scgui) {
	                super();
	                scg = scgui;
	               // controlPanel = new RestaurantControlPanel();
	                animationPanel = new RestaurantAnimationPanel();
	        }
	        
	        
	        public RestaurantAnimationPanel getAnimationPanel() {
	                return animationPanel;
	        }
	        
	}


