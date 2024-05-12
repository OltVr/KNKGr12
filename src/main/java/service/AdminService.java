package service;


import model.Room;
import model.dto.InsertRoomDto;

import Repository.AdminRepository;

public class AdminService {
    public static boolean addRoom(InsertRoomDto roomData){
       Room room=AdminRepository.getRoom(roomData.getRoomNumber(), roomData.getFloorNumber());
       if (room==null){
           return AdminRepository.insert(roomData);
       }
       return false;
    }
}
