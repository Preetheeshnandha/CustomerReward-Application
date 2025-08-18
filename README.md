**Customer Reward Application**

This **Customer Reward Application** helps us to calculate and displays customer reward points based on their transaction history.

The logic used in the application for reward calculation is
- 2 points for every dollar spend over 100 dollars.
- 1 point for every dollar spend between 50 to 100 dollars.
- 0 point for below 50 dollars.

**Technology Stack**
- Java 17
- Springboot version 3.4.8
- H2 Database(Sample data in data.sql file)
- JUnit 5  and Mockito for unit test

**Project Structure**

CustomerReward-Application/
│── src/main/java/com/customerReward/application/
│ ├── controller/
│ │ └── CustomerController.java # Handles REST API endpoints for customer & rewards
│ ├── service/
│ │ ├── CustomerService.java # Service interface for business logic
│ │ └── CustomerServiceImpl.java # Implementation of CustomerService
│ ├── repository/
│ │ ├── CustomerRepository.java # Repository for Customer entity (JPA)
│ │ └── TransactionRepository.java # Repository for Transaction entity (JPA)
│ ├── entity/
│ │ ├── Customer.java # Customer entity mapped to DB table
│ │ └── Transaction.java # Transaction entity mapped to DB table
│ ├── dto/
│ │ └── RewardDetailsDTO.java # Data Transfer Object for rewards response
│ ├── exception/
│ │ ├── GlobalExceptionHandler.java # Centralized exception handling using @RestControllerAdvice
│ │ ├── ResourceNotFoundException.java # Custom exception for missing resources (404)
│ │ └── InvalidRequestException.java # Custom exception for invalid parameters (400)
│
│── src/main/resources/
│ ├── Evidance/ # Folder for screenshots, API responses, etc.
│ ├── application.properties # Spring Boot application configuration
│ ├── data.sql # Sample data to load into DB on startup
│ └── schema.sql # Schema definition for tables
│
│── src/test/java/com/customerReward/application/
│ ├── CustomerServiceTest.java # Unit tests for CustomerService layer
│ └── CustomerControllerTest.java # Unit tests for Controller layer
│
└── pom.xml # Maven dependencies & project configuration


**Design Details**

- Created a CustomerController class which exposes API endpoints.
- Created a CustomerService interface which gives abstract methods to CustomerServiceImpl class.
- CustomerRepository and TransactionRepository are the repos for DB operations.
- Customer and Transaction entities are created for table mapping(ORM).
- Response given by RewardDetailsDTO class. 


**Exception Handling**

Implemented **GlobalExceptionHandler** using @RestControllerAdvice
- Added centralized handling for:

  • ResourceNotFoundException (404)
  Request
  GET /Customer/1000
  - Customer Id 1000 is not found
  
  Error Response
  
  {
    "error": "Resource Not Found",
    "message": "Customer details not found"
  }

  
  • InvalidRequestException (400)
  GET /customers/reward/1?lastNMonths=0
  - lastNMonths must be greater than 0
  
  Error Response
  
  {
    "error": "Invalid Request Parameter",
    "message": "lastNMonths must be greater than 0"
  }
  
  
  • Generic Exception (500 - Internal Server Error)
  GET /cusmers/1
  - Incorrect API endpoint
  - It will handle other than InvalidRequestException and ResourceNotFoundException.
  
  Error Response
  
  {
    "error": "Internal Server Error",
    "message": "Something went wrong, please check the endpoint."
  }
  
  
- Ensured consistent JSON error response structure across all APIs
- Improved error messages for invalid parameters and missing resources.

**Rest Endpoints** used for
- Retrieve all customers("/customers").
- Retrieve customer by ID("/customers/{customerId}").
- Calculate and display reward points per customer("/reward/{customerId}?lastNMonths={NumbberOfMonths}").
- Calculate and display reward points all customer("/reward") by default 3 for lastNMonths.
- Calculate and display reward points per customer over the last N months("/reward?lastNMonths={NumbberOfMonths}").


**Example API's**

1. Get the customer reward points by customer Id during lastNMonths

Request

GET /customers/reward/2?lastNMonths=1
- 2 is customer id as path variable
- 1 is lastNmonths as query paramter to fetch dynamic time duration(By default 3 months data will be fetch)

Response

{
    "customerId": 2,
    "customerName": "Ram",
    "customerEmail": "ram@gmail.com",
    "monthlyTransactionAmount": {},
    "monthlyRewards": {},
    "totalRewardPoints": 0
}

