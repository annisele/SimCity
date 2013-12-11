team14
======

SimCity201 Project Repository for CS 201 students

How to run team 14 simcity
===========================

1) clone from https://github.com/usc-csci201-fall2013/team14#team14 into your workspace

2) Since we do not have build.xml, you will have to create a new java project. Name the java project as team14 then click finish
4) https://github.com/usc-csci201-fall2013/team14.git

be sure that the build path is configured so that the src is the only source folder
and there are no excluded package


SimCity scenarios
==========================================
We have implemented 8 scenarios in our SimCity.

Scenario 1: "Full City"
This scenario creates many houses, one bank, one restaurant, and one market.  The only functionality currently is within the houses and with the bus.  If people get low on food, they will go to the store, but it has no employees so they will not enter.

Scenario 2: "Market, House, Bank";
This scenario is the normative interaction between a person and a market, and a person and a bank.  In this scenario, both the market and the bank have a full staff of people working.  The HouseInhabitant sleeps, cooks food, realizes he needs to go to the market, goes to the market, buys food, realizes he is low on money, goes to the bank and withdraws money.

Scenario 3: "Bus to Market";
This scenario shows a person approaching a bus stop (in the bottom right) and waiting for the bus.  Once it comes, he gets off at the stop closest to his destination (the market), and then goes into the market and makes his purchase.

Scenario 4: "Restaurant, Market, Bank";
This scenario shows interaction between the restaurant and the market.  The restaurant has no chicken, a customer orders chicken, and the restaurant responds by ordering chicken from the market.  A truck is sent from the market to the restaurant, and it receives them.  There is a shared data waiter that extends a regular waiter, and puts orders onto an order wheel which the cook can access.

Scenario 5: "One Market, One House";
This scenario is simple and shows a person running low on food and buying some at the market.  It is identical to Scenario 2 but without the bank.

Scenario 6: "One Market";
Simple scenario showing one market and a customer buying items.

Scenario 7: "One Restaurant";
Simple scenario showing one restaurant and three customers.

Scenario 8: "One Bank";
Simple scenario showing one bank and two customers both withdrawing money.


Member Contributions
==========================================

####Joshua Michael
#####V2: 
1. Created a now-functioning design for Car and CarPassenger with Kevin (
	-Decided to take out the agent functionality of the Car, and have the CarPassenger create his own Gui 	when he wanted to travel to any destination
2. Implemented buses going around the larger world (for example, in bus intersection test), by
   updating bus stops and making sure that buses would now stop before intersections
3. Implemented the use of "Car Garages", so that people would walk to public car garages before
   getting into their car. Levonne helped hardcode the locations of garages, and helped create basic Guis for
   them on the map.
4. (With Kevin) Designed a way for people to figure out where the nearest car parking structure was,
   and to determine the nearest parking structure to their destination.
5. Created "Lanes" by hardcoding stops to certain routes for buses. This way, they will never hit each other
   on their routes on the outer section of the city
	-Can be seen in scenario "Bus Intersection Test"
6. Half implemented my restaurant. People change into the correct roles and walk to and enter the restaurant,
   but no interactions were developed. Due to lack of time and because of the fact that I had two exams on 
   Wednesday, I dedicated what time I had over the past week to transportation.
7. Worked along with other team members to get refined, basic images for certain interior and exterior
   elements. Clayton and Kevin would also contribute to the images I created or update them to fit in nicely     into the city (more of a V1 contribution, forgot to state)
 
#####V1:
1. Worked on the initial design documents for Bus, Car, BusPassenger, CarPassenger (with Clayton)
2. Implemented the bus and BusPassenger in both of our worlds (with Kevin)
	-Included Animation for person moving to nearest bus stop and moving from bus stop to building.
	-With Kevin, derived algorithm for determining whether to use transportation or whether to walk to the nearest building. If the person decided to use transportation, we calculated which bus stop to go to, and which one to leave.
3. Helped work with GUI, more specifically, animation, layouts of the city/world, and images. (Sprites for buildings, initial background, and images of people)
	-Used pixlr to crop and resize images 
	-Created instances of images to draw on animationpanels of world that I worked on along with Clayton.
4. Unit Tested the Bus, BusPassenger, and part of InhabitantRole (other part was Kevin)

####Kevin Chan
#####V2: 
  + 1.) Transportation Design - worked with Levonne and Joshua in designing and implementing the transportation system in the city
  + 2.) Directory - worked with Levonne and Joshua in inputting the stops for intersections, parking garages, and bus stops, ensuring that the vehicles stay on their lanes
  + 3.) Intersection - designed and implemented the Intersection file to function in conjunction with cars and buses to detect whether a vehicle wants to pass
  + 4.) CarPassengerRole and CarGui - worked with Joshua in implementing the functionality of cars (pedestrian-to-gui and gui-to-pedestrian) and correctly having people show up in correct parking structures
  + 5.) BusAgent and BusGui - re-implemented BusAgent and BusGui to correspond to the newest version of the world animation panel
  + 6.) Worked on integrating the PersonAgent to send information and update itself with regards to changes in the BankCustomer and MarketCustomer roles
  + 7.) Worked on trying to integrate RestaurantFour for into the city
  + 8.) Worked on the PersonAgent scheduler to correctly detect whether or not a Person has a bank account already, and whether the Person wants to deposit or withdraw from the bank

