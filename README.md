# Introduction
The Trading App is an online stock trading simulation REST API.
The application allows users to create accounts, deposit and withdraw money from the accounts, and use the money in 
the accounts to buy or sell stock.
It can be used by front-end developers, mobile developers, and traders.

The Trading App is a MicroService which is implemented with SpringBoot.
The data is stored in a PostgreSQL database and the information about the stock is real-time information comes from the 
IEX cloud.

# Quick Start
- Prequiresites: Java, Docker, CentOS 7
- PSQL init
- git clone and mvn build
- Start Springboot app using a shell script
  - describe env vars
- How to consume REST API? (Swagger screenshot and postman with OpenAPI Specification, e.g. http://35.231.122.184:5000/v2/api-docs

# REST API Usage
## Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?

## Quote Controller
The quote controller is responsible for handling all request concerning the information on the securities that can be 
traded in this application.
The information on asking and biding price and size are retrieved from the IEX cloud and then cached in the quote table
 in the PSQL database.

Endpoints in this controller:
- GET `/quote/dailyList`: list all securities that are available to trading in this trading system
- GET `/quote/iex/ticker/{ticker}`: show the current information available on the IEX cloud for a ticker
- POST `/quote/tickerId/{tickerId}`: add a new ticker to the quote table so that this security can be used for trades
- PUT `/quote/`: manually update the information for one security in the quote table
- PUT `/quote/iexMarketData`: update all quotes from IEX which is an external market data source
  
## Trader Controller
- High-level description for trader controller(e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain your endpoints in this controller
##Order Controller
- High-level description for this controller.
- briefly explain your endpoints in this controller
  - /order/MarketOrder: explain what is a market order, and how does your business logic work. 
## App controller
- GET `/health` to make sure SpringBoot app is up and running
## Dashboard controller

# Architecture
- Draw a component diagram which contains controller, service, DAO, storage layers (you can mimic the diagram from the guide)
- briefly explain the following logic layers or components (3-5 sentences for each)
  - Controller 
  - Service
  - Dao
  - SpringBoot: webservlet/TomCat and IoC
  - PSQL and IEX

# Improvements
- implement orders with an asking price and a status pending
-
-
-
-