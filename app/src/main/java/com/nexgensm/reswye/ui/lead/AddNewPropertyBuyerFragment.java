package com.nexgensm.reswye.ui.lead;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.ui.onboarding.OnBoardingActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.ui.signupactivity.SignupActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewPropertyBuyerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewPropertyBuyerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewPropertyBuyerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     int flag;
     RelativeLayout relative_lyt_logged;
    EditText editDate,Dateviewed,propertyId,LeadComments;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "yyyy/MM/dd";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    private OnFragmentInteractionListener mListener;
    String[] propertytype;
    ImageView yearImgNeg,yearImgPos,yearImgNegMin,yearImgPosMax;
    public static final String mypreference = "mypref";
    Button bt_save;
    ProgressDialog loading;
    String listingtype_value="";
    PlacesAutocompleteTextView preffered_location;
    SwitchCompat listingtype;
    int Lead_id;
    TextView bathCount,bedCount;
    Spinner spinnerpropertytype;
    RequestQueue requestQueue;
    EditText Min_price,Max_price,Min_sqrft,Max_sqrft;
    TextView  YearcountMin,YearcountMax;
    ArrayAdapter<String> adapter;
    TextView tv_buyer;

    public AddNewPropertyBuyerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewPropertyBuyerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewPropertyBuyerFragment newInstance(String param1, String param2) {
        AddNewPropertyBuyerFragment fragment = new AddNewPropertyBuyerFragment();
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

        final View myFragmentView=inflater.inflate(R.layout.fragment_add_new_property_buyer, container, false);
        spinnerpropertytype = (Spinner) myFragmentView.findViewById(R.id.spinnerpropertytype);
        loading = new ProgressDialog(getActivity());

        spinnerpropertytype.setSelection(0,true);
        propertytype = getResources().getStringArray(R.array.Property_Type);
        preffered_location=(PlacesAutocompleteTextView) myFragmentView.findViewById(R.id.Preffered_location);
        bt_save=myFragmentView.findViewById(R.id.save_and_proceed);
        adapter= new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpropertytype.setAdapter(adapter);
        TextView featurestxt = (TextView) myFragmentView.findViewById(R.id.featurestxt);
        TextView additionalpropchartxt = (TextView) myFragmentView.findViewById(R.id.additionalpropertychartxt);
        listingtype=(SwitchCompat)myFragmentView.findViewById(R.id.listing_type);


        bedCount = (TextView)myFragmentView.findViewById(R.id.bed_number);
        ImageView bedImgNeg = (ImageView)myFragmentView.findViewById(R.id.bed_negative);
        ImageView bedImgPos = (ImageView)myFragmentView.findViewById(R.id.bed_positive);

        bathCount = (TextView)myFragmentView.findViewById(R.id.bath_number);
        ImageView bathImgNeg = (ImageView)myFragmentView.findViewById(R.id.bath_negative);
        ImageView bathImgPos = (ImageView)myFragmentView.findViewById(R.id.bath_positive);

        YearcountMin = (TextView)myFragmentView.findViewById(R.id.year_txt_min);
        YearcountMax = (TextView)myFragmentView.findViewById(R.id.year_txt_max);
         yearImgNeg = (ImageView)myFragmentView.findViewById(R.id.year_negative_min);
         yearImgPos = (ImageView)myFragmentView.findViewById(R.id.year_positive_min);
         yearImgNegMin = (ImageView)myFragmentView.findViewById(R.id.year_negative_max);
         yearImgPosMax = (ImageView)myFragmentView.findViewById(R.id.year_positive_max);

         tv_buyer=(TextView)myFragmentView.findViewById(R.id.buyer);
        tv_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ViewPropertyComentsActivity.class);
                startActivity(i);

            }
        });
        flag=SharedPrefsUtils.getInstance(getActivity()).getFlag();
        String str = Integer.toString(flag);
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

        if((flag==0))
        {
            Lead_id=SharedPrefsUtils.getInstance(getActivity()).getLeadId();
        }
        else
        {
            Lead_id=SharedPrefsUtils.getInstance(getActivity()).getLId();
        }


        if(flag==1) {
            relative_lyt_logged=(RelativeLayout)myFragmentView.findViewById(R.id.relative_lyt_logged);
            relative_lyt_logged.setVisibility(View.VISIBLE);
        }
        else{
            relative_lyt_logged=(RelativeLayout)myFragmentView.findViewById(R.id.relative_lyt_logged);
            relative_lyt_logged.setVisibility(View.GONE);
        }

      ///  preffered_location.set

        // showData();
        preffered_location.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                preffered_location.getDetailsFor(place, new DetailsCallback() {
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







        bedImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bedCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int=present_value_int+1;
                bedCount.setText(String.valueOf(present_value_int));
            }
        });
        bedImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bedCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if(present_value_int>0){

                    present_value_int=present_value_int-1;
                    bedCount.setText(String.valueOf(present_value_int));
                }
                else{

                }

            }
        });

        bathImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bathCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int=present_value_int+1;
                bathCount.setText(String.valueOf(present_value_int));
            }
        });
        bathImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bathCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if(present_value_int>0){
                    present_value_int=present_value_int-1;
                    bathCount.setText(String.valueOf(present_value_int));
                }
                else{

                }

            }
        });

        yearImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMin.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int=present_value_int+1;
                YearcountMin.setText(String.valueOf(present_value_int));
            }
        });
        yearImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMin.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if(present_value_int>0){
                    present_value_int=present_value_int-1;
                    YearcountMin.setText(String.valueOf(present_value_int));
                }
                else{

                }

            }
        });

        yearImgPosMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMax.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int=present_value_int+1;
                YearcountMax.setText(String.valueOf(present_value_int));
            }
        });
        yearImgNegMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMax.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if(present_value_int>0){
                    present_value_int=present_value_int-1;
                    YearcountMax.setText(String.valueOf(present_value_int));
                }
                else{

                }

            }
        });
        editDate = (EditText) myFragmentView.findViewById(R.id.datepic);


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


        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(myFragmentView.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        preffered_location.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });







        final EditText datepic=(EditText) myFragmentView.findViewById(R.id.datepic);
        datepic.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });
         Min_price=(EditText) myFragmentView.findViewById(R.id.min_price);
        Min_price.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });
         Max_price=(EditText) myFragmentView.findViewById(R.id.max_price);
        Max_price.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });
      Min_sqrft=(EditText) myFragmentView.findViewById(R.id.min_sqrft);
        Min_sqrft.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });
      Max_sqrft=(EditText) myFragmentView.findViewById(R.id.max_sqrft);
        Max_sqrft.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.editbox_style);

                }
                else{
                    view.setBackgroundResource( R.drawable.spinner_focus);

                }
            }
        });

        featurestxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addfeature = new Intent(getActivity(), AddFeatureActivity.class);
                addfeature.putExtra("data","feature");
                getActivity().startActivity(addfeature);

            }
        });

        additionalpropchartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addfeature = new Intent(getActivity(), AddFeatureActivity.class);
                addfeature.putExtra("data","chara");
                getActivity().startActivity(addfeature);

            }
        });

        if(flag==1)
        {

            viewData();
        }
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });




return myFragmentView;

    }

    private void viewData() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

       String  url = ApiClient.BASE_URL+"leadlistproperty/"+Lead_id;


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // ListingTypeSellRent="Rent";

                            String Status = response.getString("status").toString().trim();


                            if (Status.equals("success")) {
                                JSONArray jsonArray = response.getJSONArray("result");
                                JSONObject propertyDetails = jsonArray.getJSONObject(0);

                                String min_price = propertyDetails.getString("minimum_price");
                                String max_price =propertyDetails.getString("maximum_price");
                                String YearcountMinxt =propertyDetails.getString("yearbuilt_start");
                                String YearcountMaxt = propertyDetails.getString("yearbuilt_end");
                                String address_locationTxt = propertyDetails.getString("address_location");
                                String ListingTypeSellRent = propertyDetails.getString("listingtype").toString();
                                String listing_PriceTxt = propertyDetails.getString("listing_price");
                                int bedsTxt = propertyDetails.getInt("beds");
                                int bathsTxt = propertyDetails.getInt("baths");
                                String propertyTypeTxt=propertyDetails.getString("propertytype");
                                String available_date=propertyDetails.getString("available_on_date");
                                String square_feet_max=propertyDetails.getString("squarefootage_max");
                                String square_feet_min=propertyDetails.getString("squarefootage_min");

                                String bedString = String.valueOf(bedsTxt);
                                String bathString = String.valueOf(bathsTxt);





                                Min_price.setText(min_price);
                                Max_price.setText(max_price);
                                YearcountMin.setText(YearcountMinxt);
                                YearcountMax.setText(YearcountMaxt);
                                preffered_location.setText(address_locationTxt);
                                bedCount.setText(bedString);
                                editDate.setText(available_date);
                                bathCount.setText(bathString);
                                Max_sqrft.setText(square_feet_max);
                                Min_sqrft.setText(square_feet_min);

                                ///////////////////////////////////
                                int position= adapter.getPosition(propertyTypeTxt);
                                if(position!=0){
                                    spinnerpropertytype.setSelection(position);
                                }

                                if(ListingTypeSellRent.compareTo("Sell")==0){
                                    listingtype.setChecked(false); }
                                else {
                                    listingtype.setChecked(true);
                                }

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
        requestQueue.add(jsonRequest);
    }


    private void saveData() {

        if (listingtype.isChecked() == false) {
            listingtype_value = "Sell";
        } else {
            listingtype_value = "Buy";
        }
        loading.setMessage("loading");
        loading.show();

        if((flag==0))
        {
            Lead_id=SharedPrefsUtils.getInstance(getActivity()).getLeadId();
        }
        else
        {
            Lead_id=SharedPrefsUtils.getInstance(getActivity()).getLId();
        }
        final String addressTxt = preffered_location.getText().toString();
        final String min_price = Min_price.getText().toString();
        final String max_price = Max_price.getText().toString();
        final String listingtype_valueTxt = listingtype_value.toString();
        final String bedCountTxt = bedCount.getText().toString();
        final String bathCountTxt = bathCount.getText().toString();
        final String YearcountMinxt = YearcountMin.getText().toString();
        final String YearcountMaxt = YearcountMax.getText().toString();
        final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
        final String available_on_date= editDate.getText().toString().trim();
        final String max_square_feet=Max_sqrft.getText().toString().trim();
        final String min_square_feet=Min_sqrft.getText().toString().trim();


      if ((max_price.equals("")) ||(max_price.equals("")) ||(YearcountMaxt.equals("")) ||(available_on_date.equals("")) ||(addressTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) || (YearcountMinxt.equals("")) || (max_square_feet.equals(""))|| (min_square_feet.equals(""))) {

            Toast.makeText(getActivity(), "Please fill mandatory fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int lead_id= SharedPrefsUtils.getInstance(getActivity()).getLeadId();
            Toast.makeText(getActivity(), "led_id "+lead_id, Toast.LENGTH_SHORT).show();

            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = ApiClient.BASE_URL+ "addlead_step2buyer";
            Map<String, Object> jsonParams = new ArrayMap<>();

            jsonParams.put("lead_id", lead_id);
            jsonParams.put("flag", flag);
            jsonParams.put("beds", bedCountTxt);
            jsonParams.put("baths", bathCountTxt);
            jsonParams.put("yearbuilt_start", YearcountMinxt);
            jsonParams.put("yearbuilt_end", YearcountMaxt);
            jsonParams.put("propertytype", spinnerpropertytypeTxt);
            jsonParams.put("listingtype", listingtype_valueTxt);
            jsonParams.put("address", addressTxt);
            jsonParams.put("minimum_price", min_price);
            jsonParams.put("maximum_price", max_price);
            jsonParams.put("available_date", available_on_date);
            jsonParams.put("squarefootage_max", max_square_feet);
            jsonParams.put("squarefootage_min", min_square_feet);




            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            loading.dismiss();

                            try {
                                String responseResult = response.getString("status").trim();
                                Toast.makeText(getActivity(), responseResult, Toast.LENGTH_SHORT).show();
                                JSONObject result=response.getJSONObject("result");
                                 if(responseResult.equals("success"))
                                 {
                                   if(flag==0)
                                   {
                                       Min_price.setText("");
                                       preffered_location.setText("");
                                       Min_price.setText("");
                                       listingtype.setChecked(false);
                                       bedCount.setText("3");
                                       bathCount.setText("3");
                                       YearcountMin.setText("2018");
                                       YearcountMax.setText("2018");
                                       spinnerpropertytype.setAdapter(adapter);
                                       editDate.setText("");
                                       Max_sqrft.setText("");
                                       Min_sqrft.setText("");


                                   }
                               }

                            } catch (JSONException e) {
                                loading.dismiss();
                                //   Toast.makeText(getActivity(), e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
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



    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
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
