package com.nexgensm.reswye.ui.lead;

import android.content.Context;
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
 * Created by nithu on 02/03/18.
 */

public class PropertyViewPagerAdapter extends PagerAdapter {

    Context context;
    String[] amount;
    LayoutInflater inflater;

    public PropertyViewPagerAdapter(Context context,String[]amount) {
        this.context = context;
        this.amount = amount;
        //this.country = country;
        //this.population = population;
        //this.flag = flag;
    }

    @Override
    public int getCount() {
        return amount.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        ImageView imgprop;
        ImageView imgleadpersonalstatus;
        TextView status;
        ImageView imgpropstatus;
        TextView salestatustxt;
        ImageView imgstarstatus;
        TextView propamount;
        //ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.propertyviewpageritem, container, false);

        imgprop = (ImageView) itemView.findViewById(R.id.prop_image);
        imgleadpersonalstatus = (ImageView) itemView.findViewById(R.id.leadpersonal_status);
        status = (TextView) itemView.findViewById(R.id.status);
        imgpropstatus = (ImageView) itemView.findViewById(R.id.property_status);
        salestatustxt = (TextView) itemView.findViewById(R.id.salestatustxt);
       // imgstarstatus = (ImageView) itemView.findViewById(R.id.star_status);
        propamount = (TextView) itemView.findViewById(R.id.amount);
        propamount.setText(amount[position]);


        // Capture position and set to the TextViews
        /*txtrank.setText(rank[position]);
        txtcountry.setText(country[position]);
        txtpopulation.setText(population[position]);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView
        imgflag.setImageResource(flag[position]);*/

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
