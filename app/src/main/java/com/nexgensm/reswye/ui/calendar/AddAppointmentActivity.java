package com.nexgensm.reswye.ui.calendar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddAppointmentActivity extends AppCompatActivity {
    SwitchCompat remainder;
    Button addAppointment;
    RequestQueue addAppointmentQueue,getLeadnamesQueue;
    EditText add_app_nameTxt, add_app_dateTxt, add_app_timeTxt, add_app_purposeTxt, meetingMinutesTxt;
    SwitchCompat smsSwitchTxt, remainderTxt;
    PlacesAutocompleteTextView add_app_locationTxt;
    SharedPreferences sharedpreferences;
    String[] leadNameArray;
    int[] leadIdArray;
    public static final String mypreference = "mypref";
    String Token, url, responseResult, message, dateFormat = "dd.MM.yyyy",getAgentStatus;
    ProgressDialog loading;
    int userId, LeadId,i,smsValue,reminderValue;
    Calendar myCalendar = Calendar.getInstance();
    Spinner selectLeadTxt;
    private int mHour, mMinute;
    ProgressDialog progressDialog;
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        add_app_dateTxt = (EditText) findViewById(R.id.add_app_date);
        add_app_timeTxt = (EditText) findViewById(R.id.add_app_time);
        add_app_locationTxt =  findViewById(R.id.add_app_location);
        add_app_purposeTxt = (EditText) findViewById(R.id.add_app_purpose);
        meetingMinutesTxt = (EditText) findViewById(R.id.meetingMinutes);
        smsSwitchTxt = (SwitchCompat) findViewById(R.id.smsSwitch);
        remainderTxt = (SwitchCompat) findViewById(R.id.reminderSwitch);
        selectLeadTxt=(Spinner) findViewById(R.id.selectLead);

//        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=SharedPrefsUtils.getInstance(getApplicationContext()).getUserId();
        Token=sharedpreferences.getString("token","");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("refreshCal",0);
        editor.commit();
       // ImageUrl=sharedpreferences.getString("imageURL","");
      //  LeadId = sharedpreferences.getInt("LeadId", 1);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        add_app_dateTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAppointmentActivity.this,R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        add_app_timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final int second = mcurrentTime.get(Calendar.SECOND);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAppointmentActivity.this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        add_app_timeTxt.setText(selectedHour + ":" + selectedMinute + ":" + second);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        add_app_locationTxt.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                add_app_locationTxt.getDetailsFor(place, new DetailsCallback() {
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







//////////////////////////////GET LEAD NAMES AND LEAD ID//////////////////////////////////////////////////

        getLeadnamesQueue = Volley.newRequestQueue(this);
      ///  url = "http://202.88.239.14:8169/api/Lead/GetLeadsList";//normaly used
        url = ApiClient.BASE_URL+"listleads/"+userId;///updated in 14/06/2018
        progressDialog=new ProgressDialog(AddAppointmentActivity.this);
        progressDialog.setMessage("Loading Details");
        progressDialog.show();
      //  Map<String, Object> jsonParams = new ArrayMap<>();
       // jsonParams.put("User_Id", userId);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url,null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getAgentStatus = response.getString("status");
                            Log.e("111111111111111","status "+getAgentStatus);
                            JSONArray jsonArray=response.getJSONArray("result");
                            Log.e("11111111111111","jsonArray "+jsonArray.length());

                            String SpinnerTxt="Select Lead";
                            String str3 = "success";
                            int response_result = getAgentStatus.compareTo(str3);
                            if (getAgentStatus.equals("success"))
                            {
                                progressDialog.dismiss();
                                leadNameArray=new String[jsonArray.length()+1];
                                leadIdArray=new int[jsonArray.length()+1];

                                leadNameArray[0]=SpinnerTxt;
                                leadIdArray[0]=0;
                                for ( int i = 0; i < jsonArray.length(); i++) {
                                  //  Toast.makeText(AddAppointmentActivity.this, "Success "+i, Toast.LENGTH_SHORT).show();

                                    Log.e("11111111111111","i "+i);
                                    JSONObject leadLists = jsonArray.getJSONObject(i);
                                    Log.e("111111111111111",""+leadLists);

                                    String name = leadLists.getString("firstname");
                                    Log.e("111111111111111",""+name);
                                    int id=leadLists.getInt("lead_id");
                                    Log.e("111111111111111",""+id);
                                    leadNameArray[i+1]=name;
                                    leadIdArray[i+1]=id;

                                }
                                int ivaLUE=i;

                                selectLeadTxt.setSelection(0, true);
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,leadNameArray);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                selectLeadTxt.setAdapter(adapter);
                               // Toast.makeText(getApplicationContext(), ivaLUE, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",Token);
                return headers;
            }
        };
        getLeadnamesQueue.add(jsonObjectRequest1);


        addAppointment = (Button) findViewById(R.id.addAppointment_btn);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String add_app_spinnerTxtValue = selectLeadTxt.getSelectedItem().toString();
                int positionSpinner=selectLeadTxt.getSelectedItemPosition();
                final String add_app_dateTxtValue = add_app_dateTxt.getText().toString();
                final String add_app_timeTxtValue = add_app_timeTxt.getText().toString();
                final String add_app_locationTxtValue = add_app_locationTxt.getText().toString();
                final String add_app_purposeTxtValue = add_app_purposeTxt.getText().toString();
                final String meetingMinutesTxtValue = meetingMinutesTxt.getText().toString();
                int lead=leadIdArray[positionSpinner];

                if(smsSwitchTxt.isChecked()==false)
                {
                    smsValue=0;
                }
                else
                {
                    smsValue=1;
                }
                if(remainderTxt.isChecked()==false)
                {
                    reminderValue=0;
                }
                else
                {
                    reminderValue=1;
                }

                addAppointmentQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
                url = ApiClient.BASE_URL+"followupmeeting";

                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("agent_id",userId);

                jsonParams.put("lead_id",lead);
                jsonParams.put("location", add_app_locationTxtValue);
                jsonParams.put("purpose", add_app_purposeTxtValue);
                jsonParams.put("lead_name", add_app_spinnerTxtValue);
                jsonParams.put("date", add_app_dateTxtValue);
                jsonParams.put("time", add_app_timeTxtValue);
                jsonParams.put("meeting_minutes", meetingMinutesTxtValue);
                jsonParams.put("reminder",1);
                jsonParams.put("reminder_time","10:30");
                jsonParams.put("sms",1);
                jsonParams.put("email",1);



                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    responseResult = response.getString("status").toString().trim();
                                    if (responseResult.equals("success")) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Appointment added Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Failed to appointment", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                                   finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Volley Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        // do something...
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "");
                        return headers;
                    }
                };
                // loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Uploading Details...", false, false);

                addAppointmentQueue.add(jsonObjectRequest);
            }
        });

        remainderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (remainderTxt.isChecked()) {
                    Intent in = new Intent(AddAppointmentActivity.this, ReminderActivity.class);
                    startActivity(in);
                }


            }
        });

    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        add_app_dateTxt.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}