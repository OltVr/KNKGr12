package service;


import model.Room;
import model.dto.InsertRoomDto;

import repository.UserRepository;

public class AdminService {
    public static boolean addRoom(InsertRoomDto roomData){
       Room room=UserRepository.getRoom(roomData.getRoomNumber(), roomData.getFloorNumber());
       if (room==null){
           return UserRepository.insert(roomData);
       }
       return false;
    }
}
