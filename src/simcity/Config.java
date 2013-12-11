package simcity;

import java.util.Timer;
import java.util.TimerTask;

import simcity.buildings.market.MarketWorkerRole;

public class Config {

	private Timer timer1 = new Timer();
	private Timer timer2 = new Timer();
	private Timer timer3 = new Timer();
	private Timer timer4 = new Timer();
	private Timer timer = new Timer();
	private SystemManager systems;
	private int xIslandOffset = 430;
	private int yIslandOffset = 360;

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

		// Top Left Island
		// First row
		systems.addHouse("HOUSE9", 181, 152);
		systems.addHouse("HOUSE10", 253, 152);
		systems.addHouse("HOUSE11", 326, 152);
		systems.addHouse("HOUSE12", 398, 152); 

		// Second row
		systems.addHouse("HOUSE13", 181, 246);
		systems.addHouse("HOUSE14", 253, 246);
		systems.addHouse("HOUSE2", 326, 246);
		systems.addHouse("HOUSE1", 398, 246);

		// Third row
		systems.addBank("BANK1", 181, 338);
		systems.addHouse("HOUSE15", 253, 338);
		systems.addHouse("HOUSE16", 326, 338);
		systems.addHouse("HOUSE17", 398, 338);

		// Top Center Island
		// First row
		systems.addHouse("HOUSE9", 181 + xIslandOffset, 152);
		systems.addHouse("HOUSE10", 253 + xIslandOffset, 152);
		systems.addHouse("HOUSE11", 326 + xIslandOffset, 152);
		systems.addHouse("HOUSE12", 398 + xIslandOffset, 152); 

		// Second row
		systems.addHouse("HOUSE13", 181 + xIslandOffset, 246);
		systems.addHouse("HOUSE14", 253 + xIslandOffset, 246);
		systems.addHouse("HOUSE2", 326 + xIslandOffset, 246);
		systems.addHouse("HOUSE1", 398 + xIslandOffset, 246);

		// Third row
		systems.addBank("BANK1", 181 + xIslandOffset, 338);
		systems.addHouse("HOUSE15", 253 + xIslandOffset, 338);
		systems.addHouse("HOUSE16", 326 + xIslandOffset, 338);
		systems.addHouse("HOUSE17", 398 + xIslandOffset, 338);

		// Top Right Island
		// First row
		systems.addHouse("HOUSE9", 181 + 2*xIslandOffset, 152);
		systems.addHouse("HOUSE10", 253 + 2*xIslandOffset, 152);
		systems.addHouse("HOUSE11", 326 + 2*xIslandOffset, 152);
		systems.addHouse("HOUSE12", 398 + 2*xIslandOffset, 152); 

		// Second row
		systems.addHouse("HOUSE13", 181 + 2*xIslandOffset, 246);
		systems.addHouse("HOUSE14", 253 + 2*xIslandOffset, 246);
		systems.addHouse("HOUSE2", 326 + 2*xIslandOffset, 246);
		systems.addHouse("HOUSE1", 398 + 2*xIslandOffset, 246);

		// Third row
		systems.addBank("BANK1", 181 + 2*xIslandOffset, 338);
		systems.addHouse("HOUSE15", 253 + 2*xIslandOffset, 338);
		systems.addHouse("HOUSE16", 326 + 2*xIslandOffset, 338);
		systems.addHouse("HOUSE17", 398 + 2*xIslandOffset, 338);

		// Bottom Left Island
		// First row
		systems.addHouse("HOUSE9", 181, 152 + yIslandOffset);
		systems.addHouse("HOUSE10", 253, 152 + yIslandOffset);
		systems.addHouse("HOUSE11", 326, 152 + yIslandOffset);
		systems.addHouse("HOUSE12", 398, 152 + yIslandOffset); 

		// Second row
		systems.addHouse("HOUSE13", 181, 246 + yIslandOffset);
		systems.addHouse("HOUSE14", 253, 246 + yIslandOffset);
		systems.addHouse("HOUSE2", 326, 246 + yIslandOffset);
		systems.addHouse("HOUSE1", 398, 246 + yIslandOffset);

