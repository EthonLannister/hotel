package edu.nju.hostelworld.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin {

    private String id;
    private String adname;
    private String password;
    private List<User> users;
    private static List<Admin> adminList = new ArrayList<>();
    static { // 定义三个静态管理员信息
        adminList.add(new Admin("ad01", "admin","123"));
        adminList.add(new Admin("ad02", "Alice","111"));
        adminList.add(new Admin("ad03", "Tom","456"));
    }

    public Admin(String id, String adname, String password) {
        this.id = id;
        this.adname = adname;
        this.password = password;
        this.users = new ArrayList<>();
    }
    public Admin(){}
    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static List<Admin> getAdminList() {
        return adminList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(adname, admin.adname) &&
                Objects.equals(password, admin.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, adname, password);
    }

}
