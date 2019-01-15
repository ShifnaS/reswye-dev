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

        //  Toast.makeText(getApplicationContext(), newId, Toast.LENGTH_SHORT).show();
        //      toolbartxt.setText(newId);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("LeadId", newId);
        Log.v(TAG,"id"+newId);
        editor.commit();

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.Lead_Details, personalDetailsFragment);
////        Bundle data = new Bundle();
////        data.putInt("leadId",newId);
////        personalDetailsFragment.setArguments(data);//Set bundle data to fragment
//        stackCount =fragmentManager.getBackStackEntryCount();
//        Log.v(TAG,"Frag A, count"+stackCount);
//       fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
        changeFragment(personalDetailsFragment, Person);
//
//       /* PersonalDetailsFragment personaldetailsfragment=new PersonalDetailsFragment();
//        FragmentManager fragmentManagerProperty=getFragmentManager();
//        FragmentTransaction fragmentTransactionproperty=fragmentManagerProperty.beginTransaction();
//        fragmentTransactionproperty.replace(R.id.Lead_Creation, personaldetailsfragment);
//        fragmentTransactionproperty.addToBackStack(null);
//        fragmentTransactionproperty.commit();*/

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
//
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.Lead_Details, personalDetailsFragment);
//                fragmentManager.getBackStackEntryCount();
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                personalDetailsFragment.setArguments(data);
//               fragmentTransaction.addToBackStack(null);
//                stackCount =fragmentManager.getBackStackEntryCount();
//                Log.v(TAG,"Frag A click, count"+stackCount);
//                fragmentTransaction.commit();
                PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();

                changeFragment(personalDetailsFragment, Person);


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

//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction1= fragmentManager.beginTransaction();
//                fragmentTransaction1.replace(R.id.Lead_Details, propertyDetailsSellerFragment);
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                propertyDetailsSellerFragment.setArguments(data);
//               fragmentTransaction1.addToBackStack(null);
//                stackCount =fragmentManager.getBackStackEntryCount();
//                Log.v(TAG,"Frag B, count"+stackCount);
//                fragmentTransaction1.commit();
                PropertyDetailsSellerFragment propertyDetailsSellerFragment = new PropertyDetailsSellerFragment();

                changeFragment(propertyDetailsSellerFragment, Property);


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

//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.Lead_Details, documentDetailsSellerFragment);
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                documentDetailsSellerFragment.setArguments(data);
//                fragmentTransaction.addToBackStack(null);
//                stackCount =fragmentManager.getBackStackEntryCount();
//                Log.v(TAG,"Frag C, count"+stackCount);
//                fragmentTransaction.commit();
                DocumentDetailsBuyerFragment documentDetailsSellerFragment = new DocumentDetailsBuyerFragment();

                changeFragment(documentDetailsSellerFragment, Document);

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



    public void changeFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Lead_Details, fragment);
//                Bundle data = new Bundle();
//                data.putInt("leadId",newId);
//                documentDetailsSellerFragment.setArguments(data);
        // fragmentTransaction.addToBackStack(null);
        stackCount = fragmentManager.getBackStackEntryCount();
        Log.v(TAG, tag + stackCount);
        fragmentTransaction.commit();

    }


}


