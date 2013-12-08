package simcity;

import java.util.Timer;
import java.util.TimerTask;

import simcity.buildings.market.MarketWorkerRole;

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
	
	public void busIntersection() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundThree();
		systems.addBus("Busta");
	}

	public void busToMarket() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundTwo();
		systems.addMarket("MARKET2", 312, 27);
		systems.addMarketCashier("Mary", "MARKET2");
		systems.addBus("Busta"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET2");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorker("Bob", "MARKET2");
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
		systems.addMarketCashier("Mary", "MARKET1");
		systems.addBus("Buster"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET1");
		//systems.addPerson("Rebecca");
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorker("Bob", "MARKET1");
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
	
	public void fullMarket() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		
		systems.addMarket("MARKET1", 186, 250);
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		
		systems.addMarketCashier("Mary", "MARKET1");
		systems.setHome("Mary", "HOUSE2");
				
		//systems.addMarketWorker("Joe", "MARKET1");
		//systems.setHome("Joe", "HOUSE1");
	}

	public void oneHouse() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundOne();
		systems.addHouse("HOUSE1", 100, 100);
		systems.addPerson("Homie");
		systems.setHome("Homie", "HouseOne");
	}

	public void oneBank() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundOne();
		systems.addBank("BANK1", 100, 300);
		systems.addBankHostHack("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
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
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantTwoWaiterHack("Waiter Bob dylan", "RESTAURANT2");
								systems.addRestaurantTwoSharedDataWaiterHack("Waiter Bob dylan", "RESTAURANT2");
								timer.schedule(new TimerTask() {
									public void run() {
										//systems.addRestaurantTwoWaiterHack("Waiter Bob dylan", "RESTAURANT2");
										//systems.addRestaurantTwoSharedDataWaiterHack("Waiter Bob dylan", "RESTAURANT2");
										systems.addPerson("jenny1");
										systems.addPerson("jenny2");
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
		},1000);
	}

	public void fullCity() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addBus("Busta");

		// Top row
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		systems.addHouse("HOUSE4", 249, 27);
		systems.addHouse("HOUSE5", 312, 27); 

		// These are the right-most houses
		systems.addHouse("HOUSE6", 402, 27);
		systems.addHouse("HOUSE7", 402, 110);
		systems.addHouse("HOUSE8", 402, 189);

		// Second row
		systems.addHouse("HOUSE9", 60, 140);
		systems.addHouse("HOUSE10", 123, 140);
		systems.addHouse("HOUSE11", 186, 140);
		systems.addHouse("HOUSE12", 249, 140); 

		// Third row
		systems.addHouse("HOUSE13", 60, 250);
		systems.addHouse("HOUSE14", 123, 250);
		systems.addMarket("MARKET1", 186, 250);
		systems.addRestaurantTwo("RESTAURANT2", 249, 250);

		// Fourth row
		systems.addBank("BANK1", 60, 333);
		systems.addHouse("HOUSE15", 123, 333);
		systems.addHouse("HOUSE16", 186, 333);
		systems.addHouse("HOUSE17", 249, 333);

		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE1");


		systems.addPerson("Bobby");
		systems.setHome("Bobby", "HOUSE5");

		systems.addPerson("McKendricka");
		systems.setHome("McKendricka", "HOUSE12");

		/*//populating city: 5 normal people
		systems.addPerson("Adam");
		systems.setHome("Adam", "HOUSE1");
		systems.addPerson("Bob");
		systems.setHome("Bob", "HOUSE1");
		systems.addPerson("Bill");
		systems.setHome("Bill", "HOUSE1");
		systems.addPerson("Steve");
		systems.setHome("Steve", "HOUSE1");
		systems.addPerson("Keith");
		systems.setHome("Keith", "HOUSE1");
		//market1: 1 cashier, 4 workers
		systems.addMarketCashierHack("Chris", "MARKET1");
		systems.setHome("Chris", "HOUSE4");
		systems.addMarketWorkerHack("Will", "MARKET1");
		systems.setHome("Will", "HOUSE2");
		systems.addMarketWorkerHack("Wes", "MARKET1");
		systems.setHome("Wes", "HOUSE2");
		systems.addMarketWorkerHack("Wendy", "MARKET1");
		systems.setHome("Wendy", "HOUSE3");
		systems.addMarketWorkerHack("Wilbur", "MARKET1");
		systems.setHome("Wilbur", "HOUSE5");*/

		
		//systems.addPerson("Bobby");
		//systems.setHome("Bobby", "HOUSE5");
		
		//systems.addPerson("McKendricka");
		//systems.setHome("McKendricka", "HOUSE12");11
		


	}

	public void oneMarketOneHouse() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();

		systems.addMarket("MARKET1", 60, 140);
		systems.addHouse("HOUSE1", 249, 140);

		systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 2000);


		systems.addPerson("Hungry Antoine");
		systems.setHome("Hungry Antoine", "HOUSE1");

	}

	public void marketHouseBank() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();

		systems.addMarket("MARKET1", 60, 140);
		systems.addHouse("HOUSE1", 249, 140);

		systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 2000);

		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE1");

		systems.addBank("BANK1", 402, 27);
		systems.addBankHostHack("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Bank Teller", "BANK1");
			}
		}, 4000);

	}
	public void MarketBankRestaurant(){
		systems.clear();
		systems.clearDetailPane();
		systems.addMarket("MARKET1", 60, 140);
		systems.addBank("BANK1", 402, 27);
		systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		systems.setBackgroundTwo();
		systems.addRestaurantTwo("RESTAURANT2", 249, 140);
		systems.addRestaurantTwoHostHack("Host Bloke", "RESTAURANT2");


		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantTwoCashierHack("Cashier Blob", "RESTAURANT2");

				systems.addMarketWorker("Bob", "MARKET1");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantTwoCookHack("Cook Brute", "RESTAURANT2");

						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantTwoWaiterHack("Waiter Bob dylan", "RESTAURANT2");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addPerson("jenny");
										systems.hacker();
									}
								}, 2000);
							}
						}, 2000);
					}
				},2000);
			}

		},2000);

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 1000);
		systems.addBank("BANK1", 402, 27);
		systems.addBankHostHack("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Bank Teller", "BANK1");
			}
		}, 1000);
	}
	
	public void restaurantOne(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantOne("RESTAURANTONE", 249, 140);
		systems.addRestaurantOneHost("Jack", "RESTAURANTONE");
		timer.schedule(new TimerTask() {
			public void run(){
				systems.addRestaurantOneWaiter("Gus Fring", "RESTAURANTONE");
				timer.schedule(new TimerTask () {
					public void run() {
						systems.addRestaurantOneCashier("Ted Benacke", "RESTAURANTONE");
							}
						}, 1000);
					}
				}, 1000);
			
	}
	public void restaurantThree(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		//systems.addRestaurantSix("RESTAURANT6", 249, 140);

	}
	public void restaurantFour(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();

	}
	//Rebecca's restaurant
	public void restaurantFive(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantFive("RESTAURANT5", 249, 140);
		systems.addRestaurantFiveHost("Sarah", "RESTAURANT5");
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Hungry Jenny");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantFiveWaiter("Bob", "RESTAURANT5");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addPerson("Hungry Clayton");
							}
						}, 2000);
					}
				}, 2000);
			}
		}, 4000);

	}
	public void restaurantSix(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantSix("RESTAURANT6", 249, 140);
		systems.addRestaurantSixHost("Timothy", "RESTAURANT6");
		
		timer.schedule(new TimerTask() {
			public void run() {
				//systems.addPerson("Five");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantSixWaiter("Bob", "RESTAURANT6");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantSixCashier("Charlie", "RESTAURANT6");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addRestaurantSixCook("Hernando", "RESTAURANT6");
									}
								}, 1000);
							}
						}, 1000);
					}
				}, 2000);
			}
		}, 4000);

	}

	public void clearTimer() {
		timer.cancel();
		timer.purge();
	}

}

