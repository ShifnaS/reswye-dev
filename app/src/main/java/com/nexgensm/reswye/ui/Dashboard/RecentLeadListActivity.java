package com.nexgensm.reswye.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.LeadItems;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentLeadListActivity extends AppCompatActivity {
    private RecyclerView recyclerRecentLead;
    RequestQueue requestQueue;
    String result;
    String Token,Status_missed,name1,address,lead_CreatedDate,ImageUrl,profileimage;
    String url;
    int userId,lead_ID;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private RecentLeadRecyclerViewAdapter recentRecycleViewAdapter;
    Context context;
    private static final Integer[] lead_images= {R.drawable.profile_2,R.drawable.profile_3,R.drawable.profile_4,R.drawable.profile_5,R.drawable.profile_6,R.drawable.profile_6,R.drawable.profile_6};
    //private static final String[] lead_names = {"Charley Deets", "Mathew John", "Jaise Allen","Cheyl Tom","Steve David","Marina jones","Steve David"};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    private List<RecentItems> RecentItemsList = new ArrayList<>();

    RecentItems recentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_lead_list);

        for (int i = 0; i < lead_images.length; i++)
            ImageArray.add(lead_images[i]);
        recyclerRecentLead = (RecyclerView)findViewById(R.id.recycler_recent_leads);
        recentRecycleViewAdapter = new RecentLeadRecyclerViewAdapter(RecentItemsList,getApplicationContext());

        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        requestQueue = Volley.newRequestQueue(this);
        url = "http://202.88.239.14:8169/api/Lead/recentLeads/"+userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Status_missed = response.getString("status").toString().trim();


                            JSONArray jsonArray=response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                name1 = data.getString("firstName");
                                address = data.getString("address");
                                lead_CreatedDate = data.getString("lead_CreatedDate");
                                profileimage = data.getString("leadProfileimage");
                                String image=ImageUrl+profileimage;
                                String str3 = "Success";
                                int response_result = Status_missed.compareTo(str3);
                                if (response_result == 0)
                                {

                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerRecentLead.setLayoutManager(mLayoutManager);
                                    recyclerRecentLead.setItemAnimator(new DefaultItemAnimator());
                                    recyclerRecentLead.setAdapter(recentRecycleViewAdapter);
                                    recentItems = new RecentItems();

                                    recentItems = new RecentItems(image,name1,lead_CreatedDate ,address);
                                    RecentItemsList.add(recentItems);

                                }
                                else
                                {
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
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization",Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
//    private void prepareData()
//    {
//
//        recentItems = new RecentItems("R.drawable.profile_2",name1,lead_CreatedDate ,address);
//        RecentItemsList.add(recentItems);
//
//    }
}

