package com.nexgensm.reswye.ui.lead;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.util.SharedPrefsUtils;


public class AddNewBuyerActivity extends AppCompatActivity implements AddNewLeadFragment.OnFragmentInteractionListener,AddNewPropertyBuyerFragment.OnFragmentInteractionListener,AddNewUploadDocFragment.OnFragmentInteractionListener {

    AddNewBuyerFragment addNewLeadFragment = new AddNewBuyerFragment();
    FragmentManager fragmentManager = getFragmentManager();

    AddNewPropertyBuyerFragment propertyfragment=new AddNewPropertyBuyerFragment();
    FragmentManager fragmentManagerProperty=getFragmentManager();

    AddNewUploadDocFragment uploadfragment=new AddNewUploadDocFragment();
    FragmentManager fragmentManagerUpload=getFragmentManager();
    int backstatus;
    int flag;
    Button leadbtn ;
    Button propertybtn;
    Button uploadbtn;
    Drawable btn_click;
    Drawable btn_unclick;
    private static final int READ_REQUEST_CODE = 42;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_buyer);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.toolbarcolor));
        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);

         leadbtn = (Button)findViewById(R.id.leadbtn);
         propertybtn = (Button)findViewById(R.id.propertybtn);
         uploadbtn = (Button)findViewById(R.id.upload);
        leadbtn.setBackground(btn_click);
        propertybtn.setBackground(btn_unclick);
        uploadbtn.setBackground(btn_unclick);
        leadbtn.setTextColor(Color.WHITE);
        propertybtn.setTextColor(Color.BLACK);
        uploadbtn.setTextColor(Color.BLACK);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Lead_Creation, addNewLeadFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
        backbtnLead.setImageResource(R.mipmap.status_bar_back_arrow);

        leadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leadbtn.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                uploadbtn.setBackground(btn_unclick);

                leadbtn.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                uploadbtn.setTextColor(Color.BLACK);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Lead_Creation, addNewLeadFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(R.mipmap.status_bar_back_arrow);



            }
        });


        propertybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                propertybtn.setBackground(btn_click);
                leadbtn.setBackground(btn_unclick);
                uploadbtn.setBackground(btn_unclick);
                propertybtn.setTextColor(Color.WHITE);
                leadbtn.setTextColor(Color.BLACK);
                uploadbtn.setTextColor(Color.BLACK);


               FragmentTransaction fragmentTransactionproperty=fragmentManagerProperty.beginTransaction();
               fragmentTransactionproperty.replace(R.id.Lead_Creation, propertyfragment);
               fragmentTransactionproperty.addToBackStack(null);


                 fragmentTransactionproperty.commit();
                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(0);


            }
        });


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadbtn.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                leadbtn.setBackground(btn_unclick);
                uploadbtn.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                leadbtn.setTextColor(Color.BLACK);

                FragmentTransaction fragmentTransactionupload=fragmentManagerUpload.beginTransaction();
                fragmentTransactionupload.replace(R.id.Lead_Creation, uploadfragment);
                fragmentTransactionupload.addToBackStack(null);

                fragmentTransactionupload.commit();

                RelativeLayout currentLayout = (RelativeLayout) findViewById(R.id.buttonlayout);
                currentLayout.setBackgroundColor(Color.parseColor("#30D5C8"));

                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(0);


            }
        });
        ImageButton back= (ImageButton)findViewById(R.id.AddLead_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

@Override
    public void onFragmentInteraction(Uri uri){

    }
    @Override
    public void onBackPressed() {
        finish();
    }

}

