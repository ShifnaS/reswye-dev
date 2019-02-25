package com.nexgensm.reswye.ui.signupactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Nexmin on 14-02-2018.
 */

public class AgentSignupActivity extends AppCompatActivity {
    EditText  et_street, et_city, et_state, et_zipCcde, et_referalcode,et_address;
    RequestQueue requestQueue;
    SwitchCompat sc_gender;
    String gendString,hwfindusString,streetString,cityString,stateString,zipCodeString,referalCodeString,addressString,userTypeString;
    Spinner sp_findUs;
    String[] additionaldetails;
    Button bt_signUp;
    ProgressDialog pd;
    int userIdString;
    ScrollView ScrollView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentsignup);
        pd=new ProgressDialog(AgentSignupActivity.this);

        Intent i=getIntent();

        userIdString=i.getIntExtra("user_id",0);
        userTypeString=i.getStringExtra("user_type");

        ImageView backbutton = (ImageView) findViewById(R.id.signup_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }

        });
        bt_signUp =  findViewById(R.id.signup2_btn);
        sc_gender=findViewById(R.id.signup_gender);
        ScrollView01=findViewById(R.id.ScrollView01);
        et_address =  findViewById(R.id.address);
        et_street =  findViewById(R.id.StreetAddress);
        et_city =  findViewById(R.id.City);
        et_state =  findViewById(R.id.state);
        et_zipCcde =  findViewById(R.id.ZipCcde);
        et_referalcode =  findViewById(R.id.referalcode);
        
        sp_findUs=findViewById(R.id.signup_findus);
        hwfindusString=sp_findUs.getSelectedItem().toString();
        sp_findUs.setSelection(0, true);

        additionaldetails = getResources().getStringArray(R.array.AddNewLead);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_spinner_item, additionaldetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_findUs.setAdapter(adapter);

        setback();

        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
                Toast.makeText(getApplicationContext(), ""+checkValidation(), Toast.LENGTH_SHORT).show();
                if(checkValidation())
                {
                    pd.setMessage("Loading");
                    pd.show();
                    postData();
                }
            }
        });
    }

    private boolean checkValidation()
    {
        if(streetString.equals(""))
        {
            et_street.setError("Please enter a this field");
            return false;
        }

        else if(cityString.equals(""))
        {
            et_city.setError("Please enter a this field");
            return false;
        }
        else if(stateString.equals(""))
        {
            et_state.setError("Please enter a this field");
            return false;

        }
        else if(zipCodeString.equals("")||zipCodeString.length()!=6)
        {
            et_zipCcde.setError("Please enter a this field");
            return false;
        }
        else if(addressString.equals(""))
        {
            et_address.setError("Please enter a this field");
            return false;
        }
        else if(hwfindusString.equals("How did you find about us ?"))
        {
            Toast.makeText(this, "Please select find about us", Toast.LENGTH_SHORT).show();
            return false;

        }
       /* else if(referalCodeString.equals(""))
        {
            et_referalcode.setError("Please enter a this field");
            return false;
        }*/
        else
        {
            return true;

        }
    }
    public void getText()
    {// getting text from editText.....
        if(sc_gender.isChecked())
        {
            gendString="Female";
        }
        else{
            gendString="Male";
        }
        hwfindusString = sp_findUs.getSelectedItem().toString();
        streetString = et_street.getText().toString();
        cityString = et_city.getText().toString();
        stateString = et_state.getText().toString();
        zipCodeString = et_zipCcde.getText().toString();
        referalCodeString = et_referalcode.getText().toString();
        addressString = et_address.getText().toString();
    }

    public void postData() {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.getAgentresponse(new Request(userTypeString,zipCodeString,referalCodeString,hwfindusString,streetString,stateString,addressString,gendString,userIdString,cityString));
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    pd.dismiss();

                    String status=response.body().getStatus();
                    if(status.equals("success"))
                    {
                        Result result=response.body().getResult();
                        int user_id=result.getUser_Id();
                       // String agent_id=result.getAgent_Id();
                        Toast.makeText(AgentSignupActivity.this, "USERID "+user_id, Toast.LENGTH_SHORT).show();
                        SharedPrefsUtils.getInstance(getApplicationContext()).setUserId(user_id);
                        goToHome();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed to add ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
            pd.dismiss();
            e.printStackTrace();
        }
    }

    private void goToHome() {

        pd.dismiss();
        Intent i=new Intent(getApplicationContext(),BottomTabbarActivity.class);
        //i.putExtra("user_id",user_id);
       // i.putExtra("user_type","1");
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void setback() {
        et_street.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        et_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        et_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    ScrollView01.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    ScrollView01.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        et_zipCcde.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        }); et_state.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        }); et_referalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.spinner_bg_focus);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_bg);
                }
            }
        });


    }

}