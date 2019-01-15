package com.nexgensm.reswye.ui.calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Singleton;
import com.nexgensm.reswye.Utlity;
import com.nexgensm.reswye.ui.navigationdrawer.ProfileSettingsActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pyxis.uzuki.live.sectioncalendarview.SectionCalendarView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    Button add;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button weekly, monthly;
    ImageView calimage;
    Drawable btn_click;
    Drawable btn_unclick;
    String monthName;
    String datemonth;
    String datestr;
    SharedPreferences sharedpreferences;
    CollapsibleCalendar collapsibleCalendar;
    public static final String mypreference = "mypref";
    private JSONObject jsonObject;
    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    private List<CalItem> calItemList = new ArrayList<>();
    List<AppointmentListingRecyclerDataAdapter> GetDataAdapter1;
    int userId, Apnmt_lead_id;
    CalenderRecyclerViewAdapter calenderrecyclerViewadapter;
    String Token, ImageUrl, Apnmt_profileimage, Apnmt_Location, Apnmt_Time, lead_Name, image, Apnmt_date;
    NavigationView nvDrawer;
    int day, flag_Calender;
    int monthc, refreshCal;
    int yearc;
    EditText oldpass;
    EditText newpass;
    EditText confrmpass;
    String oldpasstxt, newpasstxt, confrmpasstxt;
    private DrawerLayout mDrawer;
    RequestQueue requestQueue, requestChangePassword;
    View myFragmentView;
    ProgressDialog progressDialog;

    public CalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        myFragmentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        Calendar c = Calendar.getInstance();
        int dayq = c.get(Calendar.DAY_OF_MONTH);
        int monthq = c.get(Calendar.MONTH);
        int yearq = c.get(Calendar.YEAR);
        // datemonth = dayq+"."+(monthq+1)+"."+yearq;
        datemonth = yearq + "." + (monthq + 1) + "." + dayq;


        collapsibleCalendar = (CollapsibleCalendar) myFragmentView.findViewById(R.id.calendar);

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect(int date, int month, int year) {
                datestr = Integer.toString(date);
                // datestr = date;
                // Toast.makeText(getApplicationContext(),Integer.toString(month),Toast.LENGTH_SHORT).show();
                getMonthShortName(month);
                day = date;
                monthc = month;
                yearc = year;
                //datemonth = monthName+" "+datestr+" "+yearc;
                datemonth = yearc + "." + (monthc + 1) + "." + day;
                sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                flag_Calender = sharedpreferences.getInt("flag_calender", 1);//WEEKLY MONTHLY FLAG
                refreshCal = sharedpreferences.getInt("refreshCal", 1);
             //   JSON_DATA_WEB_CALL1();


            }
        });
        Toast.makeText(getActivity(), datemonth, Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);

        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getInt("UserId", 0);
        Token = sharedpreferences.getString("token", "");
        ImageUrl = sharedpreferences.getString("imageURL", "");
        refreshCal = sharedpreferences.getInt("refreshCal", 1);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("refresh", 1);
        editor.commit();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        flag_Calender = sharedpreferences.getInt("flag_calender", 1);//WEEKLY MONTHLY FLAG


      //  JSON_DATA_WEB_CALL();//////////// APPOINTMENTS DISPLAY default/////////////
////shifna///
      /*  int a = 0;
        calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        recyclerView.addOnItemTouchListener(

                new AppointmentRecyclerItemClickListener(getActivity().getApplicationContext(), new AppointmentRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Integer id = GetDataAdapter1.get(position).getLead_ID();
                        Log.v("LeadFragment ID", "" + id);
                        Intent appointmentactivity = new Intent(getActivity().getApplicationContext(), AppointmentDetailsActivity.class);
                        appointmentactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appointmentactivity.putExtra("LeadId", id);
                        getActivity().startActivity(appointmentactivity);
                        recyclerView.getRecycledViewPool().clear();

                        //   transact();

                    }


                })
        );


        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);
        //calimage = (ImageView)myFragmentView.findViewById(R.id.cal);
        //weekly = (Button)myFragmentView.findViewById(R.id.weekly) ;
        // monthly = (Button)myFragmentView.findViewById(R.id.monthly) ;
        //calimage.setImageResource(R.drawable.date_bg);

//        weekly.setBackground(btn_click);
//        monthly.setBackground(btn_unclick);
//
//        weekly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // calimage.setImageResource(R.drawable.date_bg);
//                weekly.setBackground(btn_click);
//                monthly.setBackground(btn_unclick);
//
//
//
//
//            }
//        });
//        monthly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // calimage.setImageResource(R.drawable.monthly_bg);
//                weekly.setBackground(btn_unclick);
//                monthly.setBackground(btn_click);
//
//
//
//
//            }
//        });
        /*ImageView logoutBTN = (ImageView) myFragmentView.findViewById(R.id.menu);
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_logout  = new Intent(getActivity(), SigninActivity.class);
                intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_logout);

            }
        });*/


        add = (Button) myFragmentView.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appointmentactivity = new Intent(getActivity(), AddAppointmentActivity.class);
                getActivity().startActivity(appointmentactivity);

            }
        });
        // prepareData();

        nvDrawer = (NavigationView) myFragmentView.findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);

        mDrawer = (DrawerLayout) myFragmentView.findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        ImageView menu = (ImageView) myFragmentView.findViewById(R.id.cal_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(nvDrawer);
            }
        });


