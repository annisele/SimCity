package simcity.buildings.restaurant.two;


import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.gui.restauranttwo.RestaurantTwoWaiterGui;
import simcity.interfaces.restaurant.two.*;;

public class RestaurantTwoSharedDataWaiterRole extends RestaurantTwoWaiterRole  implements simcity.interfaces.restaurant.two.RestaurantTwoWaiter{

		private RestaurantTwoOrderWheel orderWheel;

private Semaphore atDest = new Semaphore(0, true);
	    public RestaurantTwoSharedDataWaiterRole(PersonAgent person, RestaurantTwoSystem R2, RestaurantTwoComputer c) 
	    {
	            super(person, R2, c);
	            
	            //this.orderWheel = orderWheel;
	    }
	    
	    public void setOrderWheel(RestaurantTwoOrderWheel wheel) {
			this.orderWheel= wheel;
		}

	/** Place pending order 
	 * @param customer customer that needs food cooked */
	    @Override
	public void DeliverOrder(mycustomer customer) {
	            //In our animation the waiter does not move to the cook in
	            //order to give him an order. We assume some sort of electronic
	            //method implemented as our message to the cook. So there is no
	            //animation analog, and hence no DoXXX routine is needed.
	    ((RestaurantTwoWaiterGui)gui).GoToKitchen();
	    	/*
	    	 try {
	    			atDest.acquire();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
*/	            customer.state = CustomerState.nothing;
	            RestaurantTwoWOrder order = new RestaurantTwoWOrder(this, customer.table_num, customer.choice);
	            Do("Placing " + customer.c.getName() + "'s choice of " + customer.choice + " onto OrderWheel");
	            orderWheel.insert(order);
	            stateChanged();
	}
}
