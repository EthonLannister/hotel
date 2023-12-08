package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.*;

import java.sql.Timestamp;
import static edu.nju.hostelworld.util.DateTrans.getDaysBetween;
import static edu.nju.hostelworld.model.Activity.ActivityInformationReader;
import static edu.nju.hostelworld.util.DateTrans.*;
import java.util.Random;
import java.util.*;

public class AdminServiceImpl implements AdminService{
    private List<Admin> adminList;
    private List<User> userList;
    public AdminServiceImpl() {
        // 获取Admin类的三个管理员
        adminList = Admin.getAdminList();

        userList = new ArrayList<>();
    }

    public User RegisterUser(String adminId, String userId, String username, String password) {

        Admin admin = getAdminById(adminId);
        if (admin == null) {
            System.out.println("管理员不存在");
            return null;
        }
        // 创建新用户
        User newUser = new User(userId, username, password);

        // 客户随机分配给管理员，在User内添加属性
        Admin assignedAdmin = getRandomAdmin();
        newUser.setAssignedAdmin(assignedAdmin);

        // 将新用户添加到用户列表中
        userList.add(newUser);

        return newUser;
    }
    private Admin getAdminById(String adminId) {
        for (Admin admin : adminList) {
            if (admin.getId().equals(adminId)) {
                return admin;
            }
        }
        return null;
    }
    // 随机分配
    private Admin getRandomAdmin() {
        Random random = new Random();
        int index = random.nextInt(adminList.size());
        return adminList.get(index);
    }

    // 显示分配给管理员的客户信息
    public void displayAssignedUsers(String adminId) {
        Admin admin = getAdminById(adminId);
        if (admin == null) {
            System.out.println("管理员不存在");
            return;
        }
        List<User> assignedUsers = getAssignedUsers(admin);
        if (assignedUsers.isEmpty()) {
            System.out.println("没有分配给您的客户");
            return;
        }
        System.out.println("分配给管理员 " + adminId + " 的客户信息：");
        for (User user : assignedUsers) {
            System.out.println(user.toString());
            System.out.println("--------------------");
        }
    }
    private List<User> getAssignedUsers(Admin admin) {
        List<User> assignedUsers = new ArrayList<>();
        for (User user : userList) {
            if (user.getAssignedAdmin() != null && user.getAssignedAdmin().getId().equals(admin.getId())) {
                assignedUsers.add(user);
            }
        }
        return assignedUsers;
    }
    // 查看并修改房间信息
    public void viewRoomInfo(Room room) {
        System.out.println(room.toString());
    }
    public void modifyRoomType(Room room, int newType) {
        room.setType(newType);
        System.out.println("Room type has been modified successfully!");
    }
    public void modifyRoomPrice(Room room, double newPrice) {
        room.setPrice(newPrice);
        System.out.println("Room price has been modified successfully!");
    }
    public void modifyRoomTime(Room room, Timestamp newStart, Timestamp newEnd) {
        room.setStartDate(newStart);
        room.setEndDate(newEnd);
        System.out.println("Room time has been modified successfully!");
    }
    // 取消预订
    public void cancelUserReserve(User user, Reserve reserve) {
        UserServiceImpl userService = new UserServiceImpl();
        // 从用户预订列表中移除该预订记录
        List<Reserve> userReserves = user.getReserves();
        userReserves.remove(reserve);
        user.setReserves(userReserves);
        // 更新房间预订信息
        Room room = reserve.getRoom();
        List<Reserve> roomReserves = room.getReserves();
        roomReserves.remove(reserve);
        room.setReserves(roomReserves);
        room.setNum(room.getNum() + reserve.getRoomNum());
        // 返还一半的钱到用户账户余额中
        long days = getDaysBetween(reserve.getStartDate(), reserve.getEndDate());
        double refundMoney = reserve.getPayMoney() / 2;
        user = userService.addBalance(user, refundMoney);
    }
    // 添加活动
    public boolean addActivity(Hostel hostel, String actName, Timestamp actTime, int capacity) {
        HostelServiceImpl hostelService = new HostelServiceImpl();
        Activity activity = hostelService.addActivity(hostel, actName, actTime, capacity);
        if (activity == null) {
            System.out.println("活动添加失败！");
            return false;
        }
        ActivityInformationReader reader = new ActivityInformationReader();
        List<Activity> activities = reader.readActivityInformation("activity_info.txt");
        activities.add(activity);
        // 调用writeActivityToDocument方法，将活动信息保存到文档中
        reader.writeActivityToDocument(activities, "activity_info.txt");
        System.out.println("活动添加成功！");
        return true;
    }



}
