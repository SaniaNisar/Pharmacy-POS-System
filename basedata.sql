CREATE DATABASE IF NOT EXISTS PharmacyPOS1;
USE PharmacyPOS1;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash CHAR(64) NOT NULL,
    role ENUM('Manager', 'Sales Assistant') NOT NULL
    );

-- Categories Table
CREATE TABLE IF NOT EXISTS categories (
                                          category_id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    description TEXT
    );

-- Products Table
CREATE TABLE IF NOT EXISTS products (
                                        product_id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id INT,
    expiration_date DATE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
    );

-- Inventory Table
CREATE TABLE IF NOT EXISTS inventory (
                                         inventory_id INT AUTO_INCREMENT PRIMARY KEY,
                                         product_id INT NOT NULL,
                                         quantity INT DEFAULT 0,
                                         low_stock_threshold INT DEFAULT 10, -- This can be adjusted as per the requirement
                                         expiry_date DATE, -- Here is the column
                                         FOREIGN KEY (product_id) REFERENCES products(product_id)
    );
select*from inventory;


-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
                                      order_id INT AUTO_INCREMENT PRIMARY KEY,
                                      user_id INT,
                                      timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

-- Order Details Table
CREATE TABLE IF NOT EXISTS order_details (
                                             order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
                                             order_id INT,
                                             product_id INT,
                                             quantity_ordered INT NOT NULL,
                                             price_at_time_of_sale DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
    );

-- Carts Table
CREATE TABLE IF NOT EXISTS carts (
                                     cart_id INT AUTO_INCREMENT PRIMARY KEY,
                                     created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Cart Items Table
CREATE TABLE IF NOT EXISTS cart_items (
                                          cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
                                          cart_id INT,
                                          product_id INT,
                                          quantity INT NOT NULL,
                                          price_at_time_of_addition DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
    );

INSERT INTO users (username, password_hash, role) VALUES
                                                      ('manager1', 'managerpassword1', 'Manager'),
                                                      ('sales1', 'salespassword1', 'Sales Assistant');

ALTER TABLE products
DROP FOREIGN KEY products_ibfk_1;

ALTER TABLE products
    ADD CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories(category_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE inventory
DROP FOREIGN KEY inventory_ibfk_1;

ALTER TABLE inventory
    ADD CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE orders
DROP FOREIGN KEY orders_ibfk_1;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE order_details
DROP FOREIGN KEY order_details_ibfk_1;

ALTER TABLE order_details
    ADD CONSTRAINT fk_orderdetails_order
        FOREIGN KEY (order_id) REFERENCES orders(order_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE order_details
DROP FOREIGN KEY order_details_ibfk_2;

ALTER TABLE order_details
    ADD CONSTRAINT fk_orderdetails_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE cart_items
DROP FOREIGN KEY cart_items_ibfk_1;

ALTER TABLE cart_items
    ADD CONSTRAINT fk_cartitems_cart
        FOREIGN KEY (cart_id) REFERENCES carts(cart_id)
            ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE cart_items
DROP FOREIGN KEY cart_items_ibfk_2;

ALTER TABLE cart_items
    ADD CONSTRAINT fk_cartitems_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

select*from users;

ALTER TABLE carts
    ADD COLUMN user_id INT DEFAULT 2;

ALTER TABLE carts
    ADD CONSTRAINT fk_carts_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS sales (
                                     sale_id INT AUTO_INCREMENT PRIMARY KEY,
                                     sale_date DATE,
                                     total_cost DECIMAL(10, 2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS sale_items (
                                          sale_item_id INT AUTO_INCREMENT PRIMARY KEY,
                                          sale_id INT,
                                          product_id INT,
                                          quantity INT NOT NULL,
                                          price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(sale_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
    );


ALTER TABLE sale_items
DROP FOREIGN KEY fk_saleitems_sale;

ALTER TABLE sale_items
    ADD CONSTRAINT fk_saleitems_sale
        FOREIGN KEY (sale_id) REFERENCES sales(sale_id)
            ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE sale_items
DROP FOREIGN KEY fk_saleitems_productid;

ALTER TABLE sale_items
    ADD CONSTRAINT fk_saleitems_productid
        FOREIGN KEY (product_id) REFERENCES products(product_id)
            ON DELETE CASCADE ON UPDATE CASCADE;