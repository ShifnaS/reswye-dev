package com.nexgensm.reswye.ui.lead;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

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
    ProgressDialog pd;

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
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
       // leadId = sharedpreferences.getInt("LeadId", 1);
        leadId=SharedPrefsUtils.getInstance(getApplicationContext()).getLeadId();

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        updateOwner = Volley.newRequestQueue(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        contactnum = (EditText) findViewById(R.id.contactnum);
        emailid = (EditText) findViewById(R.id.emailid);


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
                        jsonParams.put("lead_id", leadId);
                        jsonParams.put("phone", MobE);
                        jsonParams.put("email", MailE);

                        JsonObjectRequest jsonObject2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        pd.dismiss();

                                        try {

                                          /*  {
                                                "status": "success",
                                                    "result": {
                                                "owner_id": 7,
                                                        "lead_id": 15
                                            }
                                            }

*/
                                            Status_missed = response.getString("status").toString().trim();
                                            String str3 = "success";
                                            if (Status_missed.equals(str3)) {

                                                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                                finish();
                                                firstname.setText("");
                                                lastname.setText("");
                                                contactnum.setText("");
                                                emailid.setText("");
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
