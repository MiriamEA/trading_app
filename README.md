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
Swagger is an open-source software famework that helps developers design, build, document, and consume REST APIs.
By reading the API structure, it automatically builds an interactive API documentation.
Swagger creates an user interface that allows easy interaction with the REST API.

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
The trader controller manages the trader and account information. It allows users to create accounts,and deposit and 
withdraw money from a given account.

Endpoints in this controller:
- DELETE `/trader/traderId/{traderId}`: delete a trader and their account if and only if there is no money in the 
account and there are no open positions for that account
- POST `/trader/`: create a new trader and an associated account
- POST `/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}`: create a new 
trader and an associated account
- PUT `/trader/deposit/traderId/{traderId}/amount/{amount}`: deposit a positive amount in a given account
- PUT `/trader/withdraw/traderId/{traderId}/amount/{amount}`: withdraw a positive amount from a given account (amount
 cannot exceed available funds in the account)

##Order Controller
The order controller manages the buying and selling of securities. It has only one endpoint:
- POST `order/MarketOrder`: An order consists of an account id, a size, and a ticker for a security.
A positive size denotes buying securities and a negative size denotes selling securities. 
Buying securities is only possible if there is enough money the account. 
Selling is only possible if the account has enough position of the security (short positions are not allowed).
If an order cannot be executed it will be canceled.

## App controller
- GET `/health` to make sure SpringBoot app is up and running

## Dashboard controller
The dashboard controller is for informational purpose, showing account and position status of a trader.

Endpoins in this controller:
- GET `/dashboard/portfolio/traderId/{traderId}`: show information on all positions (open and closed) of a trader
- GET `/dashboard/profile/traderId/{traderId}`: how information on trader and the associated account

# Architecture

![Architecture](https://github.com/MiriamEA/trading_app/blob/master/assets/TradingApp.svg)


- briefly explain the following logic layers or components (3-5 sentences for each)
  - Controller 
  - Service
  - Dao
  - SpringBoot: webservlet/TomCat and IoC
  - PSQL and IEX

# Improvements
- implement orders with an asking price and a status pending
- allow multiple accounts for one trader
-
-
-