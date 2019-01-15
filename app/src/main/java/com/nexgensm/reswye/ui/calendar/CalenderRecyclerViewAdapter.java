package com.nexgensm.reswye.ui.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.calendar.AppointmentListingRecyclerDataAdapter;
import com.nexgensm.reswye.ui.lead.ServerImageParseAdapter;

import java.util.List;


public class CalenderRecyclerViewAdapter extends RecyclerView.Adapter<CalenderRecyclerViewAdapter.ViewHolder> {

    Context context;

    List<AppointmentListingRecyclerDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;
    int a = 0;

    public CalenderRecyclerViewAdapter(List<AppointmentListingRecyclerDataAdapter> getDataAdapter, Context context, int c) {

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
        this.a = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (a == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cal_item, parent, false);
        } else if (a==2){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewedpager_item_seller, parent, false);

        }else if(a==3){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashborad_lead, parent, false);

        }
        else if(a==4 ){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo_property, parent, false);


        }
        else

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.propertyviewpageritem, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        AppointmentListingRecyclerDataAdapter getDataAdapter1 = getDataAdapter.get(position);

//        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
//        Log.v("LOADER", "" + getDataAdapter1.getLead_imageUrl());
//        imageLoader1.get(getDataAdapter1.getLead_imageUrl(),
//                ImageLoader.getImageListener(
//                        Viewholder.leadPictureView,//Server Image
//                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
//                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
//                )
//        );

//        Viewholder.leadPictureView.setImageUrl(getDataAdapter1.getLead_imageUrl(), imageLoader1);
//
//        Viewholder.leadNameView.setText(getDataAdapter1.getLead_name());
//        Viewholder.leadTimeView.setText(getDataAdapter1.getLead_time());
//        Viewholder.leadAddressView.setText(getDataAdapter1.getLead_address());
        if (a == 0) {
            imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
            Log.v("LOADER", "" + getDataAdapter1.getLead_imageUrl());
            imageLoader1.get(getDataAdapter1.getLead_imageUrl(),
                    ImageLoader.getImageListener(
                            Viewholder.leadPictureView,//Server Image
                            R.mipmap.ic_launcher,//Before loading server image the default showing image.
                            android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                    )
            );
            Viewholder.leadPictureView.setImageUrl(getDataAdapter1.getLead_imageUrl(), imageLoader1);
//
            Viewholder.leadNameView.setText(getDataAdapter1.getLead_name());
            Viewholder.leadTimeView.setText(getDataAdapter1.getLead_time());
            Viewholder.leadAddressView.setText(getDataAdapter1.getLead_address());
        }

        else
        {
            imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
            Log.v("LOADER", "" + getDataAdapter1.getLead_imageUrl());
            imageLoader1.get(getDataAdapter1.getLead_imageUrl(),
                    ImageLoader.getImageListener(
                            Viewholder.leadPictureView,//Server Image
                            R.mipmap.ic_launcher,//Before loading server image the default showing image.
                            android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                    )
            );
            Viewholder.leadPictureView.setImageUrl(getDataAdapter1.getLead_imageUrl(), imageLoader1);

             Viewholder.leadNameView.setText(getDataAdapter1.getLead_name());
            Viewholder.leadTimeView.setText(getDataAdapter1.getLead_time());
            Viewholder.leadAddressView.setText(getDataAdapter1.getLead_address());
            String status = getDataAdapter1.getLead_name();
            if (status == "true") {

                Viewholder.leadNameView.setText("SHORT SALE");
            } else
                Viewholder.leadNameView.setText(getDataAdapter1.getLead_name());

        }

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView leadNameView, leadTimeView, leadAddressView;
        public TextView loggedbuyertxt,loggedbuyer,loggedphonetxt,loggedphone,loggedmailtxt,loggedmail,loggeddatetimetxt,loggeddatetime,loggedbuyercommentstxt,loggedbuyercomments;
        public NetworkImageView leadPictureView;

        public ViewHolder(View itemView) {

            super(itemView);
            if (a == 0) {
                leadNameView = (TextView) itemView.findViewById(R.id.meeting);
                leadTimeView = (TextView) itemView.findViewById(R.id.time);
                leadAddressView = (TextView) itemView.findViewById(R.id.place);
                leadPictureView = (NetworkImageView) itemView.findViewById(R.id.lead_pic);
            }

        }
    }


}