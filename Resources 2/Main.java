package edu.nju.hostelworld;

import edu.nju.hostelworld.model.*;
import edu.nju.hostelworld.service.*;


import edu.nju.hostelworld.util.DateTrans;

public class Main {
    public static void main(String[] args) {
      // 修改一下
     //key 测试旅馆创建
        HostelServiceImpl hs1=new HostelServiceImpl();
        Hostel hostelTest=hs1.InitiateHostel("Garden hotel","earth",0.0);
        //todo 此处println输出旅馆信息
        System.out.println(hostelTest);


        //key 测试用户帐户

        UserServiceImpl us1=new UserServiceImpl();
        User user1=us1.saveUser("usr001","Ann","ab12");
        User user2=us1.saveUser("usr002","Bob","cd34");
        User user3=us1.saveUser("usr003","Char","ef56");
        System.out.println(user1);
        System.out.println(user2);
        us1.addBalance(user1,2000.0);
        us1.addBalance(user2,3000.0);
        us1.addBalance(user3,1500.0);
        user1.setLevel(0);
        user2.setLevel(0);
        user3.setLevel(0);
        user1.setPassword("ab123");
        user2.setPassword("cd456");
        user3.setPassword("ef789");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        //todo 此处println输出用户信息


        //key 测试房间预定退订
        //Room room1=releaseRoomPlan("Garden",0,100.0,)
        RoomServiceImpl rm1=new RoomServiceImpl();
        //Timestamp start=

        Room room1=rm1.getRoomById(hostelTest,"room001");
        Room room2=rm1.getRoomById(hostelTest,"room002");
        Room room3=rm1.getRoomById(hostelTest,"room003");
        System.out.println(hostelTest);


        Reserve rs1=rm1.reserve("rs001",room1,user1,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2023-12-05 00:00:00"),1,1);
        Reserve rs2=rm1.reserve("rs002",room2,user1,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"),1,1);
        Reserve rs3=rm1.reserve("rs003",room3,user2,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2023-12-05 00:00:00"),1,1);
        System.out.println("\n");
        System.out.println(user1);
        System.out.println(user2);
        //System.out.println(user2);

        System.out.println(room1);
        System.out.println(room2);
        System.out.println(room3);

        //todo 此处pringln查看旅馆预订和用户预订情况

        rm1.cancelReserve(rs1);
        //todo 此处pringln再次查看旅馆预订和用户预订情况
        System.out.println("\n");
        System.out.println(hostelTest);
        System.out.println(user1);
        System.out.println(user2);

        System.out.println(room1);
        System.out.println(room2);
        System.out.println(room3);


        Reserve rs4=rm1.reserve("rs004",room2,user2,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"),1,0);
        Reserve rs5=rm1.reserve("rs005",room1,user1,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"),1,0);
        System.out.println("\n");
        System.out.println(hostelTest);
        System.out.println(user1);
        System.out.println(user2);

        System.out.println(room1);
        System.out.println(room2);
        System.out.println(room3);

        //key 检测修改预订
        Reserve rs6=rm1.reserve("rs006",room1,user3,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"),1,0);
        Reserve rs7=rm1.changeReserveDate(rs1,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"));
        Reserve rs8=rm1.changeReserveDate(rs2,DateTrans.string2time("2023-12-01 00:00:00"),DateTrans.string2time("2023-12-05 00:00:00"));
        Reserve rs9=rm1.changeReserveDate(rs3,DateTrans.string2time("2023-12-06 00:00:00"),DateTrans.string2time("2023-12-10 00:00:00"));

        System.out.println("\n");
        System.out.println(rs1);
        System.out.println(rs2);
        System.out.println(rs3);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);

        //key 测试打折和检测钱是否够
        Reserve rs10=rm1.reserve("rs007",room1,user1,DateTrans.string2time("2023-12-16 00:00:00"),DateTrans.string2time("2023-12-20 00:00:00"),1,0);
        System.out.println("\n");
        System.out.println(user1);
        Reserve rs11=rm1.reserve("rs008",room1,user1,DateTrans.string2time("2023-12-11 00:00:00"),DateTrans.string2time("2023-12-15 00:00:00"),1,0);
        //System.out.println("\n");
        System.out.println(user1);
        System.out.println(room1);
        System.out.println("\n");

        //key 测试活动
        Activity ac1=hs1.addActivity(hostelTest,DateTrans.string2time("2023-12-01 08:00:00"),"Hiking");
        Activity ac2=hs1.addActivity(hostelTest,DateTrans.string2time("2023-12-01 08:00:00"),"Biking");
        rm1.addParticipant(hostelTest,"Hiking",user1);
        rm1.addParticipant(hostelTest,"Hiking",user2);
        rm1.addParticipant(hostelTest,"Hiking",user3);
        rm1.addParticipant(hostelTest,"Ciking",user3);
        rm1.addParticipant(hostelTest,"Biking",user3);
        System.out.println("\n");
        System.out.println(ac1);
        System.out.println(ac2);
        //todo 增加日期校验
        //key 写好了

        //key 检测VIP充值
        System.out.println("\n");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        us1.VIPTopUp(user2);
        System.out.println(user2);
        Reserve rs12=rm1.reserve("rs009",room2,user2,DateTrans.string2time("2023-12-11 00:00:00"),DateTrans.string2time("2023-12-15 00:00:00"),1,0);
        System.out.println(user2);
        rm1.cancelReserve(rs12);
        System.out.println(user2);
        //key 检测不在预订时间
        //System.out.println("\n");
        Reserve rs13=rm1.reserve("rs010",room3,user2,DateTrans.string2time("2023-12-09 00:00:00"),DateTrans.string2time("2023-12-11 00:00:00"),1,0);


        //key 检测升级降级
        System.out.println("\n");
        System.out.println(rm1.getUpgradeList(rs6));
        System.out.println(rm1.getDowngradeList(rs6));
        System.out.println(rm1.getUpgradeList(rs8));
        System.out.println(rm1.getDowngradeList(rs8));

        //key 测试新的旅馆构造函数

        hs1.changeHostelName(hostelTest,"Garden");
        hs1.changeHostelAddress(hostelTest,"China");
        System.out.println("\n");
        System.out.println(hostelTest);
        room1=rm1.getRoomById(hostelTest,"room001");
        System.out.println("\n");
        System.out.println(room1);

    }
}
