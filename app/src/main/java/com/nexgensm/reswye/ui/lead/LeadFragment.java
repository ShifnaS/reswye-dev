package com.nexgensm.reswye.ui.lead;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.ResponseList;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.navigationdrawer.ProfileSettingsActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

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
 * {@link LeadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeadFragment extends Fragment {
    RequestQueue requestQueue1, requestQueue2, requestQueue;
    private String TAG = "LeadFragment";
    //    String result,image;
    String Token, Status_missed, name1, address, lead_CreatedDate, oldpasstxt, confrmpasstxt, newpasstxt;
    String url, ImageUrl, profileimage, image, leadurl, lead_categoryBS;
    int userId, lead_ID, sortsave, filterCheck;
    SharedPreferences sharedpreferences;
    EditText oldpass, newpass, confrmpass;
    public static final String mypreference = "mypref";
    private List<LeadItems> leadItemsList = new ArrayList<>();
    private RecyclerView recyclerView;
    List<LeadListingRecyclerDataAdapter> GetDataAdapter1;
    RecyclerViewAdapter recyclerViewadapter;
    private JSONObject jsonObject;
    private LeadRecyclerViewAdapter leadRecyclerViewAdapter;
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    int refresh;
    ProgressDialog nDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RequestQueue requestChangePassword;
    private String mParam1;
    private String mParam2;
    int flagcontact;
    NavigationView nvDrawer;
    private DrawerLayout mDrawer;

    LeadItems leadItems;
    private OnFragmentInteractionListener mListener;

    public LeadFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LeadFragment newInstance(String param1, String param2) {
        LeadFragment fragment = new LeadFragment();
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

        jsonObject = new JSONObject();
        GetDataAdapter1 = new ArrayList<>();
        userId = SharedPrefsUtils.getInstance(getActivity()).getUserId();
        Window window = getActivity().getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.toolbarcolor));

        View myFragmentView = inflater.inflate(R.layout.fragment_lead, container, false);
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


       // JSON_DATA_WEB_CALL();
        recyclerView.addOnItemTouchListener(

                new LeadRecyclerItemClickListener(getActivity().getApplicationContext(), new LeadRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String name = GetDataAdapter1.get(position).getLead_name();
                        String image = GetDataAdapter1.get(position).getLead_imageUrl();
                        Integer id = GetDataAdapter1.get(position).getLead_ID();
                        Log.v("LeadFragment ID", "" + id);
                        int a = 0;
                        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                        recyclerView.swapAdapter(recyclerViewadapter, true);
                        Intent addnewsellerdetailsactivity = new Intent(getActivity().getApplicationContext(), SellerDetailsActivity.class);
                        addnewsellerdetailsactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        addnewsellerdetailsactivity.putExtra("LeadId", id);
                        getActivity().startActivity(addnewsellerdetailsactivity);
                        recyclerView.getRecycledViewPool().clear();

                        //   transact();

                    }


                })
        );

        Button AddNewBtn = (Button) myFragmentView.findViewById(R.id.Add_New);
        AddNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent addNew = new Intent(getActivity(), AddNewSellerActivity.class);
                // getActivity().startActivity(addNew);
                //Bundle b = new Bundle();
                //b.putParcelable("com.example.trafficlight", leadItems);

                Intent addNewLeadCategory = new Intent(getActivity(), AddNewLeadCategoryActivity.class);
                addNewLeadCategory.putExtra("myobject", leadItems);
                //addNewLeadCategory.putExtras(b);
                getActivity().startActivityForResult(addNewLeadCategory, 1);

            }
        });

        ImageButton findlead = (ImageButton) myFragmentView.findViewById(R.id.find_lead);
        findlead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leadFindActivity = new Intent(getActivity(), LeadFindActivity.class);
                getActivity().startActivity(leadFindActivity, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        ImageButton sortlead = (ImageButton) myFragmentView.findViewById(R.id.lead_sort);
        sortlead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent leadsortActivity = new Intent(getActivity(), LeadSortedActivity.class);
                getActivity().startActivity(leadsortActivity);

            }
        });

        nvDrawer = (NavigationView) myFragmentView.findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        mDrawer = (DrawerLayout) myFragmentView.findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        ImageView menu = (ImageView) myFragmentView.findViewById(R.id.navigation_lead);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(nvDrawer);
            }
        });

         JSON_DATA_WEB_CALL();
        return myFragmentView;
    }

    public void JSON_DATA_WEB_CALL() {
        Log.v(TAG, "JSON CALL");
        filterCheck = Singleton.getInstance().getFilterFlag();

        ////check if previous filter value has been set , value 1 means previous filter value is set. Retreive the value from singleton class

        if (filterCheck == 1) {
            Log.v(TAG, "Filter ONE");
            String agentname = Singleton.getInstance().getFilterAgent();
            String firstname = Singleton.getInstance().getFilterFirstName();
            String lastname = Singleton.getInstance().getFilterLastName();
            int propID = Singleton.getInstance().getPropID();
            String propIDD = Singleton.getInstance().getPropIDD();
//           int  test =Integer.valueOf(propIDD);
            String leadStatus = Singleton.getInstance().getFilterLeadStatus();
            String loc = Singleton.getInstance().getFilterLoc();
            String date = Singleton.getInstance().getFilterDate();

            String mob = Singleton.getInstance().getFilterMob();
            String email = Singleton.getInstance().getFilterEmail();
            String minvalue = Singleton.getInstance().getMinvalue();
            String maxvalue = Singleton.getInstance().getMaxvalue();
            String propStatus = Singleton.getInstance().getPropStatus();
            Boolean filterTransferStatus = Singleton.getInstance().getFilterStatus();
            int leadCategoryBS = Singleton.getInstance().getLeadCategory();
            int filterFlag = Singleton.getInstance().getFilterFlag();
            int isfilterchecked = Singleton.getInstance().getIsfilterDefaultt();


///////API CALL to send and get filter parameters///////
            jsonObject = new JSONObject();
            GetDataAdapter1 = new ArrayList<>();
            try {
                //  jsonObject.put("LeadStatus", leadStatus);
                jsonObject.put("Location", loc);
                jsonObject.put("Created_Date", date);
                jsonObject.put("Min:Price", minvalue);
                jsonObject.put("maxPrice", maxvalue);
                // jsonObject.put("LeadWarmth", propStatus);
                // jsonObject.put("LeadCategory", leadStatus);
                // jsonObject.put("TransferStatus", filterTransferStatus);
                jsonObject.put("AgentName", agentname);
                jsonObject.put("LeadFirstName", firstname);
                jsonObject.put("LeadLastName", lastname);
                jsonObject.put("Mobile", mob);
                jsonObject.put("Email", email);
                jsonObject.put("PropertyID", propID);
                //  jsonObject.put("YearBuiltStart", "");
                //  jsonObject.put("YearBuiltEnd", "");
                jsonObject.put("isdefault", 0);
            } catch (JSONException e) {
                Log.v(TAG, e.toString());
            }

            @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
            JsonObjectRequest jsObjRequest;
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Utlity.leadFilterUrl, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            loading.dismiss();

                            JSONArray jarray = response.optJSONArray("data");
                            if (jarray.length() > 0) {
                                for (int i = 0; i < jarray.length(); i++) {
                                    LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
                                    try {
                                        JSONObject abc = jarray.getJSONObject(i);

                                        name1 = abc.getString("firstName");
                                        address = abc.getString("address");
                                        lead_ID = abc.getInt("lead_ID");

                                        lead_categoryBS = abc.getString("leadCategory");
                                        lead_CreatedDate = abc.getString("lead_CreatedDate");
                                        profileimage = abc.getString("leadProfileimage");
                                        image = ImageUrl + profileimage;
                                        GetDataAdapter2.setLead_name(name1);
                                        GetDataAdapter2.setLead_address(address);
                                        GetDataAdapter2.setLead_ID(lead_ID);
                                        GetDataAdapter2.setLead_time(lead_CreatedDate);
                                        GetDataAdapter2.setLead_imageUrl(image);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    GetDataAdapter1.add(GetDataAdapter2);
                                }
                            } else {
                                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                            }
//                            int a = 0;
//                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
//                            recyclerView.swapAdapter(recyclerViewadapter, true);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error", "" + error.toString());
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

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


        } else {
            requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.v(TAG, "NO Filter");
//            JsonObjectRequest jsObjRequest;
//            sortsave = Singleton.getInstance().getSortSaveFlag();
            if (sortsave == 0) {
                Log.v(TAG, "SORT zero");

                @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
                url = "http://192.168.0.3:3000/reswy/listleads/"+userId;
                JsonObjectRequest jsObjRequest1;
                sortsave = Singleton.getInstance().getSortSaveFlag();
                jsObjRequest1 = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                loading.dismiss();
                                Log.v(TAG, "SOT ZERO DATA PARSE");
                                sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                                refresh = sharedpreferences.getInt("refresh", 0);
                                if (refresh == 1) {

                                }
                                JSON_PARSE_DATA_AFTER_WEBCALL(response);
                                int a = 0;
                                recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                                recyclerView.swapAdapter(recyclerViewadapter, true);
                                recyclerView.removeAllViewsInLayout();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", Token);
                        return headers;
                    }
                };
                recyclerView.getRecycledViewPool().clear();
                requestQueue1.add(jsObjRequest1);
                int a = 0;
                // recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                recyclerView.swapAdapter(recyclerViewadapter, true);
                recyclerView.removeAllViewsInLayout();
            } else {
                Log.v(TAG, "SORT one");
                JsonObjectRequest jsObjRequest2;
                sortsave = Singleton.getInstance().getSortSaveFlag();
                jsonObject = new JSONObject();
                GetDataAdapter1 = new ArrayList<>();
                final String fieldname = Singleton.getInstance().getSortFiledname();
                final String ordertype = Singleton.getInstance().getSortOrdertype();
                try {
                    jsonObject.put("UserId", userId);
                    jsonObject.put("SortfieldName", fieldname);
                    jsonObject.put("Sorttype", ordertype);
                } catch (JSONException e) {
                }
                requestQueue2 = Volley.newRequestQueue(getActivity().getApplicationContext());

                @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

                jsObjRequest2 = new JsonObjectRequest
                        (Request.Method.POST, Utlity.sortingUrl, jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                loading.dismiss();

                                JSONArray jarray = response.optJSONArray("sortedData");

                                for (int i = 0; i < jarray.length(); i++) {
                                    LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
                                    try {
                                        JSONObject abc = jarray.getJSONObject(i);

                                        name1 = abc.getString("firstName");
                                        address = abc.getString("address");
                                        lead_ID = abc.getInt("lead_ID");
                                        lead_CreatedDate = "NOt available";
                                        //lead_CreatedDate = abc.getString("lead_CreatedDate");
                                        profileimage = abc.getString("leadProfileimage");
                                        image = ImageUrl + profileimage;
                                        GetDataAdapter2.setLead_name(name1);
                                        GetDataAdapter2.setLead_address(address);
                                        GetDataAdapter2.setLead_ID(lead_ID);
                                        GetDataAdapter2.setLead_time(lead_CreatedDate);
                                        GetDataAdapter2.setLead_imageUrl(image);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    GetDataAdapter1.add(GetDataAdapter2);
                                }

                                int a = 0;
                                recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                                recyclerView.swapAdapter(recyclerViewadapter, true);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
                requestQueue2.add(jsObjRequest2);
            }

        }


    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject jsonObject) {
        Log.v(TAG, "json parse");
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Token = sharedpreferences.getString("token", "");
        ImageUrl = sharedpreferences.getString("imageURL", "");
        JSONArray jarray = jsonObject.optJSONArray("result");
//
//        Log.v("test", "" + jarray.length());
//        Log.v("json", "" + jarray.toString());
        for (int i = 0; i < jarray.length(); i++) {
            LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
            try {
                JSONObject abc = jarray.getJSONObject(i);
                //   Log.v("object", "" + abc.toString());

                name1 = abc.getString("firstname");
                address = abc.getString("address");
                lead_ID = abc.getInt("lead_id");
                lead_CreatedDate = abc.getString("lead_createddate");
                profileimage = abc.getString("leadprofileimage");
                image = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.biowritingservice.com%2Fwp-content%2Fthemes%2Ftuborg%2Fimages%2FExecutive%2520Bio%2520Sample%2520Photo.png&imgrefurl=https%3A%2F%2Fwww.biowritingservice.com%2Four-bio-writing-samples%2F&docid=UmOWXvavlYWmFM&tbnid=zJshq4jeDXBpnM%3A&vet=10ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ..i&w=629&h=764&bih=657&biw=1366&q=sample%20images%20person&ved=0ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ&iact=mrc&uact=8#h=764&imgdii=RhQaTMt0JMuy5M:&vet=10ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ..i&w=629";
                GetDataAdapter2.setLead_name(name1);
                GetDataAdapter2.setLead_address(address);
                GetDataAdapter2.setLead_ID(lead_ID);
                GetDataAdapter2.setLead_time(lead_CreatedDate);
                GetDataAdapter2.setLead_imageUrl(image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);

        }

        int a = 0;

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        Log.v(TAG, "RECYCLER");
        recyclerView.swapAdapter(recyclerViewadapter, true);
        recyclerView.removeAllViewsInLayout();


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

//            case R.id.shareapp:
//                //fragmentClass = ThirdFragment.class;
//                //Intent d=new Intent(CustomerListingMyFavoritesActivity.this,OpenHouseActivity.class);
//                //startActivity(d);
//
//                break;
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
                //otp_mob =(EditText)dialog.findViewById(R.id.otp_mobile) ;
                //otp_email = (EditText)dialog.findViewById(R.id.otp_email) ;

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
                        nDialog = new ProgressDialog(getActivity());
                        nDialog.setMessage("Please Wait");
                        nDialog.show();
                        oldpasstxt = oldpass.getText().toString();
                        newpasstxt = newpass.getText().toString();
                        confrmpasstxt = confrmpass.getText().toString();
                        if ((oldpasstxt.equals("")) || (newpasstxt.equals("")) || (confrmpasstxt.equals(""))) {
//
                            nDialog.dismiss();
                            Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (!(newpasstxt.equals(confrmpasstxt))) {
                                nDialog.dismiss();
                                Toast.makeText(getActivity(), "New password and confirm password should be same", Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                ////////////////////////////// CHANGE PASSWORD -->LOGIN ////////////////////////////////////////////

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
                                                        nDialog.dismiss();
                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
//

                                                    } else {
                                                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                        nDialog.dismiss();
                                                        dialog.dismiss();
                                                    }
//

                                                } catch (JSONException e) {
                                                    nDialog.dismiss();
                                                    dialog.dismiss();
                                                    e.printStackTrace();
                                                    Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                nDialog.dismiss();
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
    public void onAttach(Context context) {
        Log.v(TAG, "attach");
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

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "TEST");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void update(LeadItems leaditem) {
        leadItemsList.add(leaditem);

    }


}


