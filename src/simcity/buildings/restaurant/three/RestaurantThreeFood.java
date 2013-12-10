package  simcity.buildings.restaurant.three;
import java.awt.*;

public class RestaurantThreeFood {

    private int x, y;
	 private String name;
	 
	 public RestaurantThreeFood(String name) {
		 this.name = name;
	 }
	 public int getX()
	    {
	        return x;
	    }
	    
	    public int getY()
	    {
	        return y;
	    }
	    public String getName()
	    {
	        return name;
	    }
	   
	    protected void move(int x, int y)
	    {
	        this.x          =   x;
	        this.y          =   y;
	    }
}