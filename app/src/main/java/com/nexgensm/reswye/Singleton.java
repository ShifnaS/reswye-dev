package com.nexgensm.reswye;

import com.nexgensm.reswye.ui.lead.ImageItems;

import java.util.ArrayList;

public class Singleton {
    private int position,sortSaveFlag,sortboxcheck;
    private String sortFiledname,sortOrdertype;

    private int leadCategory,propID,isfilterDefaultt,filterFlag,leadid;
    private String propIDD,appointmentTime,appointmentLoc,filterLeadStatus, filterLoc,filterDate,filterWarmth,filterAgent,filterFirstName,filterLastName,filterMob,filterEmail,minvalue,maxvalue,propStatus,lead_name;
    private boolean filterTransferStatus;
    private ArrayList<String> arrayList;
    private ArrayList<ImageItems> arrayImages;
    private static Singleton instance;

    private Singleton() {
        arrayList = new ArrayList<String>();

        arrayImages = new ArrayList<ImageItems>();
        int position;
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void setArrayImages(ImageItems imageItems) {

        this.arrayImages.add(imageItems);
    }

    public ArrayList<ImageItems> getArrayImages() {

        return arrayImages;
    }
    public void clearImage(){
        this.arrayImages.clear();
    }

    public void setArrayList(String encodedImage) {
        this.arrayList.add(encodedImage);
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
    public void clearArray(){
        this.arrayList.clear();
    }
    public int getPositon(){
        return  position;
    }
    public void setPosition(int  item)
    {
        this.position=item;
    }
    public String getSortFiledname(){
        return sortFiledname;
    }
    public void setSortFiledname(String filedname){
        this.sortFiledname=filedname;
    }
    public String getSortOrdertype(){
        return sortOrdertype;
    }
    public void setSortOrdertype(String ordertype){
        this.sortOrdertype=ordertype;
    }
    public void setSortSaveFlag(int flag){
        this.sortSaveFlag=flag;
    }
    public int getSortSaveFlag(){
        return sortSaveFlag;
    }
    public void setSortboxcheck(int checkbox){this .sortboxcheck=checkbox;}
    public int getSortboxcheck(){return sortboxcheck;}

    ///////filter category//////
    public void setLeadCategory(int leadcat){this .leadCategory=leadcat;}
    public int getLeadCategory(){return leadCategory;}

    public void setPropID(int propID){this .propID=propID;}
    public int getPropID(){return propID;}

    public void setPropIDD(String propID){this .propIDD=propID;}
    public String getPropIDD(){return propIDD;}

    public void setIsfilterDefaultt(int defaultt){this .isfilterDefaultt=defaultt;}
    public int getIsfilterDefaultt(){return isfilterDefaultt;}

    public String getFilterLoc(){
        return filterLoc;
    }
    public void setFilterLoc(String filedname){
        this.filterLoc=filedname;
    }

    public String getFilterDate(){
        return filterDate;
    }
    public void setFilterDate(String filedname){
        this.filterDate=filedname;
    }

    public String getFilterWarmth(){
        return filterWarmth;
    }
    public void setFilterWarmth(String filterWarmth){
        this.filterWarmth=filterWarmth;
    }

    public String getFilterAgent(){
        return filterAgent;
    }
    public void setFilterAgent(String agentname){
        this.filterAgent=agentname;
    }

    public String getFilterFirstName(){
        return filterFirstName;
    }
    public void setFilterFirstName(String agentname){
        this.filterFirstName =agentname;
    }

    public String getFilterLastName(){
        return filterLastName;
    }
    public void setFilterLastName(String agentname){
        this.filterLastName=agentname;
    }

    public String getFilterMob(){
        return filterMob;
    }
    public void setFilterMob(String agentname){
        this.filterMob=agentname;
    }

    public String getFilterEmail(){
        return filterEmail;
    }
    public void setFilterEmail(String agentname){
        this.filterEmail=agentname;
    }


    public String getFilterLeadStatus(){ return filterLeadStatus; }
    public void setFilterLeadStatus(String agentname){
        this.filterLeadStatus=agentname;
    }

    public String getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(String maxvalue) {
        this.maxvalue = maxvalue;
    }

    public String getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(String minvalue) {
        this.minvalue = minvalue;
    }

    public String getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(String propStatus) {
        this.propStatus = propStatus;
    }

    public Boolean getFilterStatus(){
        return filterTransferStatus;
    }
    public void setFilterTransferStatus(Boolean agentname){
        this.filterTransferStatus=agentname;
    }

    public int getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(int filterFlag) {
        this.filterFlag = filterFlag;
    }

    ///////////APPOINTMENT//////////////

    public int getLeadId() {
        return leadid;
    }

    public void setLeadId(int leadid) {
        this.leadid = leadid;
    }
    public String  getLeadName() {
        return lead_name;
    }

    public void setLeadName(String lead_name) {
        this.lead_name = lead_name;
    }
    public String  getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public String  getAppointmentLocation() {
        return appointmentLoc;
    }

    public void setAppointmentLocation(String appointmentLoc) {
        this.appointmentLoc = appointmentLoc;
    }

}
