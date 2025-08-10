**Customer Reward Application**

This **Customer Reward Application** helps us to calculate and displays customer reward points based on their transaction history.

The logic used in the application for reward calculation is
- 2 points for every dollar spend over 100 dollars.
- 1 point for every dollar spend between 50 to 100 dollars.
- 0 point for below 50 dollars.

**Rest Endpoints** used for
- Retrieve all customers("/customers").
- Retrieve customer by ID("/customers/{customerId}").
- Calculate and display reward points per customer("/reward/{customerId}?lastNMonths={NumbberOfMonths}").
- Calculate and display reward points all customer("/reward") by default 3 for lastNMonths.
- Calculate and display reward points per customer over the last N months("/reward?lastNMonths={NumbberOfMonths}").

**Technology Stack**
- Java 17
- Springboot version 3.4.8
- H2 Database
- JUnit 5  and Mockito for unit test

**Design Details**

- Created a CustomerController class which exposes API endpoints.
- Created a CustomerService interface which gives abstract methods to CustomerServiceImpl class.
- CustomerRepository and TransactionRepository are the repos for DB operations.
- Customer and Transaction entities are created for table mapping(ORM).
- Response given by RewardDetailsDTO class. 
