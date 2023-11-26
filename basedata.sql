CREATE DATABASE PharmacyPOS;
USE PharmacyPOS;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash CHAR(64) NOT NULL, -- Assuming SHA-256 hash
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
    expiration_date DATE NOT NULL, -- Assuming expiration dates are mandatory
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Inventory Table
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT DEFAULT 0,
    low_stock_threshold INT DEFAULT 10, -- This can be adjusted as per the requirement
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, -- Added NOT NULL constraint
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

-- Indexes for performance optimization
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_category_id ON products(category_id);
CREATE INDEX idx_product_id ON inventory(product_id);


-- Populate the Roles
INSERT INTO users (username, password_hash, role) VALUES
('manager1', SHA2('managerpassword1', 256), 'Manager'),
('sales1', SHA2('salespassword1', 256), 'Sales Assistant');

-- Populate the Categories
INSERT INTO categories (name, description) VALUES
('Analgesics', 'Pain relievers and management medications'),
('Antibiotics', 'Treatment for bacterial infections'),
('Antiseptics', 'Chemicals used to prevent infection'),
('Dermatology', 'Skin-related medications and creams');

-- Populate the Products
INSERT INTO products (name, description, price, category_id, expiration_date) VALUES
('Ibuprofen', 'Used to treat pain or inflammation caused by arthritis', 4.99, 1, '2024-05-01'),
('Amoxicillin', 'An antibiotic that fights bacteria', 15.50, 2, '2025-08-15'),
('Chlorhexidine', 'Used to clean the skin after an injury', 3.75, 3, '2023-12-31'),
('Hydrocortisone', 'A topical steroid that reduces inflammation', 8.45, 4, '2025-02-28');

-- Populate the Inventory
INSERT INTO inventory (product_id, quantity, low_stock_threshold, expiry_date) VALUES
(1, 100, 20, '2024-05-01'),
(2, 50, 15, '2025-08-15'),
(3, 200, 10, '2023-12-31'),
(4, 75, 5, '2025-02-28');

alter table inventory add expiry_date DATE;

-- Populate the Orders
INSERT INTO orders (user_id, timestamp) VALUES
(1, NOW()),
(2, NOW() - INTERVAL 1 DAY); -- Yesterday's order for testing

-- Populate the Order Details
INSERT INTO order_details (order_id, product_id, quantity_ordered, price_at_time_of_sale) VALUES
(1, 1, 2, 4.99),
(1, 3, 1, 3.75),
(2, 2, 4, 15.50),
(2, 4, 1, 8.45);