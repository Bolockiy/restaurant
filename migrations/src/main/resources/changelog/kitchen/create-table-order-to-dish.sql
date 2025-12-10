CREATE TABLE kitchen.order_to_dish(
kitchen_order_id BIGINT NOT NULL,
dish_id BIGINT NOT NULL,
dishes_number BIGINT NOT NULL
REFERENCES
kitchen.kitchen_order(kitchen_order_id),
    FOREIGN KEY (dish_id) REFERENCES
kitchen.dish(dish_id)
);