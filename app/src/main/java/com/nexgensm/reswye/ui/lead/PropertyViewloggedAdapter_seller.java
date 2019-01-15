package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexgensm.reswye.R;

/**
 * Created by Nexmin on 03-04-2018.
 */

public class PropertyViewloggedAdapter_seller extends PagerAdapter {
    Context context;
    String[] amount2;
    LayoutInflater inflater;
    public PropertyViewloggedAdapter_seller(Context context, String[] amount2) {
        this.context = context;
        this.amount2 = amount2;

    }

    @Override
    public int getCount() {return amount2.length;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

 TextView Loggedbuyertxt,loggedbuyer,loggedphonetxt,loggedphone,loggedmailtxt,loggedmail,loggeddatetimetxt,loggeddatetime,loggedbuyercommentstxt,loggedbuyercomments;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView1 = inflater.inflate(R.layout.viewedpager_item_seller, container, false);

        Loggedbuyertxt = (TextView) itemView1.findViewById(R.id.loggedbuyertxt);
        loggedbuyer = (TextView) itemView1.findViewById(R.id.loggedbuyer);
        loggedphonetxt = (TextView) itemView1.findViewById(R.id.loggedphonetxt);
        loggedphone = (TextView) itemView1.findViewById(R.id.loggedphone);
        loggedmailtxt = (TextView) itemView1.findViewById(R.id.loggedmailtxt);
        loggedmail = (TextView) itemView1.findViewById(R.id.loggedmail);
        loggeddatetimetxt = (TextView) itemView1.findViewById(R.id.loggeddatetimetxt);
        loggeddatetime = (TextView) itemView1.findViewById(R.id.loggeddatetime);
        loggedbuyercommentstxt = (TextView) itemView1.findViewById(R.id.loggedbuyercommentstxt);
        loggedbuyercomments = (TextView) itemView1.findViewById(R.id.loggedbuyercomments);

        ((ViewPager) container).addView(itemView1);

        return itemView1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
