package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static edu.nju.hostelworld.model.Room.getBFMoney;
import static edu.nju.hostelworld.util.DateTrans.getDaysBetween;
import static edu.nju.hostelworld.util.DateTrans.time2String;

/**
 * Created by yyy on 2017/3/26.
 */
public class Reserve {
    private final String ReserveId;
    private Timestamp startDate;
    private Timestamp endDate;
    private int roomNum;
    private User user;
    private Room room;
    private double payMoney;
    //private double BFMoney=50.0;

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

    public String getReserveId() {
        return ReserveId;
    }

    /*
    public void setReserveId(String id) {
        this.ReserveId = id;
    }

     */
    //key 有必要吗？ReserveId 唯一，无需重设

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

}
