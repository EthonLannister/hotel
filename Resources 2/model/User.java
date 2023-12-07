package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.util.*;

public class User {
    private String id;
    private String username;
    private String password;
    private int level;
    private double balance;

    private Admin assignedAdmin; // 用于保存分配的管理员信息
    private List<Reserve> reserves;
    private Map<Timestamp, Timestamp> ReservedDates;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.setReserves(new ArrayList<>());
        this.setReservedDates();
        this.status = 0;
        //this.cardId = cardId;
    }
    public User(){}

    public Admin getAssignedAdmin() {
        return assignedAdmin;
    }

    public void setAssignedAdmin(Admin admin) {
        this.assignedAdmin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        //if (status != user.status) return false;
        if (level != user.level) return false;
        if (Double.compare(user.balance, balance) != 0) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        //if (!Objects.equals(cardId, user.cardId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        String print = "ID:" + getId() + "\nNames:" + getUsername() + "\nLevel:" + getLevel() + "\nBalance:" + getBalance() + "\nReserves:" + getReserves();
        return print;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }
    //public int ReserveSize=getReserves().size();

    public void setReserves(List<Reserve> reservesById) {
        this.reserves = reservesById;
    }

    public List<Room> getRooms() {
        List<Room> ReserveRoomList = new ArrayList<>();
        for (Reserve reserve : getReserves()) {
            Room room = reserve.getRoom();
            ReserveRoomList.add(room);
        }
        return ReserveRoomList;
    }

    public void setReservedDates() {
        ReservedDates = new HashMap<Timestamp, Timestamp>();
        List<Reserve> resList = getReserves();
        //System.out.println("Reserves size: " + resList.size());
        for (Reserve res : resList) {
            //List<Timestamp> dates=res.getDates();
            Timestamp start = res.getStartDate();
            Timestamp end = res.getEndDate();
            //System.out.println("Start: " + start + ", End: " + end);
            ReservedDates.put(start, end);
        }
    }

    public Map<Timestamp, Timestamp> getReservedDates() {
        return ReservedDates;
    }

}