#####V1:
  + 1.) Bank Design - worked with Levonne extensively on the design of each of the agents/roles in the bank design
  + 2.) BankComputer - implemented bank accounts using maps and allowed it to work with the bank computer
  + 3.) BankSystem - implemented BankSystem records of windows and functionality to determine whether a window contained a customer and a bank teller, and allowed it to work with bank host thread
  + 4.) BankCustomer, BankHost, BankTeller roles - worked with Levonne to implement designs for the roles
  + 5.) BankGui - transitioned from using hacks in setting bank teller windows to using proper window coordinates
  + 6.) BankCustomer Unit Testing - implemented unit testing on bank customer
  + 7.) BusAgent functionality and design - worked with Joshua extensively in the design of the bus agent
  + 8.) BusAgent implementation - worked with Joshua extensively to have the buses pick-up and drop-off customers at the correct locations
  + 9.) BusGui implementation with Joshua to show proper movements
  + 10.) Worked with Clayton in the initial stages to allow the proper viewing of a detail panel
  + 11.) Worked with the whole team to get many of the refined Gui images using Pixlr
  + 12.) Added necessary functions and debugged errors in different files such as SystemManager, PersonAgent, etc. to be able to access their data and use them with lower-lewel building scenarios and interactions
  + 13.) PedestrianRole unit testing with Joshua
  + 14.) Worked with Joshua to design a distance algorithm to determine person choice (whether to take bus or walk to building, and which bus stop to go to and get off)


####Clayton Brand: 
#####V2: 
  + Worked with Rebecca on the PersonAgent so that it now schedules events correctly and with repetition.  Worked on a lot of event planning in HouseInhabitant and person.  Events like eating, sleeping, and working will now be scheduled every day, ad infinitum.  Created flexible and inflexible events.  
  + Worked with Rebecca on creating a scenario in which people have houses and work at a market.  Finished completing the market - person - house inhabitant interaction so that the house's food stock is updated once people buy food from a market.
  + Implemented a two market scenario to test and make sure that multiple markets can work in conjunction, and the workers behave normally and are able to shop at the other market.
  + Implemented draging of the WorldAnimationPanel so that we can fit more buildings into our SimCity.
  + Worked on the clock more to accommodate the planning of PersonAgent's schedule.
  + Made a ControlPanel for the house which displays info such as available food.

