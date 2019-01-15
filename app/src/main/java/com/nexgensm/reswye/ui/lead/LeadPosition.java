package com.nexgensm.reswye.ui.lead;

/**
 * Created by Nexmin on 16-05-2018.
 */

class LeadPosition {
    private int position;
    private static LeadPosition instance;
    private static final LeadPosition ourInstance = new LeadPosition();


    private LeadPosition ()
    {
        int position;
    }
    public static LeadPosition getInstance()
    {
        if (instance == null)
        {
            instance = new LeadPosition();
        }
        return instance;
    }


    public int getPositon(){
        return  position;
    }
    public void setPosition(int  item)
    {
        this.position=item;
    }
}
