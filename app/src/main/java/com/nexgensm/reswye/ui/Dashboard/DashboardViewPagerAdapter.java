package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexgensm.reswye.R;

/**
 * Created by nithu on 02/03/18.
 */

public class DashboardViewPagerAdapter extends PagerAdapter {
LinearLayout dormant;
    Context context;
    String[] amount;
    String[] missed_followups;
    String[] upcoming_followups;
    String[] dormant_followup;

    LayoutInflater inflater;
    LinearLayout missedFollowups,upComingFollowups;
    public DashboardViewPagerAdapter(Context context, String[]amount,String[] missed_followups,String[] upcoming_followups,String[] dormant_follow) {
        this.context = context;
        this.amount = amount;
        this.missed_followups = missed_followups;
        this.upcoming_followups = upcoming_followups;
        this.dormant_followup= dormant_follow;
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

        TextView date;
        TextView missed;
        TextView upcoming,dormantcount;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.dashboard_item, container,
                false);
        date = (TextView) itemView.findViewById(R.id.followupdate);
        missed = (TextView) itemView.findViewById(R.id.missedfollowupscount);
        upcoming = (TextView) itemView.findViewById(R.id.upcomingfollowupscount);
        dormantcount=(TextView) itemView.findViewById(R.id.dormantcount);
        dormant=(LinearLayout)itemView.findViewById(R.id.dormant);
        missedFollowups = (LinearLayout )itemView.findViewById(R.id.missed);
        upComingFollowups = (LinearLayout )itemView.findViewById(R.id.upcoming);
        missedFollowups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent missed = new Intent(context, MissedFollowupsActivity.class);
                context.startActivity(missed);
            }
        });
        upComingFollowups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coming = new Intent(context, UpComingFollowUpsActivity.class);
                context.startActivity(coming);
            }
        });
        dormant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dormant = new Intent(context, DormantListingActivity.class);
                context.startActivity(dormant);
            }
        });
        date.setText(amount[position]);
        missed.setText(missed_followups[position]);
        upcoming.setText(upcoming_followups[position]);
        dormantcount.setText(dormant_followup[position]);
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
