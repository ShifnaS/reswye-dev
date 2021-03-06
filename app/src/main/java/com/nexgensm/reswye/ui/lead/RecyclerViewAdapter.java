package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.model.ResultData;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    Context context;

    List<LeadListingRecyclerDataAdapter> getDataAdapter;
    List<LeadListingRecyclerDataAdapter> filterGetDataAdapter;
    private DataAdapterListener listener;
    ArrayList<ResultData> list;

    ImageLoader imageLoader1;
    int a = 0;

    public RecyclerViewAdapter(List<LeadListingRecyclerDataAdapter> getDataAdapter, Context context, int c) {

        super();
        this.getDataAdapter = getDataAdapter;
        this.filterGetDataAdapter=getDataAdapter;
        this.context = context;
        this.a = c;
    }
    public RecyclerViewAdapter(List<LeadListingRecyclerDataAdapter> getDataAdapter, Context context, int c,DataAdapterListener listener) {

        super();
        this.getDataAdapter = getDataAdapter;
        this.filterGetDataAdapter=getDataAdapter;
        this.context = context;
        this.listener=listener;
        this.a = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (a == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_list_row, parent, false);
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

        LeadListingRecyclerDataAdapter getDataAdapter1 = getDataAdapter.get(position);
        Log.d("1111111", "" + getDataAdapter.size());


        if (a == 0) {
            getDataAdapter1 = filterGetDataAdapter.get(position);
            String lead_type=getDataAdapter1.getLead_type();
            if(lead_type.equals("Buyer"))
            {
                Viewholder.lead_type.setImageResource(R.drawable.buyer_label);
            }

            imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
            Log.v("1111111", "LOADER " + getDataAdapter1.getLead_imageUrl());
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
        } else if (a==2){


            Viewholder.loggedbuyer.setText(getDataAdapter1.getBuyername());
            Viewholder.loggedphone.setText(getDataAdapter1.getBuyermobile());
            Viewholder.loggedmail.setText(getDataAdapter1.getBuyneremail());
            Viewholder.loggeddatetime.setText(getDataAdapter1.getViewedDate());
            Viewholder.loggedbuyercomments.setText(getDataAdapter1.getBuyerComments());


        }else if (a==3){

            imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
            Log.v("LOADER", "" + getDataAdapter1.getLead_imageUrl());
            imageLoader1.get(getDataAdapter1.getLead_imageUrl(),
                    ImageLoader.getImageListener(
                            Viewholder.leadPictureView1,//Server Image
                            R.mipmap.ic_launcher,//Before loading server image the default showing image.
                            android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                    )
            );
            Picasso.with(context).load(getDataAdapter1.getLead_imageUrl()).into( Viewholder.leadPictureView1);
          //  Viewholder.leadPictureView1.setImageUrl(getDataAdapter1.getLead_imageUrl(), imageLoader1);
        }else if (a==4)
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

           // Viewholder.leadNameView.setText(getDataAdapter1.getLead_name());
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

        if(a==0)
        {
            return  filterGetDataAdapter.size();
        }
        else {
            return getDataAdapter.size();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterGetDataAdapter = getDataAdapter;
                }
                else
                {
                    List<LeadListingRecyclerDataAdapter> filteredList = new ArrayList<>();
                    for (LeadListingRecyclerDataAdapter row : getDataAdapter) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLead_name().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                }

            }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterResults;
                return filterResults;

        }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                try
                {
                    filterGetDataAdapter = (List<LeadListingRecyclerDataAdapter>) filterResults.values;
                    notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    Log.e("Exception ","11111 "+e.getMessage());
                }

            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView leadNameView, leadTimeView, leadAddressView;
        public TextView loggedbuyertxt,loggedbuyer,loggedphonetxt,loggedphone,loggedmailtxt,loggedmail,loggeddatetimetxt,loggeddatetime,loggedbuyercommentstxt,loggedbuyercomments;
        public NetworkImageView leadPictureView;
        public CircleImageView leadPictureView1;
        ImageView lead_type;
     //   public ContactsAdapter(Context context, List<Contact> contactList, ContactsAdapterListener listener) {

        public ViewHolder(View itemView) {

            super(itemView);
            if (a == 0) {
                leadNameView = (TextView) itemView.findViewById(R.id.lead_name);
                leadTimeView = (TextView) itemView.findViewById(R.id.lead_time);
                leadAddressView = (TextView) itemView.findViewById(R.id.lead_address);
                leadPictureView = (NetworkImageView) itemView.findViewById(R.id.lead_pic);
                lead_type = (ImageView) itemView.findViewById(R.id.lead_type);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // send selected contact in callback
                        listener.onContactSelected(filterGetDataAdapter.get(getAdapterPosition()));
                    }
                });
            }
            else if (a==1){

                leadNameView = (TextView) itemView.findViewById(R.id.status);

                leadTimeView = (TextView) itemView.findViewById(R.id.salestatustxt);
                leadAddressView = (TextView) itemView.findViewById(R.id.amount);
                leadPictureView = (NetworkImageView) itemView.findViewById(R.id.prop_image);
            }
            else if (a==2){

                loggedbuyertxt = (TextView) itemView.findViewById(R.id.loggedbuyertxt);
                loggedbuyer = (TextView) itemView.findViewById(R.id.loggedbuyer);
                loggedphonetxt = (TextView) itemView.findViewById(R.id.loggedphonetxt);
                loggedphone = (TextView) itemView.findViewById(R.id.loggedphone);
                loggedmailtxt = (TextView) itemView.findViewById(R.id.loggedmailtxt);
                loggedmail = (TextView) itemView.findViewById(R.id.loggedmail);
                loggeddatetimetxt = (TextView) itemView.findViewById(R.id.loggeddatetimetxt);
                loggeddatetime = (TextView) itemView.findViewById(R.id.loggeddatetime);
                loggedbuyercommentstxt = (TextView) itemView.findViewById(R.id.loggedbuyercommentstxt);
                loggedbuyercomments = (TextView) itemView.findViewById(R.id.loggedbuyercomments);

            }else if (a==3){

                leadPictureView1=(CircleImageView)itemView.findViewById(R.id.dashboard_image);

            }else if (a==4){
                leadPictureView=(NetworkImageView)itemView.findViewById(R.id.view_photo_prop);


            }



        }
    }
    public interface DataAdapterListener {
        void onContactSelected(LeadListingRecyclerDataAdapter contact);
    }

}