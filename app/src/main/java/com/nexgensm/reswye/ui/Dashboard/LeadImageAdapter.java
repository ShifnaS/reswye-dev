package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.LeadItems;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nithu on 09/03/18.
 */

public class LeadImageAdapter extends RecyclerView.Adapter<LeadImageAdapter.CustomViewHolder> {

    //private List<LeadItems> LeadItemList;
    //private Context mContext;
    private ArrayList<Integer> images;
    View view;

    public LeadImageAdapter(ArrayList<Integer> images) {
        this.images = images;
        //this.mContext = context;
        //this.images = images;
    }

    @Override
    public LeadImageAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashborad_lead, null);
        LeadImageAdapter.CustomViewHolder viewHolder = new LeadImageAdapter.CustomViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeadImageAdapter.CustomViewHolder customViewHolder, int i) {
        //final LeadItems leadItems = LeadItemList.get(i);

        //Render image using Picasso library
        /*if (!TextUtils.isEmpty(leadItems.getLead_pic())) {
            Picasso.with(mContext).load(leadItems.getLead_pic())
                    .error(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .into(customViewHolder.imageView);
        }*/

     /*   view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/


        customViewHolder.imageView.setImageResource(images.get(i));

        //Setting text view title
    }

    @Override
    public int getItemCount() {
        return (images.size());
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.dashboard_image);

        }
    }
}
