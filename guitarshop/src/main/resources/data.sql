	INSERT INTO categories (category_id, category_name, category_description) VALUES
	(1, 'bass', 'bass 4-string guitar'),
	(2, 'acoustic', 'acoustic 6-string guitar'),
	(3, 'electro', 'electro 6-string guitar');
	
	INSERT INTO customers (customer_id, email, hash, firstname, lastname, city, phone, regdate) VALUES
	(1, 'user1@example.com', '000000', 'John', 'Smith', 'Minsk', '+375 44 222-45-67', '2018-09-09 14:41:13'),
	(2, 'user2@example.com', '000000', 'Peter', 'Ivanov', 'Gomel', '+375 44 111-45-67', '2018-09-09 14:41:13'),
	(3, 'user3@example.com', '000000', 'Ivan', 'Petrov', 'Paris', '+375 44 333-45-67', '2018-09-09 14:41:13');
	
	INSERT INTO makers (maker_id, maker_name, country) VALUES
	(1, 'Gibson', 'US'),
	(2, 'Fender', 'US');
	
	INSERT INTO models (model_id, category_id, maker_id, model_name, model_description, image) VALUES
	(1, 3, 1, 'Las Paul Standart', 'It''s a very well formed guitar with clear sound and long sustain', 'LPS.jpg'),
	(2, 3, 2, 'Stratocaster', 'One of the most famous guitars in the world', 'strat.jpg'),
	(3, 1, 2, 'Precision Bass', 'Bass—it''s where rhythm meets melody, creating a righteous groove that forces you to move with it', 'bass.jpg'),
	(4, 2, 1, 'Parlor Rosewood Avant Garde', 'The Parlor Rosewood AG is an innovative, next evolution of what a small-body cutaway instrument should sound like.', 'AGPL.jpg');
	
	INSERT INTO products (product_id, model_id, price) VALUES
	(1, 1, 2799.99),
	(2, 2, 1754.38),
	(3, 3, 2357.25),
	(4, 4, 1565.35);
	
	INSERT INTO orders (order_id, customer_id, complete, order_time, total_value) VALUES
	(1, 1, 0, '2018-09-14 12:13:15.781', 2799.99),
	(2, 1, 0, '2018-09-14 12:13:15.781', 2799.99),
	(3, 1, 0 ,'2018-09-14 12:13:15.781', 2799.99),
	(4, 1, 0, '2018-09-14 12:13:15.781', 2799.99);
	
	INSERT INTO order_products (order_id, product_id, add_time) VALUES
	(4, 1, '2018-09-14 12:10:15.781'),
	(4, 1, '2018-09-15 11:13:15.781'),
	(4, 1, '2018-09-12 14:13:15.781'),
	(1, 1, '2018-09-14 12:13:15.781'),
	(1, 2, '2018-09-15 12:13:15.781'),
	(1, 2, '2018-09-10 12:13:15.781'),
	(1, 3, '2018-09-11 12:13:15.781'),
	(4, 3, '2018-09-08 21:21:15.781'),
	(2, 1, '2018-09-14 12:13:15.781'),
	(4, 1, '2018-09-08 12:13:15.781'),
	(1, 1, '2018-09-14 12:13:15.781'),
	(1, 1, '2018-09-14 12:13:15.781'),
	(1, 1, '2018-09-14 12:13:15.781'),
	(1, 2, '2018-09-14 12:13:15.781'),
	(4, 2, '2018-09-08 15:13:15.781'),
	(1, 3, '2018-09-14 12:13:15.781'),
	(1, 3, '2018-09-14 12:13:15.781'),
	(2, 1, '2018-09-14 12:13:15.781'),
	(2, 2, '2018-09-14 12:13:15.781'),
	(2, 3, '2018-09-14 12:13:15.781');
	
	INSERT INTO stock (item_id, product_id, update_time) VALUES
	(1, 1, '2018-09-09 15:48:00'),
	(2, 2, '2018-09-09 15:48:00'),
	(3, 4, '2018-09-09 15:48:00'),
	(4, 3, '2018-09-09 15:48:00'),
	(5, 2, '2018-09-09 15:48:00'),
	(6, 1, '2018-09-09 15:48:00'),
	(7, 1, '2018-09-09 15:48:00'),
	(8, 3, '2018-09-09 15:48:00'),
	(9, 4, '2018-09-09 15:48:00'),
	(10, 2, '2018-09-09 15:48:00'),
	(11, 1, '2018-09-09 15:48:00'),
	(12, 3, '2018-09-09 15:48:00'),
	(13, 1, '2018-09-09 15:48:00'),
	(14, 4, '2018-09-09 15:48:00'),
	(15, 4, '2018-09-09 15:48:00'),
	(16, 1, '2018-09-09 15:48:00'),
	(17, 2, '2018-09-09 15:48:00'),
	(18, 1, '2018-09-09 15:48:00'),
	(19, 1, '2018-09-09 15:48:00'),
	(20, 3, '2018-09-09 15:48:00');
