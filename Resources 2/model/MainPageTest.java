package edu.nju.hostelworld.model;

import edu.nju.hostelworld.service.AdminService;
import edu.nju.hostelworld.service.AdminServiceImpl;
import edu.nju.hostelworld.service.HostelServiceImpl;
import edu.nju.hostelworld.service.RoomServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainPageTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String userType = "";
        while (!loggedIn) {
            System.out.println("请选择您的身份（1-用户，2-管理员）：");
            userType = scanner.nextLine();

            if (userType.equals("1") || userType.equals("2")) {
                loggedIn = true;
            } else {
                System.out.println("无效的选择！");
            }
        }
        while (true) {
            System.out.println("请输入您的ID：");
            String userId = scanner.nextLine();
            System.out.println("请输入您的密码：");
            String password = scanner.nextLine();

            // 读取用户信息文件进行验证
            String userCredentialsFile = (userType.equals("1")) ? "user_credentials.txt" : "admin_credentials.txt";
            boolean userFound = false;
            String foundUsername = "";
            int foundLevel = 0;
            double foundBalance = 0.0;

            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(userCredentialsFile));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(",");
                    String id = data[0];
                    String userPassword = data[2];

                    if (userId.equals(id) && password.equals(userPassword)) {
                        userFound = true;
                        foundUsername = data[1];
                        foundLevel = Integer.parseInt(data[3]);
                        foundBalance = Double.parseDouble(data[4]);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (userFound) {
                System.out.println("欢迎，" + ((userType.equals("1")) ? "用户" : "管理员") + " " + foundUsername + "！");
                if (userType.equals("1")) {
                    // 创建User对象并将属性值赋给它
                    User thisUser = new User();
                    thisUser.setId(userId);
                    thisUser.setUsername(foundUsername);
                    thisUser.setPassword(password);
                    thisUser.setLevel(foundLevel);
                    thisUser.setBalance(foundBalance);
                    showUserMenu(scanner, thisUser);
                } else {
                    //创建Admin对象并将属性赋值给它
                    //ToDo 管理员界面的操作都通过thisAdmin对象来
                    Admin thisAdmin = new Admin();
                    showAdminMenu(scanner, thisAdmin);
                }
                break; // 登录成功，跳出循环
            } else {
                System.out.println("无效的ID或密码，请重试。");
            }
        }
    }

    //显示用户操作界面
    public static void showUserMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("请选择操作：");
            System.out.println("1. 预定酒店");
            System.out.println("2. 查看个人信息");
            System.out.println("3. 查看预定记录");
            System.out.println("4. 参加活动");
            System.out.println("5. 退出");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Please enter the reservation time：");
                    LocalDate startDate = getUserInputDate("Start date (YYYY-MM-DD): ");
                    LocalDate endDate = getUserInputDate("End date (YYYY-MM-DD): ");
                    System.out.println("Please enter the level of the room(1-$200,2-$400,3-$600):");
                    int printLevel = scanner.nextInt();
                    hotelReservation(startDate, endDate, printLevel);

                    break;
                case 2:
                    System.out.println("进入个人信息页面。");
                    break;
                case 3:
                    System.out.println("进入预订记录页面。");
                    break;
                case 4:
                    System.out.println("进入参加活动页面。");
                    AdminService ads1 = new AdminServiceImpl();
                    RoomServiceImpl rs1 = new RoomServiceImpl();
                    Activity.ActivityInformationReader reader = new Activity.ActivityInformationReader();
                    List<Activity> activityList = reader.readActivityInformation("activity_info.txt");
                    // 打印活动信息以进行测试
                    for (Activity activity : activityList) {
                        System.out.print("Activity Name: " + activity.getActName()+"  ");
                        System.out.print("Activity Time: " + activity.getActTime()+"  ");
                        System.out.print("Capacity: " + activity.getCapacity()+"  ");
                        System.out.print("Participants: ");
                        for (User user1 : activity.getActUser()) {
                            System.out.print(" "+user1.getId());
                        }
                        System.out.println();
                    }
                    System.out.println("Please enter the activity name: ");
                    String actName = scanner.next();
                    rs1.addParticipant(actName, user);
                    return;
                case 5:
                    return;
                default:
                    System.out.println("输入无效，请重新选择。");
            }
        }
    }

    //显示管理员页面
    public static void showAdminMenu(Scanner scanner, Admin admin) {
        while (true) {
            System.out.println("请选择操作：");
            System.out.println("1. 酒店房间");
            System.out.println("2. 个人信息");
            System.out.println("3. 用户信息");
            System.out.println("4. 退出");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("进入酒店房间页面。");
                    break;
                case 2:
                    System.out.println("进入个人信息页面。");
                    break;
                case 3:
                    System.out.println("进入用户信息页面。");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("输入无效，请重新选择。");
            }
        }
    }

    public static void showAllRooms() {
        List<Room> rooms = HostelServiceImpl.RoomInformationReader.readRoomInformation("room_info.txt");
        for (Room room : rooms) {
            System.out.print(room.toString());
        }
    }

    public static void hotelReservation(LocalDate startDate, LocalDate endDate, int printLevel) {
        String roomListFile = "room_info.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(roomListFile))) {
            String line;
            boolean foundAvailableRoom = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] roomData = line.split(",");
                int roomId = Integer.parseInt(roomData[0]);
                int roomType = Integer.parseInt(roomData[1]);
                double roomPrice = Double.parseDouble(roomData[2]);
                LocalDate roomStartDate = LocalDate.parse(roomData[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDate roomEndDate = LocalDate.parse(roomData[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String roomAddress = roomData[5];
                String roomLocation = roomData[6];

                if (startDate.isAfter(roomStartDate) && endDate.isBefore(roomEndDate) && printLevel == roomType) {
                    foundAvailableRoom = true;
                    System.out.println("Available Room - Room ID: " + roomId);
                    System.out.println("Start Date: " + roomStartDate);
                    System.out.println("End Date: " + roomEndDate);
                    System.out.println("Price: " + roomPrice);
                    System.out.println("Address: " + roomAddress);
                    System.out.println("Location: " + roomLocation);
                    System.out.println();
                }
            }

            if (!foundAvailableRoom) {
                System.out.println("No available rooms for the selected dates.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static LocalDate getUserInputDate(String message) {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        while (date == null) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                if (input.contains("-")) {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else if (input.contains("/")) {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                } else {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy MM dd"));
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return date;
    }

}

