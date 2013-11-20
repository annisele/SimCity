package simcity.buildings.restaurant.four;

public class RestaurantFourTable {

	// Data
	private int tableNum;
	private RestaurantFourCustomerRole customer;
	
	// Constructor
	public RestaurantFourTable(int i) {
		this.tableNum = i;
	}
	
	// Utilities
	public int getTableNum() {return tableNum;}
	public void setTableNum(int tableNum) {this.tableNum = tableNum;}
	public RestaurantFourCustomerRole getCustomer() {return customer;}
	public void setCustomer(RestaurantFourCustomerRole customer) {this.customer = customer;}
	
}
