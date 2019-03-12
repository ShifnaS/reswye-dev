package com.nexgensm.reswye.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.nexgensm.reswye.app.Constants;

/**
 * A pack of helpful getter and setter methods for reading/writing to {@link SharedPreferences}.
 */
 public class SharedPrefsUtils {
    private static SharedPrefsUtils mInstance;
    private static Context mCtx;


    public SharedPrefsUtils() {
    }

    private SharedPrefsUtils(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefsUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefsUtils(context);
        }
        return mInstance;
    }
    public boolean setUserId(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_USER_ID,id);
        editor.apply();
        return true;
    }
    public int getUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(Constants.KEY_USER_ID, 0);
        return  id;
    }
    public boolean setLeadId(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_LEAD_ID,id);
        editor.apply();
        return true;
    }
    public int getLeadId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(Constants.KEY_LEAD_ID, 0);
        return  id;
    }
    public boolean setLid(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_LI_ID,id);
        editor.apply();
        return true;
    }
    public int getLId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(Constants.KEY_LI_ID, 0);
        return  id;
    }

    public boolean setFlag(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_FLAG,id);
        editor.apply();
        return true;
    }
    public int getFlag() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(Constants.KEY_FLAG, 0);
        return  id;
    }


    public boolean setAgentId(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_AGENT_ID,id);
        editor.apply();
        return true;
    }
    public int getAgentId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id= sharedPreferences.getInt(Constants.KEY_AGENT_ID, 0);
        return  id;
    }
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}