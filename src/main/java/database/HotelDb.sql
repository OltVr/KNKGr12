create database Hotel;

use Hotel;

create table user (
email varchar(255) unique,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
salt varchar(50) NOT NULL,
passwordHash varchar(255) not Null,
isAdmin boolean NOT NULL DEFAULT 0)