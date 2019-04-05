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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nexmin on 03-04-2018.
 */

public class PropertyViewloggedAdapter_buyer extends PagerAdapter {
    Context context;
    JSONArray jsonObject;
    LayoutInflater inflater;

    public PropertyViewloggedAdapter_buyer( Context context,JSONArray amount3) {
        this.context = context;
        this.jsonObject = amount3;
    }

    @Override
    public int getCount() {
        return jsonObject.length();
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

        DateAndTimeValue = (TextView) itemView2.findViewById(R.id.DateAndTimeValue);
        Property_ldValue = (TextView) itemView2.findViewById(R.id.Property_ldValue);
        leadCommentsValue = (TextView) itemView2.findViewById(R.id.leadCommentsValue);

        try
        {
                JSONObject obj = jsonObject.getJSONObject(position);
                DateAndTimeValue.setText(obj.getString("ViewedDatetime"));
                Property_ldValue.setText(obj.getString("propertyId"));
                leadCommentsValue.setText(obj.getString("Comments"));


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        // Property_ldValue.setText(amount3[position]);
        ((ViewPager) container).addView(itemView2);




        return itemView2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
