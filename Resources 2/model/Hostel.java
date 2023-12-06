package edu.nju.hostelworld.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Hostel {
    private String hostelName;
    private String password;
    private List<Room> rooms;
    private String address;
    private double balance;
    private List<Activity> activity;

    public Hostel(String hostelName) {
        this.hostelName = hostelName;
        this.setRooms(new ArrayList<>());
        this.setActivity(new ArrayList<>());
        //this.InitialSetRoom();
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
        return Objects.equals(password, hostel.password);
    }

    @Override
    public String toString() {
        String print = "Names:" + getHostelName() + "\nAddress:" + getAddress() + "\nBalance:" + getBalance() + "\nRoom list:" + printRooms();
        return print;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostelName, password);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<String> printRooms() {
        List<String> RoomIdList = new ArrayList<String>();
        for (Room room : getRooms()) {
            String id = room.getId();
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

/*
    public void AddRoom(String id, int type, double price,
                        Timestamp start, Timestamp end) {
        Room room = new Room(id, type, price);
        room.setStartDate(start);
        room.setEndDate(end);

        List<Room> roomList = getRooms();
        roomList.add(room);
        setRooms(roomList);
    }


    public void InitialSetRoom() {
        AddRoom("room001", 0, 100.0, DateTrans.string2time("2023-12-01 00:00:00"), DateTrans.string2time("2024-01-01 00:00:00"));
        AddRoom("room002", 1, 200.0, DateTrans.string2time("2023-12-01 00:00:00"), DateTrans.string2time("2023-12-20 00:00:00"));
        AddRoom("room003", 2, 300.0, DateTrans.string2time("2023-12-01 00:00:00"), DateTrans.string2time("2023-12-10 00:00:00"));
    }
*/

    //1. 打印所有房间信息
    public void printAllRoomsInfo() {
        List<Room> rooms = getRooms();
        if (rooms != null && !rooms.isEmpty()) {
            for (Room room : rooms) {
                // 拼接房间信息为一行字符串
                StringBuilder roomInfo = new StringBuilder();
                roomInfo.append("Room ID: ").append(room.getId())
                        .append(", Type: ").append(room.getType())
                        .append(", Price: ").append(room.getPrice())
                        .append(", Start Date: ").append(room.getStartDate())
                        .append(", End Date: ").append(room.getEndDate());

                System.out.println(roomInfo.toString());
            }
        } else {
            System.out.println("No rooms available in this hostel.");
        }
    }

    //通过文档输出
    public static class RoomInformationReader {
        public static List<Room> readRoomInformation(String filePath) {
            List<Room> rooms = new ArrayList<>();

            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] roomInfo = line.split(",");

                    String id = roomInfo[0];
                    int type = Integer.parseInt(roomInfo[1]);
                    double price = Double.parseDouble(roomInfo[2]);

                    Room room = new Room(id, type, price);
                    rooms.add(room);
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("文件不存在或无法读取！");
                e.printStackTrace();
            }

            return rooms;
        }

        //读文档
        public static List<Room> readRoomInfoFromFile(String fileName) {
            List<Room> rooms = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String id = parts[0].trim();
                    int type = Integer.parseInt(parts[1].trim());
                    double price = Double.parseDouble(parts[2].trim());

                    Room room = new Room(id, type, price);
                    // 设置其他属性...

                    rooms.add(room);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return rooms;
        }

        //写文档
        public static void writeRoomInfoToDocument(List<Room> rooms, String fileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Room room : rooms) {
                    writer.write("ID: " + room.getId());
                    writer.newLine();
                    writer.write("Type: " + room.getType());
                    writer.newLine();
                    writer.write("Price: " + room.getPrice());
                    writer.newLine();
                    writer.write("Start Date: " + room.getStartDate());
                    writer.newLine();
                    writer.write("End Date: " + room.getEndDate());
                    writer.newLine();
                    // 写入其他属性...

                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}


