package edu.nju.hostelworld.model;

import edu.nju.hostelworld.service.HostelServiceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class test {
    public static void main(String[] args) {
/*        // 创建 Hostel 实例和初始化房间列表
        Hostel hostel = new Hostel("Hostel Name");

        //String filePath = "/Users/ethon_lannister/XJTLU/oop403/cw2/hotel/Resources 2/room_info.txt";
        List<Room> rooms = HostelServiceImpl.RoomInformationReader.readRoomInformation("room_info.txt");

        for (Room room : rooms) {
            System.out.print(room.toString());
        }*/
            User user = new User("004","qwe","456");
            Room room = new Room("005",2,150);
            Reserve reserve = new Reserve("1", LocalDate.now(), LocalDate.now().plusDays(5), 101, user, room, 1000, true);
            //reserve.printInfo();
        }
}
