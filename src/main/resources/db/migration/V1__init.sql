create table limits(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number INT NOT NULL,
    expense_category ENUM('PRODUCT', 'SERVICE') NOT NULL,
    limit_usd DECIMAL(8,2) NOT NULL DEFAULT 1000.00,
    creation_date DATETIME NOT NULL,
    remains_before_exceed DECIMAL(8,2) NOT NULL,
    update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table transactions(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     account_num_from BIGINT NOT NULL,
     account_num_to BIGINT NOT NULL,
     expense_category ENUM('PRODUCT', 'SERVICE') NOT NULL,
     amount DECIMAL(8,2),
     currency ENUM('KZT', 'RUB', 'USD'),
     date_time DATETIME NOT NULL,
     limit_exceeded BOOLEAN NOT NULL
);

create table exchange_rate(
      currency_from ENUM('KZT', 'RUB', 'USD') NOT NULL,
      currency_to ENUM('KZT', 'RUB', 'USD') NOT NULL,
      rate DECIMAL(8, 2) NOT NULL,
      date_of_update DATETIME NOT NULL,
      PRIMARY KEY (currency_from, currency_to)
);