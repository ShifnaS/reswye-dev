package com.nexgensm.reswye.ui.lead;

/**
 * Created by Nexmin on 08-03-2018.
 */

public class Document_List_items {

    private String doc_name;
    private String doc_pic;
    private String doc_time;

    public Document_List_items() {
    }

    public Document_List_items(String doc_pic, String doc_name, String doc_time) {
        this.doc_pic = doc_pic;
        this.doc_name = doc_name;
        this.doc_time = doc_time;

    }



    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String lead_name) {
        this.doc_name =lead_name;
    }

    public String getDoc_pic() {
        return doc_pic;
    }

    public void setDoc_pic(String lead_pic) {
        this.doc_pic = lead_pic;
    }

    public String getDoc_time() {
        return doc_time;
    }

    public void setDoc_time(String lead_time) {
        this.doc_time = lead_time;
    }



}
