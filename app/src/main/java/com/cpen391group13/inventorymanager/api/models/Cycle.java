package com.cpen391group13.inventorymanager.api.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Kevin on 3/7/2018.
 */

public class Cycle {
    private int id;
    @SerializedName("start_time")
    private Date startTime;
    @SerializedName("end_time")
    private Date endTime;

    public Cycle(int id, Date startTime, Date endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimeString() {
//        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = null;
//        try {
//            date = isoFormat.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(startTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
//                .format(date);
//        DateFormat df = new SimpleDateFormat("E dd MMM yyyy HH:mm:ss z");
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
//        return df.toString();
//        return startTime.toGMTString().replace("GMT", "");
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
                .format(this.startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimeString() {
//        return endTime.toGMTString().replace("GMT", "");
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
                .format(this.endTime);
    }
}
