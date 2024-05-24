package service;

import Repository.ReservationRepository;
import model.Reservation;

import java.util.List;

public class ReserveService {
    public static List<Reservation> getReservationsForRoom(int roomNumber) {
        return ReservationRepository.getReservationsForRoom(roomNumber);
    }
}
