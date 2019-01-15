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
                addLead=(LinearLayout)findViewById(R.id.add_relative_contacts);
                addLead.setVisibility(View.VISIBLE);

                add_manuvaly=(ImageView)findViewById(R.id.add_manuvaly);
                fromContacts=(ImageView)findViewById(R.id.fromContacts);
                add_manuvaly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(leadcategory==0){
                            sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("leadCategory_flag", 0);
                            editor.commit();
                      Intent buyerintent = new Intent(AddNewLeadCategoryActivity.this, AddNewBuyerActivity.class);
                       startActivity(buyerintent);
                        }
                    }
                });
                fromContacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(leadcategory==0){
                            sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("leadCategory_flag", 0);
                            editor.commit();
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, PICK_CONTACT);
                        }
                    }
                });
//                PopupMenu popup = new PopupMenu(getApplicationContext(), buyerbtn);
//                popup.getMenuInflater().inflate(R.menu.addnewleadsource, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    public boolean onMenuItemClick(MenuItem item) {
//                        CharSequence title = item.getTitle();
//                        String strtitle = title.toString();
//                        if (strtitle.matches("Add From Contacts")) {
//
//                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//
//                            startActivityForResult(intent, PICK_CONTACT);
//
//                        }
//
//                        if (strtitle.matches("Add Manually")) {
//                            Intent signupintent = new Intent(AddNewLeadCategoryActivity.this, AddNewBuyerActivity.class);
//                           // sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
////                            SharedPreferences.Editor editor = sharedpreferences.edit();
////                            editor.putInt("flag", 0);
////                            editor.apply();
//                            startActivity(signupintent);
//
//
//                        }
//
//
//                        return true;
//                    }
//                });
//                popup.show();
            }
        });

        sellerbtn = (ImageButton) findViewById(R.id.seller);
        sellerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerbtn.setImageResource(R.mipmap.seller_red);
                buyerbtn.setImageResource(R.mipmap.buyer_green);
                //setResult(Activity.RESULT_OK);
//                PopupMenu popup = new PopupMenu(getApplicationContext(), sellerbtn);
//                popup.getMenuInflater().inflate(R.menu.addnewleadsource, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    public boolean onMenuItemClick(MenuItem item) {
//                        CharSequence title = item.getTitle();
//                        String strtitle = title.toString();
//                        if (strtitle.matches("Add From Contacts")) {
//
//                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                            startActivityForResult(intent, PICK_CONTACT);
//
//                        }
//
//                        if (strtitle.matches("Add Manually")) {
//                            Intent signupintent = new Intent(AddNewLeadCategoryActivity.this, AddNewSellerActivity.class);
////                            signupintent.putExtra("flag",0);
//                             startActivity(signupintent);
//
//                        }
//
//
//                        return true;
//                    }
//                });
//                popup.show();
                leadcategory = 1;
                addLead=(LinearLayout) findViewById(R.id.add_relative_contacts);
                addLead.setVisibility(View.VISIBLE);

                add_manuvaly=(ImageView)findViewById(R.id.add_manuvaly);
                fromContacts=(ImageView)findViewById(R.id.fromContacts);
                add_manuvaly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(leadcategory==1){
                            sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("leadCategory_flag", 1);
                            editor.commit();
                        Intent buyerintent = new Intent(AddNewLeadCategoryActivity.this, AddNewSellerActivity.class);
                         startActivity(buyerintent);
                        }
                    }
                });
                fromContacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(leadcategory==1){
                            sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("leadCategory_flag", 1);
                            editor.commit();
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, PICK_CONTACT);
                        }
                    }
                });

            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.toolbarcolor));

    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
//                if (ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
//                            this, Manifest.permission.READ_CONTACTS)) {
//
//
//                    } else {
//                        ActivityCompat.requestPermissions((Activity) this,
//                                new String[]{Manifest.permission.READ_CONTACTS},
//                                1);
//                    }
//
//                }
                int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},123);
                    return;
                }
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String num = "";
                    String name="";
                    if (Integer.valueOf(hasNumber) == 1) {
                        Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (numbers.moveToNext()) {
                            num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            name = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            Toast.makeText(getApplicationContext(), "Number="+num, Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Name="+name, Toast.LENGTH_LONG).show();


                            leaditem.setLead_number(num);
                            leaditem.setLead_name(name);
                            Intent intent = new Intent();
                            intent.putExtra("Obj", leaditem);
                            setResult(Activity.RESULT_OK, intent);
                           // leaditem.setLead_number(num);
                            //leaditem.setLead_name(name);
                            //final Activity activity = (Activity)setupAct;
                            //Intent intent = new Intent();
                            //intent.putExtra("lead_name",name);
                            //intent.putExtra("lead_num",num);
                            //setResult(Activity.RESULT_OK,intent);
                            finish();

                        }

                    }
                }


              /*  if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                   // displayContacts();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.READ_CONTACTS)) {
                        // show UI part if you want here to show some rationale !!!

                    } else {

                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                                REQUEST_READ_CONTACTS);
                        ///displayContacts();
                    }

                }*/

                        //displayContacts();

                        break;
                    }
                }
    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        // startActivity(new Intent(SellerDetailsActivity.this, BottomTabbarActivity.class));
        AddNewLeadCategoryActivity.super.onBackPressed();
        finish();
      //  startActivity(new Intent(AddNewLeadCategoryActivity.this, BottomTabbarActivity.class));
    }
    private void setdetail(String name,String num)
                {
                    Intent intent = new Intent();
                    intent.putExtra("lead_name",name);
                    intent.putExtra("lead_num",num);
                    setResult(RESULT_OK,intent);
                    finish();

//                    Bundle data = new Bundle();//create bundle instance
//                    data.putString("lead_name", name);
//                    LeadFragment leadFragment = new LeadFragment();
//                    leadFragment.setLeadItems(name,num);
                    //finish();
                   // fragmentManager.beginTransaction().replace(R.id.framelayout, leadFragment).commit();

                }

   /* @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   // displayContacts();

                } else {

                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }*/

  /*  private void displayContacts() {

      /*  ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            if (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    if (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
            }
        }*/

       /* Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if(people.moveToFirst()) {
            do {
                String name   = people.getString(indexName);
                String number = people.getString(indexNumber);
                // Do work...
            } while (people.moveToNext());
        }
    }*/



        }