2. Get all Customers()

Request

GET /customers

Response

[
    {
        "customerId": 1,
        "customerName": "Raj",
        "customerEmail": "raj@gmail.com",
        "transactions": [
            {
                "transactionId": 1,
                "transactionDate": "2025-07-12",
                "transactionAmount": 100
            },
            {
                "transactionId": 3,
                "transactionDate": "2025-05-18",
                "transactionAmount": 200
            }
        ]
    },
    {
        "customerId": 2,
        "customerName": "Ram",
        "customerEmail": "ram@gmail.com",
        "transactions": [
            {
                "transactionId": 2,
                "transactionDate": "2025-06-14",
                "transactionAmount": 150
            },
            {
                "transactionId": 5,
                "transactionDate": "2025-06-25",
                "transactionAmount": 1000
            }
        ]
    },
    {
        "customerId": 3,
        "customerName": "Sam",
        "customerEmail": "sam@gmail.com",
        "transactions": [
            {
                "transactionId": 4,
                "transactionDate": "2025-04-21",
                "transactionAmount": 50
            }
        ]
    },
    {
        "customerId": 4,
        "customerName": "Ragul",
        "customerEmail": "ragul@gmail.com",
        "transactions": []
    }
    }
]

3. Get all Customer reward details

Request

GET /customers/reward

Response

[
    {
        "customerId": 1,
        "customerName": "Raj",
        "customerEmail": "raj@gmail.com",
        "monthlyTransactionAmount": {
            "MAY": 200,
            "JULY": 100
        },
        "monthlyRewards": {
            "MAY": 250,
            "JULY": 50
        },
        "totalRewardPoints": 300
    },
    {
        "customerId": 2,
        "customerName": "Ram",
        "customerEmail": "ram@gmail.com",
        "monthlyTransactionAmount": {
            "JUNE": 1150
        },
        "monthlyRewards": {
            "JUNE": 2000
        },
        "totalRewardPoints": 2000
    },
    {
        "customerId": 3,
        "customerName": "Sam",
        "customerEmail": "sam@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 4,
        "customerName": "Ragul",
        "customerEmail": "ragul@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 5,
        "customerName": "Kumar",
        "customerEmail": "kumar@gmail.com",
        "monthlyTransactionAmount": {
            "JULY": 1500
        },
        "monthlyRewards": {
            "JULY": 2550
        },
        "totalRewardPoints": 2550
    },
    {
        "customerId": 6,
        "customerName": "Guna",
        "customerEmail": "guna@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 7,
        "customerName": "Baskar",
        "customerEmail": "baskar@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 8,
        "customerName": "Rakesh",
        "customerEmail": "rakesh@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 9,
        "customerName": "Samir",
        "customerEmail": "samir@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 10,
        "customerName": "Mani",
        "customerEmail": "mani@gmail.com",
        "monthlyTransactionAmount": {
            "JUNE": 600
        },
        "monthlyRewards": {
            "JUNE": 1050
        },
        "totalRewardPoints": 1050
    }
]


4. Get the all customer reward points with last N months

Request

GET /customers/reward?lastNMonths=1
- 1 is lastNmonths as query paramter to fetch dynamic time duration(By default 3 months data will be fetch)

Response

[
    {
        "customerId": 1,
        "customerName": "Raj",
        "customerEmail": "raj@gmail.com",
        "monthlyTransactionAmount": {
            "JULY": 100
        },
        "monthlyRewards": {
            "JULY": 50
        },
        "totalRewardPoints": 50
    },
    {
        "customerId": 2,
        "customerName": "Ram",
        "customerEmail": "ram@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 3,
        "customerName": "Sam",
        "customerEmail": "sam@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    },
    {
        "customerId": 4,
        "customerName": "Ragul",
        "customerEmail": "ragul@gmail.com",
        "monthlyTransactionAmount": {},
        "monthlyRewards": {},
        "totalRewardPoints": 0
    }
]

5. Get the customer data by Id

Request

GET /customers/2
- 2 is customer id as path variable

Response

{
    "customerId": 2,
    "customerName": "Ram",
    "customerEmail": "ram@gmail.com",
    "transactions": [
        {
            "transactionId": 2,
            "transactionDate": "2025-06-14",
            "transactionAmount": 150
        },
        {
            "transactionId": 5,
            "transactionDate": "2025-06-25",
            "transactionAmount": 1000
        }
    ]
}