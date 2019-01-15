package com.nexgensm.reswye.ui.lead;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.onboarding.OnBoardingActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

import java.util.ArrayList;

public class AddNewSellerActivity extends AppCompatActivity implements AddNewLeadFragment.OnFragmentInteractionListener,AddNewPropertySellerFragment.OnFragmentInteractionListener,AddNewUploadDocFragment.OnFragmentInteractionListener {


    AddNewLeadFragment addNewLeadFragment = new AddNewLeadFragment();
    AddNewPropertySellerFragment addNewPropertySellerFragment = new AddNewPropertySellerFragment();
    AddNewUploadDocFragment uploadfragment = new AddNewUploadDocFragment();
    private static final int READ_REQUEST_CODE = 42;
    int backstatus;
    Button leadbtn;
    Button propertybtn;
    Button uploadbtn;
    Drawable btn_click;
    Drawable btn_unclick;
     RelativeLayout relative_lyt_logged;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);
        leadbtn = (Button) findViewById(R.id.leadbtn);
        propertybtn = (Button) findViewById(R.id.propertybtn);
        uploadbtn = (Button) findViewById(R.id.upload);

        leadbtn.setBackground(btn_click);
        propertybtn.setBackground(btn_unclick);
        uploadbtn.setBackground(btn_unclick);
        leadbtn.setTextColor(Color.WHITE);
        propertybtn.setTextColor(Color.BLACK);
        uploadbtn.setTextColor(Color.BLACK);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Lead_Creation, addNewLeadFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        ImageButton backbtn = (ImageButton)findViewById(R.id.AddLead_Back);
        backbtn.setImageResource(R.drawable.status_bar_back_arrow);

//        if( getIntent().getExtras() != null) {
//
//
//            flag = getIntent().getExtras().getInt("flag");
//
//            String str = Integer.toString(flag);
//            String strlog = Integer.toString(flaglog);
//            Bundle bundle = new Bundle();
//            // bundle.putString("NAME_KEY", nameTxt.getText().toString());
//            bundle.putInt("flag", flag);
////            bundle.putInt("flaglog", flaglog);
//            addNewLeadFragment.setArguments(bundle);
//
//
//
//        }

        leadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leadbtn.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                uploadbtn.setBackground(btn_unclick);

                leadbtn.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                uploadbtn.setTextColor(Color.BLACK);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Lead_Creation, addNewLeadFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                ImageButton backbtn = (ImageButton)findViewById(R.id.AddLead_Back);
                backbtn.setImageResource(R.drawable.status_bar_back_arrow);

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

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Lead_Creation, addNewPropertySellerFragment);
                fragmentTransaction.commit();

                ImageButton backbtn = (ImageButton)findViewById(R.id.AddLead_Back);
                backbtn.setImageResource(android.R.color.transparent);
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

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransactionupload = fragmentManager.beginTransaction();
                fragmentTransactionupload.replace(R.id.Lead_Creation, uploadfragment);
                fragmentTransactionupload.addToBackStack(null);
                fragmentTransactionupload.commit();

                RelativeLayout currentLayout = (RelativeLayout) findViewById(R.id.buttonlayout);
                currentLayout.setBackgroundColor(Color.parseColor("#30D5C8"));

                ImageButton backbtn = (ImageButton)findViewById(R.id.AddLead_Back);
                backbtn.setImageResource(android.R.color.transparent);

            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));

        ImageButton back = (ImageButton) findViewById(R.id.AddLead_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//
//        Button addDocument = (Button) findViewById(R.id.add_doc);
//        addDocument.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDocument();
//            }
//        });
    }

//    private void getDocument() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/pdf");
//        startActivityForResult(intent, READ_REQUEST_CODE);
//
//    }
//
//    @Override
//    public void onActivityResult(int req, int result, Intent data) {
//
//        // TODO Auto-generated method stub
//        super.onActivityResult(req, result, data);
//
//        if (result == RESULT_OK) {
//
//            Uri fileuri = data.getData();
//
//            Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
//
//            try {
//                uploadfragment.generateImageFromPdf(fileuri, getApplicationContext());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
     finish();
    }


}


