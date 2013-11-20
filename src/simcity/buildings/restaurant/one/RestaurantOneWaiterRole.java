package simcity.buildings.restaurant.one;

import java.util.concurrent.Semaphore;

public class RestaurantOneWaiterRole  implements simcity.interfaces.restaurant.one.RestaurantOneWaiter{
	  private String name;
      public Semaphore DeliverFood = new Semaphore(0, true);
      public Semaphore takeOrder = new Semaphore(0, true);
      public Semaphore atCook = new Semaphore(0, true);
      

      boolean FreeCustomers = false;
      boolean Asked = false;
      public boolean OnBreak = false;
      public Semaphore offDuty = new Semaphore(0,true);
      boolean pendingActions = true;
      private CashierAgent cashagent;

      enum CustomerState{waiting, seated, readyToOrder, asked, ordered, orderGiven, FINISHED, OutofStock, doneEating, eating, allFinished};

      private List<Check> checks = new ArrayList<Check>();
      private class MyCustomer {
              public MyCustomer(CustomerAgent customer, int table, CustomerState state) {
                      cagent = customer;
                      tnumber = table;
                      s = state;

              }
              CustomerAgent cagent;
              int tnumber;
              String choice;
              CustomerState s;
      }
      
      public Semaphore orderGiven = new Semaphore(0, true);
      public Semaphore atTable = new Semaphore(0, true);
      public Semaphore leftCustomer = new Semaphore(0, true);
      public Semaphore HomeBase = new Semaphore(0, true);
      
      

      public WaiterGui waiterGui = null;
      private CookAgent cook;
      private HostAgent host;


      class Order {
              int table;
              String choice;
              

              public Order(String custchoice, int tnum) {
                      choice = custchoice;
                      table = tnum;
              }
      }

      private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
      private List<Order> FreeOrders = Collections.synchronizedList(new ArrayList<Order>());
      



      public WaiterAgent(String name) {
              super();
              this.name = name;
      }
      
      public void Setcashier(CashierAgent cashier) {
              cashagent = cashier;
      }

      public void setHost(HostAgent h) {
              host = h;
      }

      public void setCook(CookAgent c) {
              cook = c;
      }

      public String getName() {
              return name;
      }


      public void msgSitAtTable(CustomerAgent cust, int table) {
              customers.add(new MyCustomer(cust, table, CustomerState.waiting));
              print("Added a new customer");
              //WantBreak = true;
              stateChanged();
      }

      public void msgReadyToOrder(CustomerAgent cust) {
              
              for (MyCustomer mc : customers)
              {
                      if (cust.equals(mc.cagent)) {
                              mc.s = CustomerState.readyToOrder;
                              print("Customer is ready to order");
                              stateChanged();
                      }

              }
              }
              

      public void msgGiveChoice(String choice, CustomerAgent c) {
              print("Customer has made selection");
              orderGiven.release();
              for (MyCustomer mc : customers)
              {
                      if (c.equals(mc.cagent)){
                              mc.choice = choice;
                              
                              mc.s = CustomerState.ordered;
                              stateChanged();
                      }                

              }

      }

      public void msgOrderIsReady(String choice, int table) {
              FreeOrders.add(new Order(choice, table));
              stateChanged();
      }

      public void msgDoneEatingAndLeaving(CustomerAgent c){
              for(MyCustomer mc:customers){
                      if(mc.cagent.equals(c)){
                              mc.s = CustomerState.doneEating;
                              stateChanged();
                              return;
                      }
              }
      }



      public void msgTellCustomerReorder(int table) {
              for (MyCustomer mc : customers)
              {
                      if (mc.tnumber == table) {
                              mc.s = CustomerState.OutofStock; 
                              stateChanged();
                      }
              }
      }

      public void msgLeftRestaurant(CustomerAgent c) {
              for (MyCustomer mc : customers)
              {
                      if (c.equals(mc.cagent)) {
                              host.msgTableIsFree(mc.tnumber);
                              host.msgLeaving(mc.cagent);
                              mc.s = CustomerState.allFinished;
                              stateChanged();
                      }
              }
      }
      public void msgAtTable() { 
              atTable.release();
              stateChanged();
      }

