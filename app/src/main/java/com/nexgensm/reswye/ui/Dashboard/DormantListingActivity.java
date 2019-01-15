package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DormantListingActivity extends AppCompatActivity {
    private RecyclerView recyclerDormantList;
    private DormantRecyclerViewAdapter dormantRecycleViewAdapter;
    private List<DormantItems> DormantItemsList = new ArrayList<>();
    Context context;
    private static final Integer[] lead_images= {R.drawable.profile_2,R.drawable.profile_3,R.drawable.profile_4,R.drawable.profile_5,R.drawable.profile_6,R.drawable.profile_6,R.drawable.profile_6};
    //private static final String[] lead_names = {"Charley Deets", "Mathew John", "Jaise Allen","Cheyl Tom","Steve David","Marina jones","Steve David"};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    DormantItems dormantItems;
    RequestQueue requestQueue;
    String result;
    String Token,Status_missed,name1,address,appointment_Date,profileimage;
    String url,ImageUrl;
    int userId,lead_ID;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormant_listing);

        for (int i = 0; i < lead_images.length; i++)
            ImageArray.add(lead_images[i]);
        recyclerDormantList = (RecyclerView)findViewById(R.id.recycler_view_dormantList);
        dormantRecycleViewAdapter = new DormantRecyclerViewAdapter(DormantItemsList,getApplicationContext());

        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
//        requestQueue = Volley.newRequestQueue(this);
//        url = "http://202.88.239.14:8169/api/Lead/GetdormantLeadListing/"+userId;
//        Map<String, Object> jsonParams = new ArrayMap<>();
////        jsonParams.put("UserID",userId);
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(jsonParams),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Status_missed = response.getString("status").toString().trim();
//                            JSONArray jsonArray=response.getJSONArray("data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject data = jsonArray.getJSONObject(i);
//
//                                name1 = data.getString("FirstName");
//                                address = data.getString("Address");
//                                appointment_Date = data.getString("Appointment_Date");
//
//                                String str3 = "Success";
//                                int response_result = Status_missed.compareTo(str3);
//                                if (response_result == 0) {
//
//                                    Toast.makeText(getApplicationContext(), "data" + lead_ID + name1 + address + appointment_Date, Toast.LENGTH_SHORT).show();
//
//
//                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                                    recyclerDormantList.setLayoutManager(mLayoutManager);
//                                    recyclerDormantList.setItemAnimator(new DefaultItemAnimator());
//                                    recyclerDormantList.setAdapter(dormantRecycleViewAdapter);
//                                    dormantItems = new DormantItems();
//                                    prepareData();
//
//
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
//                // do something...
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                final Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization",Token);
//                return headers;
//            }
//        };
//        requestQueue.add(jsonObjectRequest);
        requestQueue = Volley.newRequestQueue(this);
        url = "http://202.88.239.14:8169/api/Lead/missed_UpcomingfollowUplistings";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("UserId",userId);
        jsonParams.put("Missed_Upcoming_Dormant",2);

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
                                    recyclerDormantList.setLayoutManager(mLayoutManager);
                                    recyclerDormantList.setItemAnimator(new DefaultItemAnimator());
                                    recyclerDormantList.setAdapter(dormantRecycleViewAdapter);
                                    dormantItems = new DormantItems();

                                    dormantItems = new DormantItems(image,name1,appointment_Date ,address);
                                    DormantItemsList.add(dormantItems);

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
//    private void prepareData()
//    {
//
//        dormantItems = new DormantItems("R.drawable.profile_2",name1,appointment_Date ,address);
//        DormantItemsList.add(dormantItems);
//
//
//    }
}
