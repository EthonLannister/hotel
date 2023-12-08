package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.*;

import java.sql.Timestamp;
import java.util.*;

import static edu.nju.hostelworld.model.Activity.getCapacity;
import static edu.nju.hostelworld.util.DateTrans.getDaysBetween;
import static edu.nju.hostelworld.util.DateTrans.*;
import static edu.nju.hostelworld.model.DiscountStrategy.*;

public class RoomServiceImpl {

    // 根据房间ID和旅馆信息获取指定房间
    public Room getRoomById(Hostel hostel, String roomId) {
        List<Room> rooms = hostel.getRooms(); // 获取旅馆的所有房间列表
        for (Room room : rooms) { // 遍历房间列表
            if (Objects.equals(room.getId(), roomId)) { // 如果房间ID匹配
                return room; // 返回找到的房间
            }
        }
        return null; // 如果没有找到，返回null
    }


    // 根据房间类型和旅馆信息获取特定类型的所有房间
    public List<Room> getRoomByType(Hostel hostel, int type) {
        List<Room> AllRooms = hostel.getRooms(); // 获取旅馆的所有房间列表
        List<Room> newTypeRooms = new ArrayList<>(); // 创建新的列表存储匹配类型的房间
        for (Room room : AllRooms) { // 遍历房间列表
            if (room.getType() == type) { // 如果房间类型匹配
                newTypeRooms.add(room); // 添加到新列表中
            }
        }
        return newTypeRooms; // 返回匹配类型的房间列表
    }

    public Reserve reserve(String ReserveId, Room room, User user, Timestamp start, Timestamp end,
                           int roomNum, int IsBreakfast) {
        // 创建一个预订对象
        Reserve reserve = new Reserve(ReserveId, start, end, roomNum, user, room, IsBreakfast);

        // 调用UserService和HostelService
        UserServiceImpl userService = new UserServiceImpl();
        HostelServiceImpl hostelService = new HostelServiceImpl();

        // 获取房间所属的旅馆对象
        Hostel hostel = room.getHostel();

        // 检测房间和用户的预订时间是否冲突，并且检查结束时间是否在房间的开放时间范围内
        boolean roomConflict = ConflictRoomDateDetect(room, reserve);
        boolean userConflict = userService.ConflictUserDateDetect(user, reserve);
        if (!roomConflict && !userConflict && isInTimeRange(end, room.getStartDate(), room.getEndDate())) {
            // 计算预订的天数
            long days = getDaysBetween(start, end);

            // 计算支付金额
            double payMoney = days * reserve.getPayMoney();

            // 获取用户已有的预订数量
            int ReserveSize = user.getReserves().size();

            // 如果用户的预订数量满足折扣条件，则应用房间折扣
            if (ReserveSize % getDiscountNum() == (getDiscountNum() - 1)) {
                payMoney *= getDiscountRoomRate();
            }

            // 如果用户是VIP会员，则应用VIP折扣
            if (user.getStatus() == 1) {
                payMoney *= getDiscountVIPRate();
            }

            // 如果用户余额足够支付预订金额
            if (user.getBalance() >= payMoney) {
                // 设置预订的支付金额
                reserve.setPayMoney(payMoney);

                // 从用户余额中扣除支付金额
                userService.pay(user, payMoney);

                // 将支付金额添加到旅馆的余额中
                hostelService.addBalance(hostel, payMoney);

                // 生成账单
                generateBill(payMoney);

                // 减少房间的可用数量
                room.setNum(room.getNum() - roomNum);

                // 将预订添加到房间和用户的预订列表中
                AddRoomReserve(room, reserve);
                AddUserReserve(user, reserve);

                System.out.println("Order success!");

                // 返回预订对象
                return reserve;
            } else {
                System.out.println("Sorry, not enough money!");
                return null;
            }
        } else if (ConflictRoomDateDetect(room, reserve) || userService.ConflictUserDateDetect(user, reserve)) {
            System.out.println("\nSorry, time conflicts!");
            return null;
        } else {
            System.out.println("\nSorry, not open for reserve!");
            return null;
        }
        //todo 计算时间差
    }


    public void AddRoomReserve(Room room, Reserve reserve) {
        List<Reserve> roomReserveList = room.getReserves();
        roomReserveList.add(reserve);
        room.setReserves(roomReserveList);
        room.setReservedDates();
    }