      public void msgLeavingCustomer() {
              leftCustomer.release();
              stateChanged();
      }

      public void msgWithCook() {
              atCook.release();
              stateChanged();
      }

      public void msgFoodDelivered() {
              DeliverFood.release();
              stateChanged();
      }

      public void msgBreakApproved() { 
              print ("Break Accepted");
              OnBreak = true;
              stateChanged();
      }

      public void msgNoBreak() {
              print ("Break Denied");
              OnBreak = false;
              stateChanged();
      }

      public void msgWantBreak() {
              Asked = true;
              stateChanged();
      }

      public void msgHereIsComputedCheck(Check c) {
              print ("Received computed check");
              checks.add(c);
              stateChanged();
      }





      /**
       * Scheduler.  Determine what action is called for, and do it.
       * @return 
       */
	/*
      protected boolean pickAndExecuteAnAction() {

              if (!OnBreak) {
                      if(!customers.isEmpty()){
                              try{
                                      for (MyCustomer mc : customers) {
                                              if (mc.s == CustomerState.waiting) {
                                                      seatCustomer(mc); 
                                                      return true;
                                              }
                                      }
                                      } catch(ConcurrentModificationException e) {
                                              return false;
                                      }
                              try {
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.asked) {
                                              orderGiven.drainPermits();
                                              try {
                                                      orderGiven.acquire();
                                              } catch (InterruptedException e) {
                                                      e.printStackTrace();
                                              }
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }

                              try{
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.readyToOrder) {
                                              print("In the scheduler");
                                              GetOrder(mc);
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return true;
                              }
                              try {
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.ordered) {
                                              mc.s = CustomerState.orderGiven;
                                              GiveCookOrder(mc);
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }

                              

                              try{
                              for(MyCustomer c:customers){
                                      if(c.s == CustomerState.doneEating) {
                                              prepareCheck(c);
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }
                              

                              
                              try{
                                      for (Check c : checks) {
                                              if (c.state == CheckState.unpaid) {
                                                      DoDeliverCheck(c);
                                                      return true;
                                              }
                                      }
                                      } catch(ConcurrentModificationException e) {
                                              return false;
                                      }
                              try {
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.OutofStock) {
                                              TellCustomerOutofStock(mc);
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }
                              
                              try {
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.FINISHED) {
                                              waiterGui.DoLeaveCustomer();
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }
                              

                              if (FreeOrders.size() > 0) {
                                      TakeFoodToCustomer();
                                      return true;
                              }
                              
                              try{
                              for (MyCustomer mc : customers) {
                                      if (mc.s == CustomerState.allFinished) {
                                              customers.remove(mc);
                                              return true;
                                      }
                              }
                              } catch(ConcurrentModificationException e) {
                                      return false;
                              }

                              if (Asked) {
                                      try{
                                      for (MyCustomer mc : customers)
                                      {
                                              if (mc.s != CustomerState.FINISHED) {
                                                      pendingActions = true;
                                                      break;
                                              }
                                              else {
                                                      pendingActions = false;
                                              }
                                      }
                                      } catch(ConcurrentModificationException e) {
                                              return false;
                                      }

                                      if (!pendingActions) { 
                                              host.msgIWantABreak(this);
                                      }
                                      return true;
                              }
                      }
              
              else if (OnBreak) {
                      waiterGui.putOffBreak();  
                      offDuty.drainPermits();
                      try {
                              offDuty.acquire();
                      } catch (InterruptedException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                      }
                      //OnBreak = false;
                      Asked = false;
                      return true;
              }

              
      }
              return false;
      }

      // Actions



      private void seatCustomer(MyCustomer c) {

              DoSeatCustomer(c.cagent, c.tnumber);
              c.cagent.msgFollowMe(new Menu());
              c.cagent.setWaiter(this);
              atTable.drainPermits();
              try {
                      atTable.acquire(); 
              } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
              }
              c.s = CustomerState.seated; 

              FreeCustomers = false;

              for (MyCustomer mc : customers) {
                      if (mc.s == CustomerState.readyToOrder) {
                              FreeCustomers = true;
                      }
              }
              
              
              

              if ((!FreeCustomers) && (FreeOrders.size() == 0))
              {
                      waiterGui.DoLeaveCustomer();
                      leftCustomer.drainPermits();
                      
                      try {
                              leftCustomer.acquire();
                      } catch (InterruptedException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                      }
                      
              }
              host.msgCustomerSeated();
              stateChanged();

      }
      
      
      

      private void GetOrder(MyCustomer c){
              print("Taking the order of " + c.cagent + " at " + c.tnumber);
              waiterGui.DoGoToTable(c.tnumber); 
              atTable.drainPermits();
              
              try {
                      atTable.acquire(); 
              } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
              }
              
              c.cagent.msgWhatWouldYouLike();
              c.s = CustomerState.asked;

      }
      
      private void TellCustomerOutofStock(MyCustomer mc) {
              waiterGui.DoGoToTable(mc.tnumber); 
              atTable.drainPermits();
              
              try {
                      atTable.acquire(); 
              } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
              }
              
              mc.cagent.msgFoodOutofStock();
              mc.s = CustomerState.seated;
      }



      

      private void TakeFoodToCustomer()
      {
              for (MyCustomer mc : customers) {
                      if (FreeOrders.size() > 0)
                      {
                              if (mc.s != CustomerState.FINISHED)
                              {
                              if (FreeOrders.get(0).table == mc.tnumber) {
                                              waiterGui.DoGoToCook();                
                                      //        atCook.drainPermits();    //CHECK THIS LINE IF NOT WORKING
                                              
                                              
                              try {
                                      atCook.acquire();
                                              } catch (InterruptedException e) {
                                                      // TODO Auto-generated catch block
                                                      e.printStackTrace();
                                              }
                                              waiterGui.GetFood(mc.choice, mc.tnumber);
                                              waiterGui.DoGoToTable(mc.tnumber); 
                                              atTable.drainPermits();
                                              
                                              try {
                                                      atTable.acquire(); 
                                              } catch (InterruptedException e) {
                                                      // TODO Auto-generated catch block
                                                      e.printStackTrace();
                                              }
                                              
                                              DeliverFood.drainPermits();
                                              waiterGui.DoDeliverFood(mc.tnumber, mc.choice, mc.cagent.getGui());
                                              try {
                                                      DeliverFood.acquire(); 
                                              } catch (InterruptedException e) {
                                                      e.printStackTrace();
                                              }
                                              
                                              mc.cagent.msgHereIsYourFood();
                                              FreeOrders.remove(0);
                                              print("Gave the food to the seated customer");
                                              mc.s = CustomerState.eating;
                                              waiterGui.DoLeaveCustomer();

                                      }
                              }
                      }
              }
      }

      
      private void GiveCookOrder(MyCustomer c){
              atCook.drainPermits();
              waiterGui.DoGoToCook();        
              c.s = CustomerState.orderGiven;
              try {
                      atCook.acquire();
              } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
              }
              cook.msgHereIsAnOrder(this, c.choice, c.tnumber);
      }

      private void DoSeatCustomer(CustomerAgent customer, int tableNumber) {
              //Notice how we print "customer" directly. It's toString method will do it.
              //Same with "table"
              waiterGui.DoBringToTable(customer.getGui(), tableNumber); 
              print("Seating customer " + customer.getName());
      }

      private void prepareCheck(MyCustomer customer) {
              print("Preparing bill for Customer");
              customer.s = CustomerState.FINISHED;
              waiterGui.DoClearTable(customer.tnumber);
              cashagent.msgHereIsorder(customer.choice, customer.tnumber, customer.cagent, this); 
      }

      private void DoDeliverCheck(Check c) {
              waiterGui.DoGoToTable(c.tablenum); 
              atTable.drainPermits();
              try {
                      atTable.acquire(); 
              } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
              }
              c.c.msgHereisYourCheck(c);
              c.state = CheckState.delivered;
      }


      public void setCashier(CashierAgent cashier){
              this.cashagent = cashier;
      }
      
      public void Waiting() {
              host.msgImOffBreak(this);
      }


      public void setGui(WaiterGui gui) {
              waiterGui = gui;
      }

      public WaiterGui getGui() {
              return waiterGui;
      }



} */
}
