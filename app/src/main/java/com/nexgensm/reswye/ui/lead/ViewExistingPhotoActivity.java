package com.nexgensm.reswye.ui.lead;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.analytics.internal.zzy.e;

public class ViewExistingPhotoActivity extends AppCompatActivity {
    private String TAG = "ViewExistingPhotoActivity";
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    //    ImageView picview;
    ArrayList<ImageItems> arrayList;
    ArrayList<String> list;
    private RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewadapter;
    AddNewPhotoActivity.MyAdapter m;
    ArrayList<ImageItems> encodedImages;
    private JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    int flag, userId;
    int LeadId;
    String url, ImageUrl, image;
    List<LeadListingRecyclerDataAdapter> GetDataAdapter1;
    RequestQueue requestQueue;
    private ProgressDialog spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exisiting_photo);
        arrayList = new ArrayList<>();
        jsonObject = new JSONObject();
        GetDataAdapter1 = new ArrayList<>();
        sharedPreferences = getApplication().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        flag = sharedPreferences.getInt("flag", 0);
        ImageUrl = sharedPreferences.getString("imageURL", "");
        LeadId = sharedPreferences.getInt("LeadId", 0);
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("LeadId", 0);
        Log.v(TAG, "leadidintent" + intValue);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_exisiting);

        //ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        //params.height = 500;
        //recyclerView.setLayoutParams(params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.v(TAG, "FLAg" + flag);
//        if (flag == 1)
            JSON_DATA_WEB_CALL();
        Button addphoto = (Button) findViewById(R.id.addphotonew);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// showPictureDialog();
                Intent ownerInfo = new Intent(ViewExistingPhotoActivity.this, AddNewPhotoActivity.class);
                // Intent ownerInfo = new Intent(getActivity(), ViewExistingPhotoActivity.class);
                ownerInfo.putExtra("LeadId", LeadId);
                startActivity(ownerInfo);
                finish();

            }
        });


    }

    public void JSON_DATA_WEB_CALL() {

        url = "http://202.88.239.14:8169/api/Lead/GetPropertyImages/" + LeadId;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "Server " + response);
                        // loading.dismiss();
                        //Displaying our grid
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.v("error", error.toString());

                    }
                }) {
            /** Passing some request headers* */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                // headers.put("Content-Type", "application/json");
                headers.put("Authorization", Utlity.testToken);
                return headers;
            }
        };

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(jsObjRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject jsonObject) {

        JSONArray jarray = jsonObject.optJSONArray("data");

        for (int i = 0; i < jarray.length(); i++) {
            LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();

            try {
                JSONObject abc = jarray.getJSONObject(i);
                String imageName = abc.getString("uploaded_DocumentName");
                Log.v(TAG, "" + imageName + "" + ImageUrl);
                image = ImageUrl + imageName;
                GetDataAdapter2.setLead_imagename(imageName);
                GetDataAdapter2.setLead_imageUrl(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }
        int b = GetDataAdapter1.size();

        for (int i = 0; i < b; i++)
            // Singleton.getInstance().setArrayImages();
            Log.v("ADAPTERSIZE", "" + GetDataAdapter1.get(i).toString());
        int a = 4;

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getApplicationContext(), a);
        recyclerView.setAdapter(recyclerViewadapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

//                        String imagename =GetDataAdapter1.get(position).getLead_imagename();
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewExistingPhotoActivity.this);
//                        builder.setTitle("Confirm dialog demo !");
//                        builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
//                        builder.setCancelable(false);
//                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        builder.show();
////                        recyclerView.setAdapter(m);
//                        Toast.makeText(getApplicationContext(), "Name :" + imagename,
//                                Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        final String imagename = GetDataAdapter1.get(position).getLead_imagename();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewExistingPhotoActivity.this);
                        builder.setTitle("Confirm delete !");
                        builder.setMessage("Delete Image . Do you really want to proceed ?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletephoto(imagename);
                                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.show();
//                        recyclerView.setAdapter(m);
                        // Toast.makeText(getApplicationContext(), "Name :" + imagename,Toast.LENGTH_LONG).show();
                    }
                })
        );

    }

    public void deletephoto(String imagenames) {
        String deleteurl = "http://202.88.239.14:8169/api/Lead/deletepropertyphotos";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("lead_Id", LeadId);
        jsonParams.put("propphotoName", imagenames);


        //   loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, deleteurl, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.v("ADDNEW", "" + response);
                            String message = response.getString("message").toString().trim();

                            if (message.compareTo("Successfully deleted the PropertyPhotos") == 0) {
                                Toast.makeText(ViewExistingPhotoActivity.this, "Delete Done", Toast.LENGTH_SHORT);
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);


                            } else {

                                Toast.makeText(ViewExistingPhotoActivity.this, "Deletion failed, try again ", Toast.LENGTH_SHORT);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                    try {
//                        Log.v("ADDNEW",""+response);
//
//
//                        String str3 = "Success";
//                        int response_result = Status_missed.compareTo(str3);
//                        if (response_result == 0) {
//                            loading.dismiss();
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            loading.dismiss();
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (JSONException e) {
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
//                        e.printStackTrace();
//                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            loading.dismiss();
//            Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Utlity.testToken);

                return headers;
            }
        };
        // loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);

        requestQueue.add(jsonObjectRequest);


    }


}
