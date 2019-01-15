package com.nexgensm.reswye.ui.onboarding;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.nexgensm.reswye.R;


import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.lead.AddNewLeadCategoryActivity;
import com.nexgensm.reswye.ui.lead.LeadFindActivity;
import com.nexgensm.reswye.ui.lead.LeadFragment;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

import com.nexgensm.reswye.ui.signupactivity.SignupActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.USE_FINGERPRINT;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OnBoardingActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] Image= {R.drawable.onboardingscreen1,R.drawable.onboardingscreen2,R.drawable.onboardingscreen33};
    private static final String[] titles = {"We care about you and your business", "Track your leads and reap the benefits", "Overwhelming leads? We do care"};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();

    private Button Signin;
    private Button Signup;
    private View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        final Drawable btn_click = getResources().getDrawable(R.drawable.white_round_button_click);
        final Drawable btn_unclick = getResources().getDrawable(R.drawable.white_round_button);
        Signin = (Button)findViewById(R.id.signin_button);
        Signup = (Button)findViewById(R.id.signup_button);

        if (checkPermission()) {
            Toast.makeText(getApplication(),"Permission already granted",Toast.LENGTH_SHORT).show();

//            Snackbar.make(this, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else {

            requestPermission();
        }

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin.setBackground(btn_unclick);
                Signup.setBackground(btn_click);

                Signup.setTextColor(Color.WHITE);
                Signin.setTextColor(getResources().getColor(R.color.ButtonTextColor));

                Intent signupintent = new Intent(OnBoardingActivity.this, SignupActivity.class);
                startActivity(signupintent);

            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Signin.setBackground(btn_click);
                Signup.setBackground(btn_unclick);

                Signin.setTextColor(Color.WHITE);
                Signup.setTextColor(getResources().getColor(R.color.ButtonTextColor));

                 Intent signinintent = new Intent(OnBoardingActivity.this, SigninActivity.class);
               startActivity(signinintent);
                //Intent signinintent = new Intent(OnBoardingActivity.this, BottomTabbarActivity.class);
                //startActivity(signinintent);
            }
        });

        init();

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.onboardingstatusbarcolor));


    }

    private void init() {
        for (int i = 0; i < Image.length; i++)
            ImageArray.add(Image[i]);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new OnBoardingAdapter(OnBoardingActivity.this, ImageArray, titles));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Image.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);
    }

////////////////Permissison //////////////////////////////////
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result7= ContextCompat.checkSelfPermission(getApplicationContext(),USE_FINGERPRINT);


        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,READ_CONTACTS,ACCESS_COARSE_LOCATION,USE_FINGERPRINT}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorageAccepted = grantResults[2]== PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[3]== PackageManager.PERMISSION_GRANTED;
                    boolean readContactsAccepted = grantResults[4]== PackageManager.PERMISSION_GRANTED;
                    boolean coarselocationAccepted = grantResults[5]== PackageManager.PERMISSION_GRANTED;
                    boolean fingerprintAccepted = grantResults[6]== PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && readStorageAccepted&& writeStorageAccepted&& readContactsAccepted&& coarselocationAccepted&& fingerprintAccepted)
                        Toast.makeText(getApplication(),"Permission granted",Toast.LENGTH_SHORT).show();
                    //  Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplication(),"Permission requested",Toast.LENGTH_SHORT).show();

                    //    Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,READ_CONTACTS,ACCESS_COARSE_LOCATION,USE_FINGERPRINT},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(OnBoardingActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
