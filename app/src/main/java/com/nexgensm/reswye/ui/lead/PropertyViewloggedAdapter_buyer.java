package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexgensm.reswye.R;

/**
 * Created by Nexmin on 03-04-2018.
 */

public class PropertyViewloggedAdapter_buyer extends PagerAdapter {
    Context context;
    String[] amount3;
    LayoutInflater inflater;

    public PropertyViewloggedAdapter_buyer( Context context, String[] amount3) {
        this.context = context;
        this.amount3 = amount3;
    }

    @Override
    public int getCount() {
        return amount3.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView DateAndTime,DateAndTimeValue,Property_id,Property_ldValue,leadComments,leadCommentsValue;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView2 = inflater.inflate(R.layout.viewedpager_item_buyer, container, false);

        DateAndTime = (TextView) itemView2.findViewById(R.id.DateAndTime);
        DateAndTimeValue = (TextView) itemView2.findViewById(R.id.DateAndTimeValue);
        Property_id = (TextView) itemView2.findViewById(R.id.Property_id);
        Property_ldValue = (TextView) itemView2.findViewById(R.id.Property_ldValue);
        leadComments = (TextView) itemView2.findViewById(R.id.leadComments);
        leadCommentsValue = (TextView) itemView2.findViewById(R.id.leadCommentsValue);

        ((ViewPager) container).addView(itemView2);

        return itemView2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
