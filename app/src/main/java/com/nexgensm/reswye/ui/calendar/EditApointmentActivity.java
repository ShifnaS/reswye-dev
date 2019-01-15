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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditApointmentActivity extends AppCompatActivity {
    String Token, ImageUrl, url, responseAppoinment, profile_image;

    EditText editDate, edit_app_time;
    TextView remaindertxt, leadnameTxt, leadidTxt, appointmentidTxt;
    Context context = this;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf;
    String dateFormat = "yyyy.MM.dd", AppoinmentResponse;
    SwitchCompat remainder;
    Button editAppBtn;
    ImageView lead_pic;

    ProgressDialog progressDialog;
    int userId, Leadid,smsV,reminderV;
    SwitchCompat smsSwitch,reminderSwitch;
    RequestQueue requestQueue1, requestQueue2;
    EditText datepicTxt, edit_app_timeTxt, edit_app_locationTxt, purposetxt, meeting_MinTxt;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    public EditApointmentActivity() {
        sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apointment);

//        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
//        ImageUrl = sharedpreferences.getString("imageURL", "");
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        Leadid = sharedpreferences.getInt("LeadId", 0);
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);

        editAppBtn = (Button) findViewById(R.id.editAppBtn);
        lead_pic = (ImageView) findViewById(R.id.lead_pic);
        leadnameTxt = (TextView) findViewById(R.id.leadname);
        leadidTxt = (TextView) findViewById(R.id.leadid);
        appointmentidTxt = (TextView) findViewById(R.id.appointmentid);
        datepicTxt = (EditText) findViewById(R.id.datepic);
        edit_app_timeTxt = (EditText) findViewById(R.id.edit_app_time);
        edit_app_locationTxt = (EditText) findViewById(R.id.edit_app_location);
        purposetxt = (EditText) findViewById(R.id.purpose);
        meeting_MinTxt = (EditText) findViewById(R.id.meeting_minTxt);
        smsSwitch=(SwitchCompat)findViewById(R.id.sms);
        reminderSwitch=(SwitchCompat)findViewById(R.id.reminder);


