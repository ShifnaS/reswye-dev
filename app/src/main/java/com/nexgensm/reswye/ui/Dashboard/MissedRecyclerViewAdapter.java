package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.BuyerDetailsActivity;
import com.nexgensm.reswye.ui.lead.SellerDetailsActivity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nexmin on 12-04-2018.
 */

public class MissedRecyclerViewAdapter extends RecyclerView.Adapter<MissedRecyclerViewAdapter.CustomViewHolder> {

    private List<MissedItems> MissedItemsList;
    private MissedItems missedItems;
    private Context mContext;
    private ArrayList<Integer> images;
    Bitmap myBitmap;
    View view;
    public MissedRecyclerViewAdapter(List<MissedItems> MissedItemsList,Context con)
    {
        this.MissedItemsList = MissedItemsList;
        this.mContext = con;
    }
    public MissedRecyclerViewAdapter()
    {

    }



    public MissedRecyclerViewAdapter(ArrayList<Integer> images,Context context, List<MissedItems> MissedItemsList) {
        this.MissedItemsList = MissedItemsList;
        this.mContext = context;
        this.images = images;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.missed_list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //final LeadItems leadItems1 = LeadItemList.get(i);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final MissedItems missedItems = MissedItemsList.get(i);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = missedItems.getLead_name();
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
//                String s=missedItems.getLead_pic();
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
       // customViewHolder.imageView.setImageBitmap(myBitmap);
        String img=missedItems.getLead_pic();
        Picasso.with(view.getContext()).load(img).into(customViewHolder.imageView);

        customViewHolder.textView.setText(Html.fromHtml(missedItems.getLead_name()));
        customViewHolder.textView1.setText(Html.fromHtml(missedItems.getLead_time()));
        customViewHolder.textView2.setText(Html.fromHtml(missedItems.getLead_address()));

    }

    @Override
    public int getItemCount() {
        return (null != MissedItemsList ? MissedItemsList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView1;

        protected TextView textView2;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.lead_pic);
            this.textView = (TextView) view.findViewById(R.id.lead_name);
            this.textView1 = (TextView) view.findViewById(R.id.lead_time);
            this.textView2 = (TextView) view.findViewById(R.id.lead_address);
        }
    }


}
