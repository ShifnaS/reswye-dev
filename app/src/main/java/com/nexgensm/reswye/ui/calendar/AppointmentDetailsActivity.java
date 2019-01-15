package com.nexgensm.reswye.ui.calendar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.lead.AddNewSellerActivity;
import com.nexgensm.reswye.ui.lead.PersonalDetailDeleteActivity;
import com.nexgensm.reswye.ui.lead.PersonalDetailTransferActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppointmentDetailsActivity extends AppCompatActivity {
    String Token, ImageUrl, url, responseAppoinment, profile_image;
    int newId, userId;
    Button cancelbtn;
    Button reshedule;
    ImageView lead_pic;
    RequestQueue requestQueue,deleteQueue;
    ProgressDialog progressDialog;
    TextView leadnameTxt, leadidTxt, addressTxt, appointmentidTxt, appointmentdateTxt, appointmenttimeTxt, remaindersetonTxt, purposeTxt, meeting_MinutesTxt;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        Bundle extras = getIntent().getExtras();
        newId = extras.getInt("LeadId", 0);

//        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
//        ImageUrl = sharedpreferences.getString("imageURL", "");
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        requestQueue = Volley.newRequestQueue(this);
        deleteQueue = Volley.newRequestQueue(this);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("LeadId", newId);
        //  Toast.makeText(getApplicationContext(), newId, Toast.LENGTH_SHORT).show();
        editor.commit();


        lead_pic = (ImageView) findViewById(R.id.lead_pic);
        leadnameTxt = (TextView) findViewById(R.id.leadname);
        leadidTxt = (TextView) findViewById(R.id.leadid);
        addressTxt = (TextView) findViewById(R.id.address);
        appointmentidTxt = (TextView) findViewById(R.id.appointmentid);
        appointmentdateTxt = (TextView) findViewById(R.id.appointmentdate);
        appointmenttimeTxt = (TextView) findViewById(R.id.appointmenttime);
        remaindersetonTxt = (TextView) findViewById(R.id.remainderseton);
        purposeTxt = (TextView) findViewById(R.id.purpose);
        meeting_MinutesTxt = (TextView) findViewById(R.id.meeting_minutesTxt);

        progressDialog=new ProgressDialog(AppointmentDetailsActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
////////////////////APPOINTMENT DETAILS DISPLAY/////////////////////////////////////

        url = "http://202.88.239.14:8169/api/Lead/GetReminderdetails";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("lead_id", newId);
        jsonParams.put("userid", userId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseAppoinment = response.getString("status").toString().trim();
                            JSONArray jsonArray = response.getJSONArray("data");

                            JSONObject data = jsonArray.getJSONObject(0);

                            profile_image = data.getString("leadProfileimage");
                            final String location = data.getString("address");
                            final String namer = data.getString("firstName");
                            final String category_lead = data.getString("lead_Category");
                            final String appointMent_ID = data.getString("appointMent_ID");
                            final String appointment_Date = data.getString("appointment_Date");
                            final String appointment_Time = data.getString("appointment_Time");
                            final String purposeOf_Meeting = data.getString("purposeOf_Meeting");
                            final String appointmentAdded_Date = data.getString("appointmentAdded_Date");
                            final String meeting_min = data.getString("meeting_minutes");
                            final String leadiD= String.valueOf(newId);

                            String image = ImageUrl + profile_image;
                            String str3 = "Success";
                            int response_result = responseAppoinment.compareTo(str3);
                            if (response_result == 0) {
                                progressDialog.dismiss();

                                leadidTxt.setText(leadiD);
                                leadnameTxt.setText(namer);
                                addressTxt.setText(location);
                                appointmentidTxt.setText(appointMent_ID);
                                appointmentdateTxt.setText(appointment_Date);
                                appointmenttimeTxt.setText(appointment_Time);
                                purposeTxt.setText(purposeOf_Meeting);
                                meeting_MinutesTxt.setText(meeting_min);
                                Picasso.with(getApplicationContext()).load(image).into(lead_pic);


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error"+error.toString(), Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

        cancelbtn = (Button) findViewById(R.id.cancel_btn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent cancelactivity = new Intent(getApplicationContext(), cancelActivity.class);
                // startActivity(cancelactivity);

            }
        });
        reshedule = (Button) findViewById(R.id.reshedule_btn);
        reshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reshedule = new Intent(getApplicationContext(), EditApointmentActivity.class);
                startActivity(reshedule);

            }
        });

        final ImageView imgmenu = (ImageView) findViewById(R.id.threedots);
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), imgmenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.personaldetailsmenu1, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {
                        CharSequence title = item.getTitle();
                        String strtitle = title.toString();
                        if (strtitle.matches("Edit")) {

                            //Toast.makeText(myFragmentView.getContext(),"edit",Toast.LENGTH_SHORT).show();
                            Intent editappactivity = new Intent(AppointmentDetailsActivity.this, EditApointmentActivity.class);
                            startActivity(editappactivity);

                        }


                        if (strtitle.matches("Delete")) {
///////////////////////////CANCEL APPOINTMENT//////////////////////////////////
progressDialog.show();
                            url = "http://202.88.239.14:8169/api/Lead/cancelAppointment";
                            Map<String, Object> jsonParams = new ArrayMap<>();
                            jsonParams.put("Lead_ID", newId);
                            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                responseAppoinment = response.getString("status").toString().trim();
                                                String message=response.getString("message").toString().trim();
                                                String str3 = "Success";
                                                int response_result = responseAppoinment.compareTo(str3);
                                                if (response_result == 0) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                else
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }

                                            } catch (JSONException e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Exception"+e.toString(), Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Volley Error"+error.toString(), Toast.LENGTH_SHORT).show();
                                    // do something...
                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    final Map<String, String> headers = new HashMap<>();
                                    headers.put("Authorization", Token);
                                    return headers;
                                }
                            };
                            deleteQueue.add(jsonObjectRequest1);

                        }
                        //Toast.makeText(myFragmentView.getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });


    }


}