//////////////////////APPOINTMENT DETAILS DISPLAY/////////////////////////////////////
progressDialog=new ProgressDialog(EditApointmentActivity.this);
progressDialog.setMessage("Loading");
progressDialog.show();
        url = "http://202.88.239.14:8169/api/Lead/GetReminderdetails";
        Map<String, Object> jsonParams;
        jsonParams = new ArrayMap<>();
        jsonParams.put("lead_id", Leadid);
        jsonParams.put("userid", userId);
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseAppoinment = response.getString("status").toString().trim();
                            JSONArray jsonArray = response.getJSONArray("data");

                            JSONObject data = jsonArray.getJSONObject(0);

                            profile_image = data.getString("leadProfileimage");
                            final String location = data.getString("address");
                            final String nameText = data.getString("firstName");
                            final String category_lead = data.getString("lead_Category");
                            final String appointMent_ID = data.getString("appointMent_ID");
                            final String appointment_Date = data.getString("appointment_Date");
                            final String appointment_Time = data.getString("appointment_Time");
                            final String purposeOf_Meeting = data.getString("purposeOf_Meeting");
                            final String appointmentAdded_Date = data.getString("appointmentAdded_Date");
                            final String meeting_min = data.getString("meeting_minutes");
                            final  String smsValueWs=data.getString("send_SMS");
                            final  String reminderValueWs=data.getString("reminder");
                            final String leadiD= String.valueOf(Leadid);
                            String image = ImageUrl + profile_image;
                            String trueTxt="true";
                            if(smsValueWs.compareTo(trueTxt)==0){
                                smsSwitch.setChecked(true);
                                smsV=1;
                            }
                            else{
                                smsSwitch.setChecked(false);
                                smsV=0;
                            }
                            if(reminderValueWs.compareTo(trueTxt)==0){
                                reminderSwitch.setChecked(true);
                                reminderV=1;
                            }
                            else{
                                reminderSwitch.setChecked(false);
                                reminderV=0;
                            }
                            String str3 = "Success";
                            int response_result = responseAppoinment.compareTo(str3);
                            if (response_result == 0) {
                                progressDialog.dismiss();
                                leadidTxt.setText(leadiD);
                                leadnameTxt.setText(nameText);
                                appointmentidTxt.setText(appointMent_ID);
                                edit_app_locationTxt.setText(location);
                                datepicTxt.setText(appointment_Date);
                                edit_app_timeTxt.setText(appointment_Time);
                                purposetxt.setText(purposeOf_Meeting);
                                meeting_MinTxt.setText(meeting_min);
                                Picasso.with(getApplicationContext()).load(image).into(lead_pic);

 /// /////////////////////////////////EDIT APPOINTMENT/////////////////////////////////////////////

                                editAppBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressDialog.show();
                                        String datepicTxtValue = datepicTxt.getText().toString();
                                        String edit_app_timeTxtValue = edit_app_timeTxt.getText().toString();
                                        String edit_app_locationTxtValue = edit_app_locationTxt.getText().toString();
                                        String purposetxtValue=purposetxt.getText().toString();
                                        String meeting_MinTxtValue=meeting_MinTxt.getText().toString();
                                       // String purposetxtValue=purposetxt.getText().toString();
                                      //  String purposetxtValue=purposetxt.getText().toString();
                                        if(smsSwitch.isChecked()==false){
                                           smsV=0;
                                        }
                                        else {
                                            smsV=1;
                                        }
                                        if(reminderSwitch.isChecked()==false){
                                            reminderV=0;
                                        }
                                        else {
                                            reminderV=1;
                                        }
                                        url = "http://202.88.239.14:8169/api/Lead/UpdateAppointment";
                                        Map<String, Object> jsonParams;
                                        jsonParams = new ArrayMap<>();
                                        jsonParams.put("lead_id", Leadid);
                                        jsonParams.put("Appointment_Date", datepicTxtValue);
                                        jsonParams.put("Appointment_Time", edit_app_timeTxtValue);
                                        jsonParams.put("Location", edit_app_locationTxtValue);
                                        jsonParams.put("PurposeOf_Meeting", purposetxtValue);
                                        jsonParams.put("Meeting_minutes", meeting_MinTxtValue);
                                        jsonParams.put("Reminder",reminderV);
                                        jsonParams.put("Send_SMS",smsV);

                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            AppoinmentResponse = response.getString("status").toString().trim();
                                                            String str3 = "Success";
                                                            int response_result = responseAppoinment.compareTo(str3);
                                                            if (response_result == 0) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(), "Appoinment rescheduled to  " + sdf.format(myCalendar.getTime()) + " at " + edit_app_time.getText(), Toast.LENGTH_SHORT).show();
                                                                 finish();
                                                            } else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                            }

                                                        } catch (JSONException e) {
                                                            progressDialog.dismiss();
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                                // do something...
                                            }
                                        }) {
                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                final Map<String, String> headers = new HashMap<>();
                                                headers.put("Authorization", Token);
                                                return headers;
                                            }
                                        };
                                        requestQueue2.add(jsonObjectRequest2);
                                    }
                                });


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        requestQueue1.add(jsonObjectRequest1);

        remaindertxt = (TextView) findViewById(R.id.setremainder);
        remaindertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remainder = new Intent(EditApointmentActivity.this, ReminderActivity.class);
                startActivity(remainder);
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
        remainder = (SwitchCompat) findViewById(R.id.reminder);
        remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (remainder.isChecked()) {
                    Intent in = new Intent(EditApointmentActivity.this, ReminderActivity.class);
                    startActivity(in);
                }


            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edit_app_time = (EditText) findViewById(R.id.edit_app_time);
        edit_app_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final int second = mcurrentTime.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditApointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edit_app_time.setText(selectedHour + ":" + selectedMinute + ":" + second);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

//        editAppBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Appoinment rescheduled to  " + sdf.format(myCalendar.getTime()) + " at " + edit_app_time.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

}
