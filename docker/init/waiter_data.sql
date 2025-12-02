\connect waiter_db

INSERT INTO waiter.waiter_account(name, sex, employment_date)
VALUES
('Иванов Иван', 'Male', NOW()),
('Петров Петр', 'Male', NOW()),
('Сидорова Анна', 'Female', NOW());

INSERT INTO waiter.waiter_order(order_no, status, create_dttm, waiter_id, table_no)
VALUES
(101, 'Paid', NOW(), 1, 'A1');

INSERT INTO waiter.menu(id, dish_name, dish_cost)
VALUES
(1, 'Soup', 150),
(2, 'Salad', 200);

INSERT INTO waiter.order_positions(composition_id, dish_num, order_no, menu_position_id)
VALUES
(1, 2, 101, 1),
(2, 1, 101, 2);

INSERT INTO waiter.payment(order_no, payment_type, payment_date, payment_sum)
VALUES
(101, 'Cash', NOW(), 500);
