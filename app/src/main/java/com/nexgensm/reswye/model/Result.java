package com.nexgensm.reswye.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("User_Id")
    private int User_Id;

    @SerializedName("Lead_Id")
    private int Lead_Id;

    @SerializedName("Agent_Id")
    private int Agent_Id;

    @SerializedName("Email")
    private String Email;

    @SerializedName("message")
    private String message;

    public int getLead_Id() {
        return Lead_Id;
    }

    public void setLead_Id(int lead_Id) {
        Lead_Id = lead_Id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_Id ()
    {
        return User_Id;
    }

    public void setUser_Id (int User_Id)
    {
        this.User_Id = User_Id;
    }

    public int getAgent_Id ()
    {
        return Agent_Id;
    }

    public void setAgent_Id (int Agent_Id)
    {
        this.Agent_Id = Agent_Id;
    }
}
