# Introduction
The Trading App is an online stock trading simulation REST API.
The application allows users to create accounts, deposit and withdraw money from the accounts, and use the money in 
the accounts to buy or sell stock.
It can be used by front-end developers, mobile developers, and traders.

The Trading App is a MicroService which is implemented with SpringBoot.
The data is stored in a PostgreSQL database and the information about the stock is real-time information comes from the 
IEX cloud.

# Quick Start

**Prerequisites**
- Docker
- Java 8
- IEX token to access market data (https://iexcloud.io/docs/api/)
- PostgreSQL
- Maven

**Runing the app**
- Clone git repository
  ```
  git clone https://github.com/MiriamEA/trading_app
  ```
- Build project with Maven
  ```
  mvn install -DSkipTests
  ```
- Start the app using a shell script. The script requires four arguments.
  ```
  bash run_trading_app.sh JDBC_HOST JDBC_USER JDBC_PASSWORD IEX_PUB_TOKEN
  ```
  - JDBC_HOST: name of the server with the database
  - JDBC_USER: username for database
  - JDBC_PASSWORD: password for database
  - IEX_PUB_TOKEN: public token to an IEX cloud account

-There are two easy ways to use this API: swagger and Postman.
  - The app includes a Swagger UI. 
    When running the app on a machine, the user interface can be accessed with the link 
    http://localhost:8080/swagger-ui.html on the same machine.
  - Postman is an application that can be used to send HTTP requests. 
    It allows users to import API specifications.
    When running the app on a machine, use the link http://localhost:8080/v2/api-docs to import the API 
    specifications to Postman on the same machine.
    All possible HTTP request for the app will be set up, just the request body or the path variable need to be edited.
    (Be sure to edit the collection and set the base URL to ```localhost:8080/``` before using it.)

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
The app controller can the used to make sure that the app is up and running.
- GET `/health`: make sure SpringBoot app is up and running

## Dashboard controller
The dashboard controller is for informational purpose, showing account and position status of a trader.

Endpoints in this controller:
- GET `/dashboard/portfolio/traderId/{traderId}`: show information on all positions (open and closed) of a trader
- GET `/dashboard/profile/traderId/{traderId}`: how information on trader and the associated account

# Architecture

![Architecture](https://github.com/MiriamEA/trading_app/blob/master/assets/TradingApp.svg)

**Controller**
The controller layer handles HTTP request to the REST API.
It maps the request to the correct method call in the service layer.

**Service**
The service layers is responsible for all business logic. It validates user input.
If the input is not valid, it throws an exception.
Otherwise, it will process the input, call the corresponding methods in the Dao layer, and then processes the response 
from the Dao layer.

**Dao**
The dao layer takes input from the service layer and makes the correct call to the database or HTTP request to the IEX 
cloud.
It will process the HTTP response/result set into Java objects and return those to the service layer.

**SpringBoot**
webservlet/TomCat and IoC

**PSQL**
The PostgreSQL database contains four tables and one view.
The table "trader" stores first name, last name, email address, country, date of birth, and an autogenerated id for 
every trader.
The table "account" stores the available fund, the id of the trader owning the account, and an autogenerated id for 
every account.
The table "quote" contains ticker, ask price, ask size, bid price, bid size, and last price for every security that 
can be traded in this app.
The table "security_order" stores the account id of the account used for the order, the status (fulfilled or 
canceled), the ticker of the traded security, the size of the order, the price of the order, and some notes.

**IEX cloud**
IEX cloud (https://iexcloud.io/) is a REST API that provides access to US and Canadian stock data.
The trading app uses the IEX cloud to get real-time information on the securities that can be traded.

# Improvements
- implement orders with an asking price and a status pending
- allow multiple accounts for one trader
- automatically update quote data regularly, so the data is never too old
- handle quote data update after market closes properly
- allow short positions
