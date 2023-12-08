package edu.nju.hostelworld.model;

import java.sql.Timestamp;
import java.util.*;

public class Activity {
    //private Map<String, List<User>> activityMap;
    private String ActName;
    private Timestamp ActTime;
    private List<User> ActUser;
    private Hostel hostel;
    private static int Capacity=2;

    public Activity(Hostel hostel,String actName, Timestamp actTime) {
        this.ActName = actName;
        this.ActTime = actTime;
        this.hostel=hostel;
        //ActUser = actUser;
        this.ActUser=new ArrayList<>();
    }

    public String getActName() {
        return ActName;
    }

    public void setActName(String actName) {
        ActName = actName;
    }

    public Timestamp getActTime() {
        return ActTime;
    }

    public void setActTime(Timestamp actTime) {
        ActTime = actTime;
    }

    public List<User> getActUser() {
        return ActUser;
    }

    public void setActUser(List<User> actUser) {
        ActUser = actUser;
    }
    public void addActUser(User actUser){
        List<User> actList=getActUser();
        actList.add(actUser);
        setActUser(actList);
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public static int getCapacity() {
        return Capacity;
    }

    public static void setCapacity(int capacity) {
        Capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity that = (Activity) o;
        return Objects.equals(ActName, that.ActName) && Objects.equals(ActTime, that.ActTime) && Objects.equals(ActUser, that.ActUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ActName, ActTime, ActUser);
    }

    @Override
    public String toString() {
        return "Activities{" +
                "ActName='" + ActName + '\'' +
                ", ActTime=" + ActTime +
                ", ActUser=" + ActUser +
                '}';
    }
}
