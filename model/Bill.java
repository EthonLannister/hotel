package edu.nju.hostelworld.model;


import java.util.Objects;

/**
 * Created by yyy on 2017/3/26.
 */

public class Bill {
    //private int id;
    private double money;
    //private Hostel hostel;

    public Bill(){}

    public Bill(double money,Hostel hostel){
        this.money = money;
        //this.hostel = hostel;
    }

/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 */

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        //if (id != bill.id) return false;
        if (Double.compare(bill.money, money) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }


    /*
    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostelByHostelId) {
        this.hostel = hostelByHostelId;
    }
     */
}
