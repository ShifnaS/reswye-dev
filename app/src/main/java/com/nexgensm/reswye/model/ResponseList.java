package com.nexgensm.reswye.model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResponseList
{
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private ArrayList<ResultData> result;

    @SerializedName("res")
    private ArrayList<ResultData> res;

    public ArrayList<ResultData> getRes() {
        return res;
    }

    public void setRes(ArrayList<ResultData> res) {
        this.res = res;
    }

    public ArrayList<ResultData> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultData> result) {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }



}