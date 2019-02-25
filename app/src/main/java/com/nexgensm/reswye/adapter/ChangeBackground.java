package com.nexgensm.reswye.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.nexgensm.reswye.R;

public class ChangeBackground {
    public static void setBack(View et, Context context)
    {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            et.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_focus_red) );
        } else {
            et.setBackground(ContextCompat.getDrawable(context, R.drawable.spinner_focus_red));
        }
    }
    public static void setBack2(View et, Context context)
    {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            et.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_focus) );
        } else {
            et.setBackground(ContextCompat.getDrawable(context, R.drawable.spinner_focus));
        }
    }
}
