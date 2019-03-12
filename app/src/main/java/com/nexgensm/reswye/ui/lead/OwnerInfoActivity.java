package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.ui.Dashboard.DormantItems;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OwnerInfoActivity extends AppCompatActivity {

    EditText firstname, lastname, contactnum, emailid;
    String url, Status_missed, Token,ImageUrl;
    Button saveOwnerInfo;
    int  userId, leadId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String FirstName, LastName, MobileNo, EmailID;
    RequestQueue requestQueue, editView, updateOwner, requestQueue2;
    ProgressDialog pd;
    int flag=0;
    int lid=0;
    int ownerid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));
        pd = new ProgressDialog(OwnerInfoActivity.this);

        ImageButton close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
       // leadId = sharedpreferences.getInt("LeadId", 1);
        leadId=SharedPrefsUtils.getInstance(getApplicationContext()).getLeadId();
        pd = new ProgressDialog(OwnerInfoActivity.this);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        updateOwner = Volley.newRequestQueue(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        contactnum = (EditText) findViewById(R.id.contactnum);
        emailid = (EditText) findViewById(R.id.emailid);
        flag=SharedPrefsUtils.getInstance(getApplicationContext()).getFlag();
        lid=SharedPrefsUtils.getInstance(getApplicationContext()).getLId();
        if (flag==1)
        {
            viewdata();
        }

        saveOwnerInfo = (Button) findViewById(R.id.saveOwnerInfo);
        saveOwnerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("loading");
                pd.show();
                String FnameE = firstname.getText().toString();
                String LnameE = lastname.getText().toString();
                String MobE = contactnum.getText().toString();
                String MailE = emailid.getText().toString();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (MobE.length() == 10) {
                    if (MailE.matches(emailPattern)) {
                        url ="http://192.168.0.3:3000/reswy/addownerinfo";;
                        Map<String, Object> jsonParams = new ArrayMap<>();
                        jsonParams.put("firstname", FnameE);
                        jsonParams.put("lastname", LnameE);
                        jsonParams.put("phone", MobE);
                        jsonParams.put("email", MailE);
                        jsonParams.put("flag", flag);
                      //  jsonParams.put("lid", lid);
                        if(flag==0)
                        {
                            jsonParams.put("lead_id", leadId);

                        }
                        else
                        {
                            jsonParams.put("lead_id", lid);

                        }


                        JsonObjectRequest jsonObject2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        pd.dismiss();

                                        try {


                                            Status_missed = response.getString("status").toString().trim();
                                            if (Status_missed.equals("success")) {
                                                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();

                                                if(flag==0)
                                                {
                                                    finish();
                                                    firstname.setText("");
                                                    lastname.setText("");
                                                    contactnum.setText("");
                                                    emailid.setText("");
                                                }
                                                else
                                                {
                                                    viewdata();
                                                }
                                            } else {
                                                firstname.setText("");
                                                lastname.setText("");
                                                contactnum.setText("");
                                                emailid.setText("");
                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();

                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                // do something...
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                pd.dismiss();

                                final Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", Token);
                                return headers;
                            }
                        };
                        requestQueue2.add(jsonObject2);
                    } else {
                        pd.dismiss();

                        Toast.makeText(getApplicationContext(), "enter valid Email id", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    pd.dismiss();

                    Toast.makeText(getApplicationContext(), "Enter valid contact number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void viewdata() {

        final ArrayList<String> stringArrayList=new ArrayList<String>();
        if(flag==1)
        {
           // tv_additionaldetails.setText("How did you find about us?");
            String url = "http://192.168.0.3:3000/reswy/ownerlist/"+lid;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                    new com.android.volley.Response.Listener<JSONObject>()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("status").toString().trim();

                                if(status.equals("success")) {

                                    JSONArray jsonArray = response.getJSONArray("result");
                                    JSONObject data = jsonArray.getJSONObject(0);
                                    String firstName = data.getString("firstname");
                                    String lastName = data.getString("lastname");
                                    String lead_id = data.getString("lead_id");
                                    String mobileNo = data.getString("mobileno");
                                    String emailID = data.getString("emailid");
                                    ownerid = data.getInt("owner_id");

                                   firstname.setText(firstName);
                                   lastname.setText(lastName);
                                   contactnum.setText(mobileNo);
                                   emailid.setText(emailID);


                                }

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getActivity(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
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
    }

    private void setback() {
        firstname.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        lastname.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        contactnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });

    }
}