    public void AddUserReserve(User user, Reserve reserve) {
        List<Reserve> userReserveList = user.getReserves();
        userReserveList.add(reserve);
        user.setReserves(userReserveList);
        user.setReservedDates();
    }

    public void cancelReserve(Reserve reserve) {
        //Reserve reserve = reserveDao.findById(reserveId);
        if (reserve != null) {
            UserServiceImpl userService = new UserServiceImpl();
            User user = reserve.getUser();
            Room room = reserve.getRoom();
            HostelServiceImpl hostelService = new HostelServiceImpl();
            Hostel hostel = room.getHostel();
            long days = getDaysBetween(reserve.getStartDate(), reserve.getEndDate());
            //返还一半的钱
            user = userService.addBalance(user, reserve.getPayMoney());
            hostel = hostelService.addBalance(hostel, reserve.getPayMoney());
            //更新待结算账单

            //Hostel hostel = room.getHostel();
            //generateBill( * reserve.getPayMoney());
            //客栈房间数增加
            room.setNum(room.getNum() + reserve.getRoomNum());
            RemoveRoomReserve(room, reserve);
            RemoveUserReserve(user, reserve);
        }
        //预订置为取消状态
        //reserve.setStatus(-1);
        //todo 写入文件
    }

    public void RemoveRoomReserve(Room room, Reserve reserve) {
        List<Reserve> roomReserveList = room.getReserves();
        roomReserveList.remove(reserve);
        room.setReserves(roomReserveList);
    }

    public void RemoveUserReserve(User user, Reserve reserve) {
        List<Reserve> userReserveList = user.getReserves();
        userReserveList.remove(reserve);
        user.setReserves(userReserveList);
    }

    //修正冲突检测逻辑
    public boolean ConflictRoomDateDetect(Room room, Reserve reserve) {
        for (Map.Entry<Timestamp, Timestamp> entry : room.getReservedDates().entrySet()) {
            if (CheckTimestampOverlap(entry.getKey(), entry.getValue(), reserve.getStartDate(), reserve.getEndDate())) {
                return true; // 只在发现冲突时返回true
            }
        }
        return false; // 如果没有冲突则返回false
    }


    public void GetReserveById(int reserveId) {

    }

    public Bill generateBill(double money) {
        Bill bill = new Bill();
        bill.setMoney(money);
        // 假设还有其他属性需要设置，例如时间戳等
        // bill.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return bill;
    }


    public Reserve changeReserveDate(Reserve reserve, Timestamp newStart, Timestamp newEnd) {
        String ReserveId = reserve.getReserveId();
        Room room = reserve.getRoom();
        User user = reserve.getUser();
        int roomNum = reserve.getRoomNum();
        int IsBreakfast = reserve.getIsBreakfast();
        Reserve newReserve = new Reserve(ReserveId, newStart, newEnd, roomNum, user, room, IsBreakfast);
        UserServiceImpl userService = new UserServiceImpl();
        if (!ConflictRoomDateDetect(room, newReserve) && !userService.ConflictUserDateDetect(user, newReserve)) {
            System.out.println("Change successful!");
            return newReserve;
        } else {
            System.out.println("Sorry,conflicting order, staying the original order");
            return reserve;
        }
        //Timestamp start=reserve.getStartDate();
        //Timestamp end=reserve.getEndDate();

        //Reserve new Reserve=
    }


