package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.util.*;

public class Room {
    private String id;
    private int type;
    private double price;
    //private String address;
    private int num;
    //private List<Live> lives;
    private List<Reserve> reserves;
    private Hostel hostel;
    private Timestamp startDate;
    private Timestamp endDate;
    private static final double BFMoney = 50.0;

    private Map<Timestamp, Timestamp> ReservedDates;
    //private List<Timestamp> Dates;

    public Room(String id, int type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
        //this.hostel=hostel;
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
        //if (!Objects.equals(address, room.address)) return false;

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


    @Override
    public String toString() {
        String print = "ID:" + getId() + "\nType:" + getType() + "\nPrice:" + getPrice() + "\nStart Date:" + getStartDate() + "\nEnd Date:" + getEndDate() + "\nReserves:" + getReserves()
                //+"\nHostel:"+getHostel()
                ;
        return print;
    }


}
