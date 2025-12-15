CREATE TABLE waiter_order (
order_no BIGSERIAL PRIMARY KEY,
status VARCHAR NOT NULL,
create_dttm TIMESTAMPTZ NOT NULL,
waiter_id BIGINT NOT NULL REFERENCES waiter_account(waiter_id),
table_no VARCHAR NOT NULL );