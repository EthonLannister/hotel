package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Reserve;
import edu.nju.hostelworld.model.Room;
import edu.nju.hostelworld.model.User;

import java.sql.Timestamp;
import java.util.Map;

import static edu.nju.hostelworld.model.DiscountStrategy.getVIPPrice;
import static edu.nju.hostelworld.util.DateTrans.isInTimeRange;

public class UserServiceImpl {
    /*
    public User saveUser(User user) {
        if(!isUsernameExist(user.getUsername())&&!isCardExist(user.getCardId())){
            //return userDao.save(user);
            return user;
            //todo 存文件
        }
        return null;
    }
    public User updateUser(User user) {
        //return userDao.save(user);
        return user;
        //todo 存文件
    }

     */
    public User saveUser(String id,String username,String password){
        User user=new User(id,username,password);
        //user.setUsername(username);
        //user.setPassword(password);
        return user;
    }

    public User addBalance(User user,double money){
        double balance=user.getBalance()+money;
        user.setBalance(balance);
        return user;
    }

    public User changePwd(User user,String password){
        user.setPassword(password);
        return user;
    }

    public User pay(User user, double money) {
        if(user.getBalance()<money){
            return null;
        }
        user.setBalance(user.getBalance()-money);
        return user;
        //TODO 写入文件
    }

    public boolean ConflictUserDateDetect(User user, Reserve reserve){
        boolean flag=false;
        for(Map.Entry<Timestamp, Timestamp> entry : user.getReservedDates().entrySet()){
            //boolean flag=false;
            Timestamp start=entry.getKey();
            Timestamp end=entry.getValue();
            if (CheckTimestampOverlap(start,end,reserve.getStartDate(),reserve.getEndDate())){
                flag=true;
            }
        }
        //return CheckTimestampOverlap(start,end,reserve.getStartDate(),reserve.getEndDate());
        return flag;
    }
    public boolean CheckTimestampOverlap(Timestamp start_time1,Timestamp end_time1,Timestamp start_time2,Timestamp end_time2){
        return (start_time1.compareTo(end_time2)<0 && start_time2.compareTo(end_time1)<0);
    }

    public boolean IsDateInHotel(User user,Timestamp time){
        boolean flag=false;
        for (Reserve userRes:user.getReserves()){
            if(isInTimeRange(time,userRes.getStartDate(),userRes.getEndDate())){
                flag=true;
            }
        }
        return flag;
    }

    public void VIPTopUp(User user){
        pay(user,getVIPPrice());
        user.setStatus(1);
    }



}
