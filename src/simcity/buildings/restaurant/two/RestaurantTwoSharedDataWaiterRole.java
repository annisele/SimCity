package simcity.buildings.restaurant.two;


import simcity.PersonAgent;
import simcity.interfaces.restaurant.two.*;;

public class RestaurantTwoSharedDataWaiterRole extends RestaurantTwoWaiterRole  implements simcity.interfaces.restaurant.two.RestaurantTwoWaiter{

		private RestaurantTwoOrderWheel orderWheel;

	    public RestaurantTwoSharedDataWaiterRole(PersonAgent person, RestaurantTwoSystem R2, RestaurantTwoComputer c, RestaurantTwoOrderWheel orderWheel) 
	    {
	            super(person, R2, c);
	            
	            this.orderWheel = orderWheel;
	    }

	/** Place pending order 
	 * @param customer customer that needs food cooked */
	public void DeliverOrder(mycustomer customer) {
	            //In our animation the waiter does not move to the cook in
	            //order to give him an order. We assume some sort of electronic
	            //method implemented as our message to the cook. So there is no
	            //animation analog, and hence no DoXXX routine is needed.
	            customer.state = CustomerState.nothing;
	            RestaurantTwoWOrder order = new RestaurantTwoWOrder(this, customer.table_num, customer.choice);
	            Do("Placing " + customer.c.getName() + "'s choice of " + customer.choice + " onto OrderWheel");
	            orderWheel.insert(order);
	            stateChanged();
	}
}
