package com.nexgensm.reswye.ui.lead;

public class LeadListingRecyclerDataAdapter {
    public String lead_name;
    public String lead_address;
    public String lead_time;
    public String lead_imageUrl, lead_imagename;
    public String buyername,buyneremail,buyermobile,viewedDate,buyerComments;
    public int lead_ID;
    public String lead_number;
    public String ImageTitleName;
    public String ID;


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

    public void setLead_imageUrl(String image) {
        this.lead_imageUrl = image;
    }

    public String getLead_imageUrl() {
        return lead_imageUrl;
    }

    public void setLead_imagename(String imageName) {
        this.lead_imagename = imageName;
    }

    public String getLead_imagename() {
        return lead_imagename;
    }


    public int getLead_ID() {
        return lead_ID;
    }

    public void setLead_ID(int id) {
        this.lead_ID = id;
    }

    public String getLead_number() {
        return lead_number;
    }

    public void setLead_number(String number) {
        this.lead_number = number;
    }

    public void setBuyername(String buyername){        this.buyername= buyername;    }

    public String getBuyername(){      return buyername;      }


    public String getViewedDate(){        return viewedDate;    }
    public void setViewedDate(String viewedDated){ this.viewedDate=viewedDated; }


    public String getBuyerComments(){   return buyerComments;    }
    public void setBuyerComments(String buyerComments){ this.buyerComments=buyerComments; }

    public String getBuyneremail(){  return buyneremail;     }
    public void setBuyeremail(String buyeremail){  this.buyneremail =buyeremail;    }

    public String getBuyermobile(){  return buyermobile;     }
    public void setBuyermobile(String buyermobile){  this.buyermobile =buyermobile;    }


    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }
}
