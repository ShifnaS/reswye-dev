package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.model.ResultData;

import java.util.ArrayList;

public class LeadListAdapter extends RecyclerView.Adapter<LeadListAdapter.MyViewHolder> {
    ArrayList<ResultData> list;
    Context context;

    public LeadListAdapter(ArrayList<ResultData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lead_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResultData resultData = list.get(position);
        holder.leadName.setText(resultData.getFirstname());
        holder.leadTime.setText(resultData.getLead_createddate());
        holder.Address.setText(resultData.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        public TextView leadTime, leadName, Address;
        ImageView leadPic;

        public MyViewHolder(View view) {
            super(view);
            leadTime = (TextView) view.findViewById(R.id.lead_time);
            leadName = (TextView) view.findViewById(R.id.lead_name);
            Address = (TextView) view.findViewById(R.id.lead_address);
        }
    }
}
