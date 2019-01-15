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

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

public class LeadFindActivity extends AppCompatActivity {


    private String TAG = "LeadFindActivity";
    Boolean transferStatusFlag;
    Integer leadCategory;
    Integer isdefault = 0;
    int propID, filersetFlag;
    String seekMin, seekMax;

    String leadStatus, prospectStatus;
    Context context = this;
    EditText editDate, agentName, firstName, lastName;
    Calendar myCalendar = Calendar.getInstance();
//    String dateFormat = "dd.MM.yyyy";
String dateFormat = "yyyy.MM.dd";
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
        leadStatus = "";
        prospectStatus = "";
        transferStatusFlag = false;
        leadCategory = 1;
        filersetFlag =Singleton.getInstance().getFilterFlag();
        final Drawable btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        final Drawable btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);

        final Button newbtn = (Button) findViewById(R.id.newleadbtn);
        final Button convertedbtn = (Button) findViewById(R.id.convertedleadbtn);
        final Button inactivebtn = (Button) findViewById(R.id.inactiveleadbtn);

        final Button warmbtn = (Button) findViewById(R.id.warmbtn);
        final Button neutraldbtn = (Button) findViewById(R.id.neutralbtn);
        final Button coldbtn = (Button) findViewById(R.id.coldbtn);

        final EditText location = (EditText) findViewById(R.id.location);
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


//        seekminText = (TextView) findViewById(R.id.minText);
//        seekmaxText = (TextView) findViewById(R.id.maxText);
//
//        RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.seekbar);
//        //final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(this);
//        rangeSeekbar.setRangeValues(0, 100);
//
//        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
//            @Override
//            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
//                seekMax = Integer.toString(maxValue);
//                seekMin = Integer.toString(minValue);
//                seekminText.setText(seekMin);
//                seekmaxText.setText(seekMax);
//                Log.v(TAG, "min-" + seekMin + "-Max - " + seekMax);
//
//            }
//        });
//
//        rangeSeekbar.setNotifyWhileDragging(true);




        transferStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    transferStatusFlag = isChecked;
//                    Snackbar.make(buttonView, " yes" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                } else {
                    transferStatusFlag = isChecked;
//                    Snackbar.make(buttonView, " no" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                }
            }
        });
        categoryStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    leadCategory = 2;
//                    Snackbar.make(buttonView, " Buyer" + isChecked, Snackbar.LENGTH_LONG)
//                            .setAction("ACTION", null).show();
                } else {
                    leadCategory = 1;
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
                //  Log.v(TAG, "agent" + agentnameTxt);
                //Log.v(TAG, "first" + firstnameTxt);
                //  Log.v(TAG, "last" + lastnameTxt);
//                Log.v(TAG, "LeadStat" + leadStatus);
//                Log.v(TAG, "loc" + locationtxt);
//                Log.v(TAG, "date" + dateTxt);
//                Log.v(TAG, "min" + propertyMinimum);
//                Log.v(TAG, "max" + propertymaximum);
//                Log.v(TAG, "LeadWarmth" + prospectStatus);
//                Log.v(TAG, "leadCat" + leadCategory);
//                Log.v(TAG, "transStat" + String.valueOf(transferStatusFlag));
//                Log.v(TAG, "agn" + agentnameTxt);
//                Log.v(TAG, "fN" + firstnameTxt);
//                Log.v(TAG, "LN" + lastnameTxt);
//                Log.v(TAG, "mob" + mobileNoTxt);
//                Log.v(TAG, "email" + emailIdTxt);
//                Log.v(TAG, "ID" + propID);

                // Log.v(TAG, "propID" + propertyIdTxt);

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
                    Singleton.getInstance().setFilterAgent(agentnameTxt);
                    Singleton.getInstance().setFilterFirstName(firstnameTxt);
                    Singleton.getInstance().setFilterLastName(lastnameTxt);
                    Singleton.getInstance().setPropID(propID);
                    Singleton.getInstance().setPropIDD(propertyIdTxt);
                    Singleton.getInstance().setFilterLeadStatus(leadStatus);
                    Singleton.getInstance().setFilterLoc(locationtxt);
                    Singleton.getInstance().setFilterDate(dateTxt);
                    Singleton.getInstance().setFilterMob(mobileNoTxt);
                    Singleton.getInstance().setFilterEmail(emailIdTxt);
                    Singleton.getInstance().setMinvalue(propertyMinimum);
                    Singleton.getInstance().setMaxvalue(propertymaximum);
                    Singleton.getInstance().setPropStatus(prospectStatus);
                    Singleton.getInstance().setFilterTransferStatus(transferStatusFlag);
                    Singleton.getInstance().setLeadCategory(leadCategory);
                    Singleton.getInstance().setIsfilterDefaultt(0);
                    Singleton.getInstance().setFilterFlag(1);
                    finish();
//
//                    if (emailedittext.length() > 0) {
//
//                        if (emailedittext.matches(emailPattern)) {
//
//                            Singleton.getInstance().setSortSaveFlag(3);
//
//
////                            Intent in = new Intent(LeadFindActivity.this, BottomTabbarActivity.class);
////                            startActivity(in);
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }
            }

        });
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadStatus = "New";
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
                leadStatus = "Success";
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
                leadStatus = "Failure";
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

// init - set date to current date
//        long currentdate = System.currentTimeMillis();
//        String dateString = sdf.format(currentdate);
//        editDate.setText(dateString);

// set calendar date and update editDate
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

//    public void JSON_CALL(){
//
//        Map<String, Object> jsonParams = new ArrayMap<>();
//        jsonParams.put("LeadStatus ", leadStatus);
//        jsonParams.put("Location ", locationtxt);
//        jsonParams.put(" Created_Date", dateTxt);
//        jsonParams.put("MinPrice ", propertyMinimum);
//        jsonParams.put("maxPrice ", propertymaximum);
//        jsonParams.put("LeadWarmth ", prospectStatus);
//        jsonParams.put("LeadCategory ", leadCategory);
//        jsonParams.put("TransferStatus ", transferStatusFlag);
//        jsonParams.put("AgentName ", listingpriceValue);
//        jsonParams.put("LeadFirstName ", foreclosure);
//        jsonParams.put("LeadLastName ", discriptionValue);
//        jsonParams.put("Mobile ", mobileNoTxt);
//        jsonParams.put("Email ", emailIdTxt);
//        jsonParams.put("PropertyID ", propID);
//        jsonParams.put("YearBuiltStart ", "");
//        jsonParams.put("YearBuiltEnd ", "");
//        jsonParams.put("isdefault ",isdefault);
//
//
//        url = "http://202.88.239.14:8169/api/Lead/UpdatePropertyDetails";
//
//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.v("ADDPROPFRAG",response.toString());
//                        try {
//                            updateStatus = response.getString("status").toString().trim();
//
//
//                            String str3 = "Success";
//                            int response_result = updateStatus.compareTo(str3);
//                            if (response_result == 0) {
//                                loading.dismiss();
//                                Toast.makeText(getActivity(), "Property details Updated", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                loading.dismiss();
//                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (JSONException e) {
//                            loading.dismiss();
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
//                Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                // do something...
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                final Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", Token);
//                return headers;
//            }
//        };
//
//
//    }


}

