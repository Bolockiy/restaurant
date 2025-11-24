\connect waiter_db

EXPLAIN ANALYZE
SELECT * FROM waiter.waiter_account WHERE name='Иванов Иван';

EXPLAIN ANALYZE
SELECT * FROM waiter.waiter_account WHERE sex='Male';

SELECT
    i.relname AS "Наименование индекса",
    c.relname AS "Наименование таблицы",
    pg_size_pretty(pg_total_relation_size(i.relid)) AS "Размер таблицы вместе с индексами",
    pg_size_pretty(pg_indexes_size(i.relid)) AS "Размер всех индексов (сумма)",
    pg_size_pretty(pg_relation_size(i.indexrelid)) AS "Размер индекса"
FROM pg_stat_all_indexes i
JOIN pg_class c ON i.relid = c.oid
WHERE i.relname IN ('name_index','sex_index');
