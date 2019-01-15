package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissedFollowupsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMissed;
    private MissedRecyclerViewAdapter missedRecycleViewAdapter;
    private List<MissedItems> MissedItemsList = new ArrayList<>();
    Context context;
    String ImageUrl;
    int userId,lead_ID;
    RequestQueue requestQueue;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private static final Integer[] lead_images= {R.drawable.profile_2,R.drawable.profile_3,R.drawable.profile_4,R.drawable.profile_5,R.drawable.profile_6,R.drawable.profile_6,R.drawable.profile_6};
    //private static final String[] lead_names = {"Charley Deets", "Mathew John", "Jaise Allen","Cheyl Tom","Steve David","Marina jones","Steve David"};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    MissedItems missedItems;

     String result;
     String Token,Status_missed,name1,address,appointment_Date,imageUrl,profileimage;
     String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed_followups);


        for (int i = 0; i < lead_images.length; i++)
            ImageArray.add(lead_images[i]);
        recyclerViewMissed = (RecyclerView)findViewById(R.id.recycler_view_missed);
        missedRecycleViewAdapter = new MissedRecyclerViewAdapter(MissedItemsList,getApplicationContext());
//        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        userId=33;
//        imageUrl="http://202.88.239.14:8169/FileUploads/";
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt("UserId",userId);
//        editor.putString("token",Token);
//        editor.putString("imageURL",imageUrl);
//        editor.commit();

        requestQueue = Volley.newRequestQueue(this);
        url = "http://202.88.239.14:8169/api/Lead/missed_UpcomingfollowUplistings";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("UserId",userId);
        jsonParams.put("Missed_Upcoming_Dormant",0);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Status_missed = response.getString("status").toString().trim();
                        JSONArray jsonArray=response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            name1 = data.getString("firstName");
                            address = data.getString("address");
                            appointment_Date = data.getString("appointment_Date");
                            profileimage = data.getString("leadProfileimage");
                            String image=ImageUrl+profileimage;
                            String str3 = "Success";
                            int response_result = Status_missed.compareTo(str3);
                            if (response_result == 0) {

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerViewMissed.setLayoutManager(mLayoutManager);
                                recyclerViewMissed.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewMissed.setAdapter(missedRecycleViewAdapter);
                                missedItems = new MissedItems();

                                missedItems = new MissedItems(image,name1,appointment_Date ,address);
                                MissedItemsList.add(missedItems);

                            } else {
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                    // do something...
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization",Token);
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);


    }
   // private void prepareData()
//    {
//
//        missedItems = new MissedItems("R.drawable.profile_2",name1,appointment_Date ,address);
//        MissedItemsList.add(missedItems);
////       M missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets", appointment_Date,"12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
////        missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets","20 jan 2017 12.30 PM ","12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
////        missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets","20 jan 2017 12.30 PM ","12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
////        missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets","20 jan 2017 12.30 PM ","12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
////        missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets","20 jan 2017 12.30 PM ","12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
////        missedItems = new MissedItems("R.drawable.profile_2","Charlie Deets","20 jan 2017 12.30 PM ","12 shelter Bay Dr Great Neck Ny,United States");
////        MissedItemsList.add(missedItems);
//
//    }
}
