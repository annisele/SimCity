package simcity.buildings.restaurant.one;

//import restaurant.interfaces.Customer;
//import restaurant.interfaces.Waiter;
//import restaurant.test.mock.MockCustomer;
//import restaurant.test.mock.MockWaiter;

import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneWaiter;



public class RestaurantOneCheck {

        
        public int tablenum;
        public String choice;
        public Double price;
        public int checknumber;
        
        public RestaurantOneCustomer c;
        public RestaurantOneWaiter w;
        
        public enum CheckState {notCalculated, unpaid, delivered, Paid, incomplete, done};
        public CheckState state;
        
        
        public RestaurantOneCheck(String custName, RestaurantOneCustomer customer, RestaurantOneWaiter waiter) {
                // TODO Auto-generated constructor stub
        }
        

        public RestaurantOneCheck(String choice, int tNum, RestaurantOneCustomer customer, RestaurantOneWaiter waiter)
        {        
                this.w = waiter;
                this.c = customer;
                state = CheckState.notCalculated;
                this.tablenum = tNum;
                this.choice = choice;
                
        }



        
        
        
}