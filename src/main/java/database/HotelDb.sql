create database Hotel;

use Hotel;

-- me u shtu check constraints per email, pass.
CREATE TABLE IF NOT EXISTS user (
email varchar(255) not null primary key,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
salt varchar(50) NOT NULL,
passwordHash varchar(255) not Null,
isAdmin boolean NOT NULL DEFAULT 0,
CreatedAt datetime NOT NULL default NOW()
);

-- me u shtu check constraints per roomNumber, floorNumber, capacity, bedNumber, price.
CREATE TABLE IF NOT EXISTS room (
    roomNumber INT NOT NULL PRIMARY KEY,
    floorNumber INT NOT NULL,
    roomType VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    bedNumber INT NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    reservationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    roomNumber INT NOT NULL,
    reservationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    numberOfPeople INT NOT NULL,
    FOREIGN KEY (email) REFERENCES user (email),
    FOREIGN KEY (roomNumber) REFERENCES room (roomNumber)
);

