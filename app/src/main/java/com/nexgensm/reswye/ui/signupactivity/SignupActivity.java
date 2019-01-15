package com.nexgensm.reswye.ui.signupactivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.onboarding.OnBoardingActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.ui.signupactivity.AgentSignupActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    public String mobile, email, first_name,last_name,password,retype_pasword,otp;
    ProgressDialog loading;
    EditText et_mobile,et_email,et_firstName,et_lastName,et_retypePassord,et_password;


    ProgressDialog pd;
    Button Signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //this.setFinishOnTouchOutside(false);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));

        et_firstName=findViewById(R.id.first_name);
        et_lastName=findViewById(R.id.last_name);
        et_email=findViewById(R.id.first_name);
        et_mobile=findViewById(R.id.first_name);
        et_password=findViewById(R.id.first_name);
        et_retypePassord=findViewById(R.id.first_name);

        Signin = (Button) findViewById(R.id.next_btn);


        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "hiii", Toast.LENGTH_SHORT).show();
                pd=new ProgressDialog(SignupActivity.this);
                pd.setMessage("Loading");
                pd.show();
              //  postData();
              goToHome();

            }

        });


    }

    private void checkValidation()
    {
        //frstname lastname email phone password retypepassword

        if(first_name.equals("")||last_name.equals("")||email.equals("")||mobile.equals("")||password.equals("")||retype_pasword.equals(""))
        {
            et_firstName.setError("Please enter a valid name");
        }

        else if(!isValidEmail(email))
        {
            et_email.setError("Please enter a valid email");
        }
        else if(mobile.length()!=10)
        {
            et_mobile.setError("Please enter a valid mobile no");

        }
        else if(password.equals(""))
        {
            et_firstName.setError("Please enter a valid mobile no");

        }
        else if(last_name.equals(""))
        {
            et_firstName.setError("Please enter a valid name");
        }
    }

    private void goToHome() {
        pd.dismiss();
        Intent i=new Intent(getApplicationContext(),AgentSignupActivity.class);
        startActivity(i);
    }

    public void getText() {// getting text from editText.....
        mobile = et_mobile.getText().toString();
        email = et_email.getText().toString();
        first_name = et_firstName.getText().toString();
        last_name = et_lastName.getText().toString();
        password = et_password.getText().toString();
        retype_pasword = et_retypePassord.getText().toString();

    }
    public void postData() {
        try {
            getText();// get Input Text by the user
            String json;// storing json object

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobile);
            jsonObject.put("first_name", first_name);
            jsonObject.put("last_name", last_name);
            jsonObject.put("password", password);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            System.out.println("Server Request:-" + json);
            String url = "http://202.88.239.14:8169/api/userregistration/SendOTP";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "fa"+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}





