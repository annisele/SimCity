package simcity.buildings.restaurant.one;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantone.RestaurantOneCookGui;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.one.RestaurantOneCashier;
import simcity.interfaces.restaurant.one.RestaurantOneHost;
import simcity.interfaces.restaurant.one.RestaurantOneWaiter;
import simcity.buildings.restaurant.one.*;

public class RestaurantOneCustomerRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {

	 Timer timer = new Timer();
	 RestaurantOneSystem restaurant;
     RestaurantOneCheck check;
     private String name;
     private int hungerLevel = 8; // determines length of meal
     private String choice;
     private boolean reorder = false;
     private double money;
     private RestaurantOneHost host;
     private RestaurantOneWaiter waiter;
     private RestaurantOneCashier cashier;
     private PersonAgent person;
     private Semaphore atDest = new Semaphore(0, true);

     //public boolean oweMoney = false;
     private double StayProbability = .8;

     public List<String> Options = Collections.synchronizedList(new ArrayList<String>());



     private RestaurantOneMenu custMenu;
     

     public enum AgentState
     {DoingNothing, WaitingInRestaurant, BeingSeated, OrderingFood, WaitingForFood, Eating, DoneEating, Leaving, WaitingForCheck, Gone};
     private AgentState state = AgentState.DoingNothing;//The start state

     public enum AgentEvent 
     {none, gotHungry, followWaiter, seated, order, foodReceived, foodUnavailable, doneEating, doneLeaving, checkArrived, letgo, decidedToWait, leftEarly};
     AgentEvent event = AgentEvent.none;

     /**
      * Constructor for CustomerAgent class
      *
      * @param personAgent.getName() name of the customer
      * @param gui  reference to the customergui so the customer can send it messages
      */
     public RestaurantOneCustomerRole(PersonAgent p){
    	 	gui = new RestaurantOneCustomerGui(this); 
             this.person = p;
             if (person.getName().equals("Broke")) {
                     money = 0;
             }
             else if (person.getName().equals("Enough")) {
                     money = 6.00;
             }
             else
                     money = 100;
     }
     

     /**
      * hack to establish connection to Host agent.
      */


     public String getCustName() {
             return name;
     }
     // Messages


     public void msgFollowMe(RestaurantOneMenu menu) {
             custMenu = menu;
             Options.clear();
             for (int i = 0; i < custMenu.menuItems.length; i++) {
                     if (custMenu.priceMap.get(custMenu.menuItems[i]) <= money) {
                             Options.add(custMenu.menuItems[i]);
                     }
             }
             reorder = false;

            // print("Received msgFollowMe");
             event = AgentEvent.followWaiter;
             stateChanged();
     }

     public void msgWhatWouldYouLike() {
            // print ("Asked What Would You Like");
             event = AgentEvent.order;
             stateChanged();
     }

     public void msgHereIsYourFood() {
           //  print ("Given Food By Waiter");
             event = AgentEvent.foodReceived;
             stateChanged();
     }

     public void msgFoodOutofStock() {
            // print ("Told that food was out of stock");
             event = AgentEvent.foodUnavailable;
             Options.remove(choice);
             reorder = true;
             stateChanged();
     }

     public void msgHereisYourCheck(RestaurantOneCheck c) {
            // print("Got bill from waiter");
             check = c;
             event = AgentEvent.checkArrived; 
             stateChanged();
     }

     public void msgAnimationFinishedGoToSeat() {
             //from animation
             event = AgentEvent.seated;
             stateChanged();
     }

     public void msgAnimationFinishedLeaveRestaurant() {
             //from animation
             event = AgentEvent.doneLeaving;
             stateChanged();
     }

     public void msgLetGo() {
             event = AgentEvent.letgo;
             //oweMoney = true;
             stateChanged();
     }


     /**
      * Scheduler.  Determine what action is called for, and do it.
      */
     public boolean pickAndExecuteAnAction() {
             //        CustomerAgent is a finite state machine


             if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
                     state = AgentState.BeingSeated;
                     SitDown();
                     return true;
             }

             if (state == AgentState.BeingSeated && event == AgentEvent.seated){
                     state = AgentState.OrderingFood;
                     CallWaiter();
                     return true;
             }

             if (state == AgentState.OrderingFood && event == AgentEvent.order){
                     state = AgentState.WaitingForFood;
                     OrderFood();
                     return true;
             }

             if (state == AgentState.WaitingForFood) {

                     if (event == AgentEvent.foodReceived){
                             state = AgentState.Eating;
                             EatFood();
                             return true;

                     }

                     if (event == AgentEvent.foodUnavailable) {
                             state = AgentState.OrderingFood;
                             event = AgentEvent.order;
                            // print ("Ordering Again");
                             return true;
                     }

             }


             if (state == AgentState.Eating && event == AgentEvent.doneEating){
                     ReadyToLeave();
                     state = AgentState.WaitingForCheck;
                     return true;
             }

             if ((state == AgentState.WaitingForCheck) && (event == AgentEvent.checkArrived)) {
                     payCheckAndLeave();
                     state = AgentState.DoingNothing;
                     return true;
             }

