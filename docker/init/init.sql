CREATE DATABASE kitchen_db;
CREATE DATABASE waiter_db;

\connect kitchen_db

CREATE SCHEMA IF NOT EXISTS kitchen;

CREATE TABLE kitchen.kitchen_order (
    kitchen_order_id BIGINT PRIMARY KEY,
    waiter_order_no BIGINT NOT NULL,
    status VARCHAR NOT NULL,
    create_dttm TIMESTAMPTZ NOT NULL
);

CREATE TABLE kitchen.dish (
    dish_id BIGINT PRIMARY KEY,
    balance BIGINT NOT NULL,
    short_name VARCHAR NOT NULL,
    dish_composition VARCHAR NOT NULL
);

CREATE TABLE kitchen.order_to_dish (
    kitchen_order_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    dishes_number BIGINT NOT NULL,
    PRIMARY KEY (kitchen_order_id, dish_id),
    FOREIGN KEY (kitchen_order_id) REFERENCES kitchen.kitchen_order(kitchen_order_id),
    FOREIGN KEY (dish_id) REFERENCES kitchen.dish(dish_id)
);

\connect waiter_db

CREATE SCHEMA IF NOT EXISTS waiter;

CREATE TABLE waiter.waiter_account (
    waiter_id BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL,
    employment_date TIMESTAMPTZ NOT NULL,
    sex VARCHAR NOT NULL
);

CREATE TABLE waiter.waiter_order (
    order_no BIGINT PRIMARY KEY,
    status VARCHAR NOT NULL,
    create_dttm TIMESTAMPTZ NOT NULL,
    waiter_id BIGINT NOT NULL REFERENCES waiter.waiter_account(waiter_id),
    table_no VARCHAR NOT NULL
);

CREATE TABLE waiter.payment (
    order_no BIGINT PRIMARY KEY REFERENCES waiter.waiter_order(order_no),
    payment_type VARCHAR,
    payment_date TIMESTAMPTZ,
    payment_sum NUMERIC
);

CREATE TABLE waiter.menu (
    id BIGINT PRIMARY KEY,
    dish_name VARCHAR NOT NULL,
    dish_cost NUMERIC NOT NULL
);

CREATE TABLE waiter.order_positions (
    composition_id BIGINT PRIMARY KEY,
    dish_num BIGINT NOT NULL,
    order_no BIGINT NOT NULL REFERENCES waiter.waiter_order(order_no),
    menu_position_id BIGINT NOT NULL REFERENCES waiter.menu(id)
);
