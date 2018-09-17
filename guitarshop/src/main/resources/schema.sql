DROP TABLE categories IF EXISTS;
DROP TABLE customers IF EXISTS;
DROP TABLE makers IF EXISTS;
DROP TABLE models IF EXISTS;
DROP TABLE orders IF EXISTS;
DROP TABLE order_products IF EXISTS;
DROP TABLE products IF EXISTS;
DROP TABLE stock IF EXISTS;


CREATE TABLE IF NOT EXISTS categories (
  category_id BIGINT(20) not NULL AUTO_INCREMENT,
  category_name VARCHAR(35) NOT NULL,
  category_description TEXT,
  PRIMARY KEY (category_id));

CREATE TABLE IF NOT EXISTS customers (
  customer_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  email VARCHAR(20) NOT NULL,
  hash VARCHAR(255) NOT NULL,
  firstname VARCHAR(35) NOT NULL,
  lastname VARCHAR(35) NOT NULL,
  city VARCHAR(35) default NULL,
  phone VARCHAR(35) default NULL,
  regdate TIMESTAMP not NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  UNIQUE KEY email (email));

CREATE TABLE IF NOT EXISTS makers (
  maker_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  maker_name VARCHAR(35) NOT NULL,
  country VARCHAR(20) NOT NULL,
  PRIMARY KEY (maker_id));

CREATE TABLE IF NOT EXISTS models (
  model_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  category_id BIGINT(20) NOT NULL,
  maker_id BIGINT(20) NOT NULL,
  model_name VARCHAR(35) NOT NULL,
  model_description text,
  image text,
  CONSTRAINT models_ibfk_1 FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT models_ibfk_2 FOREIGN KEY (maker_id) REFERENCES makers (maker_id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (model_id));

CREATE TABLE IF NOT EXISTS orders (
  order_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  customer_id BIGINT(20) NOT NULL,
  complete TINYINT  NOT NULL default 0,
  order_time TIMESTAMP NOT NULL AS CURRENT_TIMESTAMP,
  total_value DECIMAL(8,2),
  CONSTRAINT orders_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (order_id));

CREATE TABLE IF NOT EXISTS products (
  product_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  model_id BIGINT(20) NOT NULL,
  price DECIMAL(8,2) NOT NULL,
  CONSTRAINT products_ibfk_1 FOREIGN KEY (model_id) REFERENCES models (model_id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (product_id));
  
CREATE TABLE IF NOT EXISTS order_products (
  order_id BIGINT(20) NOT NULL,
  product_id BIGINT(20) NOT NULL,
  add_time TIMESTAMP NOT NULL AS CURRENT_TIMESTAMP,
  CONSTRAINT order_products_ibfk_1 FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT order_products_ibfk_2 FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE ON UPDATE CASCADE);

  CREATE TABLE IF NOT EXISTS stock (
  item_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  product_id BIGINT(20) NOT NULL,
  update_time TIMESTAMP NOT NULL AS CURRENT_TIMESTAMP,
  CONSTRAINT stock_ibfk_1 FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (item_id));

  