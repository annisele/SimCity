package simcity.buildings.restaurant.two;

import javax.swing.JPanel;

import simcity.gui.SimCityGui;
import simcity.gui.restaurantone.RestaurantOneAnimationPanel;
import simcity.gui.restauranttwo.RestaurantTwoAnimationPanel;

public class RestaurantPanel extends JPanel {

        private SimCityGui scg;
       // public RestaurantControlPanel controlPanel;
        public RestaurantTwoAnimationPanel animationPanel;
      

        public RestaurantPanel(SimCityGui scgui) {
                super();
                scg = scgui;
               // controlPanel = new BankControlPanel();
                animationPanel = new RestaurantTwoAnimationPanel();
        }
        
        
        public RestaurantTwoAnimationPanel getAnimationPanel() {
                return animationPanel;
        }
        
}