package com.nexgensm.reswye.ui.signupactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.adapter.ChangeBackground;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.util.KeyboardUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity implements View.OnTouchListener  {
    public String mobile, email, first_name,last_name,password,retype_pasword,otp;
    ProgressDialog loading;
    EditText et_mobile,et_email,et_firstName,et_lastName,et_retypePassord,et_password;
    RelativeLayout layout;

    ProgressDialog pd;
    Button Signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));
        pd=new ProgressDialog(SignupActivity.this);

        layout=findViewById(R.id.layout);
        et_firstName=findViewById(R.id.first_name);
        et_lastName=findViewById(R.id.last_name);
        et_email=findViewById(R.id.signup_email);
        et_mobile=findViewById(R.id.mobilenum);
        et_password=findViewById(R.id.signup_Password);
        et_retypePassord=findViewById(R.id.signup_Retype);

        et_firstName.setOnTouchListener(this);
        et_lastName.setOnTouchListener(this);
        et_email.setOnTouchListener(this);
        et_mobile.setOnTouchListener(this);
        et_password.setOnTouchListener(this);
        et_retypePassord.setOnTouchListener(this);
        layout.setOnTouchListener(this);

        Signin = (Button) findViewById(R.id.next_btn);

        KeyboardUtils.hideKeyboard(this);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getText();
                Toast.makeText(SignupActivity.this, ""+checkValidation(), Toast.LENGTH_SHORT).show();
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

        if(first_name.equals(""))
        {
            et_firstName.setError("Please enter your First Name");
            ChangeBackground.setBack(et_firstName,this);
            ChangeBackground.setBack2(et_lastName,this);
            ChangeBackground.setBack2(et_email,this);
            ChangeBackground.setBack2(et_mobile,this);
            ChangeBackground.setBack2(et_password,this);
            ChangeBackground.setBack2(et_retypePassord,this);
            return false;
        }
        else if(last_name.equals(""))
        {
            ChangeBackground.setBack(et_lastName,this);
            ChangeBackground.setBack2(et_firstName,this);
            ChangeBackground.setBack2(et_email,this);
            ChangeBackground.setBack2(et_mobile,this);
            ChangeBackground.setBack2(et_password,this);
            ChangeBackground.setBack2(et_retypePassord,this);
            et_lastName.setError("Please enter your Last Name");
            return false;
        }
        else if(!isValidEmail(email)||email.equals(""))
        {
            ChangeBackground.setBack(et_email,this);
            ChangeBackground.setBack2(et_firstName,this);
            ChangeBackground.setBack2(et_lastName,this);
            ChangeBackground.setBack2(et_mobile,this);
            ChangeBackground.setBack2(et_password,this);
            ChangeBackground.setBack2(et_retypePassord,this);
            et_email.setError("Please enter a valid email");
            return false;
        }
        else if(mobile.length()!=10||mobile.equals(""))
        {
            ChangeBackground.setBack(et_mobile,this);
            ChangeBackground.setBack2(et_email,this);
            ChangeBackground.setBack2(et_firstName,this);
            ChangeBackground.setBack2(et_lastName,this);
            ChangeBackground.setBack2(et_password,this);
            ChangeBackground.setBack2(et_retypePassord,this);
            et_mobile.setError("Please enter a valid mobile no");
            return false;

        }
        else if(isValidPassword(password)||password.equals(""))
        {
            ChangeBackground.setBack(et_password,this);
            ChangeBackground.setBack2(et_email,this);
            ChangeBackground.setBack2(et_firstName,this);
            ChangeBackground.setBack2(et_lastName,this);
            ChangeBackground.setBack2(et_mobile,this);
            ChangeBackground.setBack2(et_retypePassord,this);
            et_password.setError("password must contain minimum of 1 lower case letter, upper case letter, numeric character and special character");
            return false;
        }

        else if(!password.equals(retype_pasword)||retype_pasword.equals(""))
        {
            ChangeBackground.setBack(et_retypePassord,this);
            ChangeBackground.setBack2(et_email,this);
            ChangeBackground.setBack2(et_firstName,this);
            ChangeBackground.setBack2(et_lastName,this);
            ChangeBackground.setBack2(et_mobile,this);
            ChangeBackground.setBack2(et_password,this);
            et_retypePassord.setError("Password mismatch");
            return false;
        }
        else
        {
            return true;

        }
    }

    private void goToAgentSignupActivity(int user_id) {
        pd.dismiss();
        Intent i=new Intent(getApplicationContext(),AgentSignupActivity.class);
        i.putExtra("user_id",user_id);
        i.putExtra("user_type","1");
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
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.getSigupresponse(new Request(first_name,"1",last_name,retype_pasword,password,email,mobile));
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    pd.dismiss();

                    String status=response.body().getStatus();
                    if(status.equals("success"))
                    {
                        Result result=response.body().getResult();
                        int user_id=result.getUser_Id();
                        int agent_id=result.getAgent_Id();
                        Toast.makeText(SignupActivity.this, "USER_ID "+user_id+" AGENT_ID "+agent_id, Toast.LENGTH_SHORT).show();
                        goToAgentSignupActivity(user_id);
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
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

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ChangeBackground obj=new ChangeBackground();
        float TIME_DELAY=2,lastY=0;
        switch (v.getId())
        {
            case R.id.first_name:
                // do your code
                ChangeBackground.setBack(v,this);
                ChangeBackground.setBack2(et_lastName,this);
                ChangeBackground.setBack2(et_email,this);
                ChangeBackground.setBack2(et_mobile,this);
                ChangeBackground.setBack2(et_password,this);
                ChangeBackground.setBack2(et_retypePassord,this);

                break;




            case R.id.last_name:
                // do your code
                ChangeBackground.setBack(v,this);
                ChangeBackground.setBack2(et_firstName,this);
                ChangeBackground.setBack2(et_email,this);
                ChangeBackground.setBack2(et_mobile,this);
                ChangeBackground.setBack2(et_password,this);
                ChangeBackground.setBack2(et_retypePassord,this);

                break;

            case R.id.signup_email:
                // do your code
                ChangeBackground.setBack2(et_firstName,this);
                ChangeBackground.setBack2(et_lastName,this);
                ChangeBackground.setBack(v,this);
                ChangeBackground.setBack2(et_mobile,this);
                ChangeBackground.setBack2(et_password,this);
                ChangeBackground.setBack2(et_retypePassord,this);

                break;
            case R.id.mobilenum:
                // do your code
                ChangeBackground.setBack2(et_firstName,this);
                ChangeBackground.setBack2(et_lastName,this);
                ChangeBackground.setBack2(et_email,this);
                ChangeBackground.setBack(v,this);
                ChangeBackground.setBack2(et_password,this);
                ChangeBackground.setBack2(et_retypePassord,this);

                break;
            case R.id.signup_Password:
                // do your code
                ChangeBackground.setBack2(et_firstName,this);
                ChangeBackground.setBack2(et_lastName,this);
                ChangeBackground.setBack2(et_email,this);
                ChangeBackground.setBack2(et_mobile,this);
                ChangeBackground.setBack(v,this);
                ChangeBackground.setBack2(et_retypePassord,this);

                break;
            case R.id.signup_Retype:
                // do your code
                ChangeBackground.setBack2(et_firstName,this);
                ChangeBackground.setBack2(et_lastName,this);
                ChangeBackground.setBack2(et_email,this);
                ChangeBackground.setBack2(et_mobile,this);
                ChangeBackground.setBack2(et_password,this);
                ChangeBackground.setBack(v,this);

                break;


        }
        return false;
    }


}





