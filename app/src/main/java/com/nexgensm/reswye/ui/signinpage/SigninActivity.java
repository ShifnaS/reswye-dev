package com.nexgensm.reswye.ui.signinpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.adapter.ChangeBackground;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.signupactivity.SignupActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener  {
    EditText et_email,et_password;
    TextView tv_signUp;
    Button bt_signIn;
    public String emailString="",passwordString="";
    public static final String mypreference = "mypref";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);
        pd = new ProgressDialog(SigninActivity.this);

        et_email =  findViewById(R.id.Login_Emailid);
        et_password =  findViewById(R.id.Login_Password);
        tv_signUp =findViewById(R.id.Signup_link);
        bt_signIn = (Button) findViewById(R.id.Signin_Btm);

        bt_signIn.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Signup_link:
                Intent signinactivity = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(signinactivity);
                break;
            case R.id.Signin_Btm:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {

        goToHome();

        getLoginText();
        //Toast.makeText(SigninActivity.this, ""+checkLoginValidation(), Toast.LENGTH_SHORT).show();
        if(checkLoginValidation())
        {

            postData();
        }
    }

    public void getLoginText() {
        emailString = et_email.getText().toString();
        passwordString = et_password.getText().toString();
    }
    private boolean checkLoginValidation()
    {
        if(emailString.equals(""))
        {
            et_email.setError("Please enter your Email id");
           // ChangeBackground.setBack(et_email,this);
           // ChangeBackground.setBack2(et_password,this);

            return false;
        }
        else if(passwordString.equals(""))
        {
            et_password.setError("Please enter your password");
          //  ChangeBackground.setBack2(et_email,this);
          //  ChangeBackground.setBack(et_password,this);
            return false;
        }
        else
        {
            return true;

        }
    }

    private void postData() {

        pd.setMessage("Loading");
        pd.show();
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.getLoginresponse(new Request(emailString,passwordString));
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
                        String message=result.getMessage();
                        Toast.makeText(getApplicationContext(), agent_id+" "+user_id, Toast.LENGTH_SHORT).show();
                        SharedPrefsUtils.getInstance(getApplicationContext()).setUserId(agent_id);
                        SharedPrefsUtils.getInstance(getApplicationContext()).setAgentId(user_id);

                        goToHome();
                    }
                    else
                    {
                        Result result=response.body().getResult();
                        String message=result.getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
        startActivity(i);
    }




    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        return false;
    }
}