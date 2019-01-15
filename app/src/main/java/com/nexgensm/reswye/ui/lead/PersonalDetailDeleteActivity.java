package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalDetailDeleteActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    int LeadId,userId,flag;
    String Token,url,Status_missed,ImageUrl;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_delete);
        ImageButton back= (ImageButton)findViewById(R.id.Personal_detail_delete_Back);
        Button delete= (Button)findViewById(R.id.yes_delete);
        Button cancel= (Button)findViewById(R.id.delete_cancel);
//        sharedpreferences =getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        LeadId=sharedpreferences.getInt("LeadId",0);
//        Token=sharedpreferences.getString("token","");
//        userId=sharedpreferences.getInt("UserId",0);
        requestQueue = Volley.newRequestQueue(this);
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        LeadId = sharedpreferences.getInt("LeadId", 1);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://202.88.239.14:8169/api/lead/deletelead";
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("userid",userId);
                jsonParams.put("leadId",LeadId);

                JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Status_missed = response.getString("status").toString().trim();
                                    String str3 = "Success";
                                    int response_result = Status_missed.compareTo(str3);
                                    if (response_result == 0) {

                                        Toast.makeText(getApplicationContext(),"Successfully Deleted Lead", Toast.LENGTH_SHORT).show();
                                        Intent selleractivity = new Intent(PersonalDetailDeleteActivity.this, BottomTabbarActivity.class);
                                        startActivity(selleractivity);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

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
                requestQueue.add(jsonObject);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete = new Intent(PersonalDetailDeleteActivity.this, BottomTabbarActivity.class);
                startActivity(delete);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
