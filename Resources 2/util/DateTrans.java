package edu.nju.hostelworld.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by yyy on 2017/3/27.
 */
public class DateTrans {

    public static Timestamp string2time(String dateStr){

        return Timestamp.valueOf(dateStr);
    }

    public static String time2String(Timestamp ts){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(ts);
    }
    public static long getDaysBetween(Timestamp timestamp1, Timestamp timestamp2) {
        LocalDateTime dateTime1 = timestamp1.toLocalDateTime();
        LocalDateTime dateTime2 = timestamp2.toLocalDateTime();
        return ChronoUnit.DAYS.between(dateTime1.toLocalDate(), dateTime2.toLocalDate());
    }
    public static boolean CheckTimestampOverlap(Timestamp start_time1,Timestamp end_time1,Timestamp start_time2,Timestamp end_time2){
        return (start_time1.compareTo(end_time2)<0 && start_time2.compareTo(end_time1)<0);
    }
    public static boolean isInTimeRange(Timestamp timeStampToValidate, Timestamp startTimestamp, Timestamp endTimestamp) {
        return timeStampToValidate.after(startTimestamp) && timeStampToValidate.before(endTimestamp);
    }
}
