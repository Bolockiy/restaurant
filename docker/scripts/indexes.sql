\connect waiter_db

CREATE INDEX CONCURRENTLY name_index ON waiter.waiter_account(name);
CREATE INDEX CONCURRENTLY sex_index ON waiter.waiter_account(sex);
