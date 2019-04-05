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
import android.content.Intent;
import android.widget.TextView;


import com.nexgensm.reswye.R;
import com.nexgensm.reswye.util.SharedPrefsUtils;

public class BuyerDetailsActivity extends AppCompatActivity implements PropertyDetailsBuyerFragment.OnFragmentInteractionListener,PersonalDetailsBuyerFragment.OnFragmentInteractionListener,DocumentDetailsBuyerFragment.OnFragmentInteractionListener {


    FragmentManager fragmentManagerpersonal = getFragmentManager();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentManager fragmentManagerDocument = getFragmentManager();
    int  newId;
    int backstatus;
    Button Personal ;
    Button propertybtn;
    Button documents;
    Drawable btn_click;
    Drawable btn_unclick;
    Bundle bundle;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_details);

        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);

        Personal = (Button)findViewById(R.id.personalbtn);
        propertybtn = (Button)findViewById(R.id.propertybtn);
        documents = (Button)findViewById(R.id.documents);

        Personal.setBackground(btn_click);
        propertybtn.setBackground(btn_unclick);
        documents.setBackground(btn_unclick);

        Personal.setTextColor(Color.WHITE);
        propertybtn.setTextColor(Color.BLACK);
        documents.setTextColor(Color.BLACK);

        ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
        backbtnLead.setImageResource(R.mipmap.status_bar_back_arrow);
        Bundle extras = getIntent().getExtras();
        newId = SharedPrefsUtils.getInstance(getApplicationContext()).getLId();
        SharedPrefsUtils.getInstance(getApplicationContext()).setLid(newId);
        TextView toolbartxt = (TextView)findViewById(R.id.AddLead_Text);
        String newString;

        newString= extras.getString("lead_name");
        toolbartxt.setText(newString);

        bundle = new Bundle();
        bundle.putString("type", "buyer");
        PersonalDetailsBuyerFragment personalDetailsFragment = new PersonalDetailsBuyerFragment();

        FragmentTransaction fragmentPersonal = fragmentManagerpersonal.beginTransaction();
        fragmentPersonal.replace(R.id.Lead_Details, personalDetailsFragment);
        personalDetailsFragment.setArguments(bundle);

        fragmentPersonal.addToBackStack(null);
        fragmentPersonal.commit();



        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Personal.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                documents.setBackground(btn_unclick);

                Personal.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                documents.setTextColor(Color.BLACK);
                PersonalDetailsBuyerFragment personalDetailsFragment = new PersonalDetailsBuyerFragment();

                FragmentTransaction fragmentPersonal = fragmentManagerpersonal.beginTransaction();
                fragmentPersonal.replace(R.id.Lead_Details, personalDetailsFragment);
                bundle = new Bundle();
                bundle.putString("type", "buyer");

                personalDetailsFragment.setArguments(bundle);
                fragmentPersonal.addToBackStack(null);
                fragmentPersonal.commit();
                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(R.mipmap.status_bar_back_arrow);

            }
        });


        propertybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                propertybtn.setBackground(btn_click);
                Personal.setBackground(btn_unclick);
                documents.setBackground(btn_unclick);
                propertybtn.setTextColor(Color.WHITE);
                Personal.setTextColor(Color.BLACK);
                documents.setTextColor(Color.BLACK);
                PropertyDetailsBuyerFragment propertyDetailsFragment = new PropertyDetailsBuyerFragment();

                FragmentTransaction fragmentProperty = fragmentManager.beginTransaction();

                fragmentProperty.replace(R.id.Lead_Details, propertyDetailsFragment);
                bundle = new Bundle();
                bundle.putString("type", "buyer");

                propertyDetailsFragment.setArguments(bundle);
                fragmentProperty.addToBackStack(null);
                fragmentProperty.commit();
                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(0);


            }
        });


        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                documents.setBackground(btn_click);
                propertybtn.setBackground(btn_unclick);
                Personal.setBackground(btn_unclick);
                documents.setTextColor(Color.WHITE);
                propertybtn.setTextColor(Color.BLACK);
                Personal.setTextColor(Color.BLACK);
                ImageButton backbtnLead=(ImageButton)findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(0);
                DocumentDetailsBuyerFragment DocumentDetailsFragment = new DocumentDetailsBuyerFragment();

                FragmentTransaction fragmentdocument = fragmentManagerDocument.beginTransaction();

                fragmentdocument.replace(R.id.Lead_Details, DocumentDetailsFragment);
                bundle = new Bundle();
                bundle.putString("type", "buyer");

                DocumentDetailsFragment.setArguments(bundle);
                fragmentdocument.addToBackStack(null);
                fragmentdocument.commit();
                backbtnLead = (ImageButton) findViewById(R.id.AddLead_Back);
                backbtnLead.setImageResource(0);


            }
        });
        ImageButton back= (ImageButton)findViewById(R.id.AddLead_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        Button addDocument=(Button)findViewById(R.id.add_doc);
        addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

