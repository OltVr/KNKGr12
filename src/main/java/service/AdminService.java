package service;


import database.DatabaseUtil;
import model.Room;
import model.dto.InsertRoomDto;

import Repository.AdminRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    public static boolean addRoom(InsertRoomDto roomData){
       Room room=AdminRepository.getRoom(roomData.getRoomNumber(), roomData.getFloorNumber());
       if (room==null){
           return AdminRepository.insertRoom(roomData);
       }
       return false;
    }


}