		// Third row
		systems.addBank("BANK1", 181, 338);
		systems.addHouse("HOUSE15", 253, 338 + yIslandOffset);
		systems.addHouse("HOUSE16", 326, 338 + yIslandOffset);
		systems.addHouse("HOUSE17", 398, 338 + yIslandOffset);

		// Bottom Center Island
		// First row
		systems.addHouse("HOUSE9", 181 + xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE10", 253 + xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE11", 326 + xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE12", 398 + xIslandOffset, 152 + yIslandOffset); 

		// Second row
		systems.addHouse("HOUSE13", 181 + xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE14", 253 + xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE2", 326 + xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE1", 398 + xIslandOffset, 246 + yIslandOffset);

		// Third row
		systems.addBank("BANK1", 181 + xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE15", 253 + xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE16", 326 + xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE17", 398 + xIslandOffset, 338 + yIslandOffset);

		// Bottom Right Island
		// First row
		systems.addHouse("HOUSE9", 181 + 2*xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE10", 253 + 2*xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE11", 326 + 2*xIslandOffset, 152 + yIslandOffset);
		systems.addHouse("HOUSE12", 398 + 2*xIslandOffset, 152 + yIslandOffset); 

		// Second row
		systems.addHouse("HOUSE13", 181 + 2*xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE14", 253 + 2*xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE2", 326 + 2*xIslandOffset, 246 + yIslandOffset);
		systems.addHouse("HOUSE1", 398 + 2*xIslandOffset, 246 + yIslandOffset);

		// Third row
		//systems.addBank("BANK1", 181 + 2*xIslandOffset, 338 + yIslandOffset);
		systems.addMarket("MARKET1", 181 + 2*xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE15", 253 + 2*xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE16", 326 + 2*xIslandOffset, 338 + yIslandOffset);
		systems.addHouse("HOUSE17", 398 + 2*xIslandOffset, 338 + yIslandOffset);

		systems.addPerson("Gosling");
		systems.addPerson("Gosling");
		systems.addPerson("Gosling");
		systems.addPerson("Gosling");
		systems.addPerson("Gosling");
		systems.addPerson("RyanG");
		systems.addPerson("RyanG");
		systems.addPerson("RyanG");
		systems.addPerson("RyanG");
		systems.addPerson("RyanG");
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
		systems.setBackgroundThree();
		systems.addBus("clockwise");
		systems.addBus("counterclockwise");
		//systems.addCar("car1");

		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
		systems.addHouse("HOUSE2", 253, 152);
		systems.addHouse("HOUSE3", 326, 152);
		//systems.addHouse("HOUSE4", 398, 152); 

		// Second row
		systems.addHouse("HOUSE4", 181, 246);
		systems.addHouse("HOUSE5", 253, 246);
		systems.addHouse("HOUSE6", 326, 246);
		//systems.addHouse("HOUSE8", 398, 246);

		// Third row
		systems.addMarket("MARKET1", 253, 338);
		//systems.addHouse("HOUSE16", 326, 338);
		systems.addMarket("MARKET2", 398, 338);


		// Workers for MARKET1
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

		// Workers for MARKET2
		systems.addPerson("Barry");
		systems.setHome("Barry", "HOUSE4");
		systems.setWorkMarketCashier("Barry", "MARKET2");
		systems.setSleep("Barry");

		systems.addPerson("Sue");
		systems.setHome("Sue", "HOUSE5");
		systems.setWorkMarketWorker("Sue", "MARKET2");
		systems.setSleep("Sue");

		systems.addPerson("Donny");
		systems.setHome("Donny", "HOUSE6");
		systems.setSleep("Donny");

	}

	public void fullMarketAndBank() {
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundThree();
		systems.addBus("clockwise");
		systems.addBus("counterclockwise");
		//systems.addCar("car1");

		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
		systems.addHouse("HOUSE2", 253, 152);
		systems.addHouse("HOUSE3", 326, 152);
		systems.addHouse("HOUSE4", 398, 152); 

		// Second row
		systems.addHouse("HOUSE7", 181, 246);
		systems.addHouse("HOUSE5", 253, 246);
		systems.addHouse("HOUSE6", 326, 246);
		systems.addHouse("HOUSE8", 398, 246);

		// Third row
		systems.addMarket("MARKET1", 253, 338);
		//systems.addHouse("HOUSE16", 326, 338);
		systems.addMarket("MARKET2", 398, 338);

		systems.addBank("BANK1", 326, 338);


		// Workers for MARKET1
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

		// Workers for MARKET2
		systems.addPerson("Barry");
		systems.setHome("Barry", "HOUSE4");
		systems.setWorkMarketCashier("Barry", "MARKET2");
		systems.setSleep("Barry");

		systems.addPerson("Sue");
		systems.setHome("Sue", "HOUSE5");
		systems.setWorkMarketWorker("Sue", "MARKET2");
		systems.setSleep("Sue");

		systems.addPerson("Donny");
		systems.setHome("Donny", "HOUSE6");
		systems.setSleep("Donny");

		//bank workers
		systems.addPerson("Kevin");
		systems.setHome("Kevin", "HOUSE7");
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.setSleep("Kevin");
		systems.addPerson("Ben");
		systems.setHome("Ben", "HOUSE8");
		systems.setWorkBankTeller("Ben", "BANK1");
		systems.setSleep("Ben");
		systems.addBankAccount(0, 100, 0, "abcdef");
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
		systems.addBankAccount(0, 100, 0, "abcdef");


		timer4.schedule(new TimerTask() {
			public void run() {
				//systems.addPerson("Bank Robber");
			}
		}, 4000);

	}


	public void oneRestaurant() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundTwo();
		systems.addRestaurantTwo("RESTAURANT2", 300, 100);
		systems.addHouse("HOUSE1", 60, 27);
		systems.addHouse("HOUSE2", 123, 27);
		systems.addHouse("HOUSE3", 186, 27);
		systems.addHouse("HOUSE4", 249, 27);
		systems.addHouse("HOUSE5", 312, 27); 
		
		systems.addPerson("Conor oberst");
		systems.setHome("Conor oberst", "HOUSE2");
		systems.setWorkRestaurantTwoCashier("Conor oberst", "RESTAURANT2");
		systems.setSleep("Conor oberst");
		
		systems.addPerson("Kanye");
		systems.setHome("Kanye", "HOUSE1");
		systems.setWorkRestaurantTwoHost("Kanye", "RESTAURANT2");
		systems.setSleep("Kanye");
		
		systems.addPerson("Red");
		systems.setHome("Red", "HOUSE3");
		systems.setWorkRestaurantTwoCook("Red", "RESTAURANT2");
		systems.setSleep("Red");

		systems.addPerson("Kitty");
		systems.setHome("Kitty", "HOUSE4");
		systems.setSleep("Kitty");

		systems.addPerson("Kong");
		systems.setHome("Kong", "HOUSE5");
		systems.setWorkRestaurantTwoWaiter("Kong", "RESTAURANT2");
		systems.setSleep("Kong");

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
		systems.addBankAccount(0, 100, 0, "abcdef");
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
		systems.addRestaurantTwo("RESTAURANT2", 249, 140);/*
		systems.RestaurantTwoHostHack("Host Bloke", "RESTAURANT2");


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
		}, 1000);*/
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

									}
								}, 1000);
							}
						}, 1000);
					}
				}, 2000);
			}
		}, 5000);
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
		}, 1200);

		timer2.schedule(new TimerTask() {
			public void run() {
				//systems.addRestaurantFiveWaiter("Bob", "RESTAURANT5");
				systems.addRestaurantFiveHost("Sarah", "RESTAURANT5");

			}
		}, 1000);

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

		systems.addRestaurantSixHost("Timothy", "RESTAURANT6");

		timer2.schedule(new TimerTask() {
			public void run() {
				//systems.addRestaurantFiveWaiter("Bob", "RESTAURANT5");
				systems.addRestaurantSixWaiter("Bob", "RESTAURANT6");
			}
		}, 1000);

		timer3.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantSixCashier("Charlie", "RESTAURANT6");
			}
		}, 1300);

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantSixCook("Hernando", "RESTAURANT6");
			}
		}, 1400);

		timer1.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Hungry Clayton");
			}
		}, 2200);

	}

	// Final Scenarios
	public void scenarioA() {
		/*
		 * All workplaces (markets, all restaurants, banks) fully employed.
			Day starts and all workers go to work.
			One not-working person eats at home, then visits all the workplaces by walking.
			Roads should have appropriate complexity [e.g. intersections with stop signs and/or signals]
		 */
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundThree();

		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
		//systems.addHouse("HOUSE2", 253, 152);
		//systems.addHouse("HOUSE3", 326, 152);
		//systems.addHouse("HOUSE4", 398, 152); 

		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
//		systems.addHouse("HOUSE2", 253, 152);
//		systems.addHouse("HOUSE3", 326, 152);
//		systems.addHouse("HOUSE4", 398, 152); 
//
//		// Second row
//		systems.addHouse("HOUSE7", 181, 246);
//		systems.addHouse("HOUSE5", 253, 246);
//		systems.addHouse("HOUSE6", 326, 246);
//		systems.addHouse("HOUSE8", 398, 246);

		// Third row
		systems.addMarket("MARKET1", 253, 338);
		systems.addMarket("MARKET2", 398, 338);

		systems.addBank("BANK1", 326, 338);
		systems.addBank("BANK2", 253, 246);

		// Workers for MARKET1
		systems.addPerson("Mary");
		//systems.setHome("Mary", "HOUSE2");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		//systems.setSleep("Mary");

		systems.addPerson("Joe");
		//systems.setHome("Joe", "HOUSE1");
		systems.setWorkMarketWorker("Joe", "MARKET1");
		//systems.setSleep("Joe");

		systems.addPerson("Tommy");
		systems.setHome("Tommy", "HOUSE1");
		systems.setSleep("Tommy");

		// Workers for MARKET2
		systems.addPerson("Barry");
	//	systems.setHome("Barry", "HOUSE4");
		systems.setWorkMarketCashier("Barry", "MARKET2");
	//	systems.setSleep("Barry");
		systems.addPerson("Sue");
	//	systems.setHome("Sue", "HOUSE5");
		systems.setWorkMarketWorker("Sue", "MARKET2");
	//	systems.setSleep("Sue");

		// BANK1 workers
		systems.addPerson("Kevin");
	//	systems.setHome("Kevin", "HOUSE7");
		systems.setWorkBankHost("Kevin", "BANK1");
	//	systems.setSleep("Kevin");
		systems.addPerson("Ben");
	//	systems.setHome("Ben", "HOUSE8");
		systems.setWorkBankTeller("Ben", "BANK1");
	//	systems.setSleep("Ben");
		
		// BANK2 workers
		systems.addPerson("Bob");
	//	systems.setHome("Kevin", "HOUSE7");
		systems.setWorkBankHost("Bob", "BANK2");
	//	systems.setSleep("Kevin");
		systems.addPerson("Ren");
	//	systems.setHome("Ben", "HOUSE8");
		systems.setWorkBankTeller("Ren", "BANK2");
	//	systems.setSleep("Ben");
	
		
		
//		try {
//			//timer1.cancel();
//		} catch(Exception e) {
//
//		}
//
//		timer1.schedule(new TimerTask() {
//			public void run() {
//				//systems.addPerson("Hungry Jenny");
//			}
//		}, 1200);



	}

	public void scenarioB() {
		/*
		 * 	All workplaces fully employed.
			Day starts and all workers go to work.
			Three not-working persons eat at home, then visit all the workplaces in different orders. [one should walk; one 
			should take a car; one should take a bus.]
		 */
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();
		
		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
		systems.addHouse("HOUSE2", 253, 152);
		systems.addHouse("HOUSE3", 181 + xIslandOffset, 152);

		// Third row
		systems.addMarket("MARKET1", 253, 338);
		systems.addMarket("MARKET2", 398, 338);

		systems.addBank("BANK1", 326, 338);
		systems.addBank("BANK2", 253, 246);

		// Workers for MARKET1
		systems.addPerson("Mary");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		systems.addPerson("Joe");
		systems.setWorkMarketWorker("Joe", "MARKET1");

		//Buses
		systems.addBus("clockwise");
		systems.addBus("counterclockwise");
		
		// Non-working people
		systems.addPerson("Tommy");
		systems.setHome("Tommy", "HOUSE1");
		systems.setSleep("Tommy");
		systems.addPerson("Nick");
		systems.setHome("Nick", "HOUSE2");
		systems.setSleep("Nick");
		systems.addPerson("Sally");
		systems.setHome("Sally", "HOUSE3");
		systems.setSleep("Sally");

		// Workers for MARKET2
		systems.addPerson("Barry");
		systems.setWorkMarketCashier("Barry", "MARKET2");
		systems.addPerson("Sue");
		systems.setWorkMarketWorker("Sue", "MARKET2");

		// BANK1 workers
		systems.addPerson("Kevin");
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.addPerson("Ben");
		systems.setWorkBankTeller("Ben", "BANK1");
		
		// BANK2 workers
		systems.addPerson("Bob");
		systems.setWorkBankHost("Bob", "BANK2");
		systems.addPerson("Ren");
		systems.setWorkBankTeller("Ren", "BANK2");
	}

	public void scenarioC() {
		/*	
		 *	Each restaurant gets low on food and orders from market(s).
			Market delivers food to the open restaurant.
			Market sends invoice to cashier, who verifies and pays it.
		 */
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();

		// Top Left Island
		// First row
		systems.addHouse("HOUSE9", 181, 152);
		systems.addHouse("HOUSE10", 253, 152);
		systems.addHouse("HOUSE11", 326, 152);
		systems.addHouse("HOUSE12", 398, 152); 

		// Second row
		systems.addHouse("HOUSE13", 181, 246);
		systems.addHouse("HOUSE14", 253, 246);
		systems.addHouse("HOUSE2", 326, 246);
		systems.addHouse("HOUSE1", 398, 246);

		// Third row
		systems.addMarket("MARKET1", 181, 338);
		systems.addRestaurantTwo("RESTAURANT2", 253, 338);
		systems.addHouse("HOUSE16", 326, 338);
		systems.addHouse("HOUSE17", 398, 338);
		

		systems.addPerson("Conor oberst");
		systems.setHome("Conor oberst", "HOUSE9");
		systems.setWorkRestaurantTwoCashier("Conor oberst", "RESTAURANT2");
		systems.setSleep("Conor oberst");
		
		systems.addPerson("Kanye");
		systems.setHome("Kanye", "HOUSE10");
		systems.setWorkRestaurantTwoHost("Kanye", "RESTAURANT2");
		systems.setSleep("Kanye");
		
		systems.addPerson("Red");
		systems.setHome("Red", "HOUSE11");
		systems.setWorkRestaurantTwoCook("Red", "RESTAURANT2");
		systems.setSleep("Red");

		systems.addPerson("Kitty");
		systems.setHome("Kitty", "HOUSE12");
		systems.setSleep("Kitty");

		systems.addPerson("Kong");
		systems.setHome("Kong", "HOUSE13");
		systems.setWorkRestaurantTwoWaiter("Kong", "RESTAURANT2");
		systems.setSleep("Kong");
		
		systems.addPerson("Mary");
		systems.setHome("Mary", "HOUSE2");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		systems.setSleep("Mary");

		systems.addPerson("Joe");
		systems.setHome("Joe", "HOUSE1");
		systems.setWorkMarketWorker("Joe", "MARKET1");
		systems.setSleep("Joe");

		systems.addPerson("Tommy");
		systems.setHome("Tommy", "HOUSE14");
		systems.setSleep("Tommy");

		// Workers for MARKET2
		systems.addPerson("Barry");
		systems.setHome("Barry", "HOUSE16");
		systems.setWorkMarketCashier("Barry", "MARKET2");
		systems.setSleep("Barry");

		systems.addPerson("Sue");
		systems.setHome("Sue", "HOUSE17");
		systems.setWorkMarketWorker("Sue", "MARKET2");
		systems.setSleep("Sue");

	}

	public void scenarioE() {
		/*
		 * Person visits a bus stop. [as part of step 7]
			Bus arrives.
			Person gets on.
			Person gets off at destination.
		 */
		systems.clear();
		systems.clearDetailPane();
		systems.setBackgroundThree();

		systems.addBus("clockwise");
		systems.addBus("counterclockwise");

		// Top Left Island
		// First row
		systems.addHouse("HOUSE1", 181, 152);
		//systems.addHouse("HOUSE2", 253, 152);
		//systems.addHouse("HOUSE3", 326, 152);
		//systems.addHouse("HOUSE4", 398, 152); 

		// Top Center Island
		// Second Row
		systems.addMarket("MARKET1", 326 + xIslandOffset, 246);
		systems.addHouse("HOUSE2", 253 + xIslandOffset, 246);
		systems.addHouse("HOUSE3", 181 + xIslandOffset, 246);

		// This person will go to the market
		systems.addPerson("Steve Buscemi");
		systems.setHome("Steve Buscemi", "HOUSE1");
		systems.setSleep("Steve Buscemi");

		// Workers for MARKET1
		systems.addPerson("Mary");
		systems.setHome("Mary", "HOUSE2");
		systems.setWorkMarketCashier("Mary", "MARKET1");
		systems.setSleep("Mary");

		systems.addPerson("Joe");
		systems.setHome("Joe", "HOUSE3");
		systems.setWorkMarketWorker("Joe", "MARKET1");
		systems.setSleep("Joe");

		try {
			//timer1.cancel();
		} catch(Exception e) {

		}

		timer1.schedule(new TimerTask() {
			public void run() {
				//systems.addPerson("Hungry Jenny");
			}
		}, 1200);
	}

	public void scenarioF() {
		/*
		 * Bring each workplace down, one by one.
			Show how one not-working person still visits all the workplaces but not the ones that are down. Say you only have one 
			bank and it is down, the person should avoid all banking behavior.
		 */
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();
		systems.addHouse("HOUSE1", 253, 152);
		systems.addHouse("HOUSE2", 326, 152);
		systems.addHouse("HOUSE3", 398, 152); 
		systems.addBank("BANK1", 326, 338);
		// BANK1 workers
		systems.addPerson("Kevin");
		systems.setWorkBankHost("Kevin", "BANK1");
		systems.addPerson("Ben");
		systems.setWorkBankTeller("Ben", "BANK1");
		
	}

	public void scenarioG() {
		/*
		 *	Market delivery fails because restaurant is closed.
			Market redelivers when restaurant is open.
			Market sends invoice to cashier, who verifies and pays it.
		 */
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();
		
		
	}

	public void scenarioJ() {
		/*
		 *	Market delivery fails because restaurant is closed.
			Market redelivers when restaurant is open.
			Market sends invoice to cashier, who verifies and pays it.
		 */
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();
	}

	public void OneBankFull() {
		systems.clear();
		systems.clearDetailPane();

		systems.setBackgroundThree();

		systems.addBank("BANK1", 249, 140);

		systems.addPerson("Dan");
		systems.setWorkBankHost("Dan", "BANK1");

		systems.addPerson("Josh");
		systems.setWorkBankTeller("Josh", "BANK1");

		timer.schedule(new TimerTask() {
			public void run() {
				systems.addPerson("Kristi");
			}
		}, 4000);
	}

	public void clearTimer() {
		timer.cancel();
		timer.purge();
	}

}

