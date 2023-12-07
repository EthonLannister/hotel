package edu.nju.hostelworld.model;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity {
    private String ActName;
    private Timestamp ActTime;
    private List<User> ActUser;
    private static Hostel hostel;
    private int Capacity;

    public Activity(Hostel hostel, String actName, Timestamp actTime, int capacity, List<User> actUser) {
        this.hostel = hostel;
        this.ActName = actName;
        this.ActTime = actTime;
        this.Capacity=capacity;
        this.ActUser=actUser;
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

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        this.Capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity that = (Activity) o;
        return Objects.equals(ActName, that.ActName) && Objects.equals(ActTime, that.ActTime) &&
                Objects.equals(Capacity, that.Capacity) && Objects.equals(ActUser, that.ActUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ActName, ActTime, Capacity, ActUser);
    }

    @Override
    public String toString() {
        return "ActName:" + ActName +
                ", ActTime:" + ActTime +
                ", Capacity:"+Capacity+
                ", ActUser:" + ActUser;
    }


    public static class ActivityInformationReader {
        // 读取文档
        public static List<Activity> readActivityInformation(String filePath) {
            List<Activity> activities = new ArrayList<>();
            try {
                File file = new File(filePath);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    String actName = data[0].trim();
                    Timestamp actTime = convertToTimestamp(data[1].trim());
                    int capacity = Integer.parseInt(data[2].trim());
                    List<User> actUsers = new ArrayList<>();
                    if (data.length > 3) {
                        for (int i = 3; i < data.length; i++) {
                            String userId = data[i].trim();
                            User user = new User(userId, "",""); // 创建一个只含有用户id的User对象
                            actUsers.add(user);
                        }
                    }
                    Activity activity = new Activity(hostel, actName, actTime, capacity, actUsers);

                    activities.add(activity);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("文件不存在或无法读取！");
                e.printStackTrace();
            }
            return activities;
        }

        private static Timestamp convertToTimestamp(String dateString) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(dateString);
                return new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        // 将活动信息保存到名为activity_todo.txt的文件中
        public void writeActivityToDocument(List<Activity> activities, String fileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for(Activity activity : activities){
                    writer.write(activity.getActName()+",");
                    writer.write(activity.getActTime()+",");
                    writer.write(String.valueOf(activity.getCapacity()));
                    if (activity.getActUser() != null && !activity.getActUser().isEmpty()) {
                        for (User user : activity.getActUser()) {
                            writer.write(","+user.getId() );
                        }
                    }
                    writer.newLine();
                }
                System.out.println("活动信息已保存到文档" + fileName);
            } catch (IOException e) {
                System.out.println("保存文档时出错：" + e.getMessage());
            }
        }

    }
}
