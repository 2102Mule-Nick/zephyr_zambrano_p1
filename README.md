# zephyr_zambrano_p1

## Project Description

The Hotel Booking API is a system for booking hotel reservations. The application allows users to create, update, and delete accounts with the hotel. Users can also create, update, and delete hotel reservations. Users can book a hotel stay for a specific day, and then decide how long they wish to stay. They can also decide what type of room they want based on what types of rooms are available.

## Technologies Used

* Java
* JUnit
* Log4J
* Git SCM (on GitHub)
* Spring Framework
* Spring JDBC Template
* Spring JMS Template
* ActiveMQ
* REST
* SOAP
* Spring Transactions (JTA)

## Features

List of features ready and TODOs for future development
* REST: This application contains a fully functional publicly-facing REST API. Users can submit GET, PUT, POST, and DELETE requests (see the package titled "com.hotel.controller" in the project titled "hotel"). The publicly-facing REST API connects to the PostgreSQL database using JDBC.
* JDBC: This application uses JDBC to connect to the database and create, read, update, and delete data from the database.
* Logging: This application uses Log4J in the Dao classes to log all database interactions.

To-do list:
* Spring Transactions & JMS: transactions involving JMS timeout and rollback, but in the future I would like to fix this
* SOAP: right now the SOAP client does not run properly, but in the future this is something I would like to implement

## Getting Started

Git Clone Command: `$ git clone https://github.com/2102Mule-Nick/zephyr_zambrano_p1.git`

Download List
* JDK 8 or later
* Spring Tools Suite 4
* Maven
* PostgreSQL
* DBeaver
* Tomcat 8.5
* ActiveMQ
* Advanced REST Client (ARC)
* SoapUI

Installing PostgreSQL
* You will be using the username and password you create when installing PostgreSQL to access the database

Set Up PostgreSQL Database in DBeaver
* Create new database project with a PostgreSQL database connection. Name the database project `hotel_database` and name the database itself `hotel_database`
* Run `hotel_database.sql` in DBeaver to create and populate the database tables

Set Environment Variables in Spring Tools Suite 4
* HOTEL_DB_NAME -> hotel_database
* HOTEL_DB_URL -> localhost
* HOTEL_DB_USERNAME -> your postgres admin username
* HOTEL_DB_PASSWORD -> your postgres admin password
* HOTEL_DB_SCHEMA -> hotel

Running the Project
* Set up Tomcat 8.5 server in Spring Tools Suite 4
* Deploy each Maven project to the Tomcat Server
* Right click the project titled "hotel", then mouse over "Run As" and click "Run on Server"

## Usage

* Use ARC to utilize and test all of the REST functions
* Base URL: http://localhost:8080/hotel/
* All additionall URLs are in the package: "com.hotel.controller" in the project titled "hotel"