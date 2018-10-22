package com.wendy.instagramviewer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    public static String formatDateFromUnixTime(String unix_time){
        String[] splited = unix_time.split("\\.");
        String formatedDate;
        //convert unixtime to date
        if (!splited[0].equals("")) {
            Date date = new java.util.Date(Long.parseLong(splited[0]) * 1000L);
            // the format of your date
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            // give a timezone reference for formatting (see comment at the bottom)
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
            formatedDate = sdf.format(date);
        } else {
            formatedDate = "";
        }
        return formatedDate;
    }
}
