package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.nexgensm.reswye.ui.Dashboard.DormantItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OwnerInfoActivity extends AppCompatActivity {

    EditText firstname, lastname, contactnum, emailid;
    String url, Status_missed, Token,ImageUrl;
    Button saveOwnerInfo;
    int flag, userId, leadId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String FirstName, LastName, MobileNo, EmailID;
    RequestQueue requestQueue, editView, updateOwner, requestQueue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));

        ImageButton close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        leadId = sharedpreferences.getInt("LeadId", 1);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        updateOwner = Volley.newRequestQueue(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        contactnum = (EditText) findViewById(R.id.contactnum);
        emailid = (EditText) findViewById(R.id.emailid);


        saveOwnerInfo = (Button) findViewById(R.id.saveOwnerInfo);
        if (flag == 0) {


            saveOwnerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String firstName=firstname.getText().toString();
                    final  String LName=lastname.getText().toString();
                    final String ContactNU=contactnum.getText().toString();
                    final String emailTXT = emailid.getText().toString();
                    if (contactnum.length() == 10) {
                        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                        if ((emailTXT.matches(emailPattern))) {
                            url = "http://202.88.239.14:8169/api/lead/SaveLeadOwnerInfo";
                            Map<String, Object> jsonParams = new ArrayMap<>();
                            jsonParams.put("FirstName", firstName);
                            jsonParams.put("LastName", LName);
                            jsonParams.put("Lead_ID", leadId);
                            jsonParams.put("MobileNo", ContactNU);
                            jsonParams.put("EmailID", emailTXT);

                            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Status_missed = response.getString("status").toString().trim();
                                                String str3 = "Success";
                                                int response_result = Status_missed.compareTo(str3);
                                                if (response_result == 0) {

                                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
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
                            requestQueue.add(jsonObject);
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter valid email id", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {

            editView = Volley.newRequestQueue(this);
            url = "http://202.88.239.14:8169/api/Lead/GetLeadOwnerInfo/"+leadId;

            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Status_missed = response.getString("status").toString().trim();
                                JSONArray jsonArray = response.getJSONArray("data");

                                if (jsonArray.length() > 0) {
                                    JSONObject data = jsonArray.getJSONObject(0);

                                    FirstName = data.getString("firstName");
                                    LastName = data.getString("lastName");
                                    MobileNo = data.getString("mobileNo");
                                    EmailID = data.getString("emailID");
                                    firstname.setText(FirstName);
                                    lastname.setText(LastName);
                                    contactnum.setText(MobileNo);
                                    emailid.setText(EmailID);
                                }


                                String str3 = "Success";
                                int response_result = Status_missed.compareTo(str3);
                                if (response_result == 0) {

                                    if (jsonArray.length() > 0) {
                                        saveOwnerInfo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String Fname = firstname.getText().toString();
                                                String Lname = lastname.getText().toString();
                                                String Mob = contactnum.getText().toString();
                                                String Mail = emailid.getText().toString();
                                                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                                                if (Mob.length() == 10) {
                                                    if (Mail.matches(emailPattern)) {
                                                        url = "http://202.88.239.14:8169/api/Lead/UpdateLeadOwnerInfo";
                                                        Map<String, Object> jsonParams = new ArrayMap<>();
                                                        jsonParams.put("FirstName", Fname);
                                                        jsonParams.put("LastName", Lname);
                                                        jsonParams.put("MobileNo", Mob);
                                                        jsonParams.put("EmailID", Mail);
                                                        jsonParams.put("Lead_ID", leadId);

                                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        try {
                                                                            Status_missed = response.getString("status").toString().trim();


                                                                            String str3 = "Success";
                                                                            int response_result = Status_missed.compareTo(str3);
                                                                            if (response_result == 0) {
                                                                                Toast.makeText(getApplicationContext(), "Updated Owner info", Toast.LENGTH_SHORT).show();
                                                                                finish();

                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
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
                                                        updateOwner.add(jsonObjectRequest2);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "enter valid email id", Toast.LENGTH_SHORT).show();

                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "enter valid mobile number", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } else {
                                        saveOwnerInfo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String FnameE = firstname.getText().toString();
                                                String LnameE = lastname.getText().toString();
                                                String MobE = contactnum.getText().toString();
                                                String MailE = emailid.getText().toString();
                                                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                                                if (MobE.length() == 10) {
                                                    if (MailE.matches(emailPattern)) {
                                                        url = "http://202.88.239.14:8169/api/lead/SaveLeadOwnerInfo";
                                                        Map<String, Object> jsonParams = new ArrayMap<>();
                                                        jsonParams.put("FirstName", FnameE);
                                                        jsonParams.put("LastName", LnameE);
                                                        jsonParams.put("Lead_ID", leadId);
                                                        jsonParams.put("MobileNo", MobE);
                                                        jsonParams.put("EmailID", MailE);

                                                        JsonObjectRequest jsonObject2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        try {
                                                                            Status_missed = response.getString("status").toString().trim();
                                                                            String str3 = "Success";
                                                                            int response_result = Status_missed.compareTo(str3);
                                                                            if (response_result == 0) {

                                                                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
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
                                                        requestQueue2.add(jsonObject2);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "enter valid Email id", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Enter valid contact number", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    }

                                } else

                                {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
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
            editView.add(jsonObjectRequest1);
        }
    }
}
