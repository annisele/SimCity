package simcity.buildings.restaurant.two;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class RestaurantTwoOrderWheel extends Object {
    private final int N = 5;
   private int count = 0;
   public Map<String,Double> Menu= new HashMap<String, Double>();
   private Vector<RestaurantTwoWOrder> orders;
   
   public RestaurantTwoOrderWheel(){
       orders = new Vector<RestaurantTwoWOrder>();
       Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
   }

   
   synchronized public void insert(RestaurantTwoWOrder data) {
   	
   	 while (count == N) {
            try{ 
                System.out.println("\tFull, waiting");
                wait(5000);                         // Full, wait to add
            } catch (InterruptedException ex) {};
        }
            
        insert_item(data);
        count++;
        if(count == 1) {
            System.out.println("\tNot Empty, notify");
            notify();                               // Not empty, notify a 
                                                    // waiting consumer
        }
     
   }
   
   synchronized public RestaurantTwoWOrder remove() {
	   RestaurantTwoWOrder data;
   	 if(count==0){
   		 return null;
   	 }
        /*while(count == 0)
            try{ 
                System.out.println("\tEmpty, waiting");
                wait(1000);                         // Empty, wait to consume
                break;
            } catch (InterruptedException ex) {};*/
 
        data = remove_item();
        count--;
        if(count == N-1){ 
            System.out.println("\tNot full, notify");
            notify();                               // Not full, notify a 
                                                    // waiting producer
        }
        return data;
  
   }

   // changing the order -> look at readme.txt
   
   private void insert_item(RestaurantTwoWOrder data){
       orders.addElement(data);
   }
   
   private RestaurantTwoWOrder remove_item(){
	   RestaurantTwoWOrder data = (RestaurantTwoWOrder) orders.firstElement();
       orders.removeElementAt(0);
       return data;
   }
}