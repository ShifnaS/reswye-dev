package com.nexgensm.reswye.ui.lead;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Singleton;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

public class LeadFindActivity extends AppCompatActivity {


    private String TAG = "LeadFindActivity";
    int transferStatusFlag;
    String leadCategory="";
    Integer isdefault = 0;
    int propID, filersetFlag;
    String seekMin, seekMax;

    int leadStatus=0;
    String prospectStatus;
    Context context = this;
    EditText editDate, agentName, firstName, lastName;
    Calendar myCalendar = Calendar.getInstance();
//    String dateFormat = "dd.MM.yyyy";
    String dateFormat = "yyyy/MM/dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    public String locationtxt, dateTxt, propertyIdTxt, firstnameTxt, lastnameTxt, mobileNoTxt, agentnameTxt, emailIdTxt, emailedittext;
    public String propertymaximum, propertyMinimum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_lead_find);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prospectStatus = "";
        transferStatusFlag = 0;
        filersetFlag =Singleton.getInstance().getFilterFlag();
        final Drawable btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        final Drawable btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);

        final Button newbtn = (Button) findViewById(R.id.newleadbtn);
        final Button convertedbtn = (Button) findViewById(R.id.convertedleadbtn);
        final Button inactivebtn = (Button) findViewById(R.id.inactiveleadbtn);

        final Button warmbtn = (Button) findViewById(R.id.warmbtn);
        final Button neutraldbtn = (Button) findViewById(R.id.neutralbtn);
        final Button coldbtn = (Button) findViewById(R.id.coldbtn);

        final PlacesAutocompleteTextView location = (PlacesAutocompleteTextView) findViewById(R.id.location);
        final EditText dateEdittxt = (EditText) findViewById(R.id.datepic);
        final EditText agentName = (EditText) findViewById(R.id.Agent_name);
        final EditText firstName = (EditText) findViewById(R.id.first_name);
        final EditText lastName = (EditText) findViewById(R.id.last_name);
        final EditText mobileNo = (EditText) findViewById(R.id.Mobile_no);
        final EditText emailId = (EditText) findViewById(R.id.Email_id);
        final EditText propertyId = (EditText) findViewById(R.id.propery_txt);
        final EditText propertyMaxVal = (EditText) findViewById(R.id.maximunprice);
        final EditText propertyMinVal = (EditText) findViewById(R.id.minimunprice);

        final SwitchCompat categoryStatus = (SwitchCompat) findViewById(R.id
                .category_toggle);
        final SwitchCompat transferStatus = (SwitchCompat) findViewById(R.id
                .transfer_status_toggle);
        transferStatus.getShowText();

        if(filersetFlag==1){

            String agentname = Singleton.getInstance().getFilterAgent();
            agentName.setText(agentname);
            String firstname = Singleton.getInstance().getFilterFirstName();
            firstName.setText(firstname);
            String lastname = Singleton.getInstance().getFilterLastName();
            lastName.setText(lastname);
            int propID = Singleton.getInstance().getPropID();
            propertyId.setText(String.valueOf(propID));
            String loc = Singleton.getInstance().getFilterLoc();
            location.setText(loc);
            String date = Singleton.getInstance().getFilterDate();
            dateEdittxt.setText(date);
            String mob = Singleton.getInstance().getFilterMob();
            mobileNo.setText(mob);
            String email = Singleton.getInstance().getFilterEmail();
            emailId.setText(email);
            String minvalue = Singleton.getInstance().getMinvalue();
            propertyMinVal.setText(minvalue);
            String maxvalue = Singleton.getInstance().getMaxvalue();
            propertyMaxVal.setText(maxvalue);
            String propStatus = Singleton.getInstance().getPropStatus();
            Boolean filterTransferStatus = Singleton.getInstance().getFilterStatus();
            int leadCategory = Singleton.getInstance().getLeadCategory();
            int filterFlag = Singleton.getInstance().getFilterFlag();
            int isfilterchecked = Singleton.getInstance().getIsfilterDefaultt();
            String leadStatus = Singleton.getInstance().getFilterLeadStatus();

            if (leadCategory==1)
                categoryStatus.setChecked(false);
            else
                categoryStatus.setChecked(true);

            if (filterTransferStatus)
                transferStatus.setChecked(true);

                else
                transferStatus.setChecked(false);
        }

        location.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                location.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(final PlaceDetails details) {
                        Log.d("test", "details " + details);
                        //  mStreet.setText(details.name);
                        for (AddressComponent component : details.address_components) {
                            for (AddressComponentType type : component.types) {
                                switch (type) {
                                    case STREET_NUMBER:
                                        break;
                                    case ROUTE:
                                        break;
                                    case NEIGHBORHOOD:
                                        break;
                                    case SUBLOCALITY_LEVEL_1:
                                        break;
                                    case SUBLOCALITY:
                                        break;
                                    case LOCALITY:
                                        // mCity.setText(component.long_name);
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_1:
                                        // mState.setText(component.short_name);
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_2:
                                        break;
                                    case COUNTRY:
                                        break;
                                    case POSTAL_CODE:
                                        //  mZip.setText(component.long_name);
                                        break;
                                    case POLITICAL:
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(final Throwable failure) {
                        Log.d("test", "failure " + failure);
                    }
                });
            }
        });







        transferStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    transferStatusFlag = 1;
