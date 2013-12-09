package simcity.gui.restaurantone;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.one.RestaurantOneWaiter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class RestaurantOneWaiterGui extends Gui {

        Timer breakTimer = new Timer();
        public static class Coordinates {
                public Coordinates (int x, int y) {
                        this.setX(x);
                        this.y = y;
                }
                public int getX() {
                        return x;
                }
                public void setX(int x) {
                        this.x = x;
                }
                public int getY() {
                        return y;
                }
                public void setY(int y) {
                        this.y = y;
                }
                private int x;
                private int y;
        }
        
        
        private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
        private Image hostimage = ii.getImage();

        public static List<Coordinates> TableSpots = new ArrayList<Coordinates>();
        public static List<RestaurantOneFoodGui> foodItems = new ArrayList<RestaurantOneFoodGui>();

      //  protected RestaurantOneWaiterRole waiterrole = null;
        private RestaurantOneFoodGui food = null;
        private int CookLocationX= 540;
        private int CookLocationY = 200;
        


       

        public boolean returning = false;
        private RestaurantOneAnimationPanel animationPanel = null;
        //Timer breakTimer = new Timer();
        private int tableNumber = 0;

        private RestaurantPanel restPanel;

        public RestaurantOneWaiterGui(RestaurantOneWaiter wrole) {
                role = wrole;
                int n = 200;
                for (int i = 0; i < 3; i++) {
                        TableSpots.add(new Coordinates(n, 600));
                        n += 150;
                     //   x = 240;
                      //  y = 300;
                }
        }

  

        public void draw(Graphics2D g) {
        	g.drawImage(hostimage,getX(), getY(), null);
        }

      

    /*    public void DoBringToTable(RestaurantOneCustomerGui CustGui, int tNum) {
                if ((x != 40) && (y != 40)) {
                        DoLeaveCustomer();
                        try {
                                //waiterrole.leftCustomer.acquire();
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
                tableNumber = tNum;
                xDest = TableSpots.get(tableNumber-1).getX() + 20;
                yDest= TableSpots.get(tableNumber-1).y - 20;
                CustGui.DoGoToLocation(xDest, yDest);
        } */

        public void DoGoToTable(int tNum) {
                tableNumber = tNum;
                xDest = TableSpots.get(tableNumber-1).getX() + 20;
                yDest = TableSpots.get(tableNumber-1).getY() - 70;

        }
        public void DoGoToCook()
        {
                xDest = CookLocationX; 
                yDest = CookLocationY; 
        }
        
        public void DoGoToHome(){
        	DoGoToLocation(50, 50);
        }

        public void DoLeaveCustomer() {
                returning = true;
                xDest = 62;
                yDest = 62;
        }
        
        public void GetFood(String choice, int t) {
                food = new RestaurantOneFoodGui(this, choice, false, x, y, t);
                foodItems.add(food);
                animationPanel.addGui(food);
                food.moveWithWaiter();
        }


        public void DoDeliverFood(int t, String choice, RestaurantOneCustomerGui custGui) {
                food.moveToTable();
        }
        
        public void setBreak() {
             //   waiterrole.msgWantBreak();
        }
      /*  public boolean IsOnBreak() {
               // return waiterrole.OnBreak;
        }*/
        public void putOffBreak() {
                breakTimer.schedule(new TimerTask() {
                        public void run() {
                               // System.out.println(waiterrole.getName() + ": break is over");
                               // waiterrole.offDuty.release();
                               /// waiterrole.Waiting();
                        }
                },
                8000);
        }
        public void DoClearTable(int t) {
                for (RestaurantOneFoodGui f : foodItems) {
                        if (f.tableNumber == t) {
                                f.visible = false;
                        }
                        //break;
                }
        }
        public boolean isHome() {
                if ((x == 60) && (y == 60)) {
                        return true;
                }
                return false;
        }


}