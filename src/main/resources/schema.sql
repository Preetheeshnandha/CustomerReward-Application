
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    customer_email VARCHAR(100)
);


CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_date DATE,
    transaction_amount INT,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_customer FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id)   
);