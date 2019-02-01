package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nexmin on 08-03-2018.
 */

public class DocumentRecyclerViewAdapter  extends RecyclerView.Adapter<DocumentRecyclerViewAdapter.CustomViewHolder> {
    private List<Document_List_items> DocumentItemList;
    private Context mContext;
    private ArrayList<Integer> images;
    View view;
    ImageView viewOption,downloaddoc;
    public DocumentRecyclerViewAdapter(ArrayList<Integer> images,Context context, List<Document_List_items> DocumentItemList) {
        this.DocumentItemList = DocumentItemList;
        this.mContext = context;
        this.images = images;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.document_list_row, null);
       CustomViewHolder viewHolder = new CustomViewHolder(view);

        viewOption=(ImageView)view.findViewById(R.id.viewOption);
        downloaddoc=(ImageView)view.findViewById(R.id.download);
        return viewHolder;
    }
    @Override
    public int getItemCount() {
        return (null != DocumentItemList ? DocumentItemList.size() : 0);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        final Document_List_items docItems = DocumentItemList.get(i);

        viewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = docItems.getDoc_name();
                String fileName=docItems.getDoc_pic();
                String dis=docItems.getDoc_time();


                    Intent PdfViewer= new Intent(mContext, PdfViewerActivity.class);
                    PdfViewer.putExtra("doc_name", name);
                    PdfViewer.putExtra("dbdoc_name", fileName);
                    mContext.startActivity(PdfViewer);
                           }
        });

        downloaddoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String name = docItems.getDoc_name();
                String fileName=docItems.getDoc_pic();

                    try {
                       // String url = "https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/Beauty.jpg";
                          String url="http://202.88.239.14:8169/FileUploads/"+fileName;
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setDescription("Some descrition");
                        request.setTitle("Property Document");

// in order for this if to run, you must use the android 3.2 to compile your app

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        }
                        if (mContext.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.e("Permission error", "You have permission");
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.ext");

                        }

// get download service and enqueue file
                        DownloadManager manager = (DownloadManager)mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                        mContext.registerReceiver(downloadReceiver, filter);
                    } catch (Exception e) {
                        e.printStackTrace();






                }

            }
        });
        customViewHolder.imageView.setImageResource(images.get(i));
        customViewHolder.textView.setText(Html.fromHtml(docItems.getDoc_name()));
        customViewHolder.textView1.setText(Html.fromHtml(docItems.getDoc_time()));


    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView1;




        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.doc_pic);
            this.textView = (TextView) view.findViewById(R.id.doc_name);
            this.textView1 = (TextView) view.findViewById(R.id.doc_time);
        }
    }
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            Toast toast = Toast.makeText(view.getContext(),"Download Complete", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();


        }
    };
}