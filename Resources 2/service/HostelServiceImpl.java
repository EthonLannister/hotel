package edu.nju.hostelworld.service;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Room;
import edu.nju.hostelworld.model.Activity;

import java.util.List;
import java.sql.Timestamp;

public class HostelServiceImpl {
    public Hostel updateHostel(Hostel hostel){
        return hostel;
        // TODO 存入文档 hostel.save()
        //key 真的需要吗？
    }
    public Hostel InitiateHostel(String hostelName,String address,double balance) {
        Hostel hostel = new Hostel(hostelName);
        //hostel.setHostelName(hostelName);
        hostel.setAddress(address);
        hostel.setBalance(balance);
        setRoomHostel(hostel);

        //hostel.setStatus(0);
        //return new Hostel(hostelDao.save(hostel));
        return hostel;
        // TODO 存入文档 hostel.save()
    }

    public void setRoomHostel(Hostel hostel){
        List<Room> roomList=hostel.getRooms();
        for(Room room:roomList){
            room.setHostel(hostel);
        }
    }

    public Activity addActivity(Hostel hostel,Timestamp time,String activityName) {
        List<Activity> activityList=hostel.getActivities();
        Activity activity = new Activity(hostel,activityName,time);
        activityList.add(activity);
        return activity;
    }

    public void changeHostelName(Hostel hostel,String newName){
        hostel.setHostelName(newName);
    }

    public void changeHostelAddress(Hostel hostel,String newAddress){
        hostel.setAddress(newAddress);
    }

    public Hostel addBalance(Hostel hostel,double money){
        double balance=hostel.getBalance()+money;
        hostel.setBalance(balance);
        return hostel;
    }

    public Hostel reduceBalance(Hostel hostel,double money){
        double balance=hostel.getBalance()-money;
        hostel.setBalance(balance);
        return hostel;
    }

}
