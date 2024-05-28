DROP TABLE IF EXISTS "dgs-accounts".account;
CREATE TABLE "dgs-accounts".account (
    account_id INTEGER PRIMARY KEY,
    account_type VARCHAR(255),
    account_number VARCHAR(255),
    status VARCHAR(255),
    balance FLOAT,
    currency VARCHAR(255),
    last_activity_date TIMESTAMP
);

create table "dgs-accounts".client (
    account_id integer not null,
    client_id integer not null,
    country varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    middle_name varchar(255),
    primary key (account_id, client_id))