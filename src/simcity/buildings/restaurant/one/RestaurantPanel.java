package simcity.buildings.restaurant.one;

import javax.swing.JPanel;
import simcity.gui.SimCityGui;
import simcity.gui.restaurant.RestaurantAnimationPanel;

public class RestaurantPanel extends JPanel {

        private SimCityGui scg;
       // public RestaurantControlPanel controlPanel;
        public RestaurantAnimationPanel animationPanel;
      

        public RestaurantPanel(SimCityGui scgui) {
                super();
                scg = scgui;
               // controlPanel = new BankControlPanel();
                animationPanel = new RestaurantAnimationPanel();
        }
        
        
        public RestaurantAnimationPanel getAnimationPanel() {
                return animationPanel;
        }
        
}