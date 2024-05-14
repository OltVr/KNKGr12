create database Hotel;

use Hotel;

-- me u shtu constraints per email, pass.
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
    numberOfPeople INT NOT NULL,
    FOREIGN KEY (email) REFERENCES user (email) delete on cascade,
    FOREIGN KEY (roomNumber) REFERENCES rooms (roomNumber) delete on cascade,
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
--Funksion qe kqyr a ka kali checkOutDate
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


update user set isAdmin=1 where email= 'email@email.com';


ALTER TABLE rooms DROP CONSTRAINT CHK_room_type;
ALTER TABLE rooms ADD CONSTRAINT CHK_room_type CHECK (UPPER(roomType) IN ('SEA VIEW', 'CITY VIEW'));

//insertimi per drejtkendshin e  kuq te admindashboard dmth me kqyr sa rooms booked today
//po nese ne db te juj nuk figuron dhoma nr.13 edhe useri me imell si temen sju bon shkaku checkConstrains.
INSERT INTO reservation (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, numberOfPeople)
VALUES ('1', 'trimmo@gmail.com', '13', '2024-05-11', '2024-05-12', '2024-05-14', 2);

//logtable
CREATE TABLE Deleted_Reservations (
    deleted_reservation_id INT AUTO_INCREMENT PRIMARY KEY,
	reservationID INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    roomNumber INT NOT NULL,
    reservationDate TIMESTAMP NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    numberOfPeople INT NOT NULL,
    deletion_timestamp TIMESTAMP
);

//triggerat me i shti rezervimet e fshime ne tabelen e mesiperme
DELIMITER //
CREATE TRIGGER save_deleted_reservation_user
BEFORE DELETE ON user
FOR EACH ROW
BEGIN
    INSERT INTO Deleted_Reservations (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, numberOfPeople, deletion_timestamp)
    SELECT reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, numberOfPeople, NOW()
    FROM reservation
    WHERE email = OLD.email;
END;
DELIMITER;

DELIMITER //
CREATE TRIGGER save_deleted_reservation_room
BEFORE DELETE ON rooms
FOR EACH ROW
BEGIN
    INSERT INTO Deleted_Reservations (reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, numberOfPeople, deletion_timestamp)
    SELECT reservationID, email, roomNumber, reservationDate, checkInDate, checkOutDate, numberOfPeople, NOW()
    FROM reservation
    WHERE roomNumber = OLD.roomNumber;
END;
DELIMITER ;