#####V1:
  + Created initial design documents for PersonAgent and Transportation (along with Josh).
  + Created the initial GUI window for us to use in our program.  Used two views, text panels, and a control panel on the right.  Got the control panel to change when something is clicked on, although currently this has very limited functionality.  Got the DetailPanel to show up when someone clicks on the WorldPanel, initially using cardLayout but throwing that out to just change a pointer to the panel.
  + Spent a long time on the interaction between Gui, World, and System objects.  Created base AnimationPanels, ControlPanels, Guis (based on Restaurant).  Created a System approach where each “building” is led by a System.  For example, MarketSystem contains a MarketAnimationPanel, a MarketControlPanel, a MarketComputer (for internal calculations that the agents can access), and lists of all agents in the system.
  + Created GUI images for World, Bank, Restaurant, Market, and House using Pokemon sprites found online.  Created GUI objects for people as well.  Spent a lot of time getting animations in the World and the House to look smooth.  Created some sprites from scratch.
  + Re-designed the PersonAgent with Rebecca to create a thorough implementation that worked with our conception of Roles.  ALL actions require a Role, and Events are split up into sets of specific Steps to complete them.  We built a robust scheduler for the PersonAgent, which still has some issues but is for the most part solid.
  + Implemented the trace panels with Rebecca, based on examples provided by our CP Keith.  Added print statements for House and Bank.
  +  Worked to implement the HouseSystem and HouseInhabitant Role.  Designed all animations and the entire system.

####Jenny: 
#####V2: 

#####V1:
  + Did initial design doc and theorization of Market system with Rebecca, designed and co-wote data, scheduler, messages, actions. 
  + Initial design doc and file creation, convenient functions, scheduler  for house inhabitant role 
  + Implemented person agent (beta version). 
  + design for directory and time, few functions including getters, time clarifications, seperation into min/hr/days
  + Imported restaurant two and wrote additional roles/classes, including System, Computer, AnimatonPanel, Orderwheel, Orders, SharedDataWaiter. Currently restaurant can communicate with market to order items if inventory is out. It keeps track if restaurant is unable to pay market. also restaurant keeps track of customers who are unable to pay.
  + JUnit testing of restaurant two. Incomplete as of right now.

####Levonne Key :
#####V2: 
  + I implemented my restaurant. the gui looks fine and all the messages are in there but i was working on transportation and other stuffs . the restaurant is partially runnning but not entirely due to a nullpointerexception. i've been spending too much time on designing the transportation and simcity map. according to the github, https://github.com/usc-csci201-fall2013/team14/pulse, I've 2 usernames : levonne key and levonne but in the graphs, I can only see levonne but not levonne key. I've been committing my code as levonne key on github. so the graph isn't as accurate as the pulse. select 1 month from the dropdown list to see the accurate number of commits that i made as levonne key. 
  + I work on ensuring that the guis leave the bank after work, integrate house, market and bank in Config.java, integrate restaurant three in Config.java, create parking structure in Directory.java, include restaurant three in PersonAgent.java, setup restaurant three in SystemManager.java, RestaurantThreeCashierRole.java, RestaurantThreeComputer.java, RestaurantThreeCookRole.java, RestaurantThreeCustomerRole.java, RestaurantThreeFood.java, RestaurantThreeHostRole.java, RestaurantThreeMenu.java, RestaurantThreeOrder.java, RestaurantThreeOrderWheel.java, RestaurantThreeSharedDataWaiter.java, RestaurantThreeSystem.java, RestaurantThreeWaiterRole.java, WorldAnimationPanel.java (transportation and parking structure), RestaurantThreeAnimationPanel.java, RestaurantThreeCashierGui.java, RestaurantThreeControlPanel.java, RestaurantThreeCookGui.java, RestaurantThreeCustomerGui.java, RestaurantThreeHostGui.java, RestaurantThreeWaiterGui.java, RestaurantThreeCashier.java, RestaurantThreeCook.java, RestaurantThreeCustomer.java, RestaurantThreeHost.java, RestaurantThreeWaiter.java, did the initial design of transportation, parking structure and bus route with Joshua, Clayton and Kevin (decide on the postion of parking structures and the new layout of the simcity), work on the scenarios ensuring that the scenario B and F have the people start from ther house first, integrating bank, market and home in scenarios, work on junit testing for market : marketcashiertest.java and other junit testing, work on the design and code of the parking structure and route of the bus and car


