package com.nexgensm.reswye.ui.calendar;

public class AppointmentListingRecyclerDataAdapter {
    public String lead_name;
    public String lead_address;
    public String lead_time;
    public String lead_imageUrl, lead_imagename;
    public int lead_ID;
    public String getLead_name() {
        return lead_name;
    }
    public void setLead_name(String name) {
        this.lead_name = name;
    }

    public String getLead_address() {
        return lead_address;
    }

    public void setLead_address(String address) {
        this.lead_address = address;
    }

    public String getLead_time() {
        return lead_time;
    }

    public void setLead_time(String time) {
        this.lead_time = time;
    }

    public String getLead_imageUrl() {
        return lead_imageUrl;
    }
    public void setLead_imageUrl(String image) {
        this.lead_imageUrl = image;
    }

    public int getLead_ID() {
        return lead_ID;
    }

    public void setLead_ID(int id) {
        this.lead_ID = id;
    }

}