package edu.nju.hostelworld.model;

/**
 * Created by yyy on 2017/3/26.
 */

public class DiscountStrategy{

    private static int DiscountNum=3;
    private static double DiscountRoomRate=0.8;

    private static double DiscountVIPRate=0.9;
    private static double VIPPrice=300.0;

    public static int getDiscountNum() {
        return DiscountNum;
    }

    public static double getDiscountRoomRate() {
        return DiscountRoomRate;
    }

    public static double getDiscountVIPRate() {
        return DiscountVIPRate;
    }

    public static double getVIPPrice() {
        return VIPPrice;
    }
}
