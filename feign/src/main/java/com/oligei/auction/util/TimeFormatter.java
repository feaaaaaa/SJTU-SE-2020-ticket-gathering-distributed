package com.oligei.auction.util;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeFormatter {

    public Timestamp strToTimestamp(String timeStr) {
        return Timestamp.valueOf(timeStr);
    }

    public String timestampToStr(Timestamp time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public Date strToDate(String dateStr) {
        return Date.valueOf(dateStr);
    }

    public String dateToStr(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public Timestamp currectTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
