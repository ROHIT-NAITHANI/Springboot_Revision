-- Seed users (idempotent)
INSERT INTO users (name, email)
SELECT 'Alice', 'alice@example.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'alice@example.com'
);

INSERT INTO users (name, email)
SELECT 'Bob', 'bob@example.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'bob@example.com'
);

INSERT INTO users (name, email)
SELECT 'Charlie', 'charlie@example.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'charlie@example.com'
);

-- Seed orders for Alice (idempotent)
INSERT INTO orders (product_name, user_id)
SELECT 'Keyboard', u.id
FROM users u
WHERE u.email = 'alice@example.com'
  AND NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
      AND o.product_name = 'Keyboard'
);

INSERT INTO orders (product_name, user_id)
SELECT 'Mouse', u.id
FROM users u
WHERE u.email = 'alice@example.com'
  AND NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
      AND o.product_name = 'Mouse'
);

-- Seed orders for Bob (idempotent)
INSERT INTO orders (product_name, user_id)
SELECT 'Laptop', u.id
FROM users u
WHERE u.email = 'bob@example.com'
  AND NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
      AND o.product_name = 'Laptop'
);

INSERT INTO orders (product_name, user_id)
SELECT 'Monitor', u.id
FROM users u
WHERE u.email = 'bob@example.com'
  AND NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
      AND o.product_name = 'Monitor'
);

-- Seed orders for Charlie (idempotent)
INSERT INTO orders (product_name, user_id)
SELECT 'Headphones', u.id
FROM users u
WHERE u.email = 'charlie@example.com'
  AND NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
      AND o.product_name = 'Headphones'
);