package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.*;

import static edu.nju.hostelworld.model.Room.getBFMoney;
import static edu.nju.hostelworld.util.DateTrans.getDaysBetween;
import static edu.nju.hostelworld.util.DateTrans.time2String;

public class Reserve {
    private final String ReserveId;
    private Timestamp startDate;
    private Timestamp endDate;
    private int roomNum;
    private User user;
    private Room room;
    private double payMoney;

    private int IsBreakfast;

    public Reserve(String ReserveId,Timestamp start,Timestamp end,int roomNum,User user,Room room,int IsBreakfast){
        this.ReserveId = ReserveId;
        this.startDate = start;
        this.endDate = end;
        this.roomNum = roomNum;
        this.user = user;
        this.room = room;
        this.payMoney = room.getPrice()+getBFMoney()*IsBreakfast;
        //this.setDates(startDate,endDate);
    }

    //test构造方法
    public Reserve(String ReserveId, LocalDate startDate, LocalDate endDate, int roomNum, User user, Room room, int payMoney, boolean isBreakfast) {
        this.ReserveId = ReserveId;
        this.startDate = Timestamp.valueOf(startDate.atStartOfDay());
        this.endDate = Timestamp.valueOf(endDate.atStartOfDay());
        this.roomNum = roomNum;
        this.user = user;
        this.room = room;
        this.payMoney = room.getPrice() + getBFMoney() * (isBreakfast ? 1 : 0);
    }


    public String getReserveId() {
        return ReserveId;
    }

    /*
    public void setReserveId(String id) {
        this.ReserveId = id;
    }

     */

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp liveDate) {
        this.startDate = liveDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp leaveDate) {
        this.endDate = leaveDate;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public double getPayMoney(){
        return payMoney;
    }
    //public long days=getDaysBetween(getStartDate(),getEndDate());

    public void setPayMoney(double payMoney){
        this.payMoney = payMoney;
    }
    //public double getBFMoney(){return BFMoney;}
    public int getIsBreakfast(){
        return IsBreakfast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reserve reserve = (Reserve) o;

        if (ReserveId != reserve.ReserveId) return false;
        //if (status != reserve.status) return false;
        if (roomNum != reserve.roomNum) return false;
        if (!Objects.equals(startDate, reserve.startDate)) return false;
        if (!Objects.equals(endDate, reserve.endDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString(){
        //String print="ID:"+getReserveId()+"\nUser:"+getUser() +"\nRoom:"+getRoom() +"\nStartDate:"+time2String(getStartDate()) +"\nEndDate:"+time2String(getEndDate())+"\nMoney:"+getPayMoney();
        //String print="ID:"+getReserveId()+"\nStartDate:"+time2String(getStartDate()) +"\nEndDate:"+time2String(getEndDate());
        String print="ID:"+getReserveId();
        return print;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room roomByRoomId) {
        this.room = roomByRoomId;
    }


    public static class ReserveFileIO {

        private static final String FILE_PATH = "reserve_info.txt";

        public static void saveReserves(List<Reserve> reserves) {
            try (OutputStream fileOut = new FileOutputStream(FILE_PATH);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(reserves);
                System.out.println("Reserves saved successfully.");
            } catch (IOException e) {
                System.out.println("Failed to save reserves: " + e.getMessage());
            }
        }

        public static List<Reserve> loadReserves() {
            List<Reserve> reserves = new ArrayList<>();
            try (InputStream fileIn = new FileInputStream(FILE_PATH);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                reserves = (List<Reserve>) objectIn.readObject();
                System.out.println("Reserves loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load reserves: " + e.getMessage());
            }
            return reserves;
        }
    }




}
