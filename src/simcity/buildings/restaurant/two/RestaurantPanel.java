package simcity.buildings.restaurant.two;

import javax.swing.JPanel;

import simcity.gui.SimCityGui;
import simcity.gui.restaurantone.RestaurantOneAnimationPanel;

public class RestaurantPanel extends JPanel {

        private SimCityGui scg;
       // public RestaurantControlPanel controlPanel;
        public RestaurantOneAnimationPanel animationPanel;
      

        public RestaurantPanel(SimCityGui scgui) {
                super();
                scg = scgui;
               // controlPanel = new BankControlPanel();
                animationPanel = new RestaurantOneAnimationPanel();
        }
        
        
        public RestaurantOneAnimationPanel getAnimationPanel() {
                return animationPanel;
        }
        
}