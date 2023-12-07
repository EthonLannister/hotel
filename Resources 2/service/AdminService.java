package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.*;

import java.sql.Timestamp;
import java.util.List;

public interface AdminService {
    public User RegisterUser(String adminId, String userId, String username, String password);
    public void displayAssignedUsers(String adminId);
    public void viewRoomInfo(Room room);
    public void modifyRoomType(Room room, int newType);
    public void modifyRoomPrice(Room room, double newPrice);
    public void modifyRoomTime(Room room, Timestamp newStart, Timestamp newEnd);
    public void cancelUserReserve(User user, Reserve reserve);
    public boolean addActivity(Hostel hostel, String actName, Timestamp actTime, int capacity, List<User> actUser);

}
