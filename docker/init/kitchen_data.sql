\connect kitchen_db

INSERT INTO kitchen.dish(dish_id, balance, short_name, dish_composition)
VALUES
(1, 50, 'Soup', 'Water+Vegetables'),
(2, 100, 'Salad', 'Vegetables+Oil');

INSERT INTO kitchen.kitchen_order(kitchen_order_id, waiter_order_no, status, create_dttm)
VALUES
(1, 101, 'Pending', NOW());

INSERT INTO kitchen.order_to_dish(kitchen_order_id, dish_id, dishes_number)
VALUES
(1, 1, 2),
(1, 2, 1);
