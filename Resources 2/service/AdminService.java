package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Admin;
import edu.nju.hostelworld.model.Reserve;
import edu.nju.hostelworld.model.Room;
import edu.nju.hostelworld.model.User;

import java.sql.Timestamp;

public interface AdminService {
    public User RegisterUser(String adminId, String userId, String username, String password);
    public void displayAssignedUsers(String adminId);
    public void viewRoomInfo(Room room);
    public void modifyRoomType(Room room, int newType);
    public void modifyRoomPrice(Room room, double newPrice);
    public void modifyRoomTime(Room room, Timestamp newStart, Timestamp newEnd);
    public void cancelUserReserve(User user, Reserve reserve);

}
