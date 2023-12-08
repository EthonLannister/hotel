package edu.nju.hostelworld.service;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Room;
import edu.nju.hostelworld.model.Activity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Scanner;

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
                    Timestamp startDate = null;
                    Timestamp endDate = null;

                    if (!roomInfo[3].equals("null")) {
                        startDate = Timestamp.valueOf(roomInfo[3]);
                    }

                    if (!roomInfo[4].equals("null")) {
                        endDate = Timestamp.valueOf(roomInfo[4]);
                    }

                    Room room = new Room(id, type, price, startDate, endDate);
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
