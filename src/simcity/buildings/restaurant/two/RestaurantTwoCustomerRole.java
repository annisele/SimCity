package simcity.buildings.restaurant.two;

import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
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
import simcity.gui.trace.AlertLog;

import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.two.*;;

public class RestaurantTwoCustomerRole extends Role  implements simcity.interfaces.restaurant.two.RestaurantTwoCustomer {

	
	 private int waitingPosition, tnum;
     private double cashmoney, customer_check;
     
     private String  choice;
     private int hungerLevel = 5;        // determines length of meal
     Timer timer = new Timer();
      private RestaurantTwoOrderWheel OrderWheel= new RestaurantTwoOrderWheel(); 
 	private Semaphore atDest = new Semaphore(0, true);

     // agent correspondents
     
     private RestaurantTwoHost host;
     private RestaurantTwoWaiter waiter;
     private RestaurantTwoCashier cashier;
     private RestaurantTwoCook cook;
     
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
    	 	//Do("Cust Person is: ");
            person = p;

           // Do("LOOK: "+p);
            this.gui = new RestaurantTwoCustomerGui(this);
            //System.out.println("Cust Person gui is: "+gui);
             
           
           //  customer_check=0;
     }

     /**
      * hack to establish connection to Host agent.
      */
     public void setWaiter(RestaurantTwoWaiter waitr) {
             this.waiter = waitr;
     }
     public void setHost(RestaurantTwoHost host) {
             this.host = host;
     }
     public void setCook(RestaurantTwoCook cook) {
             this.cook = cook;
     }
     public void setCashier(RestaurantTwoCashier cashier) {
             this.cashier = cashier;
     }
  
     public void hack_chicken(){
             hack_c=true;
             Do("LOOK:    "+cook);
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
     /*public String getCustomerName() {
             return name;
     }*/
     // Messages
     public void msgArrivedAtRestaurant(double money) { // from gui
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "MarketCashier: " + person.getName(), "I'm here yoo");
 		//Do("I'm here yoo");
 		this.cashmoney=money;/*
 		if(h.equals("chicken")){
 			hack_chicken();
 		}*/
 		
 	}
     public void gotHungry() {//from animation
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"I'm hungry");
    	 hack_chicken();
             event = AgentEvent.gotHungry;
             stateChanged();
     }

     public void msgSitAtTable(RestaurantTwoWaiter w, int a) {
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Received msgSitAtTable");
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
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Recieved msg what is your order");
             event = AgentEvent.ordered;
             
             stateChanged();
     }
     public void msgReorder(int table, String c){
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"recieved msg to reorder "+this.state);
             event = AgentEvent.x;
         //    Do("recieved msg to reorder "+this.event);
             
             
             stateChanged();
     }
     public void msgFoodIsHere(){
             event = AgentEvent.gotFood;
             stateChanged();
     }
     public void msgHereIsCheck(double c){
    	// AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Recieved Check");
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
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Paying");
             event = AgentEvent.Leaving;
             
             stateChanged();
     }
     public void msgGoodbye() {
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Goodbye");
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
//Do("cust state");
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


                  //   Do("LOW "+lowestprice);
                    // Do("$"+cashmoney);
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
//Do("OK ORDERED");
                     if (hack_c==true){
                    	 Do("HACKING CHICKEN");
                             Order("chicken");
                     }
                     if (hack_st==true){
                             Order("steak");
                     }
                     if(hack_st==false&&hack_c==false){
                             Order(choice);
                            // Do("OK ORDERED REALLY");
                     }
                     return true;

             }

             if (state ==  AgentState.WaitingForFood && event == AgentEvent.x){
                     state= AgentState.Reordered;
             		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantWaiter: " + person.getName(),"redordering....");
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
             //Do("Going to restaurant");
             AlertLog.getInstance().logMessage(AlertTag.valueOf(this.R2.getName()), "RestaurantCustomer: " + person.getName(), "Going to restaurant.");
          	
             //Do("spots:"+((RestaurantTwoHostRole)host).waitingSpots.entrySet());
             if(R2.waitingSpots.containsValue(false)){
                     synchronized(R2.waitingSpots){
                     for(Entry<Integer, Boolean> entry : R2.waitingSpots.entrySet()){
                             if(entry.getValue()==false){
                                     waitingPosition=entry.getKey();
                                     //Do("waiting pos: "+entry.getKey());
                                     R2.waitingSpots.put(waitingPosition, true);
                                     ((RestaurantTwoCustomerGui)gui).DoGoToRestaurant(waitingPosition);
                                     ((RestaurantTwoHostRole)host).msgIWantFood(this);
                                     break;
                             }
                     }
                     }
     
             }
             //Do(""+host.waitingSpots.entrySet());
             else{
            	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Restaurant is full and I am leaving!");
                     Leave();
             }
     }

     private void SitDown() {
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Being seated. Going to table.");
             ((RestaurantTwoCustomerGui)gui).DoGoToSeat(tnum);//hack; only osne table
             R2.waitingSpots.put(waitingPosition, false);
             try {
         		atDest.acquire();
         	} catch (InterruptedException e) {
         		e.printStackTrace();
         	}
             event = AgentEvent.seated;
             stateChanged();
     
     }
     private void LookingAtMenu(){
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Looking at Menu.");
            // Do("MENU: "+ cook.getMenu().entrySet());
             timer.schedule(new TimerTask(){
                     Object cookie = 1;
                     public void run() {
                             event = AgentEvent.readytoOrder;
                             stateChanged();
                     }
             }, 3500);
     }
     private void CallWaiter(){
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Call Waiter.");
             waiter.msgReadyToOrder(this);
     }
     private String g_choice(){

             if (cashmoney>=R2.getLowestPrice()){
                     while(true){

                             Random g = new Random();
                             Object[] values =cook.getMenu().values().toArray();
                             Double r_val = (Double) values[g.nextInt(values.length)];
                             AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Making choice: "+r_val);
                             AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Current cash: "+cashmoney);
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
            	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"I am low on money!");

            	 if(R2.getLowestPrice()>20){
            		 return "nofood";
            	 }
            	 int temp= (int)(Math.random()*(8));
                     if(temp==4||temp==5||temp==6||temp==7||hack_s==true){
                    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"I am angry and leaving the restaurant because "
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
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"I am ordering "+ch+".");
             waiter.msgHereIsMyChoice(this, ch);
             
     }
     private void Reorder(){
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Reordering.");
             if(hack_st==true){
                     hack_st=false;
             }
             if (hack_s==true){
                    // lowestprice=9;
             }
             String c= g_choice();
             AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"My choice: "+ g_choice());
             if(c.equals("not ordering")){
            	 	state=AgentState.Leaving;
                     Leave();
                     
                     stateChanged();
             }
             else if(c.equals("nofood")){
            	 Do("fail..........");
             }
             else{
                     waiter.msgHereIsMyChoice(this, c);
             }
     }
     private void EatFood() {
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Eating Food.");
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
                 		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantWaiter: " + person.getName(),"Done eating, cookie=" + cookie);
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
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Leaving to pay.");
             ((RestaurantTwoCustomerGui)gui).DoGoToCashier();
             try {
     			atDest.acquire();
     		} catch (InterruptedException e) {
     			e.printStackTrace();
     		}
             timer.schedule(new TimerTask() {
                 Object cookie = 1;
                 public void run() {
                	 event = AgentEvent.Leaving;
                     stateChanged();
                 }
         },
         800);
     
     }
     private void AtCashiers(){
    	 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"At cashiers.");
            // Do(""+OrderWheel.Menu.entrySet());
             cashmoney-=OrderWheel.Menu.get(choice);
             cashier.msgHereIsMoney(this, customer_check);
             cashmoney-=customer_check;
             //Do("$: "+cashmoney);
             host.msgLeavingTable(this);
             ((RestaurantTwoCustomerGui)gui).DoExitRestaurant();
             exitBuilding();
             
     }
     private void Leave(){
             if(state==AgentState.Storming){
             waiter.msgCustLeave(this);
             host.msgLeavingTable(this);
             event=AgentEvent.doneLeaving;
             }
             if(state==AgentState.Leaving){
             event=AgentEvent.doneLeaving;
             }
             ((RestaurantTwoCustomerGui)gui).DoExitRestaurant();
             AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Has left.");
             exitBuilding();
     }
     // Accessors, etc.
/*
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
             return "customer " + person.getName();
     }

   

 
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		R2.exitBuilding(this);
		person.roleFinished();	
	}



	@Override
	public void enterBuilding(SimSystem s) {
		 R2 = (RestaurantTwoSystem)s;
		 AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantCustomer: " + person.getName(),"Entering building.");
			
		((RestaurantTwoCustomerGui)gui).DoGoToHost();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gotHungry();
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		atDest.release();
	}

	


	

	

	





     
}

	
	

