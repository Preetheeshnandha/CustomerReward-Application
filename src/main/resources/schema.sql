/**
 * Table: customers
 * -----------------
 * Stores basic information about each customer.
 *
 * Columns:
 * - customer_id (BIGINT, PK, AUTO_INCREMENT):
 *     Unique identifier for each customer. Automatically generated.
 *
 * - customer_name (VARCHAR 100):
 *     Full name of the customer.
 *
 * - customer_email (VARCHAR 100):
 *     Email address of the customer. Assumed to be unique per customer.
 */
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    customer_email VARCHAR(100)
);

/**
 * Table: transactions
 * --------------------
 * Stores individual transactions made by customers.
 *
 * Columns:
 * - transaction_id (BIGINT, PK, AUTO_INCREMENT):
 *     Unique identifier for each transaction. Automatically generated.
 *
 * - transaction_date (DATE):
 *     The date when the transaction occurred.
 *
 * - transaction_amount (BIGINT):
 *     The amount spent in the transaction.
 *
 * - customer_id (BIGINT, FK -> customers.customer_id):
 *     Foreign key reference to the customer who made the transaction.
 *
 * Constraints:
 * - fk_transaction_customer:
 *     Enforces referential integrity by linking each transaction to a valid customer.
 */
CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_date DATE,
    transaction_amount BIGINT,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_customer FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id)   
);