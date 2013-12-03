# Car Rental Service Project

## Installation

You will need Maven and a JBoss 7.1.1 server in order to run this project.
This application also makes use of a MySQL database although you could probably
reconfigure it so that it uses another JDBC compatible database

1. Open a terminal on the project's directory

`cd CarRentalService`

2. Compile the project

`mvn package`

3. Launch a properly configured JBoss server

4. Deploy using Maven

`mvn jboss-as:deploy`
 
## Setup

This guide assumes that you have a MySQL server setup on the same machine as
the JBoss server

1. Prepare the database

Launch the MySQL client `mysql` and run the following queries:

```sql
CREATE USER 'team3'@'localhost' IDENTIFIED BY 'dpatterns';
CREATE DATABASE team3;
GRANT ALL PRIVILEGES ON team3.* TO 'team3'@'localhost';
FLUSH PRIVILEGES;
```

2. Use the database setup script by using a browser

Go to `http://localhost:8080/CarRentalService/database/setup`

## Usage

You can login to the application by using the `admin` username with password `admin`
