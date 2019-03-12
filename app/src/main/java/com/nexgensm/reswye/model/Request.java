package com.nexgensm.reswye.model;

public class Request {

    private String first_name;

    private String last_name;

    private String confirmpassword;

    private String password;

    private String email_id;

    private String mobile;


    private String user_type;

    private String zip;

    private String referralcode;

    private String howfindabtus;

    private String street;

    private String state;

    private String correspondence_add;

    private String gender;

    private int user_id;

    private String city;

    private int lead_id;
    private String date;
    private String comment;
    private String mail;
    private String phone;
    private String name;


    public int getLead_id() {
        return lead_id;
    }

    public void setLead_id(int lead_id) {
        this.lead_id = lead_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Request(String user_type, String zip, String referralcode, String howfindabtus, String street, String state, String correspondence_add, String gender, int user_id, String city) {
        this.user_type = user_type;
        this.zip = zip;
        this.referralcode = referralcode;
        this.howfindabtus = howfindabtus;
        this.street = street;
        this.state = state;
        this.correspondence_add = correspondence_add;
        this.gender = gender;
        this.user_id = user_id;
        this.city = city;
    }


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getReferralcode() {
        return referralcode;
    }

    public void setReferralcode(String referralcode) {
        this.referralcode = referralcode;
    }

    public String getHowfindabtus() {
        return howfindabtus;
    }

    public void setHowfindabtus(String howfindabtus) {
        this.howfindabtus = howfindabtus;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCorrespondence_add() {
        return correspondence_add;
    }

    public void setCorrespondence_add(String correspondence_add) {
        this.correspondence_add = correspondence_add;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

  //////////////////////////////////////////////////////////////

    public Request(String first_name, String user_type, String last_name, String confirmpassword, String password, String email_id, String mobile) {
        this.first_name = first_name;
        this.user_type = user_type;
        this.last_name = last_name;
        this.confirmpassword = confirmpassword;
        this.password = password;
        this.email_id = email_id;
        this.mobile = mobile;
    }
    public Request(String email_id, String password) {
        this.email_id = email_id;
        this.password = password;

    }

    public Request(String name,String mail,String phone, String date,String comment,int lead_id) {
        this.mail = mail;
        this.phone = phone;
        this.name=name;
        this.date=date;
        this.comment=comment;
        this.lead_id=lead_id;

    }
    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public String getConfirmpassword ()
    {
        return confirmpassword;
    }

    public void setConfirmpassword (String confirmpassword)
    {
        this.confirmpassword = confirmpassword;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getEmail_id ()
    {
        return email_id;
    }

    public void setEmail_id (String email_id)
    {
        this.email_id = email_id;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

}
