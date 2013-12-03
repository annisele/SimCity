package simcity.gui.restaurantone;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;

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

        public static List<Coordinates> TableSpots = new ArrayList<Coordinates>();
        public static List<RestaurantOneFoodGui> foodItems = new ArrayList<RestaurantOneFoodGui>();

        protected RestaurantOneWaiterRole wagent = null;
        private RestaurantOneFoodGui food = null;
        private int CookLocationX= 540;
        private int CookLocationY = 200;
        


        private int xPos = 60, yPos = 60;//default waiter position
        int xDest = 40;//default start position

        int yDest = 40;

        public boolean returning = false;
        private RestaurantOneAnimationPanel animationPanel = null;
        //Timer breakTimer = new Timer();
        private int tableNumber = 0;

        private RestaurantPanel restPanel;

        public RestaurantOneWaiterGui(RestaurantOneWaiterRole agent, RestaurantPanel rp) {
                restPanel = rp;
                this.wagent = agent;
                int n = 200;
                for (int i = 0; i < 3; i++) {
                        TableSpots.add(new Coordinates(n, 600));
                        n += 150;
                }
        }

        public void updatePosition() {

                if (xPos < xDest)
                        xPos++;
                else if (xPos > xDest)
                        xPos--;

                if (yPos < yDest)
                        yPos++;
                else if (yPos > yDest)
                        yPos--;

                if (tableNumber > 0) {
                        if (xPos == xDest && yPos == yDest
                                        & (xDest == (TableSpots.get(tableNumber - 1).getX() + 20)) & (yDest == (TableSpots.get(tableNumber - 1).y - 20))) {
                                wagent.msgAtTable();
                        }                
                }

                if (tableNumber > 0) {
                        if (xPos == xDest && yPos == yDest
                                        & (xDest == (TableSpots.get(tableNumber - 1).getX() + 20)) & (yDest == (TableSpots.get(tableNumber - 1).y - 70))) {
                                wagent.msgAtTable();
                        }                
                }


                if ((xPos < 70) && (yPos < 70))
                {
                        if (returning)
                        {
                                wagent.msgLeavingCustomer();
                                returning = false;
                        }
                }    

                if ((xPos == CookLocationX) && (yPos == CookLocationY) && (xDest == CookLocationX) && (yDest == CookLocationY)){ //hack
                        wagent.msgWithCook();
                }
                
        }

        public void draw(Graphics2D g) {
                Color waiterColor = new Color(100, 120, 140);
                g.setColor(waiterColor);
                g.fillRect(xPos, yPos, 32, 32);
        }

        public boolean isPresent() {
                return true;
        }

        public void DoBringToTable(RestaurantOneCustomerGui CustGui, int tNum) {
                if ((xPos != 40) && (yPos != 40)) {
                        DoLeaveCustomer();
                        try {
                                wagent.leftCustomer.acquire();
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        System.out.println ("left the customer, now time to take order");
                }
                tableNumber = tNum;
                xDest = TableSpots.get(tableNumber-1).getX() + 20;
                yDest= TableSpots.get(tableNumber-1).y - 20;
                CustGui.DoGoToLocation(xDest, yDest);
        }

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

        public void DoLeaveCustomer() {
                returning = true;
                xDest = 62;
                yDest = 62;
        }
        
        public void GetFood(String choice, int t) {
                food = new RestaurantOneFoodGui(this, choice, false, xPos, yPos, t);
                foodItems.add(food);
                animationPanel.addGui(food);
                food.moveWithWaiter();
        }

        public int getXPos() {
                return xPos;
        }

        public int getYPos() {
                return yPos;
        }

        public void setAnimationPanel(RestaurantOneAnimationPanel ap) {
                animationPanel = ap;
        }

        public void DoDeliverFood(int t, String choice, RestaurantOneCustomerGui custGui) {
                food.moveToTable();
        }
        
        public void setBreak() {
                wagent.msgWantBreak();
        }
        public boolean IsOnBreak() {
                return wagent.OnBreak;
        }
        public void putOffBreak() {
                breakTimer.schedule(new TimerTask() {
                        public void run() {
                                System.out.println(wagent.getName() + ": break is over");
                                wagent.offDuty.release();
                                wagent.Waiting();
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
                if ((xPos == 60) && (yPos == 60)) {
                        return true;
                }
                return false;
        }


}