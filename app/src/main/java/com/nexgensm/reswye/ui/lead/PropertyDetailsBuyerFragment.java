package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertyDetailsBuyerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertyDetailsBuyerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyDetailsBuyerFragment extends Fragment {
    ViewPager viewPagerBuyer;
    PagerAdapter adapterbuyer;
    TextView tv_address,tv_propertyType,tv_date,tv_minPrice,tv_maxPrice,tv_minSquare;
    TextView tv_maxSquare,tv_bed,tv_bath,tv_year,tv_feature,tv_char,tv_preferType;
    RequestQueue requestQueue;
    int LeadId;
    String feature="",additionalChara="";

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    public PropertyDetailsBuyerFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static PropertyDetailsBuyerFragment newInstance(String param1, String param2) {
        PropertyDetailsBuyerFragment fragment = new PropertyDetailsBuyerFragment();
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
        // Inflate the layout for this fragment
        final View myFragmentView=inflater.inflate(R.layout.fragment_property_details_buyer, container, false);
        viewPagerBuyer = (ViewPager) myFragmentView.findViewById(R.id.pagerbuyer);
        LeadId = SharedPrefsUtils.getInstance(getActivity()).getLId();
        Log.v("LEADID", "" + LeadId);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // finding ids

        tv_address=myFragmentView.findViewById(R.id.address);
        tv_date=myFragmentView.findViewById(R.id.DateTxt);
        tv_propertyType=myFragmentView.findViewById(R.id.PropertyTypeTxtValue);
        tv_minPrice=myFragmentView.findViewById(R.id.MinimumPriceValue);
        tv_maxPrice=myFragmentView.findViewById(R.id.maximumPriceValue);
        tv_minSquare=myFragmentView.findViewById(R.id.MinimumAreaValue);
        tv_maxSquare=myFragmentView.findViewById(R.id.MaximumAreaValue);
        tv_bed=myFragmentView.findViewById(R.id.BedsValue);
        tv_bath=myFragmentView.findViewById(R.id.BathsValue);
        tv_year=myFragmentView.findViewById(R.id.YearBuitValue);
        tv_feature=myFragmentView.findViewById(R.id.FeaturesValue);
        tv_char=myFragmentView.findViewById(R.id.CharecteristicsValue);
        tv_preferType=myFragmentView.findViewById(R.id.Buy);

        // finding ids

        viewData();


        return myFragmentView;
    }

    private void viewData() {
        String url = "http://192.168.0.3:3000/reswy/leadlistproperty/"+LeadId;
        Log.e("11111",""+url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("REsponseJson", "" + response.toString());

                        try {
                            String Status_missed = response.getString("status").toString().trim();
                            Toast.makeText(getActivity(), "Status_missed "+Status_missed, Toast.LENGTH_SHORT).show();

                            if (Status_missed.equals("success")) {

                                JSONArray jsonArray = response.getJSONArray("result");
                                JSONObject propertyDetails = jsonArray.getJSONObject(0);
                                String beds = propertyDetails.getString("beds");
                                String baths = propertyDetails.getString("baths");
                                String address_location = propertyDetails.getString("address_location");
                                String yearBuiltStart = propertyDetails.getString("yearbuilt_start");
                                String available_on_Date = propertyDetails.getString("available_on_date");
                                String propertyType = propertyDetails.getString("propertytype");
                                String squareFootage_Min = propertyDetails.getString("squarefootage_min");
                                String squareFootage_Max = propertyDetails.getString("squarefootage_max");
                                String minimum_price = propertyDetails.getString("minimum_price");
                                String maximum_price = propertyDetails.getString("maximum_price");
                                String listingtype = propertyDetails.getString("listingtype");

                                tv_address.setText(address_location);
                                tv_bed.setText(beds);
                                tv_bath.setText(baths);
                                tv_propertyType.setText(propertyType);
                                tv_minSquare.setText(squareFootage_Min);
                                tv_maxSquare.setText(squareFootage_Max);
                                tv_maxPrice.setText(minimum_price);
                                tv_minPrice.setText(maximum_price);
                                tv_year.setText(yearBuiltStart);
                                tv_date.setText(available_on_Date);
                                tv_preferType.setText(listingtype);


                                JSONArray jsonArrayChara = response.getJSONArray("chara");
                                Log.e("0000000000000","111111111111 "+jsonArrayChara.toString());
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
                                tv_char.setText(additionalChara);
                                tv_feature.setText(feature);

                                JSONArray jsonArrayBuyer = response.getJSONArray("viewdetails");
                                Log.e("0000000000000","111111111111 "+jsonArrayBuyer.toString());
                                adapterbuyer = new PropertyViewloggedAdapter_buyer(getActivity(),jsonArrayBuyer);
                                viewPagerBuyer.setAdapter(adapterbuyer);


                            }

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
                headers.put("Authorization", "");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);





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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
