package com.nexgensm.reswye.ui.lead;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

public class SellerDetailsActivity extends AppCompatActivity implements PersonalDetailsFragment.OnFragmentInteractionListener, PropertyDetailsSellerFragment.OnFragmentInteractionListener, DocumentDetailsBuyerFragment.OnFragmentInteractionListener {


    //PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
    //  PropertyDetailsSellerFragment propertyDetailsSellerFragment = new PropertyDetailsSellerFragment();
//    DocumentDetailsBuyerFragment documentDetailsSellerFragment = new DocumentDetailsBuyerFragment();
    Button personalbtn;
    Button propertybtn;
    Button docbtn;
    Drawable btn_click;
    Drawable btn_unclick;
    int newId;
    int stackCount;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private String TAG = "SellerDetailsActivity";
    private String Person = "Personal Frag";
    private String Property = "Property Frag";
    private String Document = "Document Frag";

    //PropertyDetailsSellerFragment propertyDetailsSellerFragment=new PropertyDetailsSellerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "ACTIVITY START");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);


        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);

        personalbtn = (Button) findViewById(R.id.personalbtn);
        propertybtn = (Button) findViewById(R.id.propertybtn);
        docbtn = (Button) findViewById(R.id.upload);

        personalbtn.setBackground(btn_click);
        propertybtn.setBackground(btn_unclick);
        docbtn.setBackground(btn_unclick);

        personalbtn.setTextColor(Color.WHITE);
        propertybtn.setTextColor(Color.BLACK);
        docbtn.setTextColor(Color.BLACK);

        TextView toolbartxt = (TextView) findViewById(R.id.leadSellerDetailsToolbartxt);

        Bundle extras = getIntent().getExtras();
        newId = extras.getInt("LeadId", 0);
        SharedPrefsUtils.getInstance(getApplicationContext()).setLeadId(newId);
        //  Toast.makeText(getApplicationContext(), newId, Toast.LENGTH_SHORT).show();
        //      toolbartxt.setText(newId);
       // sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

       /* SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("LeadId", newId);*/
        Log.v(TAG,"id"+newId);
      //  editor.commit();


        PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("leadId", newId);
        personalDetailsFragment.setArguments(bundle);
        changeFragment(personalDetailsFragment, Person,newId);


        ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
        backbtn.setImageResource(R.drawable.status_bar_back_arrow);

        personalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalbtn.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                docbtn.setBackground(btn_unclick);

                personalbtn.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                docbtn.setTextColor(Color.BLACK);

                PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("leadId", newId);
                personalDetailsFragment.setArguments(bundle);
                changeFragment(personalDetailsFragment, Person,newId);

                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
                backbtn.setImageResource(R.drawable.status_bar_back_arrow);
            }
        });


        propertybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                propertybtn.setBackground(btn_click);
                personalbtn.setBackground(btn_unclick);
                docbtn.setBackground(btn_unclick);

                propertybtn.setTextColor(Color.WHITE);
                personalbtn.setTextColor(Color.BLACK);
                docbtn.setTextColor(Color.BLACK);
                //final FragmentManager fragmentManagerProperty=getFragmentManager();


                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
                backbtn.setImageResource(android.R.color.transparent);


                PropertyDetailsSellerFragment propertyDetailsSellerFragment = new PropertyDetailsSellerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("leadId", newId);
                propertyDetailsSellerFragment.setArguments(bundle);
                changeFragment(propertyDetailsSellerFragment, Property,newId);


            }
        });


        docbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                docbtn.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                personalbtn.setBackground(btn_unclick);

                docbtn.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                personalbtn.setTextColor(Color.BLACK);

                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
                backbtn.setImageResource(android.R.color.transparent);


                DocumentDetailsBuyerFragment documentDetailsSellerFragment = new DocumentDetailsBuyerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("leadId", newId);
                documentDetailsSellerFragment.setArguments(bundle);
                changeFragment(documentDetailsSellerFragment, Document,newId);

            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        // startActivity(new Intent(SellerDetailsActivity.this, BottomTabbarActivity.class));
       SellerDetailsActivity.super.onBackPressed();
        finish();

//                Bundle data = new Bundle();
//                data.putInt("leadId",newId);
//                documentDetailsSellerFragment.setArguments(data);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.addToBackStack(null);

    }



    public void changeFragment(Fragment fragment, String tag,int lead_id) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Lead_Details, fragment);

        stackCount = fragmentManager.getBackStackEntryCount();
        Log.v(TAG, tag + stackCount);
        fragmentTransaction.commit();

    }


}

