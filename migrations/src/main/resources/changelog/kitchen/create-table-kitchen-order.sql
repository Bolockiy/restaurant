CREATE TABLE kitchen.kitchen_order(
kitchen_order_id BIGSERIAL PRIMARY KEY,
waiter_order_no BIGSERIAL NOT NULL,
status varchar NOT NULL,
create_dttm TIMESTAMPTZ NOT NULL
);