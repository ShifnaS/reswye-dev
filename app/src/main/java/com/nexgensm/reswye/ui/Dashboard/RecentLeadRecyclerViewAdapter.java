package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.BuyerDetailsActivity;
import com.nexgensm.reswye.ui.lead.SellerDetailsActivity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nexmin on 18-04-2018.
 */

public class RecentLeadRecyclerViewAdapter  extends RecyclerView.Adapter<RecentLeadRecyclerViewAdapter.CustomViewHolder>{
    private List<RecentItems> RecentItemsList;
    private RecentItems recentItems;
    private Context mContext;
    private ArrayList<Integer> images;
    View view;
    Bitmap myBitmap;
    public RecentLeadRecyclerViewAdapter(List<RecentItems> RecentItemsList,Context con)
    {
        this.RecentItemsList = RecentItemsList;
        this.mContext = con;
    }
    public RecentLeadRecyclerViewAdapter()
    {

    }

    @Override
    public RecentLeadRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.missed_list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //final LeadItems leadItems1 = LeadItemList.get(i);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final RecentItems recentItems = RecentItemsList.get(i);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = recentItems.getLead_name();
                if(name=="Nithu Thomas") {
                    Intent addnewsellerdetailsactivity = new Intent(mContext, SellerDetailsActivity.class);
                    addnewsellerdetailsactivity.putExtra("lead_name", name);
                    mContext.startActivity(addnewsellerdetailsactivity);
                }
                if(name=="Keerthana") {
                    Intent addnewsellerdetailsactivity = new Intent(mContext, BuyerDetailsActivity.class);
                    addnewsellerdetailsactivity.putExtra("lead_name", name);
                    mContext.startActivity(addnewsellerdetailsactivity);
                }
            }
        });
//        new Thread() {
//            @Override
//            public void run() {
//                String s=recentItems.getLead_pic();
//                try {
//                    URL url = new URL(s);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
//                    myBitmap = BitmapFactory.decodeStream(input);
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        customViewHolder.imageView.setImageBitmap(myBitmap);
        String img=recentItems.getLead_pic();
        Picasso.with(view.getContext()).load(img).into(customViewHolder.imageView);
        customViewHolder.textView.setText(Html.fromHtml(recentItems.getLead_name()));
        customViewHolder.textView1.setText(Html.fromHtml(recentItems.getLead_time()));
        customViewHolder.textView2.setText(Html.fromHtml(recentItems.getLead_address()));

    }


    @Override
    public int getItemCount() {
        return (null != RecentItemsList ? RecentItemsList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView imageView;
        protected TextView textView;
        protected TextView textView1;

        protected TextView textView2;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (CircleImageView) view.findViewById(R.id.lead_pic);
            this.textView = (TextView) view.findViewById(R.id.lead_name);
            this.textView1 = (TextView) view.findViewById(R.id.lead_time);
            this.textView2 = (TextView) view.findViewById(R.id.lead_address);
        }
    }
}
