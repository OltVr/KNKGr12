package model.dto;

import java.sql.Date;

public class ReservationDto {
    private String email;
    private int roomNumber;
    private Date reservationDate;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfPeople;

    public ReservationDto(String email, int roomNumber, Date reservationDate, Date checkInDate, Date checkOutDate, int numberOfPeople) {
        this.email = email;
        this.roomNumber = roomNumber;
        this.reservationDate = reservationDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
    }

    public String getEmail() {
        return email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}
