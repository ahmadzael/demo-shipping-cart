CREATE DATABASE demo_product;

USE demo_product;

CREATE TABLE users (
    id VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(300),
    token VARCHAR(100) NOT NULL UNIQUE,
    token_expired BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE products (
    id VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(200) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE cart (
    id VARCHAR(100) NOT NULL,
    user_id VARCHAR(100),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cart_Item (
    id VARCHAR(100) NOT NULL,
    cart_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES Cart(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE order (
    id VARCHAR(100) NOT NULL,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(50),
    TotalAmount DECIMAL(10, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orderItem (
    id VARCHAR(100) NOT NULL,
    order_id INT,
    product_id INT,
    quantity INT,
    Price DECIMAL(10, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES Order(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);