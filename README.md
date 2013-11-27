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
Everyone in Team 14 had roles in multiple parts of the project. 

House
+Design Documentation
+Implementation
+JUnit Testing

Market
  + Design Documentation
  + Implementation
  + JUnit Testing

Bank
  + Design Documentation
  + Implementation
  + JUnit Testing

Restaurant
  + Design Documentation
  + Implementation
  + JUnit Testing

Transportation
  + Design Documentation
  + Implementation
  + JUnit Testing

Person
  + Design Documentation
  + Implementation
  + JUnit Testing

GUI
  + Animation Panels
  + Control Panel
  + Trace Panel

Overall Design
  + Design Documentation
  + Implementation
  + JUnit Testing
