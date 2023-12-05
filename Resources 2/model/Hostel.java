package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.DateTrans;

import java.sql.Timestamp;
import java.util.*;
//import

public class Hostel {
    //private int id;
    private String hostelName;
    private String password;
    private List<Room> rooms;
    private String address;
    private double balance;
    private List<Activity> activity;

    //private Map<String, List<User>> activityMap;
    //private static int ActivityCapacity=2;

    public Hostel(String hostelName){
        this.hostelName = hostelName;
        //this.password = password;
        //rooms=new List<Room>();
        this.setRooms(new ArrayList<>());
        this.setActivity(new ArrayList<>());
        this.InitialSetRoom();
        //this.activityMap = new HashMap<>();
    }


    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

        Hostel hostel = (Hostel) o;

        //if (id != hostel.id) return false;
        if (!Objects.equals(hostelName, hostel.hostelName)) return false;
        if (!Objects.equals(password, hostel.password)) return false;

        return true;
    }
    @Override
    public String toString(){
        String print="Names:"+getHostelName() +"\nAddress:"+getAddress() +"\nBalance:"+getBalance() +"\nRoom list:"+printRooms();
        return print;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostelName,password);
    }

    public List<Room> getRooms() {
        return rooms;
    }
    public List<String> printRooms(){
        List<String> RoomIdList = new ArrayList<String>();
        for(Room room:getRooms()){
            String id=room.getId();
            RoomIdList.add(id);
        }
        return RoomIdList;
    }

    public void setRooms(List<Room> roomsById) {
        this.rooms = roomsById;
    }

    public List<Activity> getActivities() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public void AddRoom(String id, int type, double price,
                                Timestamp start, Timestamp end){
        Room room = new Room(id,type,price);
        room.setStartDate(start);
        room.setEndDate(end);

        //hostel.setRooms(room);
        List<Room> roomList=getRooms();
        roomList.add(room);
        setRooms(roomList);
        //return room;
        //todo 存入文件
    }

    /*List<Room> RoomList= new ArrayList<>(Arrays.asList(
            "room001",0,100.0,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2024-01-01 00:00:00")
    ));

     */
    public void InitialSetRoom(){
        //Hostel hostel=getHostel();
        AddRoom("room001",0,100.0,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2024-01-01 00:00:00"));
        AddRoom("room002",1,200.0,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2023-12-20 00:00:00"));
        AddRoom("room003",2,300.0,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"));
    }

    //1. 打印所有房间信息
    public void printAllRoomsInfo() {
        List<Room> rooms = getRooms();
        if (rooms != null && !rooms.isEmpty()) {
            for (Room room : rooms) {
                System.out.println(room.toString()); // 直接调用 Room 的 toString 方法
            }
        } else {
            System.out.println("No rooms available in this hostel.");
        }
    }

}
