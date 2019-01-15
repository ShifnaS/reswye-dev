package com.nexgensm.reswye.ui.Dashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Utlity;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.lead.AddNewLeadCategoryActivity;
import com.nexgensm.reswye.ui.lead.AddNewUploadDocFragment;
import com.nexgensm.reswye.ui.lead.LeadListingRecyclerDataAdapter;
import com.nexgensm.reswye.ui.lead.LeadRecyclerViewAdapter;
import com.nexgensm.reswye.ui.lead.PropertyViewPagerAdapter;
import com.nexgensm.reswye.ui.lead.RecyclerViewAdapter;
import com.nexgensm.reswye.ui.lead.SellerDetailsActivity;
import com.nexgensm.reswye.ui.navigationdrawer.ProfileSettingsActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;
    TextView counttxt;
    TextView leadtxt;
    TextView pertxt;
    TextView missedfollowupscount;
    TextView upcomingfollowup;
    ImageView logout;
    TextView leadname, leaddate;
    ImageView leadPic;
    private RecyclerView recyclerView;

    private LeadImageAdapter leadImageAdapter;
    Spinner spinner;
    View myFragmentView;
    String[] items;
    EditText oldpass;
    EditText newpass;
    EditText confrmpass;

    String[] date;
    String[] missed_followups;
    String[] upcoming_followups;

    ViewPager viewPager;
    PagerAdapter adapter;
    RelativeLayout currentLeadLyt;
    String oldpasstxt, newpasstxt, confrmpasstxt, closed_percentage_txt, failed_percentage_txt, open_percentage_txt, imageUrl;
    String firstRecentLeadname, firstRecentLeadTime, firstRecentLeadImage;
    Context context;
    NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    RequestQueue requestQueue, req, requestQ, recentLeadImg, jsonRequest;
    String result, Status_missed, profileimage;
    String Token, ImageUrl,Status_count, name1, address, appointment_Date, leadCreated_date, missedFollowupcount,   upcomingFollowupcount, dormantLeadCount;
    String url, opentxt, closedtxt, failedtxt, totaltxt;
    int userId, lead_ID;
    float open, closed, failed, total, open_percentage, closed_percentage, failed_percentage;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private static final Integer[] lead_images = {R.drawable.profile_2, R.drawable.profile_3, R.drawable.profile_4, R.drawable.profile_5, R.drawable.profile_6, R.drawable.profile_6, R.drawable.profile_6, R.drawable.profile_2, R.drawable.profile_2, R.drawable.profile_2, R.drawable.profile_2};
    RequestQueue requestChangePassword;

    LeadImageItem leadImageItem;
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    List<LeadListingRecyclerDataAdapter> GetDataAdapterLead;
    RecyclerViewAdapter recyclerViewadapter;
    String[] missedCountArray, upComingArray, dormantArray;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        GetDataAdapterLead = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        req = Volley.newRequestQueue(getActivity().getApplicationContext());
        recentLeadImg = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQ = Volley.newRequestQueue(getActivity().getApplicationContext());

        missedCountArray = new String[3];
        upComingArray = new String[3];
        dormantArray = new String[3];

        for (int i = 0; i < lead_images.length; i++)
        ImageArray.add(lead_images[i]);

        myFragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        counttxt = (TextView) myFragmentView.findViewById(R.id.count);
        leadtxt = (TextView) myFragmentView.findViewById(R.id.lead);
        pertxt = (TextView) myFragmentView.findViewById(R.id.percentage);
        leadname = (TextView) myFragmentView.findViewById(R.id.selected_lead_name);
        leaddate = (TextView) myFragmentView.findViewById(R.id.selected_lead_date);
        leadPic = (ImageView) myFragmentView.findViewById(R.id.selected_image);


        viewPager = (ViewPager) myFragmentView.findViewById(R.id.pager);
        date = new String[]{"This Week", "This Month", "Today"};
        missed_followups = new String[]{"11", "14", "45"};
        upcoming_followups = new String[]{"11", "14", "46"};

        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("UserId", userId);
        editor.putString("token", Token);
        editor.putString("imageURL", ImageUrl);
        editor.putInt("refresh",1);
        editor.putInt("refreshCal",1);
        editor.commit();


        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.dashboard_recycler_view);
        leadImageAdapter = new LeadImageAdapter(ImageArray);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myFragmentView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        currentLeadLyt = (RelativeLayout) myFragmentView.findViewById(R.id.currentLead);
        currentLeadLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RecentLeadIntent = new Intent(getActivity(), RecentLeadListActivity.class);
                startActivity(RecentLeadIntent);
            }
        });

        RECENT_LEAD_DASHBOARD();
        FOLLOWUP_COUNTS();
        COUNT_OF_LEAD_PIECHART();
        //progressDialog.dismiss();
        recyclerView.addOnItemTouchListener(
         new RecentLeadItemClickListener(getActivity().getApplicationContext(), new RecentLeadItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        String name = GetDataAdapterLead.get(position).getLead_name();
                        String image = GetDataAdapterLead.get(position).getLead_imageUrl();
                        String time = GetDataAdapterLead.get(position).getLead_time();
                        leadname.setText(name);
                        leaddate.setText(time);
                        Picasso.with(getActivity()).load(image).into(leadPic);
                    }
                })
        );

        nvDrawer = (NavigationView) myFragmentView.findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        mDrawer = (DrawerLayout) myFragmentView.findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ImageView menu = (ImageView) myFragmentView.findViewById(R.id.navigation_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(nvDrawer);
            }
        });
        return myFragmentView;
    }

    public void COUNT_OF_LEAD_PIECHART(){
        url = "http://202.88.239.14:8169/api/Lead/GetCountofLeads/"+userId;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Status_count = response.getString("status").toString().trim();


                           // closed = response.getInt("closed_LeadsCount");
                           // open = response.getInt("open_LeadsCount");
                           // failed = response.getInt("failedLeads_Count");
                            closed=50;
                            open=35;
                            failed=15;
                            String str3 = "Success";
                           // int response_result = Status_count.compareTo(str3);
                            int response_result=0;
                            if (response_result == 0) {


                                total = open + closed + failed;
                                open_percentage = (float) (open / total) * 100;
                                closed_percentage = (float) (closed / total) * 100;
                                failed_percentage = (float) (failed / total) * 100;

                                PieChart pieChart = (PieChart) myFragmentView.findViewById(R.id.piechart);
                                //pieChart.setUsePercentValues(true);

                                // IMPORTANT: In a PieChart, no values (Entry) should have the same
                                // xIndex (even if from different DataSets), since no values can be
                                // drawn above each other.
                                ArrayList<Entry> yvalues = new ArrayList<Entry>();
                                yvalues.add(new Entry(closed_percentage, 0));
                                yvalues.add(new Entry(open_percentage, 1));
                                yvalues.add(new Entry(failed_percentage, 2));


                                PieDataSet dataSet = new PieDataSet(yvalues, "");

                                ArrayList<String> xVals = new ArrayList<String>();

                                xVals.add("Closed Leads");
                                xVals.add("Open Leads");
                                xVals.add("Failed Leads");


                                PieData data1 = new PieData(xVals, dataSet);
                                data1.setValueFormatter(new PercentFormatter());
                                pieChart.setData(data1);
                                // pieChart.setDescription("This is Pie Chart");

                                // pieChart.setDrawHoleEnabled(true);
                                pieChart.setTransparentCircleRadius(25f);
                                pieChart.setHoleRadius(70f);

                                //added for checking by nithu

                                final int[] MY_COLORS = {Color.rgb(48, 213, 200), Color.rgb(244, 215, 152), Color.rgb(88, 86, 86)};
                                ArrayList<Integer> colors = new ArrayList<Integer>();

                                for (int c : MY_COLORS) colors.add(c);

                                dataSet.setColors(colors);

                                //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                //data.setValueTextSize(13f);
                                //data.setValueTextColor(Color.DKGRAY);
                                totaltxt = String.valueOf((int) total);
                                closedtxt = String.valueOf((int) closed);
                                opentxt = String.valueOf((int) open);
                                failedtxt = String.valueOf((int) failed);
                                closed_percentage_txt = String.valueOf((int) closed_percentage);
                                failed_percentage_txt = String.valueOf((int) failed_percentage);
                                open_percentage_txt = String.valueOf((int) open_percentage);

                                counttxt.setText(opentxt + "/" + totaltxt);
                                leadtxt.setText("Open Leads");
                                pertxt.setText(open_percentage_txt + "%");
                                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                    @Override
                                    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                                        //Toast.makeText(getActivity(),"sbjnjs",Toast.LENGTH_SHORT).show();
                                        // final ArrayList<String> xVals = new ArrayList<String>();
                                        //Then use getXIndex() method from entry object. For example:

                                        //xVals.get(e.getXIndex());
                                        //String str = xVals.get(0);
                                        //String str1 =str.toString();
                                        //Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();

                                        int xindex = e.getXIndex();

                                        if (xindex == 0) {
                                            counttxt.setText(closedtxt + "/" + totaltxt);
                                            leadtxt.setText("Closed Leads");
                                            pertxt.setText(closed_percentage_txt + "%");

                                        }

                                        if (xindex == 1) {
                                            counttxt.setText(opentxt + "/" + totaltxt);
                                            leadtxt.setText("Open Leads");
                                            pertxt.setText(open_percentage_txt + "%");

                                        }
                                        if (xindex == 2) {
                                            counttxt.setText(failedtxt + "/" + totaltxt);
                                            leadtxt.setText("Failed Leads");
                                            pertxt.setText(failed_percentage_txt + "%");

                                        }
                                        float str1 = e.getVal();
                                        //String str = ((String) str1);
                                        //String str =  Float.toString(str1);
                                        //Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
                                        Log.i("VAL SELECTED",
                                                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                                        + ", DataSet index: " + dataSetIndex);

                                    }

                                    @Override
                                    public void onNothingSelected() {

                                    }
                                });

                                pieChart.animateXY(1400, 1400);

                            } else {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                closed=50;
                open=35;
                failed=15;





                    total = open + closed + failed;
                    open_percentage = (float) (open / total) * 100;
                    closed_percentage = (float) (closed / total) * 100;
                    failed_percentage = (float) (failed / total) * 100;

                    PieChart pieChart = (PieChart) myFragmentView.findViewById(R.id.piechart);



                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(closed_percentage, 0));
                    yvalues.add(new Entry(open_percentage, 1));
                    yvalues.add(new Entry(failed_percentage, 2));


                    PieDataSet dataSet = new PieDataSet(yvalues, "");
                    ArrayList<String> xVals = new ArrayList<String>();


                    xVals.add("Closed Leads");
                    xVals.add("Open Leads");
                    xVals.add("Failed Leads");





                    //PieData data1 = new PieData(xVals, dataSet);
                PieData data1 = new PieData(xVals, dataSet);

                data1.setValueFormatter(new PercentFormatter());
                    pieChart.setData(data1);

                    pieChart.setTransparentCircleRadius(25f);
                    pieChart.setHoleRadius(70f);




                    //added for checking by nithu

                    final int[] MY_COLORS = {Color.rgb(48, 213, 200), Color.rgb(244, 215, 152), Color.rgb(88, 86, 86)};
                    ArrayList<Integer> colors = new ArrayList<Integer>();

                    for (int c : MY_COLORS) colors.add(c);
                    dataSet.setColors(colors);




                    totaltxt = String.valueOf((int) total);
                    closedtxt = String.valueOf((int) closed);
                    opentxt = String.valueOf((int) open);
                    failedtxt = String.valueOf((int) failed);
                    closed_percentage_txt = String.valueOf((int) closed_percentage);
                    failed_percentage_txt = String.valueOf((int) failed_percentage);
                    open_percentage_txt = String.valueOf((int) open_percentage);

                    counttxt.setText(opentxt + "/" + totaltxt);
                    leadtxt.setText("Open Leads");
                    pertxt.setText(open_percentage_txt + "%");
                    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


                            int xindex = e.getXIndex();

                            if (xindex == 0) {
                                counttxt.setText(closedtxt + "/" + totaltxt);
                                leadtxt.setText("Closed Leads");
                                pertxt.setText(closed_percentage_txt + "%");

                            }

                            if (xindex == 1) {
                                counttxt.setText(opentxt + "/" + totaltxt);
                                leadtxt.setText("Open Leads");
                                pertxt.setText(open_percentage_txt + "%");

                            }
                            if (xindex == 2) {
                                counttxt.setText(failedtxt + "/" + totaltxt);
                                leadtxt.setText("Failed Leads");
                                pertxt.setText(failed_percentage_txt + "%");

                            }
                            float str1 = e.getVal();

                            Log.i("VAL SELECTED",
                                    "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                            + ", DataSet index: " + dataSetIndex);

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });

                    pieChart.animateXY(1400, 1400);





                Toast.makeText(getActivity(), "Error" + error, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        requestQueue.add(jsonRequest);

    }
    public  void RECENT_LEAD_DASHBOARD(){
        url = "http://202.88.239.14:8169/api/Lead/recentLeads/"+userId;
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Status_missed = response.getString("status").toString().trim();


                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
                                JSONObject data = jsonArray.getJSONObject(i);
                                JSONObject firstData = jsonArray.getJSONObject(0);
                                firstRecentLeadname = firstData.getString("firstName");
                                firstRecentLeadTime = firstData.getString("lead_CreatedDate");
                                String firstimageurl = firstData.getString("leadProfileimage");
                                firstRecentLeadImage = ImageUrl + firstimageurl;
                                name1 = data.getString("firstName");
                                profileimage = data.getString("leadProfileimage");
                                leadCreated_date = data.getString("lead_CreatedDate");
                                String image = ImageUrl + profileimage;
                                GetDataAdapter2.setLead_name(name1);
                                GetDataAdapter2.setLead_imageUrl(image);
                                GetDataAdapter2.setLead_time(leadCreated_date);
                                GetDataAdapterLead.add(GetDataAdapter2);
                                String str3 = "Success";
                                int response_result = Status_missed.compareTo(str3);
                                if (response_result == 0) {
                                    progressDialog.dismiss();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            int a = 3;
                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapterLead, getActivity().getApplication(), a);
                            recyclerView.setAdapter(recyclerViewadapter);

                            leadname.setText(firstRecentLeadname);
                            leaddate.setText(firstRecentLeadTime);
                            Picasso.with(getActivity()).load(firstRecentLeadImage).into(leadPic);


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error_Recent " + error, Toast.LENGTH_SHORT).show();


                for (int i = 0; i < 10; i++) {
                    LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
                    firstRecentLeadname = "David";
                    firstRecentLeadTime ="26/12/2018";
                    firstRecentLeadImage = "http://firstflagrealty.com/images/ProfileImages/10237.jpg";
                    name1 = "David";
                    profileimage = "http://firstflagrealty.com/images/ProfileImages/10237.jpg";
                    leadCreated_date = "26/12/2018";
                    String image = "http://firstflagrealty.com/images/ProfileImages/10237.jpg";
                    GetDataAdapter2.setLead_name("David");
                    GetDataAdapter2.setLead_imageUrl(image);
                    GetDataAdapter2.setLead_time("26/12/2018");
                    GetDataAdapterLead.add(GetDataAdapter2);
                    int response_result = 0;
                    if (response_result == 0) {
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                int a = 3;
                recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapterLead, getActivity().getApplication(), a);
                recyclerView.setAdapter(recyclerViewadapter);

                leadname.setText(firstRecentLeadname);
                leaddate.setText(firstRecentLeadTime);
                Picasso.with(getActivity()).load(firstRecentLeadImage).into(leadPic);


            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        recentLeadImg.add(jsonObjectRequest);
    }
    public void FOLLOWUP_COUNTS(){
        String testUrl = "http://202.88.239.14:8169/api/Lead/followupCounts/"+userId;
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, testUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("TESTJSON", "" + response.toString());
                        try {
                            JSONArray weekCount = response.getJSONArray("weekCount");
                            JSONArray monthCount = response.getJSONArray("monthCount");
                            JSONArray dayCount = response.getJSONArray("dayCount");
                            JSONObject weekCountJSONObject = weekCount.getJSONObject(0);
                            JSONObject monthCountJSONObject = monthCount.getJSONObject(0);
                            JSONObject dayCountJSONObject = dayCount.getJSONObject(0);
                            String weekMissed = weekCountJSONObject.getString("missedfollowups");
                            String weekupcoming = weekCountJSONObject.getString("upcomingfollowups");
                            String weekdormant = weekCountJSONObject.getString("dormantList");

                            String monthmissed = monthCountJSONObject.getString("missedfollowups");
                            String monthupcoming = monthCountJSONObject.getString("upcomingfollowups");
                            String monthdormant = monthCountJSONObject.getString("dormantList");

                            String daymissed = dayCountJSONObject.getString("missedfollowups");
                            String dayupcoming = dayCountJSONObject.getString("upcomingfollowups");
                            String daydormant = dayCountJSONObject.getString("dormantList");

                            missedCountArray[0] = weekMissed;
                            missedCountArray[1] = monthmissed;
                            missedCountArray[2] = daymissed;
                            upComingArray[0] = weekupcoming;
                            upComingArray[1] = monthupcoming;
                            upComingArray[2] = dayupcoming;
                            dormantArray[0] = weekdormant;
                            dormantArray[1] =  monthdormant;
                            dormantArray[2] = daydormant;

                            adapter = new DashboardViewPagerAdapter(getActivity(), date, missedCountArray, upComingArray,dormantArray);
                            viewPager.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error_Follow_Up " + error, Toast.LENGTH_SHORT).show();

                String weekMissed = "120";
                String weekupcoming = "50";
                String weekdormant = "30";

                String monthmissed = "120";
                String monthupcoming = "50";
                String monthdormant = "30";

                String daymissed = "120";
                String dayupcoming = "150";
                String daydormant = "130";

                missedCountArray[0] = weekMissed;
                missedCountArray[1] = monthmissed;
                missedCountArray[2] = daymissed;
                upComingArray[0] = weekupcoming;
                upComingArray[1] = monthupcoming;
                upComingArray[2] = dayupcoming;
                dormantArray[0] = weekdormant;
                dormantArray[1] =  monthdormant;
                dormantArray[2] = daydormant;

                adapter = new DashboardViewPagerAdapter(getActivity(), date, missedCountArray, upComingArray,dormantArray);
                viewPager.setAdapter(adapter);



            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        recentLeadImg.add(jsonObjectRequest1);
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
            case R.id.renew:
                // fragmentClass = ListingMyFavoritesActivity.class;
                //Intent b=new Intent(CustomerListingMyFavoritesActivity.this,CustomerListingMyFavoritesActivity.class);
                //startActivity(b);
                break;
            case R.id.refer:
                // fragmentClass = ListingMyFavoritesActivity.class;
                //Intent b=new Intent(CustomerListingMyFavoritesActivity.this,CustomerListingMyFavoritesActivity.class);
                //startActivity(b);
                break;
            case R.id.changepass:
                // fragmentClass = ListingMyFavoritesActivity.class;
                //Intent b=new Intent(CustomerListingMyFavoritesActivity.this,CustomerListingMyFavoritesActivity.class);
                //startActivity(b);
                break;
            case R.id.logout:
                Intent intent_logout = new Intent(getActivity(), SigninActivity.class);
                intent_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_logout);
                break;

           /* case R.id.changepass:
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

                dialog.show();
                //otp_mob =(EditText)dialog.findViewById(R.id.otp_mobile) ;
                //otp_email = (EditText)dialog.findViewById(R.id.otp_email) ;

                Button okbtn = (Button) dialog.findViewById(R.id.ok);
                oldpass = (EditText) dialog.findViewById(R.id.oldpassword);
                newpass = (EditText) dialog.findViewById(R.id.newpassword);
                confrmpass = (EditText) dialog.findViewById(R.id.confirmpassword);
                requestChangePassword=Volley.newRequestQueue(getActivity().getApplicationContext());
                sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                final String Email_id=sharedpreferences.getString("Email",null);
                // if decline button is clicked, close the custom dialog
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog=new ProgressDialog(getActivity());
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
                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
//                                                        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//                                                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                                                        editor.putInt("firstTime", 1);
//                                                        editor.commit();


                                                    } else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
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
*/
               // break;
            default:

        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
