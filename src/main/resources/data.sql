/**
 * Inserts initial customer records into the 'customers' table.
 * Each customer has a unique name and email address.
 *
 * Table: customers
 * Columns:
 *   - customer_name: Name of the customer (String)
 *   - customer_email: Email address of the customer (String)
 */

insert into customers(customer_name,customer_email)
values ('Raj','raj@gmail.com');

insert into customers(customer_name,customer_email)
values ('Ram','ram@gmail.com');

insert into customers(customer_name,customer_email)
values ('Sam','sam@gmail.com');

insert into customers(customer_name,customer_email)
values ('Ragul','ragul@gmail.com');

insert into customers(customer_name,customer_email)
values ('Kumar','kumar@gmail.com');

insert into customers(customer_name,customer_email)
values ('Guna','guna@gmail.com');

insert into customers(customer_name,customer_email)
values ('Baskar','baskar@gmail.com');

insert into customers(customer_name,customer_email)
values ('Rakesh','rakesh@gmail.com');

insert into customers(customer_name,customer_email)
values ('Samir','samir@gmail.com');

insert into customers(customer_name,customer_email)
values ('Mani','mani@gmail.com');


/**
 * Inserts transaction records for various customers into the 'transactions' table.
 * Each transaction is linked to a customer via 'customer_id' and contains a date and amount.
 *
 * Table: transactions
 * Columns:
 *   - transaction_date: Date when the transaction occurred (YYYY-MM-DD format)
 *   - transaction_amount: Monetary amount of the transaction (Long)
 *   - customer_id: Foreign key reference to the 'customers' table (Long)
 *
 * Notes:
 *   - Used for testing reward points calculation logic.
 *   - Includes a variety of months and values for edge case handling.
 */
insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-07-12',100,1);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-06-05',300,1);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-06-14',150,2);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-05-18',200,1);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-04-21',50,3);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-06-25',1000,2);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-04-01',250,5);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-07-12',500,5);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-03-11',500,6);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-04-19',5500,7);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-03-12',500,9);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-01-10',500,9);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-06-05',600,10);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-07-12',500,5);

insert into transactions(transaction_date,transaction_amount,customer_id)
values ('2025-07-12',500,5);