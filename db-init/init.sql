-- This script runs automatically the very first time the PostgreSQL container starts.
-- It creates the three separate databases required by our microservices.
-- We do this because the standard postgres image only creates one database by default.

CREATE DATABASE ecommerce_user_db;
CREATE DATABASE ecommerce_product_db;
CREATE DATABASE ecommerce_order_db;