    public Map<Room,Double> getUpgradeMap(Reserve reserve) {
        Map<Room,Double> upgradeMap=new HashMap<>();
        List<Integer> TypeList = new ArrayList<>(Arrays.asList(0, 1, 2));
        //List<Room> upgradeRooms = new ArrayList<>();
        //List<Double> ExtraMoney =new ArrayList<>();
        Hostel hostel = reserve.getRoom().getHostel();
        int typeflag = -1;
        int dateflag = -1;
        int currType = reserve.getRoom().getType();
        for (int type : TypeList) {
            if (type > currType) {
                typeflag = 1;
                List<Room> UpRooms = getRoomByType(hostel, type);
                for (Room uproom : UpRooms) {
                    if (isInTimeRange(reserve.getStartDate(), uproom.getStartDate(), uproom.getEndDate())
                            && isInTimeRange(reserve.getEndDate(), uproom.getStartDate(), uproom.getEndDate())) {
                        dateflag = 1;
                        double MoneyEachDay=uproom.getPrice()+uproom.getBFMoney()*reserve.getIsBreakfast();
                        long days=getDaysBetween(reserve.getStartDate(), reserve.getEndDate());
                        double TotalMoney=MoneyEachDay*days;
                        //upgradeRooms.add(uproom);
                        //ExtraMoney.add(TotalMoney);
                        upgradeMap.put(uproom,TotalMoney);
                        //double MoneyEachDay=
                        //System.out.println("New room price:"+UpRooms.get);
                        //System.out.println("Found rooms for upgrade!");
                        //return upgradeRooms;
                    }
                }
                //upgradeRooms.addAll(UpRooms);
            }
        }
        if (typeflag == 1 && dateflag == 1) {
            System.out.println("Found rooms for upgrade!");
        }
        if (typeflag == -1) {
            System.out.println("Sorry, all rooms are full during this period");
        }
        if (typeflag == 1 && dateflag == -1) {
            System.out.println("Sorry, already the top type");
        }
        return upgradeMap;
    }



    public Map<Room,Double> getDowngradeMap(Reserve reserve) {
        Map<Room,Double> downgradeMap=new HashMap<>();
        List<Integer> TypeList = new ArrayList<>(Arrays.asList(0, 1, 2));
        //List<Room> downgradeRooms = new ArrayList<>();
        Hostel hostel = reserve.getRoom().getHostel();
        //Hostel hostel=reserve
        int typeflag = -1;
        int dateflag = -1;
        int currType = reserve.getRoom().getType();
        for (int type : TypeList) {
            if (type < currType) {
                typeflag = 1;
                List<Room> DownRooms = getRoomByType(hostel, type);
                for (Room uproom : DownRooms) {
                    if (isInTimeRange(reserve.getStartDate(), uproom.getStartDate(), uproom.getEndDate())
                            && isInTimeRange(reserve.getEndDate(), uproom.getStartDate(), uproom.getEndDate())) {
                        dateflag = 1;
                        //downgradeRooms.addAll(DownRooms);
                        //System.out.println("Found rooms for downgrade!");
                        //return downgradeRooms;
                        double MoneyEachDay=uproom.getPrice()+uproom.getBFMoney()*reserve.getIsBreakfast();
                        long days=getDaysBetween(reserve.getStartDate(), reserve.getEndDate());
                        double TotalMoney=MoneyEachDay*days;
                        //upgradeRooms.add(uproom);
                        //ExtraMoney.add(TotalMoney);
                        downgradeMap.put(uproom,TotalMoney);
                    }
                }
                //upgradeRooms.addAll(UpRooms);
            }
        }
        if (typeflag == 1 && dateflag == 1) {
            System.out.println("Found rooms for downgrade!");
        }
        if (typeflag == -1) {
            System.out.println("Sorry, all rooms are full during this period");
        }
        if (typeflag == 1 && dateflag == -1) {
            System.out.println("Sorry, already the bottom type");
        }
        return downgradeMap;
    }

    public void addParticipant(Hostel hostel, String activityName, User user) {
        //Hostel hostel = new Hostel(hostelName);
        UserServiceImpl usi = new UserServiceImpl();
        List<Activity> activityList = hostel.getActivities();
        int flag = 0;
        for (Activity act : activityList) {
            if (Objects.equals(act.getActName(), activityName) && act.getActUser().size() < getCapacity() && !usi.IsDateInHotel(user, act.getActTime())) {
                act.addActUser(user);
                System.out.println("Activity successfully booked!");
                flag = 1;
            } else if (Objects.equals(act.getActName(), activityName) && act.getActUser().size() >= getCapacity()) {
                System.out.println("Sorry, this activity is full");
                flag = 1;
            } else if (Objects.equals(act.getActName(), activityName) && act.getActUser().size() < getCapacity() && usi.IsDateInHotel(user, act.getActTime())) {
                System.out.println("Sorry,you're not in hotel at this time");
                flag = 1;
            }
        }
        if (flag == 0) {
            System.out.println("Sorry,activity doesnt exist");
        }
    }


}
