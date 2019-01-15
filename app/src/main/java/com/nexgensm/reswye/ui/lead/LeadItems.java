package com.nexgensm.reswye.ui.lead;

import java.io.Serializable;

/**
 * Created by nithu on 14/02/18.
 */

public class LeadItems implements Serializable {
    private String lead_name;
    private String lead_pic;
    private String lead_time;
    private String lead_address;
    private String lead_number;
    private  int lead_ID;
  private  int id;

//    public LeadItems(String image, String name1, String lead_CreatedDate, String address, int lead_ID) {
//    }

    public LeadItems(String lead_pic, String lead_name, String lead_time,String lead_address,int lead_ID) {
        this.lead_pic = lead_pic;
        this.lead_name = lead_name;
        this.lead_time = lead_time;
        this.lead_address = lead_address;
        this.lead_ID=lead_ID;
    }


    public int getLead_id() {
        return lead_ID   ;
    }
    public void setLead_id(int lead_ID) {
        this.lead_ID =lead_ID;
        this.id =lead_ID;
    }
    public String getLead_name() {
        return lead_name;
    }

    public void setLead_name(String lead_name) {
        this.lead_name =lead_name;
    }

    public String getLead_pic() {
        return lead_pic;
    }

    public void setLead_pic(String lead_pic) {
        this.lead_pic = lead_pic;
    }

    public String getLead_time() {
        return lead_time;
    }

    public void setLead_time(String lead_time) {
        this.lead_time = lead_time;
    }


    public String getLead_address() {
        return lead_address;
    }

    public void setLead_address(String lead_address) {
        this.lead_address = lead_address;
    }

    public String getLead_number(){
        return lead_number;
    }
    public void setLead_number(String lead_number)
    {
        this.lead_number = lead_number;
    }


}
