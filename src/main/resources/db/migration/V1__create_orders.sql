CREATE TABLE orders (
                        id          UUID PRIMARY KEY,
                        customer_id VARCHAR(50)    NOT NULL,
                        status      VARCHAR(20)    NOT NULL,
                        total       NUMERIC(12, 2) NOT NULL,
                        created_at  TIMESTAMPTZ    NOT NULL
);

CREATE TABLE order_items (
                             id           UUID PRIMARY KEY,
                             order_id     UUID           NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
                             product_name VARCHAR(120)   NOT NULL,
                             quantity     INT            NOT NULL CHECK (quantity > 0),
                             unit_price   NUMERIC(12, 2) NOT NULL
);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);
CREATE INDEX idx_order_items_order_id ON order_items (order_id);