#####V1: 
  + initial market interaction diagram with Jenny
  + initial design doc and interaction diagrams the bank design, implementation and Junit testing with Kevin. I created the interfaces and test package structure for testing such as mock  and test. I updated the design document for bank teller,bank customer, market customer and market truck and redesign the interaction diagram for bank normative and non normative scenario. I initiated the testing procedure and formatted the test files. I worked on creating the gui for bank. I worked with Jenny on how to write her restaurant test. 
  + used hack for the bankroles to function since we only have one bank. The pay rent function couldn’t be tested since we do not have landlord. 

####Rebecca Hao:
#####V2: 
  + I debugged the Person Agent scheduler and House Inhabitant role so that people now schedule events continuously and autonomously. For example, they will eat and sleep as they should every day, and go to the bank and market as needed. This included major changes in Person Agent, House Inhabitant, and Clock, and minor changes in files such as Config and System Manager. Worked with Clayton on this.
  + Redesigned how to schedule the non-flexible events of sleeping and going to work (which are supposed to be done around a certain time). This involved changes to Person Agent's scheduler and clock. We didn't have time to fully debug the scheduling, so sometimes the Person Agent runs into scheduling conflicts.
  + Created a scenario for market where every person, including the cashier and market worker, have homes. In v1, they would just go straight to work and never eat or sleep, but now they function as normal people, as well as go to work when it's time. This structure of adding jobs to people, as opposed to using a hack to create a person who only works and doesn't act like a full person, paved the way for similar scenarios for bank and restaurant.
  + Created scenarios which integrated multiple buildings into one scenario.
  + I implemented my restaurant, Restaurant Five. This included integrating it into Sim City, getting messaging between agents to work in Sim City, redoing animation (all the GUI files) so that it looks good in Sim City, and adding code so it works with the trace panels and control panel. I was not able to add the Producer/Consumer parts of it because I spent all my time trying to get Person Agent to work and integrating multiple buildings into single scenarios.
  + Set up full city scenarios with multiple work places and houses for everyone in the scenario.
  + Fixed Market control panel (which Jenny wrote) so that when you set inventory, you have to enter numbers into all fields before pressing the "Set Inventory" button. Before, it would throw errors if you left a field blank, and now it disables the button until all fields are filled.

#####V1:
  + I created the original design documents and interaction diagrams for the market scenarios, and collaborated with Jenny to finalize them. The design doc for “Person Orders Items” was left unfinished because it became a non-norm. Kevin updated the interaction diagrams and design docs to match my code right before turning in V1.
  + I wrote the code for all the Market roles, systems, interfaces, and animation.
  + I contributed a lot to all of the base classes, including AnimationPanel, ControlPanel, Gui, SimCityGui, SimSystem, Config, Role, Directory, SystemManager, etc. Most of my time was spent designing the overall design of the project, so that it would be easier for others to integrate their individual parts (bank, restaurant, etc) into the program.
  + I finished the Market Scenario early on and figured out how to implement it in the panels and program, then wrote a wiki page to help team members integrate their parts into their project.
  + I collaborated with Clayton to completely design the person agent and write all the code.
  + I wrote the original skeleton for the control panel, laying it out and linking it to the animation panels so that the buttons and checkboxes affect the program (for example, showing and hiding messages from the trace panels).
  + I implemented the trace panels, and created a document to show teammates how to change their print statements so that they show up in the trace panels. I used Keith (the CP’s) trace panel code. I also made it so that when buildings are clicked on, the correct messages are hidden and shown in the detail trace panel, so you only see the messages of one building at a time.
  + I used a hack, since we only ever have one restaurant (RestaurantTwo) on screen at a time, to make the bus deliver to that restaurant’s location.
  + Person Agent’s scheduler still needs to be debugged and fixed. The design that Clayton and I created was extremely thought out, however we didn’t have much time to implement it and test it because Person Agent was initially assigned to someone else, so we didn’t start working on it until later in the project.
 
