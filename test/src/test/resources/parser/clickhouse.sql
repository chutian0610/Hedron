SELECT 1;
SELECT * FROM users;
SELECT id FROM users;
SELECT id, name FROM users;
SELECT * FROM users WHERE id = 1;
SELECT * FROM users WHERE id > 0;
SELECT * FROM users ORDER BY id;
SELECT COUNT(*) FROM users;
SELECT id FROM users GROUP BY id;
SELECT * FROM users LIMIT 1;
SELECT * FROM users ORDER BY name LIMIT 10;

-- TPCH-style queries
SELECT * FROM orders WHERE o_orderdate >= '1995-01-01';
SELECT c_custkey, c_name FROM customer WHERE c_acctbal > 1000 ORDER BY c_acctbal DESC;
SELECT COUNT(*) FROM lineitem WHERE l_shipdate BETWEEN '1995-01-01' AND '1995-12-31';
SELECT l_orderkey, SUM(l_quantity) AS total_qty FROM lineitem GROUP BY l_orderkey;
SELECT c_name, COUNT(*) AS order_count FROM customer JOIN orders ON c_custkey = o_custkey GROUP BY c_name;
SELECT * FROM orders WHERE o_orderstatus IN ('O', 'F');
SELECT DISTINCT o_orderstatus FROM orders;
SELECT c_name, c_acctbal FROM customer WHERE c_acctbal BETWEEN 100 AND 1000 ORDER BY c_acctbal;
SELECT * FROM lineitem WHERE l_discount > 0.05 AND l_tax < 0.1;
SELECT l_orderkey, AVG(l_extendedprice) AS avg_price FROM lineitem GROUP BY l_orderkey HAVING AVG(l_extendedprice) > 1000;

-- Complex queries with multiple conditions
SELECT * FROM orders WHERE o_orderdate >= '1995-01-01' AND o_orderstatus = 'O' ORDER BY o_orderdate DESC LIMIT 100;
SELECT c_name, SUM(o_totalprice) AS total_spent FROM customer JOIN orders ON c_custkey = o_custkey GROUP BY c_name HAVING SUM(o_totalprice) > 5000;
SELECT l_orderkey, SUM(l_extendedprice * (1 - l_discount)) AS revenue FROM lineitem GROUP BY l_orderkey ORDER BY revenue DESC LIMIT 10;
SELECT * FROM orders WHERE o_orderkey IN (SELECT l_orderkey FROM lineitem WHERE l_quantity > 50);
SELECT c_name, o_orderdate FROM customer JOIN orders ON c_custkey = o_custkey WHERE o_totalprice > (SELECT AVG(o_totalprice) FROM orders);

