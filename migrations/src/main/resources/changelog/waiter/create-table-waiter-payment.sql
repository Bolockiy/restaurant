CREATE TABLE payment (
payment_id BIGSERIAL PRIMARY KEY,
order_no BIGINT NOT NULL REFERENCES waiter_order(order_no),
payment_type VARCHAR,
payment_date TIMESTAMPTZ, payment_sum NUMERIC
);