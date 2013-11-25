package simcity.buildings.restaurant.two;

import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.restauranttwo.*;
import simcity.interfaces.restaurant.two.*;;

public class RestaurantTwoCustomerRole extends Role  implements simcity.interfaces.restaurant.two.RestaurantTwoCustomer {

	
	 private int waitingPosition, tnum,lowestprice;
     private double cashmoney, customer_check;
     
     private String name, choice;
     private int hungerLevel = 5;        // determines length of meal
     Timer timer = new Timer();
     private RestaurantTwoCustomerGui customerGui;
     private RestaurantTwoOrderWheel OrderWheel= new RestaurantTwoOrderWheel(); 
     

     // agent correspondents
     
     private RestaurantTwoHostRole host;
     private RestaurantTwoWaiter waiter;
     private RestaurantTwoCashierRole cashier;
     private RestaurantTwoCookRole cook;
     private RestaurantTwoSystem R2;
     private boolean hack_c=false;
     private boolean hack_s=false;
     private boolean hack_st=false;
     //    private boolean isHungry = false; //hack for gui
     public enum AgentState
     {DoingNothing, WaitingInRestaurant, BeingSeated, Seated, WaitingForWaiter,  WaitingForFood, Reordered,readytopay, Eating, DoneEating, Leaving, Storming};
     private AgentState state = AgentState.DoingNothing;//The start state

     public enum AgentEvent 
     {none, gotHungry, followWaiter, seated, readytoOrder,x, ordered,gotFood, doneEating, paying, Leaving,doneLeaving, arrivedAtRestaurant};
     AgentEvent event = AgentEvent.none;

     /**
      * Constructor for CustomerAgent class
      *
      * @param name name of the customer
      * @param gui  reference to the customergui so the customer can send it messages
      */
     public RestaurantTwoCustomerRole(PersonAgent p){
            
             person = p;
            
             this.gui = new RestaurantTwoCustomerGui(this);
             /*
             lowestprice=6;
             double temp= 5+(double)(Math.random()*(15));
             DecimalFormat f =new DecimalFormat("##.00");
             String formate=f.format(temp);
            
                     try {
						cashmoney=(Double)f.parse(formate);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
          
            // Do("$$$= "+cashmoney);
           //  customer_check=0;
     }

     /**
      * hack to establish connection to Host agent.
      */
     public void setWaiter(RestaurantTwoWaiter waitr) {
             this.waiter = waitr;
     }
     public void setHost(RestaurantTwoHostRole host) {
             this.host = host;
     }
     public void setCook(RestaurantTwoCookRole cook) {
             this.cook = cook;
     }
     public void setCashier(RestaurantTwoCashierRole cashier) {
             this.cashier = cashier;
     }
     
     public void hack_chicken(){
             hack_c=true;
             cook.hack_chicken();
     }
     public void hack_salad(){
             hack_s=true;
             cook.hack_salad();
     }
     public void hack_steak(){
             hack_st=true;
             cook.hack_steak();
     }
     public void hack_mdebt(){
     
             hack_c=true;
             cook.hack_chicken();
             cashier.modBalance(50);
     }
     public String getCustomerName() {
             return name;
     }
     // Messages
     public void msgArrivedAtRestaurant() { // from gui
 		System.out.println("I'm ehere yoo");
 		event = AgentEvent.arrivedAtRestaurant;
 		Do("person: "+person);
 		stateChanged();
 	}
     public void gotHungry() {//from animation
             Do("I'm hungry");
     
             event = AgentEvent.gotHungry;
             stateChanged();
     }

     public void msgSitAtTable(RestaurantTwoWaiter w, int a) {
             Do("Received msgSitAtTable");
             waiter=w;
             event = AgentEvent.followWaiter;
             stateChanged();
             tnum=a;
     }

     public void msgAnimationFinishedGoToSeat() {
             //from animation
             event = AgentEvent.seated;
             stateChanged();
     }
     public void msgWhatsYourOrder(){
             Do("Recieved msg what is your order");
             event = AgentEvent.ordered;
             
             stateChanged();
     }
     public void msgReorder(int table, String c){
             Do("recieved msg to reorder "+this.state);
             event = AgentEvent.x;
             Do("recieved msg to reorder "+this.event);
             
             
             stateChanged();
     }
     public void msgFoodIsHere(){
             event = AgentEvent.gotFood;
             stateChanged();
     }
     public void msgHereIsCheck(double c){
             Do("Recieved Check");
             if(cashmoney>c){
             customer_check=c;
             }
             else
                     customer_check=cashmoney;
             event = AgentEvent.paying;
             stateChanged();
     }
     
     public void msgPaying() {
             //from animation
             Do("paying"+state);
             event = AgentEvent.Leaving;
             
             stateChanged();
     }
     public void msgGoodbye() {
             Do("Goodbye");
             event = AgentEvent.doneLeaving;
             stateChanged();
             
     }
     
     /*public void msgAnimationFinishedLeaveRestaurant() {
             //from animation
             event = AgentEvent.doneLeaving;
             stateChanged();
             Do("Has left restuarant");
     }
     */
     
     /**
      * Scheduler.  Determine what action is called for, and do it.
      */
     public boolean pickAndExecuteAnAction() {
             //        CustomerAgent is a finite state machine

             if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
                     state = AgentState.WaitingInRestaurant;
                     goToRestaurant();
                     return true;
             }
             if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
                     state = AgentState.BeingSeated;
                     SitDown();
                     return true;
             }
             if (state == AgentState.BeingSeated && event == AgentEvent.seated){
                     state = AgentState.Seated;
                     if(hack_s==true){
                             cashmoney=4;
                             Do("money: "+cashmoney);
                     }
                     if(hack_st==true){
                             cashmoney=18;
                     }
                     LookingAtMenu();
                     return true;
             }
             if (state == AgentState.Seated && event == AgentEvent.readytoOrder){
                     state = AgentState.WaitingForWaiter;


                     Do("LOW "+lowestprice);
                     Do("$"+cashmoney);
                     choice=g_choice();
                     if(choice.equals("not ordering")){
                             Leave();
                             state=AgentState.Storming;
                             return false;
                             }
                     CallWaiter();

                     return true;
             }