-- Aggregation and window functions
SELECT COUNT(*), SUM(o_totalprice), AVG(o_totalprice), MIN(o_totalprice), MAX(o_totalprice) FROM orders;
SELECT c_name, COUNT(*) OVER (PARTITION BY c_nationkey) AS nation_customer_count FROM customer;
SELECT o_orderdate, SUM(o_totalprice) OVER (ORDER BY o_orderdate ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS running_total FROM orders;
SELECT l_orderkey, l_quantity, RANK() OVER (PARTITION BY l_orderkey ORDER BY l_quantity DESC) AS quantity_rank FROM lineitem;
SELECT c_name, o_orderdate, o_totalprice, ROW_NUMBER() OVER (PARTITION BY c_name ORDER BY o_totalprice DESC) AS row_num FROM customer JOIN orders ON c_custkey = o_custkey;

-- Subqueries and CTE-like patterns
SELECT * FROM (SELECT c_name, c_acctbal FROM customer WHERE c_acctbal > 1000) AS high_balance_customers;
SELECT c_name, (SELECT COUNT(*) FROM orders WHERE o_custkey = c_custkey) AS order_count FROM customer;
WITH top_customers AS (SELECT c_name, c_acctbal FROM customer ORDER BY c_acctbal DESC LIMIT 10) SELECT * FROM top_customers;
SELECT * FROM orders WHERE o_totalprice > (SELECT AVG(o_totalprice) FROM orders WHERE o_orderstatus = 'O');
SELECT l_orderkey, l_quantity FROM lineitem WHERE l_orderkey IN (SELECT o_orderkey FROM orders WHERE o_orderdate >= '1995-01-01');

SELECT * FROM orders WHERE o_orderdate >= '1995-01-01';
SELECT * FROM orders ORDER BY o_orderdate LIMIT 5 OFFSET 10;

-- Complex JOIN operations
SELECT c_name, o_orderkey, o_orderdate, l_linenumber, l_quantity FROM customer JOIN orders ON c_custkey = o_custkey JOIN lineitem ON o_orderkey = l_orderkey;
SELECT c_name, COUNT(DISTINCT o_orderkey) AS distinct_orders FROM customer LEFT JOIN orders ON c_custkey = o_custkey GROUP BY c_name;
SELECT * FROM orders o1 JOIN orders o2 ON o1.o_custkey = o2.o_custkey WHERE o1.o_orderdate < o2.o_orderdate;
SELECT c_name, SUM(l_extendedprice) AS total_spent FROM customer JOIN orders ON c_custkey = o_custkey JOIN lineitem ON o_orderkey = l_orderkey GROUP BY c_name;
SELECT c_name, o_orderdate, l_quantity FROM customer JOIN orders ON c_custkey = o_custkey JOIN lineitem ON o_orderkey = l_orderkey WHERE l_quantity > 50;

-- String and date operations
SELECT * FROM customer WHERE c_name LIKE 'A%';
SELECT c_name, SUBSTRING(c_phone, 1, 3) AS area_code FROM customer;
SELECT o_orderdate, YEAR(o_orderdate) AS `year`, MONTH(o_orderdate) AS `month` FROM orders;
SELECT EXTRACT(YEAR FROM o_orderdate) AS `year`, EXTRACT(MONTH FROM o_orderdate) AS `month` FROM orders;
SELECT * FROM orders WHERE o_orderdate BETWEEN '1995-01-01' AND '1995-12-31';
SELECT c_name, LENGTH(c_name) AS name_length FROM customer WHERE LENGTH(c_name) > 10;

-- CASE expressions
SELECT c_name, c_acctbal, CASE WHEN c_acctbal > 1000 THEN 'High' WHEN c_acctbal > 500 THEN 'Medium' ELSE 'Low' END AS balance_level FROM customer;
SELECT o_orderstatus, COUNT(*), CASE WHEN o_orderstatus = 'O' THEN 'Open' WHEN o_orderstatus = 'F' THEN 'Closed' ELSE 'Other' END AS status_desc FROM orders GROUP BY o_orderstatus;
SELECT l_quantity, CASE WHEN l_quantity > 50 THEN 'Large' WHEN l_quantity > 20 THEN 'Medium' ELSE 'Small' END AS quantity_size FROM lineitem;
SELECT c_name, c_acctbal, CASE WHEN c_acctbal < 0 THEN 'Negative' WHEN c_acctbal = 0 THEN 'Zero' ELSE 'Positive' END AS balance_sign FROM customer;
SELECT o_orderdate, o_totalprice, CASE WHEN o_totalprice > 10000 THEN 'High Value' WHEN o_totalprice > 5000 THEN 'Medium Value' ELSE 'Low Value' END AS value_category FROM orders;

-- UNION and set operations
SELECT c_name FROM customer WHERE c_acctbal > 1000 UNION SELECT c_name FROM customer WHERE c_acctbal < 0;
SELECT o_orderstatus FROM orders UNION ALL SELECT o_orderstatus FROM orders;
SELECT c_name FROM customer WHERE c_name LIKE 'A%' INTERSECT SELECT c_name FROM customer WHERE c_acctbal > 500;
SELECT c_name FROM customer EXCEPT SELECT c_name FROM customer WHERE c_acctbal < 0;
SELECT o_orderstatus FROM orders UNION SELECT o_orderstatus FROM orders ORDER BY o_orderstatus;

-- Advanced filtering and sorting
SELECT * FROM orders WHERE o_orderdate >= '1995-01-01' AND o_totalprice BETWEEN 1000 AND 10000 ORDER BY o_totalprice DESC, o_orderdate ASC;
SELECT * FROM lineitem WHERE l_discount > 0.05 OR l_tax < 0.1 ORDER BY l_quantity DESC LIMIT 100;
SELECT c_name, c_acctbal FROM customer WHERE c_acctbal > (SELECT AVG(c_acctbal) FROM customer) ORDER BY c_acctbal DESC;
SELECT * FROM orders WHERE o_orderstatus IN ('O', 'F', 'P') AND o_orderdate >= '1995-01-01' ORDER BY o_orderdate;
SELECT l_orderkey, l_quantity, l_extendedprice FROM lineitem WHERE l_quantity * l_extendedprice > 10000 ORDER BY l_quantity DESC;

-- Multiple aggregations and grouping
SELECT c_nationkey, COUNT(*) AS customer_count, SUM(c_acctbal) AS total_balance, AVG(c_acctbal) AS avg_balance FROM customer GROUP BY c_nationkey;
SELECT o_orderstatus, COUNT(*) AS order_count, SUM(o_totalprice) AS total_value, AVG(o_totalprice) AS avg_value FROM orders GROUP BY o_orderstatus;
SELECT l_orderkey, COUNT(*) AS line_count, SUM(l_quantity) AS total_qty, AVG(l_discount) AS avg_discount FROM lineitem GROUP BY l_orderkey HAVING COUNT(*) > 5;
SELECT c_name, COUNT(DISTINCT o_orderkey) AS distinct_orders, SUM(o_totalprice) AS total_spent FROM customer JOIN orders ON c_custkey = o_custkey GROUP BY c_name HAVING SUM(o_totalprice) > 10000;
SELECT YEAR(o_orderdate) AS `year`, MONTH(o_orderdate) AS `month`, COUNT(*) AS order_count, SUM(o_totalprice) AS total_value FROM orders GROUP BY YEAR(o_orderdate), MONTH(o_orderdate) ORDER BY `year`, `month`;
SELECT EXTRACT(YEAR FROM o_orderdate) AS `year`, EXTRACT(MONTH FROM o_orderdate) AS `month`, COUNT(*) AS order_count, SUM(o_totalprice) AS total_value FROM orders GROUP BY EXTRACT(YEAR FROM o_orderdate), EXTRACT(MONTH FROM o_orderdate) ORDER BY EXTRACT(YEAR FROM o_orderdate), EXTRACT(MONTH FROM o_orderdate);

-- Complex nested queries
SELECT * FROM customer WHERE c_custkey IN (SELECT o_custkey FROM orders WHERE o_orderkey IN (SELECT l_orderkey FROM lineitem WHERE l_quantity > 50));
SELECT c_name, (SELECT SUM(o_totalprice) FROM orders WHERE o_custkey = c_custkey) AS total_spent FROM customer WHERE (SELECT SUM(o_totalprice) FROM orders WHERE o_custkey = c_custkey) > 10000;
SELECT * FROM orders WHERE o_totalprice > (SELECT AVG(o_totalprice) FROM orders WHERE o_orderstatus = (SELECT o_orderstatus FROM orders WHERE o_orderdate = (SELECT MIN(o_orderdate) FROM orders)));
SELECT c_name, c_acctbal FROM customer WHERE c_acctbal > (SELECT AVG(c_acctbal) FROM customer WHERE c_nationkey = (SELECT c_nationkey FROM customer WHERE c_name = 'Customer1'));
SELECT * FROM lineitem WHERE l_orderkey IN (SELECT o_orderkey FROM orders WHERE o_custkey IN (SELECT c_custkey FROM customer WHERE c_acctbal > 1000));

-- Performance-related queries
SELECT * FROM lineitem WHERE l_shipdate > l_commitdate AND l_receiptdate > l_shipdate;
SELECT * FROM orders WHERE o_orderstatus = 'F' AND o_orderdate < o_shipdate;
SELECT c_name, c_acctbal FROM customer WHERE c_acctbal > 0 ORDER BY c_acctbal DESC LIMIT 10;
SELECT l_orderkey, SUM(l_extendedprice * (1 - l_discount)) AS net_price FROM lineitem GROUP BY l_orderkey ORDER BY net_price DESC LIMIT 20;
SELECT c_name, COUNT(*) AS order_count FROM customer JOIN orders ON c_custkey = o_custkey WHERE o_orderdate >= '1995-01-01' GROUP BY c_name HAVING COUNT(*) > 5 ORDER BY order_count DESC;
