package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;

public class AddNewLeadCategoryActivity extends AppCompatActivity  {

    int leadcategory;
    private static final int PICK_CONTACT = 1000;
    public static final int REQUEST_READ_CONTACTS = 79;
    LinearLayout addLead;
    LeadItems leaditem;
    ImageView add_manuvaly,fromContacts;
    FragmentManager fragmentManager;
    ImageButton sellerbtn,buyerbtn;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    int lid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lead_category);

        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("flag", 0);
        editor.putInt("refresh",0);
        editor.commit();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            leaditem = (LeadItems) getIntent().getExtras()
                    .getSerializable("myobject");
        }

        fragmentManager = getSupportFragmentManager();
        final Drawable btn_click = getResources().getDrawable(R.drawable.white_round_button_click);
        final Drawable btn_unclick = getResources().getDrawable(R.drawable.white_round_button);

        buyerbtn = (ImageButton) findViewById(R.id.buyer);
        buyerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadcategory = 0;
                buyerbtn.setImageResource(R.drawable.leadcategorybuyer);
                sellerbtn.setImageResource(R.drawable.leadcategoryseller);
                //addLead=(LinearLayout)findViewById(R.id.add_relative_contacts);
                //addLead.setVisibility(View.VISIBLE);
                Intent buyerintent = new Intent(AddNewLeadCategoryActivity.this, AddNewBuyerActivity.class);
                startActivity(buyerintent);

            }
        });

        sellerbtn = (ImageButton) findViewById(R.id.seller);
        sellerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerbtn.setImageResource(R.mipmap.seller_red);
                buyerbtn.setImageResource(R.mipmap.buyer_green);
                leadcategory = 1;
              //  addLead=(LinearLayout) findViewById(R.id.add_relative_contacts);
              //  addLead.setVisibility(View.VISIBLE);

                Intent buyerintent = new Intent(AddNewLeadCategoryActivity.this, AddNewSellerActivity.class);
                buyerintent.putExtra("flag",0);
                buyerintent.putExtra("lid",0);
                startActivity(buyerintent);

            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));

    }


    @Override
    public void onBackPressed() {
        AddNewLeadCategoryActivity.super.onBackPressed();
        finish();
    }


    }
