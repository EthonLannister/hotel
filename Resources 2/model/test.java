package edu.nju.hostelworld.model;

import edu.nju.hostelworld.service.AdminService;
import edu.nju.hostelworld.service.AdminServiceImpl;
import edu.nju.hostelworld.service.HostelServiceImpl;
import edu.nju.hostelworld.model.Activity.ActivityInformationReader;
import edu.nju.hostelworld.service.RoomServiceImpl;
import edu.nju.hostelworld.util.DateTrans;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class test {
    public static void main(String[] args) {
        Hostel hostel = new Hostel("Name");
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
        /*ads1.addActivity(hostel, "Swimming", DateTrans.string2time("2023-12-03 09:00:00"), 5); */
        // 打印活动信息以进行测试
        User user1 = new User("user1", "Jack", "111");
        rs1.addParticipant("Hiking", user1);
        //ads1.addActivity(hostel, "Biking", DateTrans.string2time("2023-12-13 09:00:00"), 5);
        //ads1.addActivity(hostel, "Hiking", DateTrans.string2time("2023-12-06 09:00:00"), 3);
        User.UserInformationReader reader1 = new User.UserInformationReader();
        List<User> userList = reader1.readUserInformation("user_credentials.txt");
        for (User user : userList) {
            System.out.print("Id: " + user.getId()+"  ");
            System.out.print("Name: " + user.getUsername()+"  ");
            System.out.print("Password: " + user.getPassword()+"  ");
            System.out.print("Status: " + user.getStatus()+"  ");
            System.out.print("Balance: "+user.getBalance()+"  ");
            System.out.println();
        }
        //User user2 = new User("user2", "Jack", "111",1,500);
        //rs1.addParticipant("Hiking", user2);
    }
}
