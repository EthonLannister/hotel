package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Room;
import edu.nju.hostelworld.model.User;

import java.util.List;

/**
 * Created by yyy on 2017/3/19.
 */
public interface HostelService {

    Hostel updateHostel(Hostel hostel);

    /**
     * 修改客栈信息
     * @param hostelName 客栈名称
     * @param address 地址
     * @return
     */
    Hostel InitiateHostel(String hostelName,String address,double balance);
    void setRoomHostel(Hostel hostel);

    void addActivity(Hostel hostel,String activityName);

    void changeHostelName(Hostel hostel,String newName);

    void changeHostelAddress(Hostel hostel,String newAddress);
    Hostel addBalance(Hostel hostel,double money);
    Hostel reduceBalance(Hostel hostel,double money);

}