//        collapsibleCalendar = (CollapsibleCalendar)myFragmentView.findViewById(R.id.calendar);
//        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
//            @Override
//            public void onDaySelect(String date) {
//
//                datestr = date;
//                Toast.makeText(myFragmentView.getContext(),datestr,Toast.LENGTH_SHORT).show();
//
//            }
//        });

        return myFragmentView;
    }

    public void JSON_DATA_WEB_CALL() {

        jsonObject = new JSONObject();
        String date = datemonth;
        int ee = flag_Calender;
        GetDataAdapter1 = new ArrayList<>();
        try {
            jsonObject.put("UserId", userId);
            jsonObject.put("flag_ID", flag_Calender);
            jsonObject.put("Currentdate", date);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  Log.v("JsonObject", "" + jsonObject.toString());
        @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, Utlity.AppointmentListUrl, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                     /*   sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                        refreshCal = sharedpreferences.getInt("refreshCal", 0);
                        if (refreshCal == 1) {
                            JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        }
                        int a = 0;
                        calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);
                        recyclerView.removeAllViewsInLayout();*/

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.v("Error", "" + error.toString());

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);

                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(jsObjRequest);

        int a = 0;
       /* Shifna*/
       /* calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);
        recyclerView.removeAllViewsInLayout();*/


    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject jsonObject) {

        JSONArray jarray = jsonObject.optJSONArray("data");

        for (int i = 0; i < jarray.length(); i++) {
            AppointmentListingRecyclerDataAdapter GetDataAdapter2 = new AppointmentListingRecyclerDataAdapter();
            try {
                JSONObject abc = jarray.getJSONObject(i);

                lead_Name = abc.getString("lead_Name");
                Apnmt_Location = abc.getString("location");
                Apnmt_lead_id = abc.getInt("lead_ID");
                Apnmt_Time = abc.getString("appointment_Time");
                Apnmt_date = abc.getString("appointment_Date");
                Apnmt_profileimage = abc.getString("leadProfileimage");
                image = ImageUrl + Apnmt_profileimage;
                GetDataAdapter2.setLead_name(lead_Name);
                GetDataAdapter2.setLead_address(Apnmt_Location);
                GetDataAdapter2.setLead_ID(Apnmt_lead_id);
                GetDataAdapter2.setLead_time(Apnmt_Time);
                GetDataAdapter2.setLead_imageUrl(image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);

        }

        int a = 0;
        /*shifna*/
     /*   calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        // Log.v(TAG, "RECYCLER");
        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);*/

    }


    //////////////////////////////////////REMINDER DATES on date click//////////////////////////////////////////////////
    public void JSON_DATA_WEB_CALL1() {

        jsonObject = new JSONObject();
        String date = datemonth;
        GetDataAdapter1 = new ArrayList<>();
        try {
            jsonObject.put("UserId", userId);
            jsonObject.put("Current_Date", date);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  Log.v("JsonObject", "" + jsonObject.toString());
        @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
        String url = "http://202.88.239.14:8169/api/Lead/GetReminders";
        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                      /*  try {
                            loading.dismiss();
                            String ss = "Success";
                            String Status = response.getString("status").toString();
                            if (ss.compareTo(Status) == 0) {
                                sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                                refreshCal = sharedpreferences.getInt("refreshCal", 0);
                                if (refreshCal == 1) {
                                    JSON_PARSE_DATA_AFTER_WEBCALL1(response);
                                }
                            } else {
                                Toast.makeText(getActivity(), "No Appointments Found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }
*/

//                        int a = 0;
//                        calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
//                        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);
//                        recyclerView.removeAllViewsInLayout();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.v("Error", "" + error.toString());

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);

                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(jsObjRequest);

        int a = 0;
        /*shifna*/
/*        calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);
        recyclerView.removeAllViewsInLayout();*/


    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL1(JSONObject jsonObject) {

        JSONArray jarray = jsonObject.optJSONArray("data");
        if (jarray.length() > 0) {

            for (int i = 0; i < jarray.length(); i++) {
                AppointmentListingRecyclerDataAdapter GetDataAdapter2 = new AppointmentListingRecyclerDataAdapter();
                try {
                    JSONObject abc = jarray.getJSONObject(i);

                    lead_Name = abc.getString("firstName");
                    Apnmt_Location = abc.getString("location");
                    Apnmt_lead_id = abc.getInt("lead_ID");
                    Apnmt_Time = abc.getString("appointment_Time");
                    Apnmt_date = abc.getString("appointment_Date");
                    Apnmt_profileimage = abc.getString("leadProfileimage");
                    image = ImageUrl + Apnmt_profileimage;
                    GetDataAdapter2.setLead_name(lead_Name);
                    GetDataAdapter2.setLead_address(Apnmt_Location);
                    GetDataAdapter2.setLead_ID(Apnmt_lead_id);
                    GetDataAdapter2.setLead_time(Apnmt_Time);
                    GetDataAdapter2.setLead_imageUrl(image);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GetDataAdapter1.add(GetDataAdapter2);

            }
        }
        int a = 0;
            /*Shifna*/
      /*  calenderrecyclerViewadapter = new CalenderRecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        // Log.v(TAG, "RECYCLER");
        recyclerView.swapAdapter(calenderrecyclerViewadapter, true);*/

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        System.out.println(" Inside setupDrawerContent ");
                        selectDrawerItem(menuItem);
                        System.out.println(" After calling selectDrawerItem");
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        System.out.print("inside selectDrawerItem ");
        android.support.v4.app.Fragment fragment = null;

        Class fragmentClass;
        switch (menuItem.getItemId()) {

            case R.id.accountsettings:
                //fragmentClass = ListingPropertyActivity.class;
                Intent profilesettingsintent = new Intent(getActivity(), ProfileSettingsActivity.class);
                getActivity().startActivity(profilesettingsintent);
                break;
            case R.id.notifications:
                // fragmentClass = ListingMyFavoritesActivity.class;
                //Intent b=new Intent(CustomerListingMyFavoritesActivity.this,CustomerListingMyFavoritesActivity.class);
                //startActivity(b);
                break;
            case R.id.logout:
                Intent intent_logout = new Intent(getActivity(), SigninActivity.class);
                intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_logout);
                break;
            case R.id.changepass:
                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file
                dialog.setContentView(R.layout.password_change_dialog);
                dialog.setCanceledOnTouchOutside(true);
                // Set dialog title
                String titleText = "Change Password";
                //dialog.setTitle("Cha");
                ForegroundColorSpan backgroundColorSpan = new ForegroundColorSpan(Color.BLACK);
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                // Apply the text color span
                ssBuilder.setSpan(
                        backgroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                dialog.setTitle(ssBuilder);
                //titleText.(Gravity.CENTER_HORIZONTAL);*/

                dialog.show();
                Button okbtn = (Button) dialog.findViewById(R.id.ok);
                oldpass = (EditText) dialog.findViewById(R.id.oldpassword);
                newpass = (EditText) dialog.findViewById(R.id.newpassword);
                confrmpass = (EditText) dialog.findViewById(R.id.confirmpassword);
                requestChangePassword = Volley.newRequestQueue(getActivity().getApplicationContext());
                sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                final String Email_id = sharedpreferences.getString("Email", null);
                // if decline button is clicked, close the custom dialog
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        oldpasstxt = oldpass.getText().toString();
                        newpasstxt = newpass.getText().toString();
                        confrmpasstxt = confrmpass.getText().toString();
                        if ((oldpasstxt.equals("")) || (newpasstxt.equals("")) || (confrmpasstxt.equals(""))) {
//
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (!(newpasstxt.equals(confrmpasstxt))) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "New password and confirm password should be same", Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                //////////////////////////////////////////////// CHANGE PASSWORD -->LOGIN ////////////////////////////////////////////

                                //emailForgot = forgot_emailTxt.getText().toString();
                                //  mobForgot = forgot_mobTxt.getText().toString();
                                String url = "http://202.88.239.14:8169/api/userregistration/Changepassword";
                                Map<String, Object> jsonParams = new ArrayMap<>();
                                jsonParams.put("Email", Email_id);
                                jsonParams.put("newpassword", newpasstxt);


                                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    String status = response.getString("status").toString().trim();
                                                    String message = response.getString("message").toString().trim();
                                                    String str3 = "Success";
                                                    if (status.compareTo(str3) == 0) {
                                                        progressDialog.dismiss();
                                                        dialog.dismiss();
                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

//                                                        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//                                                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                                                        editor.putInt("firstTime", 1);
//                                                        editor.commit();

                                                    } else {
                                                        progressDialog.dismiss();
                                                        dialog.dismiss();
                                                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                    }


                                                } catch (JSONException e) {
                                                    progressDialog.dismiss();
                                                    dialog.dismiss();
                                                    e.printStackTrace();
                                                    Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                progressDialog.dismiss();
                                                dialog.dismiss();
                                                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                requestChangePassword.add(request1);
                            }

                        }
//
//
                    }


                });
                break;

            default:

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
//        JSON_DATA_WEB_CALL();
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private String getMonthShortName(int monthNumber) {


        if (monthNumber >= 0 && monthNumber < 12)
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                // simpleDateFormat.setCalendar ;
                monthName = simpleDateFormat.format(calendar.getTime());

                // Toast.makeText(getApplicationContext(),monthName,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                if (e != null)
                    e.printStackTrace();
            }
        return monthName;
    }
}
