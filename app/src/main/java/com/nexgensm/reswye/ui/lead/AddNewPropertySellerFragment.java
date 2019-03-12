

package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.nexgensm.reswye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient;

import static android.app.Activity.RESULT_OK;

import com.google.android.gms.location.places.ui.PlacePicker;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.KeyboardUtils;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewPropertySellerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewPropertySellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewPropertySellerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    PlacesAutocompleteTextView address;

    private OnFragmentInteractionListener mListener;
    private int PLACE_PICKER_REQUEST = 1;

    RelativeLayout relative_lyt_loggedSeller;
    TextView YearcountMin, bedCount, bathCount, featurestxt, additionalpropchartxt;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date,date2,date3;
    String dateFormat = "yyyy/MM/dd", listingtype_value="Sell", ListingTypeSellRent,forclosureTxt,shortsaleBox_value, foreclosureBox_value, Token, responseResult, message;
    ProgressDialog loading;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    String[] propertytype;
    String[] pointofinterest;

    int PresentYear;
    EditText propdescription, et_squareFeet,et_available,  propertid, foreclosure, listingprice;
    int flag, userId, LeadId;
    String buyerName,viewedDatetime, email, mobile, comments, updateStatus, yearString, bedString, bathString;
    String ImageUrl, editStatus,  address_locationTxt, foreclosure_DateTxt, listing_PriceTxt, pointOfInterestTxt,propertyTypeTxt,emaiLTxt, discriptionTxt,shortsaleTxt;
    ImageView yearImgNeg, yearImgPos, bedImgNeg, bedImgPos, bathImgNeg, bathImgPos, loc, AddViewed;
    ImageButton btnSpeak, propphoto;
    Spinner spinnerpropertytype, spinnerpointofinterest;
    Button savebtn_property;
    String url,testdate,testdate2;
    int bedsTxt, yearBuiltStartTxt, bathsTxt;
    GoogleMap googlemap;
    SwitchCompat listingtype;
    CheckBox shortsaleBox, foreclosureBox;
    TextView et_buyer;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    RequestQueue requestQueue, editViewQueue, update, requestQueue3;
    ProgressDialog pd;
    int lid=0;

    public AddNewPropertySellerFragment() {
        // Required empty public constructor
    }



    public static AddNewPropertySellerFragment newInstance(String param1, String param2) {
        AddNewPropertySellerFragment fragment = new AddNewPropertySellerFragment();
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

        View myFragmentView = inflater.inflate(R.layout.fragment_add_new_property_seller, container, false);

        Calendar calendar = Calendar.getInstance();
        PresentYear = calendar.get(Calendar.YEAR);
        pd = new ProgressDialog(getActivity());
        loading = new ProgressDialog(getActivity());
        //Toast.makeText(getActivity(), "year:"+PresentYear, Toast.LENGTH_SHORT).show();
       // btnSpeak = (ImageButton) myFragmentView.findViewById(R.id.btnSpeak);
        propdescription = (EditText) myFragmentView.findViewById(R.id.propertydescription);
        listingtype = (SwitchCompat) myFragmentView.findViewById(R.id.listingtype);

        foreclosureBox = (CheckBox) myFragmentView.findViewById(R.id.foreclosureBox);
        shortsaleBox = (CheckBox) myFragmentView.findViewById(R.id.shortsaleBox);
        spinnerpropertytype = (Spinner) myFragmentView.findViewById(R.id.spinnerpropertytype);
        spinnerpointofinterest = (Spinner) myFragmentView.findViewById(R.id.spinnerpointofinterest);

        bedCount = (TextView) myFragmentView.findViewById(R.id.bed_number);
        bedImgNeg = (ImageView) myFragmentView.findViewById(R.id.bed_negative);
        bedImgPos = (ImageView) myFragmentView.findViewById(R.id.bed_positive);

        bathCount = (TextView) myFragmentView.findViewById(R.id.bath_number);
        bathImgNeg = (ImageView) myFragmentView.findViewById(R.id.bath_negative);
        bathImgPos = (ImageView) myFragmentView.findViewById(R.id.bath_positive);

        YearcountMin = (TextView) myFragmentView.findViewById(R.id.year_txt_min);
        yearImgNeg = (ImageView) myFragmentView.findViewById(R.id.year_negative_min);
        yearImgPos = (ImageView) myFragmentView.findViewById(R.id.year_positive_min);

        et_available = (EditText) myFragmentView.findViewById(R.id.propertyavailable);
        et_squareFeet= (EditText) myFragmentView.findViewById(R.id.propertysqrft);
//
        et_buyer = (TextView) myFragmentView.findViewById(R.id.buyer);
       // BuyerComments = (EditText) myFragmentView.findViewById(R.id.BuyerComments);

      //  phoneviewed = (EditText) myFragmentView.findViewById(R.id.phoneviewed);
       // Mail = (EditText) myFragmentView.findViewById(R.id.Mail);

        featurestxt = (TextView) myFragmentView.findViewById(R.id.featurestxt);
        ImageButton ownerbtn = (ImageButton) myFragmentView.findViewById(R.id.ownerinfo);
        propertid = (EditText) myFragmentView.findViewById(R.id.propertyidedittext);
       // address = (EditText) myFragmentView.findViewById(R.id.addressedittext);
        address=myFragmentView.findViewById(R.id.addressedittext);
        foreclosure = (EditText) myFragmentView.findViewById(R.id.datepic);
        listingprice = (EditText) myFragmentView.findViewById(R.id.listingprice);
        additionalpropchartxt = (TextView) myFragmentView.findViewById(R.id.additionalpropertychartxt);
        propphoto = (ImageButton) myFragmentView.findViewById(R.id.addphoto);
        loc = (ImageView) myFragmentView.findViewById(R.id.addressloc);
        savebtn_property = (Button) myFragmentView.findViewById(R.id.savebtn_property);
        KeyboardUtils.hideKeyboard(getActivity());
        setback();
//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag=sharedpreferences.getInt("flag",0);
//        LeadId=sharedpreferences.getInt("LeadId",0);
        flag=SharedPrefsUtils.getInstance(getActivity()).getFlag();
        Toast.makeText(getActivity(), "flag "+flag, Toast.LENGTH_SHORT).show();
        lid=SharedPrefsUtils.getInstance(getActivity()).getLId();
        if(flag==1)
        {
            et_buyer.setVisibility(View.VISIBLE);
        }
        et_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),AddBuyerActivity.class);
                startActivity(i);

            }
        });
       // showData();
        address.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                address.getDetailsFor(place, new DetailsCallback() {
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










        LeadId=SharedPrefsUtils.getInstance(getActivity()).getLeadId();
    /*    btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                promptSpeechInput();
            }
        });*/
        if (listingtype.isChecked() == false) {
            listingtype_value = "Sell";
        } else {
            listingtype_value = "Buy";
        }
        if (shortsaleBox.isChecked() == false) {
            shortsaleBox_value = "0";
        } else {
            shortsaleBox_value = "1";
        }
        if (foreclosureBox.isChecked() == false) {
            foreclosureBox_value = "0";
        } else {
            foreclosureBox_value = "1";
        }
        spinnerpropertytype.setSelection(0, true);
        propertytype = getResources().getStringArray(R.array.propertyType);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpropertytype.setAdapter(adapter);


        spinnerpointofinterest.setSelection(0, true);
        pointofinterest = getResources().getStringArray(R.array.point_of_interest);
        final ArrayAdapter<String> pointofinterestadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, pointofinterest);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpointofinterest.setAdapter(pointofinterestadapter);

        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
      //  flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
       // LeadId = sharedpreferences.getInt("LeadId", 1);

        if (flag == 1) {
            relative_lyt_loggedSeller = (RelativeLayout) myFragmentView.findViewById(R.id.relative_lyt_loggedSeller);
            relative_lyt_loggedSeller.setVisibility(View.VISIBLE);
        } else {
            relative_lyt_loggedSeller = (RelativeLayout) myFragmentView.findViewById(R.id.relative_lyt_loggedSeller);
            relative_lyt_loggedSeller.setVisibility(View.GONE);
        }

        bedImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bedCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int = present_value_int + 1;
                bedCount.setText(String.valueOf(present_value_int));
            }
        });
        bedImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bedCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if (present_value_int > 0) {

                    present_value_int = present_value_int - 1;
                    bedCount.setText(String.valueOf(present_value_int));
                } else {

                }

            }
        });

        bathImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bathCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                present_value_int = present_value_int + 1;
                bathCount.setText(String.valueOf(present_value_int));
            }
        });
        bathImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = bathCount.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if (present_value_int > 0) {
                    present_value_int = present_value_int - 1;
                    bathCount.setText(String.valueOf(present_value_int));
                } else {

                }

            }
        });

        yearImgPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMin.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if(present_value_int<PresentYear){
                    present_value_int = present_value_int + 1;
                }
                YearcountMin.setText(String.valueOf(present_value_int));
            }
        });
        yearImgNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String present_value_string = YearcountMin.getText().toString();
                int present_value_int = Integer.parseInt(present_value_string);
                if (present_value_int > 0) {
                    present_value_int = present_value_int - 1;
                    YearcountMin.setText(String.valueOf(present_value_int));
                } else {

                }

            }
        });




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
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate2();
            }

        };
        date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate3();
            }

        };
        foreclosure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), R.style.DialogTheme,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_available.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), R.style.DialogTheme,date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

       /* Date_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/



        ownerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ownerInfo = new Intent(getActivity(), OwnerInfoActivity.class);
                getActivity().startActivity(ownerInfo);
            }
        });


        propertid.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);

                }
            }
        });

        foreclosure.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star, 0);

                }
            }
        });

        listingprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star, 0);

                }
            }
        });



        propphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ownerInfo = new Intent(getActivity(), AddNewPhotoActivity.class);
                Intent ownerInfo = new Intent(getActivity(), ViewExistingPhotoActivity.class);
                ownerInfo.putExtra("LeadId",LeadId);
                getActivity().startActivity(ownerInfo);

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

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        if (flag == 1) {

            update = Volley.newRequestQueue(getActivity().getApplicationContext());
            editViewQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
           // imageUrl = "http://202.88.239.14:8169/FileUploads/";
            yearImgPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = YearcountMin.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if(present_value_int<PresentYear){
                        present_value_int = present_value_int + 1;
                    }
                    YearcountMin.setText(String.valueOf(present_value_int));
                }
            });
            yearImgNeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = YearcountMin.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if (present_value_int > 0) {
                        present_value_int = present_value_int - 1;
                        YearcountMin.setText(String.valueOf(present_value_int));
                    } else {

                    }

                }
            });
            bedImgPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = bedCount.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    present_value_int = present_value_int + 1;
                    bedCount.setText(String.valueOf(present_value_int));
                }
            });
            bedImgNeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = bedCount.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if (present_value_int > 0) {

                        present_value_int = present_value_int - 1;
                        bedCount.setText(String.valueOf(present_value_int));
                    } else {

                    }

                }
            });

            bathImgPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = bathCount.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    present_value_int = present_value_int + 1;
                    bathCount.setText(String.valueOf(present_value_int));
                }
            });
            bathImgNeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String present_value_string = bathCount.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if (present_value_int > 0) {
                        present_value_int = present_value_int - 1;
                        bathCount.setText(String.valueOf(present_value_int));
                    } else {

                    }

                }
            });

            url = "http://192.168.0.3:3000/reswy/leadlistproperty/"+lid;


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                               // ListingTypeSellRent="Rent";

                                editStatus = response.getString("status").toString().trim();


                                if (editStatus.equals("success")) {
                                    JSONArray jsonArray = response.getJSONArray("result");

                                    JSONObject propertyDetails = jsonArray.getJSONObject(0);
                                    address_locationTxt = propertyDetails.getString("address_location");
                                    ListingTypeSellRent = propertyDetails.getString("listingtype").toString();
                                    Log.v("listtype",ListingTypeSellRent);
                                    foreclosure_DateTxt = propertyDetails.getString("foreclosure_date");
                                    listing_PriceTxt = propertyDetails.getString("listing_price");
                                    bedsTxt = propertyDetails.getInt("beds");
                                    bathsTxt = propertyDetails.getInt("baths");
                                    emaiLTxt = propertyDetails.getString("emailid");
                                    propertyTypeTxt=propertyDetails.getString("propertytype");
                                    pointOfInterestTxt=propertyDetails.getString("pointofinterest");
                                    yearBuiltStartTxt = propertyDetails.getInt("yearbuilt_start");
                                    discriptionTxt = propertyDetails.getString("property_description");
                                    int shortsaleTxt = propertyDetails.getInt("short_sale");
                                    int forclosureTxt=propertyDetails.getInt("foreclosure");
                                   // Toast.makeText(getActivity(), "forclosureTxt "+forclosureTxt, Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getActivity(), "shortsaleTxt "+shortsaleTxt, Toast.LENGTH_SHORT).show();

                                    String available_date=propertyDetails.getString("available_on_date");
                                    et_available.setText(available_date);
                                    String square_feet=propertyDetails.getString("squarefootage_max");
                                    et_squareFeet.setText(square_feet);

                                   //Toast.makeText(getActivity(), "available_on_date "+available_date, Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getActivity(), "squarefootage_max "+square_feet, Toast.LENGTH_SHORT).show();

                                    yearString = String.valueOf(yearBuiltStartTxt);
                                    bedString = String.valueOf(bedsTxt);
                                    bathString = String.valueOf(bathsTxt);

                                    address.setText(address_locationTxt);
                                    bedCount.setText(bedString);
                                    foreclosure.setText(foreclosure_DateTxt);
                                    bathCount.setText(bathString);
                                    listingprice.setText(listing_PriceTxt);
                                    YearcountMin.setText(yearString);
                                    propdescription.setText(discriptionTxt);
                                    ///////////////////////////////////
                                    int position1= pointofinterestadapter.getPosition(pointOfInterestTxt);
                                    int position= adapter.getPosition(propertyTypeTxt);
                                    if(position!=0){
                                        spinnerpropertytype.setSelection(position);
                                    }
                                    if(position1!=0){
                                        spinnerpointofinterest.setSelection(position1);
                                    }



                                    //String trueTxt="1";
                                    if(shortsaleTxt==1){
                                        shortsaleBox.setChecked(true);
                                    }
                                    if(forclosureTxt==1){
                                        foreclosureBox.setChecked(true);
                                    }
                                   if(ListingTypeSellRent.compareTo("Sell")==0){
                                       listingtype.setChecked(false); }
                                    else {
                                            listingtype.setChecked(true);
                                   }

                                    //  Log.v("year",""+year);


                                  /*  JSONArray jsonArray2 = response.getJSONArray("viewpropdetails");


                                    if (jsonArray2.length() > 0) {
                                        JSONObject viewpropdetails = jsonArray2.getJSONObject(0);
                                        buyerName = viewpropdetails.getString("buyerName");
                                        mobile = viewpropdetails.getString("mobile");
                                        email = viewpropdetails.getString("email");
                                        viewedDatetime = viewpropdetails.getString("viewedDatetime");
                                        comments = viewpropdetails.getString("comments");
                                    }*/


                                } else {
                                    yearBuiltStartTxt = 2018;
                                    bedsTxt = 3;
                                    bathsTxt = 3;
                                    yearString = String.valueOf(yearBuiltStartTxt);
                                    bedString = String.valueOf(bedsTxt);
                                    bathString = String.valueOf(bathsTxt);
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
                    headers.put("Authorization", Token);
                    return headers;
                }
            };
            editViewQueue.add(jsonRequest);
        }



        savebtn_property.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                loading.setMessage("loading");
                loading.show();

                final String propertidTxt = propertid.getText().toString();
                final String addressTxt = address.getText().toString();
                final String foreclosureTxt = foreclosure.getText().toString();
                Log.v("forclosuredate",""+foreclosureTxt);
                final String listingtype_valueTxt = listingtype_value.toString();

                final String listingpriceTxt = listingprice.getText().toString();
                final String bedCountTxt = bedCount.getText().toString();
                final String bathCountTxt = bathCount.getText().toString();
                final String YearcountMinxt = YearcountMin.getText().toString();
                final String propdescriptionTxt = propdescription.getText().toString();
                final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
                final String spinnerpointofinterestTxt = spinnerpointofinterest.getSelectedItem().toString();
                final String available_on_date= et_available.getText().toString().trim();
                final String square_feet=et_squareFeet.getText().toString().trim();

                if ((addressTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) || (YearcountMinxt.equals("")) || (propdescriptionTxt.equals(""))) {


                    Toast.makeText(getActivity(), "Please fill mandatory fields", Toast.LENGTH_SHORT).show();
                } else {

                    int lead_id= SharedPrefsUtils.getInstance(getActivity()).getLeadId();
                    Toast.makeText(getActivity(), "led_id "+lead_id, Toast.LENGTH_SHORT).show();

                    requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    url = "http://192.168.0.3:3000/reswy/addlead_step2";
                    Map<String, Object> jsonParams = new ArrayMap<>();
                    if(flag==1)
                    {
                        jsonParams.put("lead_id", lid);
                    }
                    else
                    {
                        jsonParams.put("lead_id", lead_id);

                    }
                   // jsonParams.put("lead_id", lid);
                    jsonParams.put("flag", flag);
                    jsonParams.put("Update", 0);
                    jsonParams.put("beds", bedCountTxt);
                    jsonParams.put("baths", bathCountTxt);
                    jsonParams.put("yearbuilt", YearcountMinxt);
                    jsonParams.put("propertytype", spinnerpropertytypeTxt);
                    jsonParams.put("listingtype", listingtype_valueTxt);
                    jsonParams.put("listing_price", listingpriceTxt);
                    jsonParams.put("pointofinterest", spinnerpointofinterestTxt);
                    jsonParams.put("address", addressTxt);
                    jsonParams.put("short_sale", shortsaleBox_value);
                    jsonParams.put("foreclosure", foreclosureBox_value);
                    jsonParams.put("foreclosure_date", testdate);
                    jsonParams.put("available_date", available_on_date);
                    jsonParams.put("square_feet", square_feet);

                    jsonParams.put("propertchara", propdescriptionTxt);
                    jsonParams.put("propertyid", "11");
                    jsonParams.put("features", "13");

                /*        {
                            "status": "success",
                                "result": {
                            "ffrid": 138,
                                    "lead_id": 18
                        }
                        }*/

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    loading.dismiss();

                                    try {
                                        responseResult = response.getString("status").toString().trim();
                                        Toast.makeText(getActivity(), responseResult, Toast.LENGTH_SHORT).show();
                                        JSONObject result=response.getJSONObject("result");
                                        String lead_id=result.getString("lead_id");
                                        String ffrid=result.getString("ffrid");
                                        //  Toast.makeText(getActivity(), lead_id+" "+ffrid, Toast.LENGTH_SHORT).show();

//                                                Lead_ID = response.getInt("Lead_ID");
                                        if(flag==0)
                                        {
                                            propertid.setText("");
                                            address.setText("");
                                            foreclosure.setText("");
                                            listingprice.setText("");

                                            bedCount.setText("3");
                                            bathCount.setText("3");
                                            YearcountMin.setText("2018");
                                            propdescription.setText("");
                                            et_squareFeet.setText("");
                                            et_available.setText("");
                                            // listingprice.setText("");
                                            // listingprice.setText("");

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
                            headers.put("Authorization", Token);
                            return headers;
                        }
                    };

                    requestQueue.add(jsonObjectRequest);
                }
            }
        });




        return myFragmentView;



    }



    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            //getActivity().getFragmentManager().beginTransaction().remove(this).commit();


            //startActivityForResult(intent,REQ_CODE_SPEECH_INPUT_1);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(), getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speaktxt = result.get(0);
                    // Toast.makeText(getActivity(),str1,Toast.LENGTH_SHORT).show();
                    propdescription.setText(speaktxt);
                }


                break;
            }
        }

    }

    private void updateDate() {
        foreclosure.setText(sdf.format(myCalendar.getTime()));
         testdate=foreclosure.getText().toString();
        Log.v("Date",testdate);

    }
    private void updateDate3() {
        et_available.setText(sdf.format(myCalendar.getTime()));
       // testdate3=Date_Time.getText().toString();
//        Log.v("Date",testdate2);
        //   String timeValue=" T00:00:00";
        //  foreclosure.append(timeValue);
    }
    private void updateDate2() {
        //Date_Time.setText(sdf.format(myCalendar.getTime()));
       // testdate2=Date_Time.getText().toString();
       // Log.v("Date",testdate2);
        //   String timeValue=" T00:00:00";
        //  foreclosure.append(timeValue);
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

    private void setback() {
        foreclosure.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        listingprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        propdescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });

    }
}
