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
CREATE TABLE IF NOT EXISTS rooms (
    roomNumber INT NOT NULL PRIMARY KEY,
    floorNumber INT NOT NULL,
    roomType VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    bedNumber INT NOT NULL,
    price decimal(10,2) NOT NULL,
    isAvailable boolean NOT NULL DEFAULT 1,
    CONSTRAINT CHK_floor_number CHECK (floorNumber >= 1),
    CONSTRAINT CHK_room_number CHECK (roomNumber >= 1),
    CONSTRAINT CHK_bed_number CHECK (bedNumber >= 1),
    CONSTRAINT CHK_capacity CHECK (capacity >= 1),
    CONSTRAINT CHK_price CHECK (price > 0.00)
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
    FOREIGN KEY (roomNumber) REFERENCES rooms (roomNumber),
     CONSTRAINT CHK_check_out_after_check_in
        CHECK (checkOutDate >= checkInDate)
);

Delimiter //
CREATE TRIGGER reservation_made_trigger
AFTER INSERT ON reservation
FOR EACH ROW
BEGIN
    UPDATE rooms
    SET isAvailable = FALSE
    WHERE roomNumber = NEW.roomNumber;
END;
delimiter ;
