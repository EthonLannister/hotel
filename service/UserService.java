package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yyy on 2017/3/10.
 */
public interface UserService {

    //User saveUser(User user);

    //User updateUser(User user);

    //void deleteUser(String username);

    //User findUser(String username, String password);

    //User findUser(String username);

    //boolean isCardExist(String cardId);

    //User stopUser(String username);


/***********************************我是洗心革面的分割线*************************************************/
    /**
     * 新增一名用户
     * @param username 用户名
     * @param password 密码
     * @return {@link User}
     */
    User saveUser(String username,String password);

    /**
     * 会员充值
     * @param userId 用户id
     * @param topValue 充值金额
     * @return {@link User}
     */
    //User topUp(int userId,double topValue);
    //KEY can be replaced with addBalance()?
    //key 好吧是vip充值，不过打算自己写了

    /**
     * 积分兑换
     * @param userId 用户id
     * @param credit 要兑换的积分值
     * @return
     */
    //User changeCredit(int userId,int credit);
    //KEY 要实现吗？原文直接return null

    /**
     * 修改密码
     * @param password 新密码
     * @return
     */
    User changePwd(User user,String password);

    List<User> findAllUser();

    /**
     * 用户支付
     * @param user
     * @param money
     * @return
     */
    User pay(User user,double money);

    /**
     * 用户余额变化
     * @param user
     * @param money
     * @return
     */
    User addBalance(User user,double money);

    /**
     * 通过id查找用户
     * @param userId
     * @return
     */
    User findUserById(int userId);

    boolean IsDateInHotel(User user,Timestamp time);

    void VIPTopUp(User user);

}
