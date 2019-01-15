package com.nexgensm.reswye.ui.calendar;

/**
 * Created by nithu on 12/03/18.
 */

public class CalItem {
    private String purpose;
    private String place;
    private String time;

    CalItem(String purpose,String place,String time)
    {
        this.purpose = purpose;
        this.place=place;
        this.time = time;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
