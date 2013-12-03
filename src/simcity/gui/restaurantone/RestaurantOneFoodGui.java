package simcity.gui.restaurantone;

import java.awt.Color;
import java.awt.Graphics2D;

import simcity.buildings.restaurant.one.*;
import simcity.gui.Gui;

public class RestaurantOneFoodGui extends Gui {

        private RestaurantOneWaiterGui waiter = null;
    private Graphics2D g = null;
        private int xPos, yPos; 
        private int xDestination, yDestination; 
        private String food;
        private boolean delivered;
         public boolean visible = true;
        public int tableNumber;


        public RestaurantOneFoodGui(RestaurantOneWaiterGui waiter, String item, boolean delivered, int x, int y, int t) {
                food = item;
                this.waiter = waiter;
                this.delivered = delivered;
                xPos = x;
                yPos = y;
                tableNumber = t;
        }

        public void draw(Graphics2D g) { 
                if (visible) {
                        this.g = g;
                        Color plate = new Color (100, 160, 200);
                        g.setColor(plate);
                        g.fillRect(xPos, yPos, 16, 18);
                        g.setColor(new Color(0,0,0));
                        
                
                                if (food == "Steak")
                                        g.drawString("S", xPos+5,  yPos+12);
                                else if (food == "Pizza")
                                        g.drawString("P", xPos+5,  yPos+12);
                                else if (food == "Salad")
                                        g.drawString("Sal", xPos+1,  yPos+12);
                                else if (food == "Chicken")
                                        g.drawString("C", xPos+3,  yPos+12);
                        }
                
        }


        public boolean isPresent() {
                return true;
        }

        public int getXPos() {
                return xPos;
        }

        public int getYPos() {
                return yPos;
        }


        public void updatePosition() {
                if (xPos < xDestination)
                        xPos++;
                else if (xPos > xDestination)
                        xPos--;

                if (yPos < yDestination)
                        yPos++;
                else if (yPos > yDestination)
                        yPos--;

                if (xPos == waiter.TableSpots.get(tableNumber-1).getX()+20 && yPos == waiter.TableSpots.get(tableNumber-1).getY()+20) {
                        waiter.wagent.msgFoodDelivered();
                }
        }

        public void moveWithWaiter()
        {
                xDestination = waiter.TableSpots.get(tableNumber-1).getX() + 20;
                yDestination = waiter.TableSpots.get(tableNumber-1).getY() - 70;
        }


        public void moveToTable() {
                delivered = true;
                xDestination = waiter.TableSpots.get(tableNumber-1).getX()+20;
                yDestination = waiter.TableSpots.get(tableNumber-1).getY()+20;
        }
}