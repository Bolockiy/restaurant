INSERT INTO dish (short_name, balance, dish_composition)
VALUES
    ('Pizza Margherita', 10, 'Flour, Tomato, Cheese'),
    ('Caesar Salad', 15, 'Lettuce, Chicken, Dressing'),
    ('Spaghetti Carbonara', 8, 'Pasta, Eggs, Bacon');

-- 5. Вставляем тестовый заказ
INSERT INTO kitchen_order (waiter_order_no, status, create_dttm)
VALUES
    (1001, 'NEW', NOW());

-- 6. Вставляем позиции заказа
INSERT INTO order_to_dish (kitchen_order_id, dish_id, dishes_number)
VALUES
    (1, 1, 2),
    (1, 2, 1);