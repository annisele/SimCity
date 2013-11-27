team14
======

SimCity201 Project Repository for CS 201 students

How to run team 14 simcity
===========================

1) Download as ZIP from https://github.com/usc-csci201-fall2013/team14#team14

2) Your zip folder should currently be in the download file

3) Since we do not have build.xml, you will have to create a new java project. Drag and drop your files into the src 
   package.


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

Joshua Michael
1. Worked on the initial design documents for Bus, Car, BusPassenger, CarPassenger (with Clayton)
2. Implemented the bus and BusPassenger in both of our worlds (with Kevin)
	-Included Animation for person moving to nearest bus stop and moving from bus stop to building.
	-With Kevin, derived algorithm for determining whether to use transportation or whether to walk to the nearest building. If the person decided to use transportation, we calculated which bus stop to go to, and which one to leave.
3. Helped work with GUI, more specifically, animation, layouts of the city/world, and images. (Sprites for buildings, initial background, and images of people)
	-Used pixlr to crop and resize images 
	-Created instances of images to draw on animationpanels of world that I worked on along with Clayton.
4. Unit Tested the Bus, BusPassenger, and part of InhabitantRole (other part was Kevin)

Kevin Chan
1.) Bank Design - worked with Levonne extensively on the design of each of the agents/roles in the bank design
2.) BankComputer - implemented bank accounts using maps and allowed it to work with the bank computer
3.) BankSystem - implemented BankSystem records of windows and functionality to determine whether a window contained a customer and a bank teller, and allowed it to work with bank host thread
4.) BankCustomer, BankHost, BankTeller roles - worked with Levonne to implement designs for the roles
5.) BankGui - transitioned from using hacks in setting bank teller windows to using proper window coordinates
6.) BankCustomer Unit Testing - implemented unit testing on bank customer
7.) BusAgent functionality and design - worked with Joshua extensively in the design of the bus agent
8.) BusAgent implementation - worked with Joshua extensively to have the buses pick-up and drop-off customers at the correct locations
9.) BusGui implementation with Joshua to show proper movements
10.) Worked with Clayton in the initial stages to allow the proper viewing of a detail panel
11.) Worked with the whole team to get many of the refined Gui images using Pixlr
12.) Added necessary functions and debugged errors in different files such as SystemManager, PersonAgent, etc. to be able to access their data and use them with lower-lewel building scenarios and interactions
13.) PedestrianRole unit testing with Joshua
14.) Worked with Joshua to design a distance algorithm to determine person choice (whether to take bus or walk to building, and which bus stop to go to and get off)


Clayton Brand:  
  + Created initial design documents for PersonAgent and Transportation (along with Josh).
  + Created the initial GUI window for us to use in our program.  Used two views, text panels, and a control panel on the right.  Got the control panel to change when something is clicked on, although currently this has very limited functionality.  Got the DetailPanel to show up when someone clicks on the WorldPanel, initially using cardLayout but throwing that out to just change a pointer to the panel.
  + Spent a long time on the interaction between Gui, World, and System objects.  Created base AnimationPanels, ControlPanels, Guis (based on Restaurant).  Created a System approach where each “building” is led by a System.  For example, MarketSystem contains a MarketAnimationPanel, a MarketControlPanel, a MarketComputer (for internal calculations that the agents can access), and lists of all agents in the system.
  + Created GUI images for World, Bank, Restaurant, Market, and House using Pokemon sprites found online.  Created GUI objects for people as well.  Spent a lot of time getting animations in the World and the House to look smooth.  Created some sprites from scratch.
  + Re-designed the PersonAgent with Rebecca to create a thorough implementation that worked with our conception of Roles.  ALL actions require a Role, and Events are split up into sets of specific Steps to complete them.  We built a robust scheduler for the PersonAgent, which still has some issues but is for the most part solid.
  + Implemented the trace panels with Rebecca, based on examples provided by our CP Keith.  Added print statements for House and Bank.
  +  Worked to implement the HouseSystem and HouseInhabitant Role.  Designed all animations and the entire system.



 
Jenny: 
- Did initial design doc and theorization of Market system with Rebecca, designed and co-wote data, scheduler, messages, actions. 
- Initial design doc and file creation, convenient functions, scheduler  for house inhabitant role 
- Implemented person agent (beta version). 
- design for directory and time, few functions including getters, time clarifications, seperation into min/hr/days
- Imported restaurant two and wrote additional roles/classes, including System, Computer, AnimatonPanel, Orderwheel, Orders, SharedDataWaiter. Currently restaurant can communicate with market to order items if inventory is out. It keeps track if restaurant is unable to pay market. also restaurant keeps track of customers who are unable to pay.
- JUnit testing of restaurant two. Incomplete as of right now.

Levonne Key : 
-initial market interaction diagram with Jenny
-initial design doc and interaction diagrams the bank design, implementation and Junit testing with Kevin. I created the interfaces and test package structure for testing such as mock  and test. I updated the design document for bank teller,bank customer, market customer and market truck and redesign the interaction diagram for bank normative and non normative scenario. I initiated the testing procedure and formatted the test files. I worked on creating the gui for bank. I worked with Jenny on how to write her restaurant test. 
- used hack for the bankroles to function since we only have one bank. The pay rent function couldn’t be tested since we do not have landlord. 

Rebecca Hao:
  + I created the original design documents and interaction diagrams for the market scenarios. The design doc for “Person Orders Items” was left unfinished becasue it became a non-norm. Kevin updated the interaction diagrams and design docs to match my code right before turning in V1.
  + I wrote the code for all the Market roles, systems, interfaces, and animation.
  + I contributed a lot to all of the base classes, including AnimationPanel, ControlPanel, Gui, SimCityGui, SimSystem, Config, Role, Directory, SystemManager, etc. Most of my time was spent designing the overall design of the project, so that it would be easier for others to integrate their individual parts (bank, restaurant, etc) into the program.
  + I finished the Market Scenario early on and figured out how to implement it in the panels and program, then wrote a wiki page to help team members integrate their parts into their project.
  + I collaborated with Clayton to completely design the person agent and write all the code.
  + I wrote the origninal skeleton for the control panel, laying it out and linking it to the animation panels so that the buttons and checkboxes affect the program (for example, showing and hiding messages from the trace panels).
  + I implemented the trace panels, and created a document to show teammates how to change their print statements so that they show up in the trace panels. I used Keith (the CP’s) trace panel code. I also made it so that when buildings are clicked on, the correct messages are hidden and shown in the detail trace panel, so you only see the messages of one building at a time.
  + I used a hack, since we only ever have one restaurant (RestaurantTwo) on screen at a time, to make the bus deliver to that restaurant’s location.
  + Person Agent’s scheduler still needs to be debugged and fixed. The design that Clayton and I created was extremely thought out, however we didn’t have much time to implement it and test it because Person Agent was initially assigned to someone else, so we didn’t start working on it until later in the project.
 
