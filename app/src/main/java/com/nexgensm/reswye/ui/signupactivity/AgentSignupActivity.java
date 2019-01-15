package com.nexgensm.reswye.ui.signupactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.transition.Slide;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Nexmin on 14-02-2018.
 */

public class AgentSignupActivity extends AppCompatActivity {
    EditText mobileNo, StreetAddress, City, state, ZipCcde, referalcode;
    RequestQueue requestQueue;
    SwitchCompat signup_genderTxt;
    String genderValue,hwfindus;
    Spinner signup_findusTxt;
    String[] additionaldetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentsignup);

        ImageView backbutton = (ImageView) findViewById(R.id.signup_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }

        });
        Button Signup2 = (Button) findViewById(R.id.signup2_btn);

//gender
        //mobileNo = (EditText) findViewById(R.id.Signup_mobileNo);
        signup_genderTxt=(SwitchCompat)findViewById(R.id.signup_gender);
        StreetAddress = (EditText) findViewById(R.id.StreetAddress);
        City = (EditText) findViewById(R.id.City);
        state = (EditText) findViewById(R.id.state);
        ZipCcde = (EditText) findViewById(R.id.ZipCcde);
        referalcode = (EditText) findViewById(R.id.referalcode);
        signup_findusTxt=(Spinner)findViewById(R.id.signup_findus);
        hwfindus=signup_findusTxt.getSelectedItem().toString();
        signup_findusTxt.setSelection(0, true);
        additionaldetails = getResources().getStringArray(R.array.AddNewLead);
 final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_spinner_item, additionaldetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signup_findusTxt.setAdapter(adapter);
        if(signup_genderTxt.isChecked()){
            genderValue="Female";
        }
        else{
            genderValue="Male";
        }

        requestQueue = Volley.newRequestQueue(this);
        Signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // String mobileTxt = mobileNo.getText().toString();

               // String  Addresstxt = correspondance.getText().toString();


//                if ((mobileTxt.equals("")))
//                {
//                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                else{

                String url = "http://192.168.0.4:88/api/UserRegistration/CreateUser";
                Map<String, Object> jsonParams = new ArrayMap<>();
               // jsonParams.put("First_Name", Email);
              //  jsonParams.put("Last_Name", otp_mobtxt);
               // jsonParams.put("Email_Id", otp_emailtxt);
               // jsonParams.put("Password", Email);
                //jsonParams.put("UserType", otp_mobtxt);
               // jsonParams.put("Mobile", otp_emailtxt);
                jsonParams.put("Gender", genderValue);
                jsonParams.put("Street", StreetAddress);
                jsonParams.put("City", City);
                jsonParams.put("State", state);
                jsonParams.put("Zip", ZipCcde);
              //  jsonParams.put("Correspondence_Add", otp_mobtxt);
                jsonParams.put("HowfindAbtUs", hwfindus);
                jsonParams.put("Referral_Code", referalcode);
                //jsonParams.put("DeviceToken", otp_emailtxt);

                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String re = response.getString("status").toString().trim();
                                    String message = response.getString("message").toString().trim();
                                    String str3 = "Success";
                                    int result = re.compareTo(str3);
                                    if (result == 0) {
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(AgentSignupActivity.this, SubscriptionSignupActivity.class);
                                        startActivity(in);
                                        finish();
                                    } else {
                                        Intent in = new Intent(AgentSignupActivity.this, SubscriptionSignupActivity.class);
                                        startActivity(in);
                                        finish();
                                      //  Toast.makeText(getApplicationContext(),"hiiii"+ message, Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    //loading.dismiss();
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Intent in = new Intent(AgentSignupActivity.this, SubscriptionSignupActivity.class);
                                startActivity(in);

                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(request1);

//
//                Intent in = new Intent(AgentSignupActivity.this, AnimatedGifActivity.class);
//                startActivity(in);
               //  }


            }

        });



       /* mobileNo.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);
                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);
                }
            }
        });*/
        state.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });

        City.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });

        ZipCcde.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        StreetAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });


        referalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });


    }


    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        finish();
    }
}