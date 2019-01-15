package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexgensm.reswye.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nithu on 11/03/18.
 */

public class LeadSortedAdapter extends RecyclerView.Adapter<LeadSortedAdapter.CustomViewHolder> {
    private List<LeadSortedList> leadSortedLists;
View view;
Drawable done;
Context context;
    private OnItemCheckClickListner mCallback;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public LeadSortedAdapter( List<LeadSortedList> leadSortedLists,Context context) {
        this.leadSortedLists = leadSortedLists;
        this.context = context;

    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lead_sorted_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //final LeadItems leadItems1 = LeadItemList.get(i);
        done =context.getResources().getDrawable(R.drawable.ic_done_black_24dp);

        return viewHolder;
    }
    @Override
    public int getItemCount() {
        return leadSortedLists.size();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        //protected ImageView imageView;
        protected CheckBox check;




        public CustomViewHolder(View view) {
            super(view);
            //this.imageView = (ImageView) view.findViewById(R.id.lead_pic);
            this.textView = (TextView) view.findViewById(R.id.sorted_txt);
            //this.imageView =(ImageView)view.findViewById(R.id.selection);
            this.check = (CheckBox)view.findViewById(R.id.chek);


        }
    }

    @Override
    public void onBindViewHolder(final  CustomViewHolder customViewHolder, int i) {
        final LeadSortedList leadSortedList = leadSortedLists.get(i);
        //final LeadSortedAdapter adapter;

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.check.setVisibility(view.VISIBLE);

                customViewHolder.check.setChecked(true);
                mCallback.onItemClick(view);


                //customViewHolder.imageView.setBackground(done);
               // customViewHolder.imageView.setVisibility(View.VISIBLE);

            }
        });*/
        /*customViewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        //leadSortedLists.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                //fonts.get(clickedPos).setSelected(cb.isChecked);
            }

        });*/



        customViewHolder.textView.setText(leadSortedList.getName());

            //Setting text view title
            // customViewHolder.textView.setText(Html.fromHtml(leadItems.getLead_name()));

            //customViewHolder.textView1.setText(Html.fromHtml(leadItems.getLead_time()));

            // customViewHolder.textView2.setText(Html.fromHtml(leadItems.getLead_address()));

        }
    }

