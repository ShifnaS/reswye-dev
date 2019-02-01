package com.nexgensm.reswye.model;

import com.google.gson.annotations.SerializedName;

public class Response
{
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private Result result;

    public Result getResult ()
    {
        return result;
    }

    public void setResult (Result result)
    {
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