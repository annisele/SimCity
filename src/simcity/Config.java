package simcity;

import java.util.Timer;
import java.util.TimerTask;

import simcity.buildings.market.MarketWorkerRole;

public class Config {

	private Timer timer1 = new Timer();
	private Timer timer2 = new Timer();
	private Timer timer3 = new Timer();
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
		systems.addBus("clockwise");
		systems.addBus("counterclockwise");
		//systems.addCar("car1");
		systems.addMarket("MARKET2", 312, 27);
		
		systems.addPerson("Gosling");
		//systems.setWorkMarketWorker("Gosling", "MARKET2");
	}

	public void busToMarket() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundTwo();
		systems.addMarket("MARKET2", 312, 27);
		
		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET2");
		
		//systems.addMarketCashier("Mary", "MARKET2");
		systems.addBus("Busta"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET2");
		
		try {
			timer1.cancel();
			timer2.cancel();
			timer1 = new Timer();
			timer2 = new Timer();
		} catch(Exception e) {
		
		}
		timer1.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET2");
				//systems.addMarketWorker("Bob", "MARKET2");
			}
		}, 2000);
		timer2.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Josh");
			}
		}, 4000);
		
		
	}

	public void oneMarket() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundOne();
		systems.addMarket("MARKET1", 100, 100);
		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET1");
	//	systems.addMarketCashier("Mary", "MARKET1");
		systems.addBus("Buster"); //Take this out if you don't want the bus here
		systems.addMarketTruck("MARKET1");
		//systems.addPerson("Rebecca");
		
		try {
			timer1.cancel();
			timer2.cancel();
			timer1 = new Timer();
			timer2 = new Timer();
		} catch(Exception e) {
		
		}
		timer1.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET1");
				//systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 500);
		timer2.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Rebecca");
			}
		}, 1500);

	}
	
	public void fullMarket() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundTwo();
		
		systems.addMarket("MARKET1", 186, 250);
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		
		systems.addPerson("Mary");
		systems.setHome("Mary", "HOUSE2");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		systems.setSleep("Mary");
		//systems.addMarketCashier("Mary", "MARKET1");
		
		systems.addPerson("Joe");
		systems.setHome("Joe", "HOUSE1");
		systems.setWorkMarketWorker("Joe", "MARKET1");
		systems.setSleep("Joe");
		//systems.addMarketWorker("Joe", "MARKET1");
		
		systems.addPerson("Tommy");
		systems.setHome("Tommy", "HOUSE3");
		systems.setSleep("Tommy");

		
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
		///////
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addBank("BANK1", 100, 300);
		systems.addMarket("MARKET1", 186, 250);
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		systems.addHouse("HOUSE4", 249, 27);
		systems.addHouse("HOUSE5", 312, 27); 
		systems.addPerson("Mary");
		systems.setHome("Mary", "HOUSE2");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		systems.setSleep("Mary");
		systems.addPerson("Joe");
		systems.setHome("Joe", "HOUSE1");
		systems.setWorkMarketWorker("Joe", "MARKET1");
		systems.setSleep("Joe");
		systems.addPerson("Tommy");
		systems.setHome("Tommy", "HOUSE3");
		systems.setSleep("Tommy");
		systems.addPerson("Kevin");
		systems.setHome("Kevin", "HOUSE4");
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.setSleep("Kevin");
		systems.addPerson("Ben");
		systems.setHome("Ben", "HOUSE5");
		systems.setWorkBankTeller("Ben", "BANK1");
		systems.setSleep("Ben");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		

		///////////////////////////
		/*
		
		
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundOne();
		systems.addBank("BANK1", 100, 300);
		///////////////////////////////
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		
		systems.addPerson("Kevin");
		systems.setHome("Kevin", "HOUSE2");
		systems.setWorkBankHost("Kevin", "BANK1");
		
		systems.addPerson("Ben");
		systems.setHome("Ben", "HOUSE1");
		systems.setWorkBankTeller("Ben", "BANK1");
		

		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE3");
		
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		*/
		/*
		try {
			timer1.cancel();
			timer2.cancel();
			timer3.cancel();
			timer1 = new Timer();
			timer2 = new Timer();
			timer3 = new Timer();
		} catch(Exception e) {
		
		}
		timer1.schedule(new TimerTask() {
			public void run() {
				systems.setWorkBankTeller("Ben", "BANK1");
			}
		}, 500);
		timer2.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Levonne");
			}
		}, 1000);
		timer3.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Levanne");
			}
		}, 1500);
		*/
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
	
	public void LargeCity(){
		systems.clear();
		systems.clearDetailPane();
		
		systems.setBackgroundThree();
	}

	public void oneMarketOneHouse() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();

		systems.addMarket("MARKET1", 60, 140);
		systems.addHouse("HOUSE1", 249, 140);

		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		//systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET1");
				//systems.addMarketWorker("Bob", "MARKET1");
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

		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		//systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET1");
				//systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 2000);

		systems.addPerson("Tony");
		systems.setHome("Tony", "HOUSE1");

		systems.addBank("BANK1", 402, 27);
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.setWorkBankTeller("Ben", "BANK1");
			}
		}, 4000);

	}
	public void MarketBankRestaurant(){
		systems.clear();
		systems.clearDetailPane();
		systems.addMarket("MARKET1", 60, 140);
		systems.addBank("BANK1", 402, 27);
		
		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		//systems.addMarketCashier("Mary", "MARKET1");
		systems.addMarketTruck("MARKET1");

		systems.setBackgroundTwo();
		systems.addRestaurantTwo("RESTAURANT2", 249, 140);
		systems.addRestaurantTwoHostHack("Host Bloke", "RESTAURANT2");


		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantTwoCashierHack("Cashier Blob", "RESTAURANT2");

				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET1");
				//systems.addMarketWorker("Bob", "MARKET1");
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
				systems.addPerson("Bob");
				systems.setWorkMarketWorker("Bob", "MARKET1");
				//systems.addMarketWorker("Bob", "MARKET1");
			}
		}, 1000);
		systems.addBank("BANK1", 402, 27);
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.addHackedBankAccount(0, 100, 0, "abcdef");
		timer.schedule(new TimerTask(){
			public void run() {
				systems.setWorkBankTeller("Ben", "BANK1");
			}
		}, 1000);
	}
	
	public void restaurantOne(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantOne("RESTAURANT1", 249, 140);
		systems.addRestaurantOneHost("Jack", "RESTAURANT1");
		timer.schedule(new TimerTask() {
			public void run(){
				systems.addRestaurantOneWaiter("Gus Fring", "RESTAURANT1");
				timer.schedule(new TimerTask () {
					public void run() {
						systems.addRestaurantOneCashier("Ted Benacke", "RESTAURANT1");
						timer.schedule(new TimerTask () {
							public void run() {
								systems.addRestaurantOneCook("Gayle", "RESTAURANT1");
								timer.schedule(new TimerTask() { 
									public void run() {
									systems.addPerson("Huell");
								}
								}, 1000);
							}
						}, 1000);
							}
						}, 1000);
					}
				}, 1000);
			
	}
	//LEVONNE'S RESTAURANT
	public void restaurantThree(){
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundTwo();
		systems.addRestaurantThree("RESTAURANT3", 249, 140);
		systems.addRestaurantThreeHost("Levonne", "RESTAURANT3");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantThreeCook("NELSON", "RESTAURANT3");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantThreeWaiter("EDWARD", "RESTAURANT3");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addRestaurantThreeCashier("JUSTIN", "RESTAURANT3");
								timer.schedule(new TimerTask() {
									public void run() {
										systems.addPerson("Hungry Harry");
										timer.schedule(new TimerTask() {
											public void run() {
												systems.addPerson("Hungry Aaron");
											}
										}, 2000);
									}
								}, 1000);
							}
						}, 1000);
					}
				}, 2000);
			}
		}, 4000);
	}
	
	public void restaurantFour(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantFour("RESTAURANT4", 249, 140);
		systems.addRestaurantFourHost("R4Host", "RESTAURANT4");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantFourWaiter("R4Waiter", "RESTAURANT4");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addRestaurantFourCook("R4Cook", "RESTAURANT4");
						timer.schedule(new TimerTask() {
							public void run() {
								systems.addPerson("Hungry R4Customer1");
							}
						}, 1000);
					}
				}, 1000);
			}
		}, 1000);
	}
	
	//Rebecca's restaurant
	public void restaurantFive(){
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantFive("RESTAURANT5", 249, 140);
		systems.addRestaurantFiveWaiter("Bob", "RESTAURANT5");
	//	systems.addRestaurantFiveHost("Sarah", "RESTAURANT5");
		
		
		try {
			timer1.cancel();
			timer2.cancel();
			timer3.cancel();
			timer.cancel();
			timer1 = new Timer();
			timer2 = new Timer();
			timer3 = new Timer();
			timer = new Timer();
		} catch(Exception e) {
		
		}
		
		timer1.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Hungry Jenny");
			}
		}, 1000);
		
		timer2.schedule(new TimerTask() {
			public void run() {
				//systems.addRestaurantFiveWaiter("Bob", "RESTAURANT5");
				systems.addRestaurantFiveHost("Sarah", "RESTAURANT5");

			}
		}, 1600);
		
		timer3.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Hungry Clayton");
			}
		}, 1500);
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantFiveCook("Manny", "RESTAURANT5");
			}
		}, 2000);

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

