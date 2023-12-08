package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Room {
    private String id;
    private int type;
    private double price;
    private int num;
    private List<Reserve> reserves;
    private Hostel hostel;
    private Timestamp startDate;
    private Timestamp endDate;
    private static final double BFMoney = 50.0;

    private Map<Timestamp, Timestamp> ReservedDates;

    public Room(String id, int type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.setReserves(new ArrayList<>());
        this.setReservedDates();
    }

    public  Room(String id, int type, double price, Timestamp startDate, Timestamp endDate){
        this.id = id;
        this.type = type;
        this.price = price;
        this.endDate = endDate;
        this.startDate = startDate;
        this.setReserves(new ArrayList<>());
        this.setReservedDates();
    }
    public String getId() {
        return id;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static double getBFMoney() {
        return BFMoney;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (type != room.type) return false;
        if (Double.compare(room.price, price) != 0) return false;
        if (num != room.num) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public List<Reserve> getReserves() {
        return reserves;
    }

    public void setReserves(List<Reserve> reservesById) {
        this.reserves = reservesById;
    }

    //public void setReservedDates
    public void setReservedDates() {
        ReservedDates = new HashMap<Timestamp, Timestamp>();
        List<Reserve> resList = getReserves();
        for (Reserve res : resList) {
            Timestamp start = res.getStartDate();
            Timestamp end = res.getEndDate();
            ReservedDates.put(start, end);
        }
    }

    public Map<Timestamp, Timestamp> getReservedDates() {
        return ReservedDates;
    }

    // 在 Room 类中添加一个新的方法，用于格式化时间
    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(timestamp);
        } else {
            return "null";
        }
    }


    // 修改 toString 方法，使用上面新添加的方法来格式化时间
    @Override
    public String toString() {
        return "ID: " + id +
                " Type: " + type +
                " Price: " + price +
                " Start Date: " + formatTimestamp(startDate) +
                " End Date: " + formatTimestamp(endDate) +
                " Reserves: " + reserves.toString() + "\n";
    }


}
