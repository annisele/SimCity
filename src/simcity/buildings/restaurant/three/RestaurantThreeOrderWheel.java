package simcity.buildings.restaurant.three;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class RestaurantThreeOrderWheel extends Object {
   private final int N = 5;
   private int count = 0;
   public Map<String,Double> Menu= new HashMap<String, Double>();
   private Vector<RestaurantThreeOrder> orders;
   
   public RestaurantThreeOrderWheel(){
        orders = new Vector<RestaurantThreeOrder>();
        Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
   }

   
   synchronized public void insert(RestaurantThreeOrder data) {
   	
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
   
   synchronized public RestaurantThreeOrder remove() {
	   RestaurantThreeOrder data;
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
   
   private void insert_item(RestaurantThreeOrder data){
       orders.addElement(data);
   }
   
   private RestaurantThreeOrder remove_item(){
	   RestaurantThreeOrder data = (RestaurantThreeOrder) orders.firstElement();
       orders.removeElementAt(0);
       return data;
   }
}