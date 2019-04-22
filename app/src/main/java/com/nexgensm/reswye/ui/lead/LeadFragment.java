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
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.nexgensm.reswye.util.Message;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
public class LeadFragment extends Fragment implements RecyclerViewAdapter.DataAdapterListener {
    RequestQueue requestQueue1, requestQueue2, requestQueue;
    private String TAG = "LeadFragment";
    //    String result,image;
    String Token, Status_missed, name1, address, lead_CreatedDate, oldpasstxt, confrmpasstxt, newpasstxt;
    String url, ImageUrl, profileimage, image, leadurl, lead_categoryBS;
    int userId, lead_ID, sortsave=0, filterCheck;
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
    int sorttype=0;
    LeadItems leadItems;
    String[] arr;
    private OnFragmentInteractionListener mListener;
    SearchView   tv_search;
    ArrayAdapter<String> adapter ;
    ArrayList<String> arrayList=new ArrayList<String>();

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
        Singleton.getInstance().setFilterFlag(0);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.toolbarcolor));

        View myFragmentView = inflater.inflate(R.layout.fragment_lead, container, false);
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        SharedPrefsUtils.getInstance(getActivity()).setData("filter","nofilter");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tv_search=myFragmentView.findViewById(R.id.lead_search);



       // JSON_DATA_WEB_CALL();
        recyclerView.addOnItemTouchListener(

                new LeadRecyclerItemClickListener(getActivity().getApplicationContext(), new LeadRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String lead_type = GetDataAdapter1.get(position).getLead_type();
                        Integer id = GetDataAdapter1.get(position).getLead_ID();
                        String name = GetDataAdapter1.get(position).getLead_name();

                        SharedPrefsUtils.getInstance(getActivity()).setLid(id);
                        SharedPrefsUtils.getInstance(getActivity()).setFlag(1);

                        Log.v("LeadFragment ID", "" + id);
                        int a = 0;
                        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                        recyclerView.swapAdapter(recyclerViewadapter, true);
                        recyclerViewadapter.notifyDataSetChanged();
                        if(lead_type.equals("Seller"))
                        {
                            Intent addnewsellerdetailsactivity = new Intent(getActivity().getApplicationContext(), SellerDetailsActivity.class);
                            addnewsellerdetailsactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            addnewsellerdetailsactivity.putExtra("lead_name", name);
                            getActivity().startActivity(addnewsellerdetailsactivity);
                        }
                        else
                        {
                            Intent addnewsellerdetailsactivity = new Intent(getActivity().getApplicationContext(), BuyerDetailsActivity.class);
                            addnewsellerdetailsactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            addnewsellerdetailsactivity.putExtra("lead_name", name);
                            getActivity().startActivity(addnewsellerdetailsactivity);
                        }

                        recyclerView.getRecycledViewPool().clear();

                        //   transact();

                    }


                })
        );

        Button AddNewBtn = (Button) myFragmentView.findViewById(R.id.Add_New);
        AddNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addNewLeadCategory = new Intent(getActivity(), AddNewLeadCategoryActivity.class);
                addNewLeadCategory.putExtra("myobject", leadItems);
                addNewLeadCategory.putExtra("lid", 0);
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


        return myFragmentView;
    }

    public void JSON_DATA_WEB_CALL() {
        Log.v(TAG, "JSON CALL");

            requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
            Log.v(TAG, "NO Filter");
            if (sortsave == 0) {
                Log.v(TAG, "SORT zero");

                @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
               url = ApiClient.BASE_URL+"listleads/"+userId;

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
                                recyclerViewadapter.notifyDataSetChanged();



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
                recyclerView.swapAdapter(recyclerViewadapter, true);
                recyclerView.removeAllViewsInLayout();
            }
            else {

                String url = ApiClient.BASE_URL+ "sortdetails";
                Map<String, Object> jsonParams = new ArrayMap<>();

                jsonParams.put("user_id", userId);
                jsonParams.put("sts", sortsave);


                @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                loading.dismiss();
                                JSON_PARSE_DATA_AFTER_WEBCALL(response);

                                int a = 0;
                                recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                                recyclerView.swapAdapter(recyclerViewadapter, true);
                                recyclerView.removeAllViewsInLayout();
                                recyclerViewadapter.notifyDataSetChanged();
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
                recyclerView.getRecycledViewPool().clear();
                requestQueue1.add(jsonObjectRequest);
                int a = 0;
                recyclerView.swapAdapter(recyclerViewadapter, true);
                recyclerView.removeAllViewsInLayout();
            }




    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject jsonObject) {


        Log.v(TAG, "json parse");
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Token = sharedpreferences.getString("token", "");
        ImageUrl = sharedpreferences.getString("imageURL", "");
        JSONArray jarray = jsonObject.optJSONArray("result");
        Log.v(TAG, "json arayy length "+jarray.length());
       arr = new String[jarray.length()];
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
                String leadType=abc.getString("lead_category");
                Log.e("name ",name1 );
                Log.e("address ",address);
                Log.e("lead_ID ",""+lead_ID);
                Log.e("lead_CreatedDate ",lead_CreatedDate);
                arr[i]=name1;
                arrayList.add(name1);

             //   image = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.biowritingservice.com%2Fwp-content%2Fthemes%2Ftuborg%2Fimages%2FExecutive%2520Bio%2520Sample%2520Photo.png&imgrefurl=https%3A%2F%2Fwww.biowritingservice.com%2Four-bio-writing-samples%2F&docid=UmOWXvavlYWmFM&tbnid=zJshq4jeDXBpnM%3A&vet=10ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ..i&w=629&h=764&bih=657&biw=1366&q=sample%20images%20person&ved=0ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ&iact=mrc&uact=8#h=764&imgdii=RhQaTMt0JMuy5M:&vet=10ahUKEwi99LGSjtbgAhWBfCsKHUeiDTIQMwhAKAEwAQ..i&w=629";
                GetDataAdapter2.setLead_type(leadType);
                GetDataAdapter2.setLead_name(name1);
                GetDataAdapter2.setLead_address(address);
                GetDataAdapter2.setLead_ID(lead_ID);
                GetDataAdapter2.setLead_time(lead_CreatedDate);
                GetDataAdapter2.setLead_imageUrl(ApiClient.BASE_URL_IMG+profileimage);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);

        }

      //  adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arr);


        int a = 0;

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
        Log.v(TAG, "RECYCLER");
        recyclerView.swapAdapter(recyclerViewadapter, true);
        recyclerView.removeAllViewsInLayout();
        recyclerViewadapter.notifyDataSetChanged();
       /// SearchView searchView = (SearchView) MenuItemCompat.getActionView(tv_search);




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


       // Toast.makeText(getActivity(), "Filter Flag "+SharedPrefsUtils.getInstance(getActivity()).getData("filter"), Toast.LENGTH_SHORT).show();
        Log.v(TAG, "TEST");
        JSON_DATA_WEB_CALL();

        tv_search.setMaxWidth(Integer.MAX_VALUE);
        tv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {

              //  recyclerViewadapter.getFilter().filter(s);
             //   Toast.makeText(getActivity(), "String 1 "+s, Toast.LENGTH_SHORT).show();
                GetDataAdapter1.clear();
                // recyclerViewadapter.notifyDataSetChanged();
                recyclerView.getRecycledViewPool().clear();
                recyclerView.swapAdapter(recyclerViewadapter, true);
                recyclerView.removeAllViewsInLayout();
                JSON_DATA_WEB_CALL1(name);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
              //  Toast.makeText(getActivity(), "String 2 "+s, Toast.LENGTH_SHORT).show();

                // recyclerViewadapter.getFilter().filter(s);
                if(s.equals(""))
                {
                    GetDataAdapter1.clear();
                    // recyclerViewadapter.notifyDataSetChanged();
                    recyclerView.getRecycledViewPool().clear();
                    recyclerView.swapAdapter(recyclerViewadapter, true);
                    recyclerView.removeAllViewsInLayout();
                    JSON_DATA_WEB_CALL();
                }
                return false;
            }
        });


        String flag=SharedPrefsUtils.getInstance(getActivity()).getData("filter");
        if(flag.equals("filter"))
        {
            GetDataAdapter1.clear();
            // recyclerViewadapter.notifyDataSetChanged();
            recyclerView.getRecycledViewPool().clear();
            recyclerView.swapAdapter(recyclerViewadapter, true);
            recyclerView.removeAllViewsInLayout();
            JSON_DATA_WEB_CALL_Filter();


        }

    }

    @Override
    public void onPause() {

       // Singleton.getInstance().setFilterFlag(1);
        SharedPrefsUtils.getInstance(getActivity()).setData("filter","nofilter");

        GetDataAdapter1.clear();
       // recyclerViewadapter.notifyDataSetChanged();
        recyclerView.getRecycledViewPool().clear();
        recyclerView.swapAdapter(recyclerViewadapter, true);
        recyclerView.removeAllViewsInLayout();
        super.onPause();
    }

    @Override
    public void onContactSelected(LeadListingRecyclerDataAdapter contact) {
        Toast.makeText(getActivity(), "Selected: " + contact.getLead_name(), Toast.LENGTH_LONG).show();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void update(LeadItems leaditem) {
        leadItemsList.add(leaditem);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(Message event){
        //Toast.makeText(getActivity(), ""+event.getFlag(), Toast.LENGTH_SHORT).show();
        sortsave=event.getFlag();
    //    EventBus.getDefault().removeStickyEvent(sti); // don't forget to remove the sticky event if youre done with it

    }




    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    public void JSON_DATA_WEB_CALL1(String name) {
        Log.v(TAG, "JSON CALL");
        url = ApiClient.BASE_URL+"searchbyname";

        requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.v(TAG, "NO Filter");
        if (sortsave == 0) {
            Log.v(TAG, "SORT zero");

            @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
            Map<String, Object> jsonParams = new ArrayMap<>();

            jsonParams.put("agent_id", userId);
            jsonParams.put("name", name);
            JsonObjectRequest jsObjRequest1;
            sortsave = Singleton.getInstance().getSortSaveFlag();
            jsObjRequest1 = new JsonObjectRequest
                    (Request.Method.POST, url, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            loading.dismiss();
                            Log.v(TAG, "SOT ZERO DATA PARSE");
                            sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            refresh = sharedpreferences.getInt("refresh", 0);
                            if (refresh == 1) {

                            }
                            Log.e("111111111",""+response);
                            JSON_PARSE_DATA_AFTER_WEBCALL(response);
                            int a = 0;
                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                            recyclerView.swapAdapter(recyclerViewadapter, true);
                            recyclerView.removeAllViewsInLayout();
                            recyclerViewadapter.notifyDataSetChanged();



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
            recyclerView.swapAdapter(recyclerViewadapter, true);
            recyclerView.removeAllViewsInLayout();
        }
        else {
            Toast.makeText(getActivity(), "inside else", Toast.LENGTH_SHORT).show();

            String url = ApiClient.BASE_URL+ "sortdetails";
            Map<String, Object> jsonParams = new ArrayMap<>();

            jsonParams.put("user_id", userId);
            jsonParams.put("sts", sortsave);


            @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            loading.dismiss();
                            JSON_PARSE_DATA_AFTER_WEBCALL(response);

                            int a = 0;
                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                            recyclerView.swapAdapter(recyclerViewadapter, true);
                            recyclerView.removeAllViewsInLayout();
                            recyclerViewadapter.notifyDataSetChanged();
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
            recyclerView.getRecycledViewPool().clear();
            requestQueue1.add(jsonObjectRequest);
            int a = 0;
            recyclerView.swapAdapter(recyclerViewadapter, true);
            recyclerView.removeAllViewsInLayout();
        }




    }


    public void JSON_DATA_WEB_CALL_Filter() {


        String agentName= SharedPrefsUtils.getInstance(getActivity()).getData("agentName");
        String firstName=SharedPrefsUtils.getInstance(getActivity()).getData("firstName");
        String lasttName=SharedPrefsUtils.getInstance(getActivity()).getData("lasttName");
        String propID=SharedPrefsUtils.getInstance(getActivity()).getData("propID");
        String propertyIdTxt=SharedPrefsUtils.getInstance(getActivity()).getData("propertyIdTxt");
        int leadStatus=SharedPrefsUtils.getInstance(getActivity()).getIntData("leadStatus");
        String locationtxt=SharedPrefsUtils.getInstance(getActivity()).getData("locationtxt");
        String dateTxt=SharedPrefsUtils.getInstance(getActivity()).getData("dateTxt");
        String mobileNoTxt=SharedPrefsUtils.getInstance(getActivity()).getData("mobileNoTxt");
        String emailIdTxt=SharedPrefsUtils.getInstance(getActivity()).getData("emailIdTxt");
        String propertyMinimum=SharedPrefsUtils.getInstance(getActivity()).getData("propertyMinimum");
        String propertymaximum=SharedPrefsUtils.getInstance(getActivity()).getData("propertymaximum");
        String prospectStatus=SharedPrefsUtils.getInstance(getActivity()).getData("prospectStatus");
        int transferStatusFlag=SharedPrefsUtils.getInstance(getActivity()).getIntData("transferStatusFlag");
        String leadCategory=SharedPrefsUtils.getInstance(getActivity()).getData("leadCategory");
        Log.v(TAG, "JSON CALL");
        url = ApiClient.BASE_URL+"filterlead";
        requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.v(TAG, "NO Filter");
        Toast.makeText(getActivity(), "Sort "+sortsave, Toast.LENGTH_SHORT).show();

            @SuppressLint({"NewApi", "LocalSuppress"}) final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("agent_id", userId);
            jsonParams.put("sts", leadStatus);
            jsonParams.put("createddate", dateTxt);
            jsonParams.put("location", locationtxt);
            jsonParams.put("minprice", propertyMinimum);
            jsonParams.put("maxprice", propertymaximum);
            jsonParams.put("prospectus", prospectStatus);
            jsonParams.put("category", leadCategory);
            jsonParams.put("transfersts", transferStatusFlag);
            jsonParams.put("transferagent", agentName);
            jsonParams.put("propertyid", propID);
            jsonParams.put("leadfname", firstName);
            jsonParams.put("leadlname", lasttName);
            jsonParams.put("mobile", mobileNoTxt);
            jsonParams.put("email", emailIdTxt);
            JsonObjectRequest jsObjRequest1;
            sortsave = Singleton.getInstance().getSortSaveFlag();
            jsObjRequest1 = new JsonObjectRequest
                    (Request.Method.POST, url, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            loading.dismiss();
                            Log.v(TAG, "SOT ZERO DATA PARSE");
                            sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                            refresh = sharedpreferences.getInt("refresh", 0);
                            if (refresh == 1) {

                            }
                            Log.e("111111111",""+response);

                            JSONArray jarray = jsonObject.optJSONArray("result");
                            if(jarray!=null)
                            {
                                JSON_PARSE_DATA_AFTER_WEBCALL(response);

                            }

                           // mAdapter = new MoviesAdapter(movieList);
                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), 0);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(recyclerViewadapter);
/*
                            int a = 0;
                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity().getApplication(), a);
                            recyclerView.swapAdapter(recyclerViewadapter, true);
                            recyclerView.removeAllViewsInLayout();
                            recyclerViewadapter.notifyDataSetChanged();*/



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
            recyclerView.swapAdapter(recyclerViewadapter, true);
            recyclerView.removeAllViewsInLayout();





    }




}







