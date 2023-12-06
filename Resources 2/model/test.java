package edu.nju.hostelworld.model;

import java.util.List;
public class test {
    public static void main(String[] args) {
        // 创建 Hostel 实例和初始化房间列表
        Hostel hostel = new Hostel("Hostel Name");

        //String filePath = "/Users/ethon_lannister/XJTLU/oop403/cw2/hotel/Resources 2/room_info.txt";
        List<Room> rooms = Hostel.RoomInformationReader.readRoomInformation("room_info.txt");

        for (Room room : rooms) {
            System.out.print(room.toString());
        }

    }
}