             if ((state == AgentState.DoingNothing) && (event == AgentEvent.letgo)) {
                     justApologize();
                     state = AgentState.Gone;
                     return true;
             }


             return false;
     }

     // Actions

  //   private void GoToRestaurant() {
  //           host.msgIWantFood(this);//send our instance, so he can respond to us
  //   }

     private void SitDown() {
             //gui.DoGoToSeat();//hack; only one table
     }

     private void CallWaiter() {
             waiter.msgReadyToOrder(this);
     }

     private void OrderFood() {

             if (name.equals("Broke")) {
                     int randchoice = (int)((Math.random()) * 4);
                     choice = custMenu.menuItems[randchoice];
                     Do ("I would like to order " + choice); 
                     waiter.msgGiveChoice(choice, this);
             }

             else {
                     if (Options.size() <= 0) {
                             state = AgentState.DoingNothing;
                             LeaveTable();
                     }
                     else if ((name.equals("salad")) || (name.equals("Salad")) || (name.equals("steak")) || (name.equals("Steak"))  || (name.equals("pizza")) || (name.equals("Pizza")) || (name.equals("chicken")) || (name.equals("Chicken")))
                     {
                             if (!reorder) {
                                     choice = name;
                                     if (choice.equals("steak"))
                                             choice = "Steak";
                                     if (choice.equals("pizza"))
                                             choice = "Pizza";
                                     if (choice.equals("salad"))
                                             choice = "Salad";
                                     if (choice.equals("chicken"))
                                             choice = "Chicken";

                                     if (Options.contains(choice)) {
                                             Do ("I would like to order " + choice); 
                                             waiter.msgGiveChoice(choice, this);
                                     }
                             }

                             if (reorder) {
                             //        Random randomGenerator = new Random();
                                     //int randomInt = randomGenerator.nextInt(Options.size());
                                     int randchoice = (int)((Math.random()) * 4);
                                     choice = custMenu.menuItems[randchoice];
                                     choice = Options.get(randchoice);
                                     waiter.msgGiveChoice(choice, this);
                                     Do ("I would like to order " + choice);
                             }
                     }
                     else {
                     //        Random randomGenerator = new Random();
                             //int randomInt = randomGenerator.nextInt(Options.size());
                             int randchoice = (int)((Math.random()) * (Options.size()-1));
                             choice = custMenu.menuItems[randchoice];
                             choice = Options.get(randchoice);
                             waiter.msgGiveChoice(choice, this);
                             Do ("I would like to order " + choice);
                     }
             }
     }


     private void EatFood() {
             Do("Eating Food");
             timer.schedule(new TimerTask() {
                     public void run() {
                          //   print("Done eating");
                             event = AgentEvent.doneEating;
                             stateChanged();
                     }
             },
             getHungerLevel() * 1000);
     }

     private void ReadyToLeave() {
             waiter.msgDoneEatingAndLeaving(this); 
     }

     private void payCheckAndLeave() {

             timer.schedule(new TimerTask() {
                     public void run() {

                             if (check.price > money) {
                                     cashier.msgCustomerPaying(check, money-check.price);
                                    // print ("Couldn't afford check");
                             }
                             else {
                                     cashier.msgCustomerPaying(check, check.price);
                                //     print("Paid check of " + check.price + " for " + choice);
                             }
                             //oweMoney = false;
                             LeaveTable();
                            // print("Leaving the restaurant");

                             //customerGui.DoExitRestaurant(); //for the animation

                     }},
                     500);
     }


     private void LeaveTable() {
             Do("Leaving.");
             waiter.msgLeftRestaurant(this);
           //  gui.DoExitRestaurant();
     }

     private void LeaveTableWithoutEating() {
             Do ("Leaving");
           //  gui.DoExitRestaurant();
             if (waiter != null)
                     waiter.msgLeftRestaurant(this);
             else
                     host.msgLeaving(this);
     }
     
     public void setHost(RestaurantOneHost h) {
    	 host = h;
     }

     public void setWaiter(RestaurantOneWaiterRole waiter) {
             this.waiter = waiter;
     }

     public String getName() {
             return name;
     }

     public int getHungerLevel() {
             return hungerLevel;
     }

     public void setHungerLevel(int hungerLevel) {
             this.hungerLevel = hungerLevel;
             //could be a state change. Maybe you don't
             //need to eat until hunger lever is > 5?
     }

     public String toString() {
             return "customer " + getName();
     }

     public void setCashier(RestaurantOneCashierRole c) {
             cashier = c;
     }

     public void setGui(RestaurantOneCustomerGui g) {
             gui = g;
     }

     public RestaurantOneCustomerGui getGui() {
             return ((RestaurantOneCustomerGui)gui);
     }

     // If customer doesn't have enough money, he/she steals it. 
     private void justApologize() {
    	 System.out.println("sorry");
     }

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantOneSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantOneCustomer: " + person.getName(), "Ready to work at the restaurant!");


		((RestaurantOneCustomerGui)gui).DoGoToFront();		
		host.msgIWantFood(this);
		
	}
	
	public void msgArrivedAtRestaurant(double money) {
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		atDest.release();
		
	}


	
	
}
