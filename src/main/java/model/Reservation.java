package model;

import java.sql.Date;

public class Reservation {
    private int reservationID;
    private String email;
    private int roomNumber;
    private Date reservationDate;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;

    public Reservation(int reservationID, String email, int roomNumber, Date reservationDate, Date checkInDate, Date checkOutDate, double totalPrice) {
        this.reservationID = reservationID;
        this.email = email;
        this.roomNumber = roomNumber;
        this.reservationDate = reservationDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int getReservationID() {
        return reservationID;
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

    public double getTotalPrice() {
        return totalPrice;
    }
}
