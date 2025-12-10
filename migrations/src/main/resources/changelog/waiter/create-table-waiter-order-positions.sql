CREATE TABLE waiter.order_positions (
composition_id BIGSERIAL PRIMARY KEY,
dish_num BIGINT NOT NULL,
order_no BIGINT NOT NULL REFERENCES waiter.waiter_order(order_no),
menu_position_id BIGINT NOT NULL REFERENCES waiter.menu(id)
);