             if (state ==  AgentState.WaitingForWaiter && event == AgentEvent.ordered){
                     state = AgentState.WaitingForFood;

                     if (hack_c==true){
                             Order("chicken");
                     }
                     if (hack_st==true){
                             Order("steak");
                     }
                     if(hack_st==false&&hack_c==false){
                             Order(choice);
                     }
                     return true;

             }

             if (state ==  AgentState.WaitingForFood && event == AgentEvent.x){
                     state= AgentState.Reordered;
                     Do("back to order");
                     Reorder();
                     return true;
             }
             if (state == AgentState.Reordered&& event == AgentEvent.gotFood){
                     state = AgentState.Eating;
                     EatFood();
                     return true;
             }
             if (state == AgentState.WaitingForFood && event == AgentEvent.gotFood){
                     state = AgentState.Eating;
                     EatFood();
                     return true;
             }
             if (state == AgentState.Eating && event == AgentEvent.doneEating){
                     state = AgentState.readytopay;
                     getCheck();
                     return true;
             }
             if (state == AgentState.readytopay && event == AgentEvent.paying){
                     state = AgentState.Leaving;
                     LeaveToPay();
                     return true;
             }
             if (state == AgentState.Leaving && event == AgentEvent.Leaving){
                     state = AgentState.DoingNothing;
                     AtCashiers();
                     return true;
             }
             if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
                     state = AgentState.DoingNothing;
                     //no action
                     return true;
             }
             return false;
     }

     // Actions

     private void goToRestaurant() {
             Do("Going to restaurant");
             if(host.waitingSpots.containsValue(false)){
                     synchronized(host.waitingSpots){
                     for(Entry<Integer, Boolean> entry : host.waitingSpots.entrySet()){
                             if(entry.getValue()==false){
                                     waitingPosition=entry.getKey();
                                     host.waitingSpots.put(waitingPosition, true);
                                     customerGui.DoGoToRestaurant(waitingPosition);
                                     host.msgIWantFood(this);
                                     break;
                             }
                     }
                     }
     
             }
             //Do(""+host.waitingSpots.entrySet());
             else{
             Do("Restaurant is full and I am leaving!");
                     Leave();
             }
     }

     private void SitDown() {
             Do("Being seated. Going to table");
             customerGui.DoGoToSeat(tnum);//hack; only osne table
             host.waitingSpots.put(waitingPosition, false);
     }
     private void LookingAtMenu(){
             Do("Looking at Menu");
             Do("MENU: "+ cook.Menu.entrySet());
             timer.schedule(new TimerTask(){
                     Object cookie = 1;
                     public void run() {
                             event = AgentEvent.readytoOrder;
                             stateChanged();
                     }
             }, 5000);
     }
     private void CallWaiter(){
             Do("Call Waiter");
             waiter.msgReadyToOrder(this);
     }
     private String g_choice(){

             if (cashmoney>=lowestprice){
                     while(true){

                             Random g = new Random();
                             Object[] values =cook.Menu.values().toArray();
                             Double r_val = (Double) values[g.nextInt(values.length)];
                             Do("making choice: "+r_val);
                             Do("cash: "+cashmoney);
                             if (r_val==10.99){
                                     if(cashmoney>=11){
                                             choice="chicken";
                                             break;
                                     }
                             }
                             if (r_val==15.99){
                                     if(cashmoney>=16){
                                             choice="steak";
                                             break;
                                     }
                             }
                             if (r_val==5.99){
                                     if(cashmoney>=6){
                                             choice="salad";
                                             break;
                                     }
                             }
                             if (r_val==8.99){
                                     if(cashmoney>=9){
                                             choice="pizza";
                                             break;
                                     }
                             }
                     }
                     return choice;
             }
             else{
                     Do("customer is low on money!");
                     int temp= (int)(Math.random()*(8));
                     if(temp==4||temp==5||temp==6||temp==7||hack_s==true){
                             Do("customer is angry and leaving the restaurant because "
                                             + "ze cannot pay");
                             return "not ordering";
                     }
                     else if(temp==0){
                             choice="steak";
                             return choice;
                     }
                     else if(temp==1){
                             choice="chicken";
                             return choice;
                     }
                     else if(temp==2){
                             choice="salad";
                             return choice;
                     }
                     else if(temp==3){
                             choice="pizza";
                             return choice;
                     }
             }
             return choice;
     }
     private void Order (String ch){
             Do("Order "+ch);
             waiter.msgHereIsMyChoice(this, ch);
     }
     private void Reorder(){
             Do("reordering ");
             if(hack_st==true){
                     hack_st=false;
             }
             if (hack_s==true){
                     lowestprice=9;
             }
             String c= g_choice();
             Do("here"+ g_choice());
             if(c.equals("not ordering")){
                     Do("here");
                     Leave();
                     state=AgentState.Leaving;
                     stateChanged();
             }
             else{
                     waiter.msgHereIsMyChoice(this, c);
             }
     }
     private void EatFood() {
             Do("Eating Food");
             //This next complicated line creates and starts a timer thread.
             //We schedule a deadline of getHungerLevel()*1000 milliseconds.
             //When that time elapses, it will call back to the run routine
             //located in the anonymous class created right there inline:
             //TimerTask is an interface that we implement right there inline.
             //Since Java does not all us to pass functions, only objects.
             //So, we use Java syntactic mechanism to create an
             //anonymous inner class that has the public method run() in it.
             timer.schedule(new TimerTask() {
                     Object cookie = 1;
                     public void run() {
                             Do("Done eating, cookie=" + cookie);
                             event = AgentEvent.doneEating;
                             //isHungry = false;
                             stateChanged();
                     }
             },
             5000);//getHungerLevel() * 1000);//how long to wait before running task
     }
     private void getCheck(){
             waiter.msgReadyToPay(this);
     }
     private void LeaveToPay() {
             Do("Leaving to pay.");
             customerGui.DoLeaveToPay();
             
     }
     private void AtCashiers(){
             Do("At cashiers");
             Do(""+OrderWheel.Menu.entrySet());
             cashmoney-=OrderWheel.Menu.get(choice);
             cashier.msgHereIsMoney(this, customer_check);
             cashmoney-=customer_check;
             //Do("$: "+cashmoney);
             host.msgLeavingTable(this);
             
     }
     private void Leave(){
             if(state==AgentState.Storming){
             waiter.msgCustLeave(this);
             host.msgLeavingTable(this);
             }
             event=AgentEvent.doneLeaving;
             customerGui.DoExitRestaurant();
             Do("Has left");
     }
     // Accessors, etc.

     public String getName() {
             return name;
     }
     
     /*public static mycustomer find(RestaurantTwoCustomer c) {
             return null;
     }*/
     
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

     public void setGui(RestaurantTwoCustomerGui g) {
             customerGui = g;
     }

     public RestaurantTwoCustomerGui getGui() {
             return customerGui;
     }

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void msgEnterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	

	

	





     
}

	
	

