package simcity.buildings.restaurant.one;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.one.RestaurantOneCheck.CheckState;
import simcity.gui.restaurantone.RestaurantOneCashierGui;
import simcity.gui.restaurantone.RestaurantOneWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;

public class RestaurantOneCashierRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCashier {

	

			private RestaurantOneSystem restaurant;

			private PersonAgent person;
	        private String Name;
	        public Double cash;
	        public double owedmoney = 0.00;

	        public RestaurantOneCashierRole(PersonAgent p) {
	                super();
	                this.person = p;
	                this.Name = p.getName();
	                cash = 10.00; //cashier starts out with 100 dollars in the register
	                this.gui = new RestaurantOneCashierGui(this);
	        }
	        
	        public Map<String, Double> prices = Collections.synchronizedMap(new HashMap<String, Double>()); { { 
	                prices.put("Steak", 15.99); 
	                prices.put("Chicken", 10.99);
	                prices.put("Salad", 5.99);
	                prices.put("Pizza", 8.99);
	                
	        }};
	        
	        public List<CashierCheck> calculatedChecks = Collections.synchronizedList( new ArrayList<CashierCheck>());
	        //CashierCheck- when the cashier has actually computed the price of the check
	        public List<RestaurantOneCheck> uncalculatedChecks = Collections.synchronizedList(new ArrayList<RestaurantOneCheck>());
	        //Checks in circulation- haven't been calculated yet
	        public Semaphore atDest = new Semaphore(0, true);

	        
	        public class CashierCheck {
	                public RestaurantOneCheck check;
	                public Double Payment;

	                public CashierCheck(RestaurantOneCheck check){
	                        this.check = check;
	                        this.Payment = 0.0;
	                }
	        }
	        

	        public void msgCustomerPaying(RestaurantOneCheck check, Double payment) {
	                synchronized(calculatedChecks) {
	                for(CashierCheck ch:calculatedChecks) {
	                        if(ch.check.equals(check)) {
	                                ch.Payment = payment;
	                                ch.check.state = CheckState.Paid;
	                                break;
	                        }        
	                }
	                }
	                stateChanged();
	        }
	        




	        public void msgHereIsorder(String choice, int table, RestaurantOneCustomerRole customer, RestaurantOneWaiterRole waiter) {
	                uncalculatedChecks.add(new RestaurantOneCheck(choice, table, customer, waiter));
	               // print ("Cashier has received Order, added to uncomputed checks");
	                stateChanged();
	        }


	        public boolean pickAndExecuteAnAction() {

	                synchronized(calculatedChecks) {
	                for(CashierCheck check: calculatedChecks) {
	                        if(check.check.state == RestaurantOneCheck.CheckState.Paid) {
	                                finishCheck(check);
	                                return true;
	                        }
	                }
	                }
	                
	                synchronized(calculatedChecks) {
	                for(RestaurantOneCheck check: uncalculatedChecks) {
	                        if(check.state == CheckState.notCalculated) {
	                                check.state = CheckState.unpaid;
	                                CalculateCheck(check);
	                                return true;
	                        }
	                }
	                }
	                return false;

	        }
	        



	        public void CalculateCheck(RestaurantOneCheck check) {
	               // print ("Calculating check");
	                calculatedChecks.add(new CashierCheck(check));
	                check.price = prices.get(check.choice);
	                
	                check.w.msgHereIsComputedCheck(check);
	        }
	        
	        public void finishCheck(CashierCheck c)
	        {
	                if (c.check.price <= c.Payment) {
	                        c.check.state = RestaurantOneCheck.CheckState.done;
	                        cash = cash + c.Payment;
	                  //      print("total money for cashier is now " + cash + "after transaction of " + c.Payment);
	                    //    print("Doing check for Customer");
	                        if (cash >= owedmoney) {
	                                cash = cash - owedmoney; //Cashier is paying off his owed money once he gets enough currency
	                        }
	                }
	                
	        }




	        public String getName() {
	                return Name;
	        }




	        public void msgMarketBill(double total) {
	                // TODO Auto-generated method stub
	                if ((cash - total) > 0) {
	                cash = cash - total;
	               // print("Total cash is now " + cash);
	        }    
	                else {
	                        owedmoney = owedmoney + -1 * (cash-total);
	                       // print("The Cashier now owes the market " + owedmoney);
	                        //cash = cash-total;
	                        
	                }
	        }





			@Override
			public void exitBuilding() {
				// TODO Auto-generated method stub
				
			}





			@Override
			public void enterBuilding(SimSystem s) {
				// TODO Auto-generated method stub
				restaurant = (RestaurantOneSystem)s;
				AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantOneCashier: " + person.getName(), "Ready to work at the restaurant!");


				((RestaurantOneCashierGui) gui).DoGoToStand();
				
			}





			@Override
			public void atDestination() {
				// TODO Auto-generated method stub
				atDest.release();
				System.out.println("Cashier at destination");
				}
}