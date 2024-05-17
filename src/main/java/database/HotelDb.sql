create database Hotel;

use Hotel;

CREATE TABLE IF NOT EXISTS user (
email varchar(255) not null primary key,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
salt varchar(50) NOT NULL,
passwordHash varchar(255) not Null,
isAdmin boolean NOT NULL DEFAULT 0,
CreatedAt datetime NOT NULL default NOW()
);

CREATE TABLE IF NOT EXISTS rooms (
    roomNumber INT NOT NULL PRIMARY KEY,
    floorNumber INT NOT NULL,
    roomType VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    bedNumber INT NOT NULL,
    price decimal(10,2) NOT NULL,
    isAvailable boolean NOT NULL DEFAULT 1,
    CONSTRAINT CHK_room_type CHECK (UPPER(roomType) IN ('SEA VIEW', 'CITY VIEW')),
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
totalPrice DECIMAL(10,2) NOT NULL,
FOREIGN KEY (email) REFERENCES user (email) on delete cascade,
FOREIGN KEY (roomNumber) REFERENCES rooms (roomNumber) on delete cascade,
CONSTRAINT CHK_check_out_after_check_in CHECK (checkOutDate >= checkInDate)
);
-- Editohet tabela me u shtu total price pa e bo drop databazen
ALTER TABLE reservation
CHANGE COLUMN numberOfPeople totalPrice DECIMAL(10, 2) NOT NULL;

-- DROP TABLE EDHE CREATE PRAP PER ME E NDREQ
CREATE TABLE IF NOT EXISTS staff (
    staffID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(255) NOT NULL,
    -- U shtu last name
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    position ENUM('General Manager', 'Receptionist', 'Waiter', 'Maintenance Technician') NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    isEmployed BOOLEAN NOT NULL DEFAULT TRUE,
    isFullTime BOOLEAN NOT NULL DEFAULT TRUE,
    hasBenefits BOOLEAN NOT NULL DEFAULT FALSE,
    createdAt DATETIME NOT NULL DEFAULT NOW() -- nuk osht ntabele
);

use hotel;
select*from staff;

-- Trigger qe e llogarit totalPrice para insertit ne reservim
DELIMITER //

CREATE TRIGGER calculate_total_price BEFORE INSERT ON reservation
FOR EACH ROW
BEGIN
    DECLARE num_days INT;
    DECLARE room_price DECIMAL(10, 2);

    -- Get the number of days between check-in and check-out dates
    SET num_days = DATEDIFF(NEW.checkOutDate, NEW.checkInDate);

    -- Get the room price based on the room number
    SELECT price INTO room_price FROM rooms WHERE roomNumber = NEW.roomNumber;

    -- Calculate the total price
    SET NEW.totalPrice = num_days * room_price;
END;
//

DELIMITER ;

-- Trigger qe e bon dhomen unavailable kur rezervohet
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

-- Funksion qe kqyr a ka kalu checkOutDate, nese po e bon dhomen available
delimiter //
CREATE EVENT check_checkout_dates
ON SCHEDULE EVERY 1 DAY
DO
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE roomNumber INT;
    DECLARE checkOutDate DATE;
    DECLARE cur CURSOR FOR SELECT roomNumber, checkOutDate FROM reservation WHERE checkOutDate <= CURDATE();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO roomNumber, checkOutDate;
        IF done THEN
            LEAVE read_loop;
        END IF;

        UPDATE rooms
        SET isAvailable = TRUE
        WHERE roomNumber = roomNumber;
    END LOOP;
    CLOSE cur;
END;
delimiter ;

-- Per me e kthy userin ne admin
update user set isAdmin=1 where email= 'email@email.com';


-- Insertim ne tabelen Reservation per testime
INSERT INTO reservation (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate)
VALUES ('11', 'trimmo@gmail.com', '4', '2024-05-11', '2024-05-12', '2024-05-14');
select*from reservation;
select*from rooms;

-- logtable
CREATE TABLE Deleted_Reservations (
    deleted_reservation_id INT AUTO_INCREMENT PRIMARY KEY,
	reservationID INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    roomNumber INT NOT NULL,
    reservationDate TIMESTAMP NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    totalPrice DECIMAL(10,2) NOT NULL,
    deletion_timestamp TIMESTAMP
);

use Hotel;
select*from Deleted_Reservations;

-- Ndryshohet edhe kjo
ALTER TABLE Deleted_Reservations
CHANGE COLUMN numberOfPeople totalPrice DECIMAL(10, 2) NOT NULL;

-- DUHET ME DROP EDHE ME BO PRAP SE KA NDRYSHIME
DROP TRIGGER IF EXISTS save_deleted_reservation_user;
-- triggerat me i shti rezervimet e fshime ne tabelen e mesiperme
DELIMITER //
CREATE TRIGGER save_deleted_reservation_user
BEFORE DELETE ON user
FOR EACH ROW
BEGIN
    INSERT INTO Deleted_Reservations (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, totalPrice, deletion_timestamp)
    SELECT reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, totalPrice, NOW()
    FROM reservation
    WHERE email = OLD.email;
END;
DELIMITER;

DROP TRIGGER IF EXISTS save_deleted_reservation_room;

DELIMITER //
CREATE TRIGGER save_deleted_reservation_room
BEFORE DELETE ON rooms
FOR EACH ROW
BEGIN
    INSERT INTO Deleted_Reservations (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, totalPrice, deletion_timestamp)
    SELECT reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, totalPrice, NOW()
    FROM reservation
    WHERE roomNumber = OLD.roomNumber;
END;
DELIMITER ;


