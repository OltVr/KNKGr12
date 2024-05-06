create database Hotel;

use Hotel;

CREATE TABLE IF NOT EXISTS user (
email varchar(255) unique,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
salt varchar(50) NOT NULL,
passwordHash varchar(255) not Null,
isAdmin boolean NOT NULL DEFAULT 0,
CreatedAt datetime NOT NULL default NOW(),
PRIMARY KEY (email)
);


