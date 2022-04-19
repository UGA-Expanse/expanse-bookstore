CREATE TABLE price_detail
(
    line_id         INT AUTO_INCREMENT,
    book_id         INT,
    list_price      DECIMAL(5, 2),
    price_currency  varchar(20),
    CONSTRAINT pk_orderline PRIMARY KEY (line_id),
    CONSTRAINT fk_prc_book FOREIGN KEY (book_id) REFERENCES book (book_id)
);
