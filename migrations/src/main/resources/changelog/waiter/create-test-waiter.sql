INSERT INTO waiter_account (waiter_id, name, sex,employment_date)
VALUES
    (1, 'Иван Иванов', 'M', '2023-01-10T09:00:00+03'),
    (2, 'Мария Петрова', 'F', '2022-06-15T10:30:00+03');

INSERT INTO menu (id, dish_name, dish_cost)
VALUES
    (1, 'Салат Цезарь', 350),
    (2, 'Борщ', 250),
    (3, 'Стейк Рибай', 1200),
    (4, 'Паста Карбонара', 450),
    (5, 'Чай зеленый', 150);

INSERT INTO waiter_order (order_no, status, create_dttm, waiter_id, table_no)
VALUES
    (1001, 'COOKING', NOW(), 1, 'A1'),
    (1002, 'COOKING', NOW(), 2, 'B3');

INSERT INTO order_positions (composition_id, dish_num, order_no, menu_position_id)
VALUES
    (1, 2, 1001, 1),
    (2, 1, 1001, 2),
    (3, 1, 1002, 3),
    (4, 2, 1002, 5);

INSERT INTO payment (order_no, payment_type, payment_date, payment_sum)
VALUES
    (1001, 'CASH', NOW(), 950),
    (1002, 'CARD', NOW(), 1500);