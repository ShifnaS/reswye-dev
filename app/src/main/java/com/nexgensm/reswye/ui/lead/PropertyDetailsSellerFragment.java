package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Utlity;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertyDetailsSellerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertyDetailsSellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyDetailsSellerFragment extends Fragment {

    ViewPager viewPager, viewloggedPager;
    PagerAdapter adapter, logged_adapter;

    String[] amount, amount2;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    int LeadId;
    String Token, url, imageUrl, beds, feature="", baths, squareFootage_Min, squareFootage_Max, discription, emaiL, additionalChara="", shortsale, listingprice, listingtype, image;
    String viewedDatetime, emailbuyer, buyername, mobilebuyer, commentbuyer;
    RequestQueue requestQueue;
    TextView address, pointofinterest, loggedbuyer, loggeddatetime, features, characteristics, status, salesstatus, propertyamount;
    TextView bed_count, bath_count, minSqft, maxSqft, propertydescription, propertyId, lead, phone, email, yearbuilt, availableondate, foreclosuredate, propertytype;
    String Status_missed, mobileNo, lastName,firstName, address_location, yearBuiltStart, available_on_Date, foreclosure_Date, propertyType, pointOfInterest, propertyPhotoName;
    private List<PropertySellerItems> propertySellerItemList = new ArrayList<>();
    private RecyclerView recyclerView, recyclerViewPropertyviewed;
    List<LeadListingRecyclerDataAdapter> GetDataAdapter1, GetDataAdapter2;
    RecyclerViewAdapter recyclerViewadapter, recyclerViewAdapter2;
    //private PropertDetailsSellerRecyclerViewAdapter propertDetailsSellerRecyclerViewAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PropertyDetailsSellerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertyDetailsSellerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertyDetailsSellerFragment newInstance(String param1, String param2) {
        PropertyDetailsSellerFragment fragment = new PropertyDetailsSellerFragment();
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

        GetDataAdapter1 = new ArrayList<>();
        GetDataAdapter2 = new ArrayList<>();

        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_property_details_seller, container, false);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclepropertyviewphoto);
        recyclerViewPropertyviewed = (RecyclerView) myFragmentView.findViewById(R.id.pagerViewedLogged);

        recyclerViewPropertyviewed.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPropertyviewed.setLayoutManager(mLayoutManager1);
        recyclerViewPropertyviewed.setItemAnimator(new DefaultItemAnimator());

        amount2 = new String[]{"$123.456", "$346,542", "$462,768", "$637,537"};
        logged_adapter = new PropertyViewloggedAdapter_seller(getActivity(), amount2);

        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        LeadId = SharedPrefsUtils.getInstance(getActivity()).getLId();

        Token = sharedpreferences.getString("token", "");

        Log.v("LEADID", "" + LeadId);
        address = (TextView) myFragmentView.findViewById(R.id.address);
        bed_count = (TextView) myFragmentView.findViewById(R.id.bed_count);
        bath_count = (TextView) myFragmentView.findViewById(R.id.bath_count);
        minSqft = (TextView) myFragmentView.findViewById(R.id.minSqft);
        maxSqft = (TextView) myFragmentView.findViewById(R.id.maxSqft);

        propertyId = (TextView) myFragmentView.findViewById(R.id.propertyId);
        lead = (TextView) myFragmentView.findViewById(R.id.lead);
        phone = (TextView) myFragmentView.findViewById(R.id.phone);
        email = (TextView) myFragmentView.findViewById(R.id.email);
        yearbuilt = (TextView) myFragmentView.findViewById(R.id.yearbuilt);
        availableondate = (TextView) myFragmentView.findViewById(R.id.availableondate);
        foreclosuredate = (TextView) myFragmentView.findViewById(R.id.foreclosuredate);
        propertytype = (TextView) myFragmentView.findViewById(R.id.propertytype);
        propertydescription = (TextView) myFragmentView.findViewById(R.id.propertydescription);
        pointofinterest = (TextView) myFragmentView.findViewById(R.id.pointofinterest);
        features = (TextView) myFragmentView.findViewById(R.id.features);
        characteristics = (TextView) myFragmentView.findViewById(R.id.characteristics);

        loggedbuyer = (TextView) myFragmentView.findViewById(R.id.loggedbuyer);
        loggeddatetime = (TextView) myFragmentView.findViewById(R.id.loggeddatetime);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());


        url = "http://192.168.0.3:3000/reswy/leadlistproperty/"+LeadId;
        Log.e("11111",""+url);
       /* Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Lead_ID", LeadId);*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("REsponseJson", "" + response.toString());

                        try {
                            Status_missed = response.getString("status").toString().trim();
                            Toast.makeText(getActivity(), "Status_missed "+Status_missed, Toast.LENGTH_SHORT).show();

                            if (Status_missed.equals("success")) {
                                JSONArray jsonArray = response.getJSONArray("result");
                                JSONObject propertyDetails = jsonArray.getJSONObject(0);
                                mobileNo = propertyDetails.getString("mobileno");
                                emaiL = propertyDetails.getString("emailid");
                                discription = propertyDetails.getString("property_description");
                                firstName = propertyDetails.getString("firstname");
                                lastName = propertyDetails.getString("lastname");

                                beds = propertyDetails.getString("beds");
                                baths = propertyDetails.getString("baths");
                                address_location = propertyDetails.getString("address_location");
                                yearBuiltStart = propertyDetails.getString("yearbuilt_start");
                                available_on_Date = propertyDetails.getString("available_on_date");
                                foreclosure_Date = propertyDetails.getString("foreclosure_date");
                                propertyType = propertyDetails.getString("propertytype");
                                pointOfInterest = propertyDetails.getString("pointofinterest");
                                squareFootage_Min = propertyDetails.getString("squarefootage_min");
                                squareFootage_Max = propertyDetails.getString("squarefootage_max");
                                shortsale = propertyDetails.getString("short_sale");
                                // Log.v("shortsale", "" + shortsale);
                                listingprice = propertyDetails.getString("listing_price");
                                listingtype = propertyDetails.getString("listingtype");
                                Toast.makeText(getActivity(), "sqrft "+squareFootage_Max, Toast.LENGTH_SHORT).show();

                                address.setText(address_location);
                                bed_count.setText(beds);
                                bath_count.setText(baths);
                                //minSqft.setText(squareFootage_Min);
                                maxSqft.setText(squareFootage_Max);

                                lead.setText(firstName+" "+lastName);
                                phone.setText(mobileNo);
                                email.setText(emaiL);
                                yearbuilt.setText(yearBuiltStart);
                                availableondate.setText(available_on_Date);
                                foreclosuredate.setText(foreclosure_Date);
                                propertytype.setText(propertyType);
                                pointofinterest.setText(pointOfInterest);
                                propertydescription.setText(discription);

                                viewedDatetime = "12-04-2018";
                                buyername = "Shifna S";
                                emailbuyer = "shifnashahida60@gmail.com";
                                mobilebuyer = "5676564567";
                                commentbuyer ="nice rooms and hall";


                                JSONArray jsonArrayChara = response.getJSONArray("chara");
                                Log.e("0000000000000","111111111111 "+jsonArrayChara.toString());
                                //Toast.makeText(getContext(), "chara "+jsonArrayChara.length(), Toast.LENGTH_SHORT).show();
                                for(int j=0;j<jsonArrayChara.length();j++)
                                {
                                  //  Toast.makeText(getActivity(), "hii "+j, Toast.LENGTH_SHORT).show();
                                    Log.e("0000000000000","111111111111 "+jsonArrayChara.getJSONObject(j).toString());
                                    JSONObject featuresObject = jsonArrayChara.getJSONObject(j);
                                    //   loggeddatetime.setText(viewedDatetime);
                                    if(featuresObject.getInt("fid_cid")==1)
                                    {
                                        if(feature.equalsIgnoreCase(""))
                                        {
                                            feature=featuresObject.getString("features_characteristics");
                                        }
                                        else
                                        {
                                            feature=feature+","+featuresObject.getString("features_characteristics");

                                        }
                                    }
                                    else
                                    {

                                        if(additionalChara.equals(""))
                                        {
                                            additionalChara=featuresObject.getString("features_characteristics");

                                        }
                                        else
                                        {
                                            additionalChara=additionalChara+","+featuresObject.getString("features_characteristics");

                                        }
                                    }

                                }
                                characteristics.setText(additionalChara);
                                features.setText(feature);

                                JSONArray jsonArrayBuyer = response.getJSONArray("viewdetails");
                                Log.e("0000000000000","111111111111 "+jsonArrayBuyer.toString());
                                for(int i=0;i<jsonArrayBuyer.length();i++)
                                {
                                    JSONObject featuresObject = jsonArrayBuyer.getJSONObject(i);
                                    LeadListingRecyclerDataAdapter GetDataAdapter3 = new LeadListingRecyclerDataAdapter();
                                    GetDataAdapter3.setBuyername(featuresObject.getString("BuyerName"));
                                    GetDataAdapter3.setViewedDate(featuresObject.getString("ViewedDatetime"));
                                    GetDataAdapter3.setBuyeremail(featuresObject.getString("ViewedDatetime"));
                                    GetDataAdapter3.setBuyermobile(featuresObject.getString("Mobile"));
                                    GetDataAdapter3.setBuyerComments(featuresObject.getString("Comments"));
                                    GetDataAdapter2.add(GetDataAdapter3);

                                }

                                JSONArray jsonArray3 = response.getJSONArray("photos");
                                Log.e("0000000000000","111111111111 "+jsonArrayBuyer.toString());
                                for (int i = 0; i < jsonArray3.length(); i++) {
                                    JSONObject abc = jsonArray3.getJSONObject(i);
                                   LeadListingRecyclerDataAdapter GetDataAdapter2 = new LeadListingRecyclerDataAdapter();
                                   // GetDataAdapter2.setLead_name(shortsale);
                                   // GetDataAdapter2.setLead_address(listingprice);
                                   // GetDataAdapter2.setLead_time(listingtype);
                                    propertyPhotoName = abc.getString("Uploaded_DocumentName");

                                    image = ApiClient.BASE_URL_IMG + propertyPhotoName;
                                    Log.v("imageURLTEST", "" + image);
                                    GetDataAdapter2.setLead_imageUrl(image);
                                    GetDataAdapter1.add(GetDataAdapter2);

                                }


                            } else {

                                mobileNo = "Not available";
                                emaiL = "Not available";
                                discription = "Not available";
                                firstName = "Not available";
                                beds = "N/A";
                                baths = "N/A";
                                address_location = "Not available";
                                yearBuiltStart = "Not available";
                                available_on_Date = "Not available";
                                foreclosure_Date = "Not available";
                                propertyType = "Not available";
                                pointOfInterest = "Not available";
                                squareFootage_Min = "Not available";
                                squareFootage_Max = "Not available";
                                shortsale = "Not available";
                                // Log.v("shortsale", "" + shortsale);
                                listingprice = "Not available";
                                listingtype = "Not available";

                                additionalChara="Not available";
                                feature="Not available";


                                viewedDatetime = "Not available";
                                buyername = "Not available";
                                emailbuyer = "Not available";
                                mobilebuyer = "Not available";
                                commentbuyer ="Not available";

                            }


                            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getContext(), 1);
                            recyclerView.setAdapter(recyclerViewadapter);



                            recyclerViewAdapter2 = new RecyclerViewAdapter(GetDataAdapter2, getContext(), 2);
                            recyclerViewPropertyviewed.setAdapter(recyclerViewAdapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
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

        requestQueue.add(jsonObjectRequest);

        return myFragmentView;

    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
