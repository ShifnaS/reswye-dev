package com.nexgensm.reswye.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.BuyerDetailsActivity;
import com.nexgensm.reswye.ui.lead.LeadItems;
import com.nexgensm.reswye.ui.lead.LeadRecyclerViewAdapter;
import com.nexgensm.reswye.ui.lead.SellerDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nithu on 12/03/18.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CustomViewHolder> {

    private Context context;
    List<CalItem> calItemList;
    Context con;
    View view;
    public CalendarAdapter(Context con,List<CalItem> calItemList) {


        this.con = con;
        this.calItemList = calItemList;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cal_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //final LeadItems leadItems1 = LeadItemList.get(i);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final CalItem calItem = calItemList.get(i);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent appointmentdetailsactivity = new Intent(con, AppointmentDetailsActivity.class);
                con.startActivity(appointmentdetailsactivity);
            }
        });


        //customViewHolder.imageView.setImageResource(images.get(i));

        //Setting text view title
        //customViewHolder.textView.setText(Html.fromHtml(calItem.getPurpose()));

        //customViewHolder.textView1.setText(Html.fromHtml(calItem.getPlace()));

        //customViewHolder.textView2.setText(Html.fromHtml(calItem.getTime()));
    }
    @Override
    public int getItemCount() {
        return calItemList.size() ;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        protected TextView textView1;

        protected TextView textView2;


        public CustomViewHolder(View view) {
            super(view);

            this.textView = (TextView) view.findViewById(R.id.meeting);
            this.textView1 = (TextView) view.findViewById(R.id.place);
            this.textView2 = (TextView) view.findViewById(R.id.time);
        }
    }


}
