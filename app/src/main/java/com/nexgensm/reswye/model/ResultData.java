package com.nexgensm.reswye.model;

import com.google.gson.annotations.SerializedName;

public class ResultData
{
    private String lead_createddate;

    private String leadprofileimage;

    private String firstname;

    private String address;

    private String lead_statusid;

    private String transfered_status;

    private String gender;

    private String lead_updateddate;

    private String hwfindabtus;

    private String emailid;

    private String mobileno;

    private String isdeleted;

    private String lastname;

    private String user_id;

    private String lead_prospect;

    private String lead_warmth;

    private String lead_category;

    private String lead_id;


    @SerializedName("id")
    private int id;

    @SerializedName("features_characteristics")
    private String features_characteristics;

    @SerializedName("fid_cid")
    private int fid_cid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatures_characteristics() {
        return features_characteristics;
    }

    public void setFeatures_characteristics(String features_characteristics) {
        this.features_characteristics = features_characteristics;
    }

    public int getFid_cid() {
        return fid_cid;
    }

    public void setFid_cid(int fid_cid) {
        this.fid_cid = fid_cid;
    }

    public String getLead_createddate ()
{
    return lead_createddate;
}

    public void setLead_createddate (String lead_createddate)
    {
        this.lead_createddate = lead_createddate;
    }

    public String getLeadprofileimage ()
    {
        return leadprofileimage;
    }

    public void setLeadprofileimage (String leadprofileimage)
    {
        this.leadprofileimage = leadprofileimage;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getLead_statusid ()
{
    return lead_statusid;
}

    public void setLead_statusid (String lead_statusid)
    {
        this.lead_statusid = lead_statusid;
    }

    public String getTransfered_status ()
{
    return transfered_status;
}

    public void setTransfered_status (String transfered_status)
    {
        this.transfered_status = transfered_status;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getLead_updateddate ()
{
    return lead_updateddate;
}

    public void setLead_updateddate (String lead_updateddate)
    {
        this.lead_updateddate = lead_updateddate;
    }

    public String getHwfindabtus ()
    {
        return hwfindabtus;
    }

    public void setHwfindabtus (String hwfindabtus)
    {
        this.hwfindabtus = hwfindabtus;
    }

    public String getEmailid ()
    {
        return emailid;
    }

    public void setEmailid (String emailid)
    {
        this.emailid = emailid;
    }

    public String getMobileno ()
    {
        return mobileno;
    }

    public void setMobileno (String mobileno)
    {
        this.mobileno = mobileno;
    }

    public String getIsdeleted ()
{
    return isdeleted;
}

    public void setIsdeleted (String isdeleted)
    {
        this.isdeleted = isdeleted;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getLead_prospect ()
    {
        return lead_prospect;
    }

    public void setLead_prospect (String lead_prospect)
    {
        this.lead_prospect = lead_prospect;
    }

    public String getLead_warmth ()
{
    return lead_warmth;
}

    public void setLead_warmth (String lead_warmth)
    {
        this.lead_warmth = lead_warmth;
    }

    public String getLead_category ()
{
    return lead_category;
}

    public void setLead_category (String lead_category)
    {
        this.lead_category = lead_category;
    }

    public String getLead_id ()
    {
        return lead_id;
    }

    public void setLead_id (String lead_id)
    {
        this.lead_id = lead_id;
    }


}
