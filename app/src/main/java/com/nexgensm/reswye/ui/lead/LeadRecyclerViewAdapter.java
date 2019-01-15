package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.squareup.picasso.Picasso;
import com.nexgensm.reswye.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nithu on 14/02/18.
 */

public class LeadRecyclerViewAdapter extends RecyclerView.Adapter<LeadRecyclerViewAdapter.CustomViewHolder> {
    Bitmap myBitmap;
    private List<LeadItems> LeadItemList;
    private LeadItems leadItems;
    private Context mContext;
    private ArrayList<Integer> images;
     View view;
     public LeadRecyclerViewAdapter(List<LeadItems> LeadItemList,Context con)
     {
         this.LeadItemList = LeadItemList;
         this.mContext = con;
     }
     public LeadRecyclerViewAdapter()
     {

     }



    public LeadRecyclerViewAdapter(ArrayList<Integer> images,Context context, List<LeadItems> LeadItemList) {
        this.LeadItemList = LeadItemList;
        this.mContext = context;
        this.images = images;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
         view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lead_list_row,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //final LeadItems leadItems1 = LeadItemList.get(i);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder,  int i) {

        final LeadItems leadItems = LeadItemList.get(i);
        Singleton.getInstance().setPosition(i);

        Log.v("First Position",""+i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = leadItems.getLead_name();
//                if(name=="Nithu Thomas") {
//                    Intent addnewsellerdetailsactivity = new Intent(mContext, SellerDetailsActivity.class);
//                    addnewsellerdetailsactivity.putExtra("lead_name", name);
//                    mContext.startActivity(addnewsellerdetailsactivity);
//                }
//                if(name=="Keerthana") {
//                    Intent addnewsellerdetailsactivity = new Intent(mContext, BuyerDetailsActivity.class);
//                    addnewsellerdetailsactivity.putExtra("lead_name", name);
//                    mContext.startActivity(addnewsellerdetailsactivity);
//                }
          // int pos= (int) getItemId(i);
                int pos= Singleton.getInstance().getPositon();
                Log.v("second",""+pos);
                int leadId1 = LeadItemList.get(pos).getLead_id();
                int leadId = leadItems.getLead_id();
                Log.v("LeadIDD",""+leadId +"/"+leadId1);
                Intent addnewsellerdetailsactivity = new Intent(mContext, SellerDetailsActivity.class);
                addnewsellerdetailsactivity.putExtra("leadId", leadId);
                mContext.startActivity(addnewsellerdetailsactivity);
            }
        });


        new Thread() {
            @Override
            public void run() {
                String s=leadItems.getLead_pic();
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //customViewHolder.imageView.setImageResource(images.get(i));

        //Setting text view title
//        customViewHolder.imageView.setImageURI((Uri) Html.fromHtml(leadItems.getLead_pic()));

        customViewHolder.imageView.setImageBitmap(myBitmap);
        customViewHolder.textView.setText(Html.fromHtml(leadItems.getLead_name()));

        customViewHolder.textView1.setText(Html.fromHtml(leadItems.getLead_time()));

        customViewHolder.textView2.setText(Html.fromHtml(leadItems.getLead_address()));
    }

    @Override
    public int getItemCount() {
        return (null != LeadItemList ? LeadItemList.size() : 0);
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
