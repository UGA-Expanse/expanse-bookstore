CREATE TABLE cart (
                      cart_id INT AUTO_INCREMENT,
                      cart_session_id VARCHAR(200),
                      cart_item_id INT,
                      CONSTRAINT pk_cart_id PRIMARY KEY (cart_id),
                      CONSTRAINT fk_cart_item FOREIGN KEY (cart_item_id) REFERENCES cart_item (cart_item_id),
                      CONSTRAINT uc_session UNIQUE (cart_session_id)
);