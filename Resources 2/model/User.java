package edu.nju.hostelworld.model;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class User {
    private String id;
    private String username;
    private String password;
    private int level;
    private double balance;

    private Admin assignedAdmin; // 用于保存分配的管理员信息
    private List<Reserve> reserves;
    private Map<Timestamp, Timestamp> ReservedDates;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.setReserves(new ArrayList<>());
        this.setReservedDates();
    }
    public User(String id, String username, String password, int status, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.balance = balance;
    }
    public User(){}

    public Admin getAssignedAdmin() {
        return assignedAdmin;
    }

    public void setAssignedAdmin(Admin admin) {
        this.assignedAdmin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

        User user = (User) o;

        if (id != user.id) return false;
        //if (status != user.status) return false;
        if (level != user.level) return false;
        if (Double.compare(user.balance, balance) != 0) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        //if (!Objects.equals(cardId, user.cardId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        String print = "ID:" + getId() + "\nNames:" + getUsername() + "\nLevel:" + getLevel() + "\nBalance:" + getBalance() + "\nReserves:" + getReserves();
        return print;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }
    //public int ReserveSize=getReserves().size();

    public void setReserves(List<Reserve> reservesById) {
        this.reserves = reservesById;
    }

    public List<Room> getRooms() {
        List<Room> ReserveRoomList = new ArrayList<>();
        for (Reserve reserve : getReserves()) {
            Room room = reserve.getRoom();
            ReserveRoomList.add(room);
        }
        return ReserveRoomList;
    }

    public void setReservedDates() {
        ReservedDates = new HashMap<Timestamp, Timestamp>();
        List<Reserve> resList = getReserves();
        //System.out.println("Reserves size: " + resList.size());
        for (Reserve res : resList) {
            //List<Timestamp> dates=res.getDates();
            Timestamp start = res.getStartDate();
            Timestamp end = res.getEndDate();
            //System.out.println("Start: " + start + ", End: " + end);
            ReservedDates.put(start, end);
        }
    }

    public Map<Timestamp, Timestamp> getReservedDates() {
        return ReservedDates;
    }

    public static class UserInformationReader {
        // 读取文档
        public static List<User> readUserInformation(String filePath) {
            List<User> users = new ArrayList<>();
            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    String userId = data[0].trim();
                    String userName = data[1].trim();
                    String password = data[2].trim();
                    int status = Integer.parseInt(data[3].trim());
                    double balance = Double.parseDouble(data[4].trim());
                    User user = new User(userId, userName, password, status, balance);
                    users.add(user);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("文件不存在或无法读取！");
                e.printStackTrace();
            }
            return users;
        }
        public void writeUserToDocument(List<User> users, String fileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for(User user : users){
                    writer.write(user.getId()+",");
                    writer.write(user.getUsername()+",");
                    writer.write(user.getPassword()+",");
                    writer.write(String.valueOf(user.getStatus()));
                    writer.write(String.valueOf(user.getBalance()));
                    writer.newLine();
                }
                System.out.println("客户信息已保存到文档" + fileName);
            } catch (IOException e) {
                System.out.println("保存文档时出错：" + e.getMessage());
            }
        }

    }

}