//package com.nexgensm.reswye.ui.lead;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.nexgensm.reswye.R;
//
//public class SellerDetailsActivity extends AppCompatActivity implements PersonalDetailsFragment.OnFragmentInteractionListener, PropertyDetailsSellerFragment.OnFragmentInteractionListener, DocumentDetailsBuyerFragment.OnFragmentInteractionListener {
//
//
//    private String TAG = "SellerDetailsActivity";
//    PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
//    PropertyDetailsSellerFragment propertyDetailsSellerFragment = new PropertyDetailsSellerFragment();
//    DocumentDetailsBuyerFragment documentDetailsSellerFragment = new DocumentDetailsBuyerFragment();
//    Button personalbtn;
//    Button propertybtn;
//    Button docbtn;
//    Drawable btn_click;
//    Drawable btn_unclick;
//    int newId;
//    SharedPreferences sharedpreferences;
//    public static final String mypreference = "mypref";
//    FragmentManager fragmentManager;
//
//
//    //PropertyDetailsSellerFragment propertyDetailsSellerFragment=new PropertyDetailsSellerFragment();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_seller_details);
//
//
//        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
//        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);
//
//        personalbtn = (Button) findViewById(R.id.personalbtn);
//        propertybtn = (Button) findViewById(R.id.propertybtn);
//        docbtn = (Button) findViewById(R.id.upload);
//
//        personalbtn.setBackground(btn_click);
//        propertybtn.setBackground(btn_unclick);
//        docbtn.setBackground(btn_unclick);
//
//        personalbtn.setTextColor(Color.WHITE);
//        propertybtn.setTextColor(Color.BLACK);
//        docbtn.setTextColor(Color.BLACK);
//
//        TextView toolbartxt = (TextView) findViewById(R.id.leadSellerDetailsToolbartxt);
//
//        Bundle extras = getIntent().getExtras();
//        newId = extras.getInt("LeadId", 0);
//        //  Toast.makeText(getApplicationContext(), newId, Toast.LENGTH_SHORT).show();
//        //      toolbartxt.setText(newId);
//        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt("LeadId", newId);
//        editor.commit();
//
//        fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.Lead_Details, personalDetailsFragment);
//        Log.v(TAG, "PersonalFragment added");
////        Bundle data = new Bundle();
////        data.putInt("leadId",newId);
////        personalDetailsFragment.setArguments(data);//Set bundle data to fragment
//        fragmentTransaction.addToBackStack(null);
//        int abc = fragmentManager.getBackStackEntryCount();
//        Log.v(TAG, "PersonalA" + abc);
//        fragmentTransaction.commit();
//
//
//
//
//
//       /* PersonalDetailsFragment personaldetailsfragment=new PersonalDetailsFragment();
//        FragmentManager fragmentManagerProperty=getFragmentManager();
//        FragmentTransaction fragmentTransactionproperty=fragmentManagerProperty.beginTransaction();
//        fragmentTransactionproperty.replace(R.id.Lead_Creation, personaldetailsfragment);
//        fragmentTransactionproperty.addToBackStack(null);
//        fragmentTransactionproperty.commit();*/
//
//        ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
//        backbtn.setImageResource(R.drawable.status_bar_back_arrow);
//
//        personalbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                personalbtn.setBackground(btn_click);
//                propertybtn.setBackground(btn_unclick);
//                docbtn.setBackground(btn_unclick);
//
//                personalbtn.setTextColor(Color.WHITE);
//                propertybtn.setTextColor(Color.BLACK);
//                docbtn.setTextColor(Color.BLACK);
//
//                fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.Lead_Details, personalDetailsFragment);
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                personalDetailsFragment.setArguments(data);
//               // fragmentTransaction.addToBackStack(null);
//                int abc = fragmentManager.getBackStackEntryCount();
//                Log.v(TAG, "PersonalB" + abc);
//                fragmentTransaction.commit();
//
//                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
//                backbtn.setImageResource(R.drawable.status_bar_back_arrow);
//            }
//        });
//
//
//        propertybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                propertybtn.setBackground(btn_click);
//                personalbtn.setBackground(btn_unclick);
//                docbtn.setBackground(btn_unclick);
//
//                propertybtn.setTextColor(Color.WHITE);
//                personalbtn.setTextColor(Color.BLACK);
//                docbtn.setTextColor(Color.BLACK);
//                //final FragmentManager fragmentManagerProperty=getFragmentManager();
//
//
//                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
//                backbtn.setImageResource(android.R.color.transparent);
//
//                fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.Lead_Details, propertyDetailsSellerFragment);
//                Log.v(TAG, "PropertyFrag added");
//
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                propertyDetailsSellerFragment.setArguments(data);
//                fragmentTransaction.addToBackStack(null);
//                int abc = fragmentManager.getBackStackEntryCount();
//                Log.v(TAG, "property" + abc);
//                fragmentTransaction.commit();
//
//
//            }
//        });
//
//
//        docbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                docbtn.setBackground(btn_click);
//                propertybtn.setBackground(btn_unclick);
//                personalbtn.setBackground(btn_unclick);
//
//                docbtn.setTextColor(Color.WHITE);
//                propertybtn.setTextColor(Color.BLACK);
//                personalbtn.setTextColor(Color.BLACK);
//
//                ImageButton backbtn = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
//                backbtn.setImageResource(android.R.color.transparent);
//
//                fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.Lead_Details, documentDetailsSellerFragment);
//                Log.v(TAG, "Doc frag added");
//
////                Bundle data = new Bundle();
////                data.putInt("leadId",newId);
////                documentDetailsSellerFragment.setArguments(data);
//                fragmentTransaction.addToBackStack(null);
//                int abc = fragmentManager.getBackStackEntryCount();
//                Log.v(TAG, "doc" + abc);
//                fragmentTransaction.commit();
//
//            }
//        });
//
//        ImageButton back = (ImageButton) findViewById(R.id.leadSellerDetails_Back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//    }
//
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
////    @Override
////    public void onBackPressed() {
////        //Here we are clearing back stack fragment entries
////        int backStackEntry = fragmentManager.getBackStackEntryCount();
////        if (backStackEntry > 0) {
//////            Log.v(TAG,"A"+backStackEntry);
////            for (int i = 0; i < backStackEntry; i++) {
////                fragmentManager = getFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                Log.v(TAG, "A" + backStackEntry);
////                fragmentManager.popBackStackImmediate();
////
////            }
////            super.onBackPressed();
////            finish();
////        } else {
////
////            Log.v(TAG, "B" + backStackEntry);
////            super.onBackPressed();
////            finish();
////        }
//////        getFragmentManager().popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//////        finish();
////    }
////    @Override
////    public void onBackPressed() {
////        if (getFragmentManager().getBackStackEntryCount() > 0) {
////            getFragmentManager().popBackStack();
////        } else {
////            super.onBackPressed();
////        }
////        //  finish();
////    }
//
//
//}
