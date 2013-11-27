package simcity;

import java.util.Timer;
import java.util.TimerTask;

public class Config {
	
	private Timer timer = new Timer();
	private SystemManager systems;
	
	public Config(SystemManager s) {
		systems = s;
	}

	public void threeBuildings() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundOne();
		systems.addMarket("MARKET1", 100, 100);
		//systems.addRestaurantTwo("RestaurantTwo", 300, 100);
		systems.addRestaurantOne("RESTAURANT2", 300, 100);
		systems.addBank("BANK1", 100, 300);
		systems.addHouse("HOUSE1", 300, 300);
		systems.addPerson("Mark");

		systems.addBus("Buster");
		
	}
	
	public void restaurantMarket() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundTwo();
		
		systems.addRestaurantTwo("RESTAURANT2", 60, 27);
		systems.addMarket("MARKET1", 123, 27);
		
		
	}
	
	public void busToMarket() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundOne();
		systems.addMarket("MARKET2", 100, 100);
		systems.addMarketCashierHack("Mary", "MARKET2");
		systems.addBus("Buster"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET2");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "MARKET2");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addPerson("Josh");
					}
				}, 2000);
			}
		}, 2000);
	}
	
	public void oneMarket() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundOne();
		systems.addMarket("MARKET1", 100, 100);
		systems.addMarketCashierHack("Mary", "MARKET1");
		systems.addBus("Buster"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET1");
		//systems.addPerson("Rebecca");
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "MARKET1");
				timer.schedule(new TimerTask() {
					public void run() {
						//systems.addPerson("Rebecca");
						//systems.addMarketWorkerHack("Bill", "Market");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addPerson("Rebecca");
							}
						}, 2000);
					}
				}, 2000);
			}
		}, 2000);
		
	}
	
	public void oneHouse() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundOne();
		systems.addHouse("HOUSE1", 100, 100);
		//systems.addPerson("Homie"); // This guy will live in the house, hence his name
		systems.addPerson("Homie");
		systems.setHome("Homie", "HOUSE1");
	}
	
	public void oneBank() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundOne();
		systems.addBank("BANK1", 100, 300);
		systems.addBankHostHack("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Bank Teller", "BANK1");
				timer.schedule(new TimerTask(){
					public void run() {
						systems.addPerson("Levonne");
						timer.schedule(new TimerTask() {
							public void run() {
								//systems.addBankTellerHack("Bank Teller2", "Bank");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addPerson("Levanne");
									}
								}, 500);
							}
						}, 500);
					}
				}, 500);
			}
		}, 500);
	}

	public void oneRestaurant() {
		systems.clear();
		systems.clearDetailPane();

		
		systems.setBackgroundOne();
		systems.addRestaurantTwo("RESTAURANT2", 300, 100);
		systems.addRestaurantTwoHostHack("Host Bloke", "RESTAURANT2");
		timer.schedule(new TimerTask() {
			public void run() {
				
				systems.addRestaurantTwoCashierHack("Cashier Blob", "RESTAURANT2");
				
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantTwoCookHack("Cook Brute", "RESTAURANT2");
						systems.addPerson("jenny1");
						systems.addPerson("jenny2");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantTwoWaiterHack("Waiter Bob dylan", "RESTAURANT2");
								systems.addRestaurantTwoSharedDataWaiterHack("Waiter Bob dylan", "RESTAURANT2");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addPerson("jenny3");
									}
								}, 1000);
							}
						}, 1000);
					}
				},1000);
			}

		},1000);

	}
	

	public void fullCity() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundTwo();
		systems.addBus("Busta");
		
		// Top row
		systems.addHouse("HouseSOne", 60, 27);
		systems.addHouse("HouseSOne", 123, 27);
		systems.addHouse("HouseSOne", 186, 27);
		systems.addHouse("HouseSOne", 249, 27);
		systems.addHouse("HouseSOne", 312, 27);
		
		// These are the right-most houses
		systems.addHouse("HouseSOne", 402, 27);
		systems.addHouse("HouseSOne", 402, 110);
		systems.addHouse("HouseSOne", 402, 189);
		
		// Second row
		systems.addHouse("HouseSOne", 60, 140);
		systems.addHouse("HouseSOne", 123, 140);
		systems.addHouse("HouseSOne", 186, 140);
		systems.addHouse("HouseSOne", 249, 140);
		
		// Third row
		systems.addHouse("HouseSOne", 60, 250);
		systems.addHouse("HouseSOne", 123, 250);
		systems.addMarket("MarketOne", 186, 250);
		systems.addRestaurantTwo("RestaurantTwo", 249, 250);

		// Fourth row
		systems.addBank("BankOne", 60, 333);
		systems.addHouse("HouseSOne", 123, 333);
		systems.addHouse("HouseSOne", 186, 333);
		systems.addHouse("HouseSOne", 249, 333);
		
	}
	
	public void oneMarketOneHouse() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundTwo();
		
		systems.addMarket("MARKET1", 60, 140);
		systems.addHouse("HOUSE1", 249, 140);
		
		systems.addMarketCashierHack("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "MARKET1");
			}
		}, 2000);
		
		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE1");

	}
	
	public void marketHouseBank() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundTwo();
		
		systems.addMarket("MARKET1", 60, 140);
		systems.addHouse("HOUSE1", 249, 140);
		
		systems.addMarketCashierHack("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "MARKET1");
			}
		}, 2000);
		
		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE1");
		
		systems.addBank("BANK1", 402, 27);
		systems.addBankHostHack("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Bank Teller", "BANK1");
			}
		}, 4000);

	}
	public void MarketBankRestaurant(){
		systems.clear();
		systems.clearDetailPane();

		
		systems.setBackgroundOne();
		systems.addRestaurantTwo("RestaurantTwo", 300, 100);
		systems.addRestaurantTwoHostHack("Host Bloke", "RestaurantTwo");
		systems.addMarket("Market", 60, 140);
		systems.addBank("Bank", 402, 27);
		systems.addMarketCashierHack("Mary", "Market");
		systems.addMarketTruck("Market");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantTwoCashierHack("Cashier Blob", "RestaurantTwo");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantTwoCookHack("Cook Brute", "RestaurantTwo");

						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantTwoWaiterHack("Waiter Bob dylan", "RestaurantTwo");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addPerson("jenny");
									}
								}, 1000);
							}
						}, 1000);
					}
				},1000);
			}

		},1000);

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "Market");
			}
		}, 1000);
		systems.addBank("Bank", 402, 27);
		systems.addBankHostHack("Kevin", "Bank");
		systems.addHackedBankAccount(0, 100, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Bank Teller", "Bank");
			}
		}, 1000);
	}
	
	public void clearTimer() {
		timer.cancel();
		timer.purge();
	}

	}

