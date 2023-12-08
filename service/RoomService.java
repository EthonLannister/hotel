package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.*;


import java.sql.Timestamp;
import java.util.List;

public interface RoomService {
    Room getRoomById(Hostel hostel,int roomId);
    Room getRoomByType(Hostel hostel,int type);

    /**
     * 会员预订
     *
     * @param ReserveId
     * @param start     入住时间
     * @param end       退房时间
     * @param roomNum   房间数
     * @return {@link Reserve}
     */
    Reserve reserve(String ReserveId,Room room, User user, Timestamp start, Timestamp end,
                      int roomNum,int IsBreakfast);

    /**
     * 取消预约
     * @param reserveId 预约id
     */
    void cancelReserve(int reserveId);

    /**
     * 新增或更新hostel对客栈的待结算账单
     * @param money，为正数则增加金额，为负数则减少金额
     * @return
     */
    Bill generateBill(double money);

    List<Reserve> getReserveList(int status);

    //List<Reserve> getUserReserveList(int userId,int status);
    //todo 重构，reserve没有status了

    Reserve changeReserveDate(Reserve reserve,Timestamp newStart, Timestamp newEnd);

    List<Room> getUpgradeList(Hostel hostel,Reserve reserve);

    List<Room> getDowngradeList(Hostel hostel,Reserve reserve);
    void addParticipant(Hostel hostel,String activityName, User user);

    //Reserve Upgrade(Hostel hostel,Reserve reserve);
    //Reserve Downgrade(Hostel hostel,Reserve reserve);


}