//                    Snackbar.make(buttonView, " yes" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                } else {
                    transferStatusFlag = 0;
//                    Snackbar.make(buttonView, " no" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                }
            }
        });
        categoryStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    leadCategory = "Buyer";
//                    Snackbar.make(buttonView, " Buyer" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                } else {
                    leadCategory = "Seller";
//                    Snackbar.make(buttonView, " Seller" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                }
            }
        });





        Button ApplyBtn = (Button) findViewById(R.id.apply_btn);
        ApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    agentnameTxt = agentName.getText().toString();
                //     firstnameTxt =firstName.getText().toString();
                //     lastnameTxt=lastName.getText().toString();
                locationtxt = location.getText().toString();
                dateTxt = dateEdittxt.getText().toString();
                agentnameTxt = agentName.getText().toString();
                firstnameTxt = firstName.getText().toString();
                lastnameTxt = lastName.getText().toString();
                mobileNoTxt = mobileNo.getText().toString();
                emailIdTxt = emailId.getText().toString();

                propertyIdTxt = propertyId.getText().toString().trim();
                String test = "";

                if (!propertyIdTxt.equals(test)) {
                    propID = Integer.valueOf(propertyIdTxt);
                }
                propertyMinimum = propertyMinVal.getText().toString();
                propertymaximum = propertyMaxVal.getText().toString();


                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                emailedittext = emailId.getText().toString().trim();

                if ((propertyMinVal.equals("")) && (propertyMaxVal.equals("")) && (locationtxt.equals("")) && (dateFormat.equals("")) && (agentnameTxt.equals("")) && (firstnameTxt.equals("")) && (lastnameTxt.equals("")) && (mobileNoTxt.equals("")) && (emailIdTxt.equals("")) && (propertyIdTxt.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.v(TAG, "LeadStat" + leadStatus);
                    Log.v(TAG, "loc" + locationtxt);
                    Log.v(TAG, "date" + dateTxt);
                    Log.v(TAG, "min" + propertyMinimum);
                    Log.v(TAG, "max" + propertymaximum);
                    Log.v(TAG, "LeadWarmth" + prospectStatus);
                    Log.v(TAG, "leadCat" + leadCategory);
                    Log.v(TAG, "transStat" + String.valueOf(transferStatusFlag));
                    Log.v(TAG, "agn" + agentnameTxt);
                    Log.v(TAG, "fN" + firstnameTxt);
                    Log.v(TAG, "LN" + lastnameTxt);
                    Log.v(TAG, "mob" + mobileNoTxt);
                    Log.v(TAG, "email" + emailIdTxt);
                    Log.v(TAG, "ID" + propertyIdTxt);

                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("agentName",agentnameTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("firstName",firstnameTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("lasttName",lastnameTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("propID",""+propID);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("propertyIdTxt",propertyIdTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setIntData("leadStatus",leadStatus);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("locationtxt",locationtxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("dateTxt",dateTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("mobileNoTxt",mobileNoTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("emailIdTxt",""+emailIdTxt);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("propertyMinimum",propertyMinimum);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("propertymaximum",propertymaximum);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("prospectStatus",prospectStatus);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setIntData("transferStatusFlag",transferStatusFlag);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("leadCategory",""+leadCategory);
                    SharedPrefsUtils.getInstance(getApplicationContext()).setData("filter","filter");

                    Singleton.getInstance().setFilterAgent(agentnameTxt);
                    Singleton.getInstance().setFilterFirstName(firstnameTxt);
                    Singleton.getInstance().setFilterLastName(lastnameTxt);
                    Singleton.getInstance().setPropID(propID);
                    Singleton.getInstance().setPropIDD(propertyIdTxt);
                 //   Singleton.getInstance().setFilterLeadStatus(leadStatus);
                    Singleton.getInstance().setFilterLoc(locationtxt);
                    Singleton.getInstance().setFilterDate(dateTxt);
                    Singleton.getInstance().setFilterMob(mobileNoTxt);
                    Singleton.getInstance().setFilterEmail(emailIdTxt);
                    Singleton.getInstance().setMinvalue(propertyMinimum);
                    Singleton.getInstance().setMaxvalue(propertymaximum);
                    Singleton.getInstance().setPropStatus(prospectStatus);
                   // Singleton.getInstance().setFilterTransferStatus(""+transferStatusFlag);
                 //   Singleton.getInstance().setLeadCategory(leadCategory);
                    Singleton.getInstance().setIsfilterDefaultt(0);
                    Singleton.getInstance().setFilterFlag(1);
                    finish();

                }
            }

        });
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadStatus = 1;
                newbtn.setBackground(btn_click);
                convertedbtn.setBackground(btn_unclick);
                inactivebtn.setBackground(btn_unclick);

                newbtn.setTextColor(Color.WHITE);
                convertedbtn.setTextColor(Color.BLACK);
                inactivebtn.setTextColor(Color.BLACK);
            }
        });


        convertedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable dr = getResources().getDrawable(R.drawable.add_new_btn_click);
                leadStatus = 2;
                convertedbtn.setBackground(btn_click);
                newbtn.setBackground(btn_unclick);
                inactivebtn.setBackground(btn_unclick);
                convertedbtn.setTextColor(Color.WHITE);
                newbtn.setTextColor(Color.BLACK);
                inactivebtn.setTextColor(Color.BLACK);

            }
        });


        inactivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable dr = getResources().getDrawable(R.drawable.add_new_btn_click);
                leadStatus = 3;
                inactivebtn.setBackground(btn_click);
                convertedbtn.setBackground(btn_unclick);
                newbtn.setBackground(btn_unclick);
                inactivebtn.setTextColor(Color.WHITE);
                convertedbtn.setTextColor(Color.BLACK);
                newbtn.setTextColor(Color.BLACK);

            }
        });


        warmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prospectStatus = "Warm";
                warmbtn.setBackground(btn_click);
                neutraldbtn.setBackground(btn_unclick);
                coldbtn.setBackground(btn_unclick);

                warmbtn.setTextColor(Color.WHITE);
                neutraldbtn.setTextColor(Color.BLACK);
                coldbtn.setTextColor(Color.BLACK);
            }
        });
        neutraldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable dr = getResources().getDrawable(R.drawable.add_new_btn_click);
                prospectStatus = "Neutral";
                neutraldbtn.setBackground(btn_click);
                warmbtn.setBackground(btn_unclick);
                coldbtn.setBackground(btn_unclick);
                neutraldbtn.setTextColor(Color.WHITE);
                warmbtn.setTextColor(Color.BLACK);
                coldbtn.setTextColor(Color.BLACK);

            }
        });


        coldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable dr = getResources().getDrawable(R.drawable.add_new_btn_click);
                prospectStatus = "Cold";
                coldbtn.setBackground(btn_click);
                neutraldbtn.setBackground(btn_unclick);
                warmbtn.setBackground(btn_unclick);
                coldbtn.setTextColor(Color.WHITE);
                neutraldbtn.setTextColor(Color.BLACK);
                warmbtn.setTextColor(Color.BLACK);

            }
        });
        editDate = (EditText) findViewById(R.id.datepic);


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };
// onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.navigation_lead);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }


    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }




}

