//package com.nexgensm.reswye.ui.lead;
//
//import android.Manifest;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.speech.RecognizerIntent;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.SwitchCompat;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.style.ForegroundColorSpan;
//import android.util.ArrayMap;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.nexgensm.reswye.R;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import static android.app.Activity.RESULT_OK;
//
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.nexgensm.reswye.ui.signinpage.SigninActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddNewPropertySellerFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddNewPropertySellerFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AddNewPropertySellerFragment extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private String mParam1;
//    private String mParam2;
//    private final int REQ_CODE_SPEECH_INPUT = 100;
//
//    private OnFragmentInteractionListener mListener;
//    private int PLACE_PICKER_REQUEST = 1;
//    private String TAG = "AddNewPropertySellerFragment";
//    RelativeLayout relative_lyt_loggedSeller;
//    TextView YearcountMin, bedCount, bathCount, featurestxt, additionalpropchartxt;
//    Calendar myCalendar = Calendar.getInstance();
//    DatePickerDialog.OnDateSetListener date;
//    String dateFormat = "dd.MM.yyyy", listingtype_value, shortsaleBox_value, foreclosureBox_value, Token, responseResult, message;
//    ProgressDialog loading;
//    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
//    String[] propertytype;
//    EditText propdescription, BuyerViewed, phoneviewed, Mail, Date_Time, BuyerComments, propertid, foreclosure, listingprice, address;
//    int flag, userId, LeadId;
//    String buyerName, viewedDatetime, email, mobile, comments, updateStatus, yearString, bedString, bathString;
//    String imageUrl, editStatus, address_locationTxt, foreclosure_DateTxt, listing_PriceTxt, emaiLTxt, discriptionTxt;
//    ImageView yearImgNeg, yearImgPos, bedImgNeg, bedImgPos, bathImgNeg, bathImgPos, loc, AddViewed;
//    ImageButton btnSpeak, propphoto;
//    Spinner spinnerpropertytype, spinnerpointofinterest;
//    Button savebtn_property;
//    String url;
//    int bedsTxt, yearBuiltStartTxt, bathsTxt;
//    GoogleMap googlemap;
//    SwitchCompat listingtype;
//    CheckBox shortsaleBox, foreclosureBox;
//    SharedPreferences sharedpreferences;
//    public static final String mypreference = "mypref";
//    RequestQueue requestQueue, editViewQueue, update, requestQueue3;
//
//    public AddNewPropertySellerFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddNewPropertySellerFragment.
//     */
//
//    public static AddNewPropertySellerFragment newInstance(String param1, String param2) {
//        AddNewPropertySellerFragment fragment = new AddNewPropertySellerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View myFragmentView = inflater.inflate(R.layout.fragment_add_new_property_seller, container, false);
//        btnSpeak = (ImageButton) myFragmentView.findViewById(R.id.btnSpeak);
//        propdescription = (EditText) myFragmentView.findViewById(R.id.propertydescription);
//        listingtype = (SwitchCompat) myFragmentView.findViewById(R.id.listingtype);
//
//        foreclosureBox = (CheckBox) myFragmentView.findViewById(R.id.foreclosureBox);
//        shortsaleBox = (CheckBox) myFragmentView.findViewById(R.id.shortsaleBox);
//        spinnerpropertytype = (Spinner) myFragmentView.findViewById(R.id.spinnerpropertytype);
//        spinnerpointofinterest = (Spinner) myFragmentView.findViewById(R.id.spinnerpointofinterest);
//
//        bedCount = (TextView) myFragmentView.findViewById(R.id.bed_number);
//        bedImgNeg = (ImageView) myFragmentView.findViewById(R.id.bed_negative);
//        bedImgPos = (ImageView) myFragmentView.findViewById(R.id.bed_positive);
//
//        bathCount = (TextView) myFragmentView.findViewById(R.id.bath_number);
//        bathImgNeg = (ImageView) myFragmentView.findViewById(R.id.bath_negative);
//        bathImgPos = (ImageView) myFragmentView.findViewById(R.id.bath_positive);
//
//        YearcountMin = (TextView) myFragmentView.findViewById(R.id.year_txt_min);
//        yearImgNeg = (ImageView) myFragmentView.findViewById(R.id.year_negative_min);
//        yearImgPos = (ImageView) myFragmentView.findViewById(R.id.year_positive_min);
//
//
//        BuyerViewed = (EditText) myFragmentView.findViewById(R.id.BuyerViewed);
//        BuyerComments = (EditText) myFragmentView.findViewById(R.id.BuyerComments);
//
//        phoneviewed = (EditText) myFragmentView.findViewById(R.id.phoneviewed);
//        Mail = (EditText) myFragmentView.findViewById(R.id.Mail);
//        Date_Time = (EditText) myFragmentView.findViewById(R.id.Date_Time);
//        AddViewed = (ImageView) myFragmentView.findViewById(R.id.AddViewed);
//
//        featurestxt = (TextView) myFragmentView.findViewById(R.id.featurestxt);
//        ImageButton ownerbtn = (ImageButton) myFragmentView.findViewById(R.id.ownerinfo);
//        propertid = (EditText) myFragmentView.findViewById(R.id.propertyidedittext);
//        address = (EditText) myFragmentView.findViewById(R.id.addressedittext);
//
//        foreclosure = (EditText) myFragmentView.findViewById(R.id.datepic);
//        listingprice = (EditText) myFragmentView.findViewById(R.id.listingprice);
//        additionalpropchartxt = (TextView) myFragmentView.findViewById(R.id.additionalpropertychartxt);
//        propphoto = (ImageButton) myFragmentView.findViewById(R.id.addphoto);
//        loc = (ImageView) myFragmentView.findViewById(R.id.addressloc);
//        savebtn_property = (Button) myFragmentView.findViewById(R.id.savebtn_property);
//
////        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
////        flag=sharedpreferences.getInt("flag",0);
////        LeadId=sharedpreferences.getInt("LeadId",0);
//        btnSpeak.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                promptSpeechInput();
//            }
//        });
//        if (listingtype.isChecked() == false) {
//            listingtype_value = "SELL";
//        } else {
//            listingtype_value = "BUY";
//        }
//        if (shortsaleBox.isChecked() == false) {
//            shortsaleBox_value = "false";
//        } else {
//            shortsaleBox_value = "true";
//        }
//        if (foreclosureBox.isChecked() == false) {
//            foreclosureBox_value = "false";
//        } else {
//            foreclosureBox_value = "true";
//        }
//        spinnerpropertytype.setSelection(0, true);
//        propertytype = getResources().getStringArray(R.array.Property_Type);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerpropertytype.setAdapter(adapter);
//
//        spinnerpointofinterest.setSelection(0, true);
//        propertytype = getResources().getStringArray(R.array.point_of_interest);
//        ArrayAdapter<String> pointofinterestadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerpointofinterest.setAdapter(pointofinterestadapter);
//
//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag = sharedpreferences.getInt("flag", 0);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
//        LeadId = sharedpreferences.getInt("LeadId", 1);
//        if (flag == 1) {
//            relative_lyt_loggedSeller = (RelativeLayout) myFragmentView.findViewById(R.id.relative_lyt_loggedSeller);
//            relative_lyt_loggedSeller.setVisibility(View.VISIBLE);
//        } else {
//            relative_lyt_loggedSeller = (RelativeLayout) myFragmentView.findViewById(R.id.relative_lyt_loggedSeller);
//            relative_lyt_loggedSeller.setVisibility(View.GONE);
//        }
//
//        bedImgPos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = bedCount.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                present_value_int = present_value_int + 1;
//                bedCount.setText(String.valueOf(present_value_int));
//            }
//        });
//        bedImgNeg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = bedCount.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                if (present_value_int > 0) {
//
//                    present_value_int = present_value_int - 1;
//                    bedCount.setText(String.valueOf(present_value_int));
//                } else {
//
//                }
//
//            }
//        });
//
//        bathImgPos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = bathCount.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                present_value_int = present_value_int + 1;
//                bathCount.setText(String.valueOf(present_value_int));
//            }
//        });
//        bathImgNeg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = bathCount.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                if (present_value_int > 0) {
//                    present_value_int = present_value_int - 1;
//                    bathCount.setText(String.valueOf(present_value_int));
//                } else {
//
//                }
//
//            }
//        });
//
//        yearImgPos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = YearcountMin.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                present_value_int = present_value_int + 1;
//                YearcountMin.setText(String.valueOf(present_value_int));
//            }
//        });
//        yearImgNeg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String present_value_string = YearcountMin.getText().toString();
//                int present_value_int = Integer.parseInt(present_value_string);
//                if (present_value_int > 0) {
//                    present_value_int = present_value_int - 1;
//                    YearcountMin.setText(String.valueOf(present_value_int));
//                } else {
//
//                }
//
//            }
//        });
//
////        yearImgPosMax.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                String present_value_string = YearcountMax.getText().toString();
////                int present_value_int = Integer.parseInt(present_value_string);
////                present_value_int=present_value_int+1;
////                YearcountMax.setText(String.valueOf(present_value_int));
////            }
////        });
////        yearImgNegMin.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                String present_value_string = YearcountMax.getText().toString();
////                int present_value_int = Integer.parseInt(present_value_string);
////                if(present_value_int>0){
////                    present_value_int=present_value_int-1;
////                    YearcountMax.setText(String.valueOf(present_value_int));
////                }
////                else{
////
////                }
////
////            }
////        });
//
//
//        date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateDate();
//            }
//
//        };
//
//        foreclosure.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        Date_Time.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        AddViewed.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                BuyerComments.setText("");
//                BuyerViewed.setText("");
//                phoneviewed.setText("");
//                Mail.setText("");
//                Date_Time.setText("");
//            }
//        });
//
//        ownerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ownerInfo = new Intent(getActivity(), OwnerInfoActivity.class);
//                getActivity().startActivity(ownerInfo);
//            }
//        });
//
//
//        propertid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                }
//            }
//        });
//
//        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//
//                }
//            }
//        });
//
//        foreclosure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//
//        listingprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    //firstnameedittext.setCompoundDrawablesWithIntrinsicBounds( 0,0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//
//        propphoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent ownerInfo = new Intent(getActivity(), AddNewPhotoActivity.class);
//                Intent ownerInfo = new Intent(getActivity(), ViewExistingPhotoActivity.class);
//                ownerInfo.putExtra("LeadId",LeadId);
//                getActivity().startActivity(ownerInfo);
//
//            }
//        });
//
//        featurestxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent addfeature = new Intent(getActivity(), AddFeatureActivity.class);
//                getActivity().startActivity(addfeature);
//
//            }
//        });
//
//        additionalpropchartxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent addchar = new Intent(getActivity(), Additional_charecterestics.class);
//                getActivity().startActivity(addchar);
//            }
//        });
//
//        loc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(getActivity());
//                // Include dialog.xml file
//                dialog.setContentView(R.layout.lead_location_dlg);
//                dialog.setCanceledOnTouchOutside(true);
//                // Set dialog title
//                String titleText = "Select Your Location";
//                //dialog.setTitle("Cha");
//                ForegroundColorSpan backgroundColorSpan = new ForegroundColorSpan(Color.BLACK);
//                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
//
//                // Apply the text color span
//                ssBuilder.setSpan(
//                        backgroundColorSpan,
//                        0,
//                        titleText.length(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
//                dialog.setTitle(ssBuilder);
//                //titleText.(Gravity.CENTER_HORIZONTAL);*/
//
//                dialog.show();
//
//
//            }
//        });
//
//        if (googlemap == null) {
//
//
//            MapFragment mapFragment = (MapFragment) getFragmentManager()
//                    .findFragmentById(R.id.PropertyMapViewDetails_Map);
//
//            //googlemap.setMyLocationEnabled(true);
//            // mapFragment.getMapAsync(this.getActivity());
//        }
//        if (flag == 0) {
//
//
//            savebtn_property.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onClick(View v) {
//                    final String propertidTxt = propertid.getText().toString();
//                    final String addressTxt = address.getText().toString();
//                    final String foreclosureTxt = foreclosure.getText().toString();
//                    final String listingtype_valueTxt = listingtype_value.toString();
//                    final String shortsaleBox_valueTxt = shortsaleBox_value.toString();
//                    final String foreclosureBox_valueTxt = foreclosureBox_value.toString();
//                    final String foreclosure_valueTxt = foreclosure.toString();
//                    final String listingpriceTxt = listingprice.getText().toString();
//                    final String bedCountTxt = bedCount.getText().toString();
//                    final String bathCountTxt = bathCount.getText().toString();
//                    final String YearcountMinxt = YearcountMin.getText().toString();
//                    final String propdescriptionTxt = propdescription.getText().toString();
//                    final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
//                    final String spinnerpointofinterestTxt = spinnerpointofinterest.getSelectedItem().toString();
//
//                    if ((addressTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) || (YearcountMinxt.equals("")) || (propdescriptionTxt.equals(""))) {
//                        Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//                        url = "http://202.88.239.14:8169/api/Lead/AddPropertyDetails";
//                        Map<String, Object> jsonParams = new ArrayMap<>();
//                        jsonParams.put("Lead_ID", LeadId);
//                        jsonParams.put("Update", 0);
//                        jsonParams.put("Beds", bedCountTxt);
//                        jsonParams.put("Baths", bathCountTxt);
//                        jsonParams.put("YearBuilt_Start", YearcountMinxt);
//                        jsonParams.put("PropertyType", spinnerpropertytypeTxt);
//                        jsonParams.put("ListingType", listingtype_valueTxt);
//                        jsonParams.put("Listing_Price", listingpriceTxt);
//                        jsonParams.put("PointOfInterest", spinnerpointofinterestTxt);
//                        jsonParams.put("Address_location", addressTxt);
//                        jsonParams.put("Short_Sale", shortsaleBox_value);
//                        jsonParams.put("ForeClosure", foreclosureBox_value);
//                        jsonParams.put("Foreclosure_Date", foreclosure);
//                        jsonParams.put("Property_Description", propdescriptionTxt);
//
//
//                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                                new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        try {
//                                            responseResult = response.getString("status").toString().trim();
//                                            message = response.getString("message").toString().trim();
////                                                Lead_ID = response.getInt("Lead_ID");
//
//
//                                            String str3 = "Success";
//                                            int response_result = responseResult.compareTo(str3);
//                                            if (response_result == 0) {
//                                                loading.dismiss();
//                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                                            } else {
//                                                loading.dismiss();
//                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        } catch (JSONException e) {
//                                            loading.dismiss();
//                                            //   Toast.makeText(getActivity(), e, Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                loading.dismiss();
//                                Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                                // do something...
//                            }
//                        }) {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                final Map<String, String> headers = new HashMap<>();
//                                headers.put("Authorization", Token);
//                                return headers;
//                            }
//                        };
//                        loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);
//
//                        requestQueue.add(jsonObjectRequest);
//
//
//                    }
//                }
//            });
//        } else {
//            update = Volley.newRequestQueue(getActivity().getApplicationContext());
//            editViewQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//            imageUrl = "http://202.88.239.14:8169/FileUploads/";
//            url = "http://202.88.239.14:8169/api/Lead/GetPropertyDetails";
//
//            yearImgPos.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = YearcountMin.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    present_value_int = present_value_int + 1;
//                    YearcountMin.setText(String.valueOf(present_value_int));
//                }
//            });
//            yearImgNeg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = YearcountMin.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    if (present_value_int > 0) {
//                        present_value_int = present_value_int - 1;
//                        YearcountMin.setText(String.valueOf(present_value_int));
//                    } else {
//
//                    }
//
//                }
//            });
//            bedImgPos.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = bedCount.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    present_value_int = present_value_int + 1;
//                    bedCount.setText(String.valueOf(present_value_int));
//                }
//            });
//            bedImgNeg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = bedCount.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    if (present_value_int > 0) {
//
//                        present_value_int = present_value_int - 1;
//                        bedCount.setText(String.valueOf(present_value_int));
//                    } else {
//
//                    }
//
//                }
//            });
//
//            bathImgPos.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = bathCount.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    present_value_int = present_value_int + 1;
//                    bathCount.setText(String.valueOf(present_value_int));
//                }
//            });
//            bathImgNeg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String present_value_string = bathCount.getText().toString();
//                    int present_value_int = Integer.parseInt(present_value_string);
//                    if (present_value_int > 0) {
//                        present_value_int = present_value_int - 1;
//                        bathCount.setText(String.valueOf(present_value_int));
//                    } else {
//
//                    }
//
//                }
//            });
//
//            Map<String, Object> jsonParams = new ArrayMap<>();
//            jsonParams.put("Lead_ID", LeadId);
//
//            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                editStatus = response.getString("status").toString().trim();
//                                JSONArray jsonArray = response.getJSONArray("propertyDetails");
//                                if (jsonArray.length() > 0) {
//                                    JSONObject propertyDetails = jsonArray.getJSONObject(0);
//                                    address_locationTxt = propertyDetails.getString("address_location");
//                                    foreclosure_DateTxt = propertyDetails.getString("foreclosure_Date");
//                                    listing_PriceTxt = propertyDetails.getString("listing_Price");
//                                    bedsTxt = propertyDetails.getInt("beds");
//                                    bathsTxt = propertyDetails.getInt("baths");
//                                    emaiLTxt = propertyDetails.getString("email");
//                                    yearBuiltStartTxt = propertyDetails.getInt("yearBuiltStart");
//                                    discriptionTxt = propertyDetails.getString("propertyDescription");
//
//                                    yearString = String.valueOf(yearBuiltStartTxt);
//                                    bedString = String.valueOf(bedsTxt);
//                                    bathString = String.valueOf(bathsTxt);
//                                } else {
//                                    yearBuiltStartTxt = 2018;
//                                    bedsTxt = 3;
//                                    bathsTxt = 3;
//                                    yearString = String.valueOf(yearBuiltStartTxt);
//                                    bedString = String.valueOf(bedsTxt);
//                                    bathString = String.valueOf(bathsTxt);
//                                }
//
//
//                                //  Log.v("year",""+year);
//                                JSONArray jsonArray2 = response.getJSONArray("viewpropdetails");
//
//
//                                if (jsonArray2.length() > 0) {
//                                    JSONObject viewpropdetails = jsonArray2.getJSONObject(0);
//                                    buyerName = viewpropdetails.getString("buyerName");
//                                    mobile = viewpropdetails.getString("mobile");
//                                    email = viewpropdetails.getString("email");
//                                    viewedDatetime = viewpropdetails.getString("viewedDatetime");
//                                    comments = viewpropdetails.getString("comments");
//                                }
//
//                                String str3 = "Success";
//                                int response_result = editStatus.compareTo(str3);
//                                if (response_result == 0) {
//
//                                    address.setText(address_locationTxt);
//                                    bedCount.setText(bedString);
//                                    foreclosure.setText(foreclosure_DateTxt);
//                                    bathCount.setText(bathString);
//                                    listingprice.setText(listing_PriceTxt);
//                                    YearcountMin.setText(yearString);
//                                    propdescription.setText(discriptionTxt);
//
//                                    BuyerViewed.setText(buyerName);
//                                    BuyerComments.setText(comments);
//                                    Mail.setText(email);
//                                    Date_Time.setText(viewedDatetime);
//                                    phoneviewed.setText(mobile);
//
//                                    if (jsonArray.length() > 0) {
//
//
//                                        savebtn_property.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//
//
//                                                Map<String, Object> jsonParams = new ArrayMap<>();
//                                                String addressValue = address.getText().toString();
//                                                String discriptionValue = propdescription.getText().toString();
//                                                String bedCountValue = bedCount.getText().toString();
//                                                String bathCountValue = bathCount.getText().toString();
//                                                String listingpriceValue = listingprice.getText().toString();
//                                                String YearcountMinValue = YearcountMin.getText().toString();
//                                                String foreclosureValue = foreclosure.getText().toString();
//                                                String BuyerViewedValue = BuyerViewed.getText().toString();
//                                                String BuyerCommentsValue = BuyerComments.getText().toString();
//                                                String buyerViewedDate = Date_Time.getText().toString();
//                                                String MailValue = Mail.getText().toString();
//                                                String phoneviewedValue = phoneviewed.getText().toString();
//                                                String propertyTypeValue = spinnerpropertytype.getSelectedItem().toString();
//                                                String pointofInterestvalue = spinnerpointofinterest.getSelectedItem().toString();
//
//                                                if (listingtype.isChecked() == false) {
//                                                    listingtype_value = "SELL";
//                                                } else {
//                                                    listingtype_value = "BUY";
//                                                }
//                                                if (shortsaleBox.isChecked() == false) {
//                                                    shortsaleBox_value = "false";
//                                                } else {
//                                                    shortsaleBox_value = "true";
//                                                }
//                                                if (foreclosureBox.isChecked() == false) {
//                                                    foreclosureBox_value = "false";
//                                                } else {
//                                                    foreclosureBox_value = "true";
//                                                }
//
//                                                jsonParams.put("Lead_ID", LeadId);
//                                                jsonParams.put("Address_location", addressValue);
//                                                jsonParams.put("Beds", bedCountValue);
//                                                jsonParams.put("Baths", bathCountValue);
//                                                jsonParams.put("YearBuilt_Start", YearcountMinValue);
//                                                jsonParams.put("ListingType", listingtype_value);
//                                                jsonParams.put("Short_Sale", shortsaleBox_value);
//                                                jsonParams.put("ForeClosure", foreclosureBox_value);
//                                                jsonParams.put("Listing_Price", listingpriceValue);
//                                                jsonParams.put("Foreclosure_Date", foreclosure);
//                                                jsonParams.put("Property_Description", discriptionValue);
//                                                jsonParams.put("BuyerName", BuyerViewedValue);
//                                                jsonParams.put("Comments", BuyerCommentsValue);
//                                                jsonParams.put("Email", MailValue);
//                                                jsonParams.put("PropertyType", propertyTypeValue);
//                                                jsonParams.put("PointOfInterest", pointofInterestvalue);
//                                                  jsonParams.put("ViewedDatetime",Date_Time);
//                                                jsonParams.put("Mobile", phoneviewedValue);
//
//                                                url = "http://202.88.239.14:8169/api/Lead/UpdatePropertyDetails";
//
//                                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                                                        new Response.Listener<JSONObject>() {
//                                                            @Override
//                                                            public void onResponse(JSONObject response) {
//                                                                try {
//                                                                    updateStatus = response.getString("status").toString().trim();
//
//
//                                                                    String str3 = "Success";
//                                                                    int response_result = updateStatus.compareTo(str3);
//                                                                    if (response_result == 0) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(getActivity(), "Property details Updated", Toast.LENGTH_SHORT).show();
//
//                                                                    } else {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//
//                                                                    }
//
//                                                                } catch (JSONException e) {
//                                                                    loading.dismiss();
//                                                                    e.printStackTrace();
//                                                                }
//                                                            }
//                                                        }, new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        loading.dismiss();
//                                                        Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                                                        // do something...
//                                                    }
//                                                }) {
//                                                    @Override
//                                                    public Map<String, String> getHeaders() throws AuthFailureError {
//
//                                                        final Map<String, String> headers = new HashMap<>();
//                                                        headers.put("Authorization", Token);
//                                                        return headers;
//                                                    }
//                                                };
//                                                loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);
//
//                                                update.add(jsonObjectRequest1);
//                                            }
//                                        });
//                                    } else {
//                                        savebtn_property.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                final String propertidTxt = propertid.getText().toString();
//                                                final String addressTxt = address.getText().toString();
//                                                final String foreclosureTxt = foreclosure.getText().toString();
//                                                final String listingpriceTxt = listingprice.getText().toString();
//                                                final String bedCountTxt = bedCount.getText().toString();
//                                                final String bathCountTxt = bathCount.getText().toString();
//                                                final String YearcountMinxt = YearcountMin.getText().toString();
//                                                final String propdescriptionTxt = propdescription.getText().toString();
//                                                final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
//                                                final String spinnerpointofinterestTxt = spinnerpointofinterest.getSelectedItem().toString();
//                                                String BuyerViewedValue = BuyerViewed.getText().toString();
//                                                String BuyerDateTime = Date_Time.getText().toString();
//                                                String BuyerCommentsValue = BuyerComments.getText().toString();
//                                                String MailValue = Mail.getText().toString();
//                                                String phoneviewedValue = phoneviewed.getText().toString();
//                                                if ((addressTxt.equals("")) || (listingpriceTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) || (listingpriceTxt.equals("")) || (YearcountMinxt.equals("")) || (propdescriptionTxt.equals(""))) {
//                                                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                                                } else {
//
//                                                    requestQueue3 = Volley.newRequestQueue(getActivity().getApplicationContext());
//                                                    url = "http://202.88.239.14:8169/api/Lead/AddPropertyDetails";
//                                                    Map<String, Object> jsonParams = new ArrayMap<>();
//                                                    jsonParams.put("Lead_ID", LeadId);
//                                                    jsonParams.put("Update", 1);
//                                                    jsonParams.put("Beds", bedCountTxt);
//                                                    jsonParams.put("Baths", bathCountTxt);
//                                                    jsonParams.put("YearBuilt_Start", YearcountMinxt);
//                                                    jsonParams.put("PropertyType", spinnerpropertytypeTxt);
//                                                    jsonParams.put("ListingType", listingtype_value);
//                                                    jsonParams.put("Listing_Price", listingpriceTxt);
//                                                    jsonParams.put("PointOfInterest", spinnerpointofinterestTxt);
//                                                    jsonParams.put("Address_location", addressTxt);
//                                                    jsonParams.put("Short_Sale", shortsaleBox_value);
//                                                    jsonParams.put("ForeClosure", foreclosureBox_value);
//                                                    jsonParams.put("Foreclosure_Date", foreclosure);
//                                                    jsonParams.put("Property_Description", propdescriptionTxt);
//                                                    jsonParams.put("BuyerName", BuyerViewedValue);
//                                                    jsonParams.put("Comments", BuyerCommentsValue);
//                                                    jsonParams.put("Email", MailValue);
//                                                    jsonParams.put("ViewedDatetime", Date_Time);
//                                                    jsonParams.put("Mobile", phoneviewedValue);
//
//
//                                                    JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                                                            new Response.Listener<JSONObject>() {
//                                                                @Override
//                                                                public void onResponse(JSONObject response) {
//                                                                    try {
//                                                                        responseResult = response.getString("status").toString().trim();
//                                                                        message = response.getString("message").toString().trim();
////                                                Lead_ID = response.getInt("Lead_ID");
//
//
//                                                                        String str3 = "Success";
//                                                                        int response_result = responseResult.compareTo(str3);
//                                                                        if (response_result == 0) {
//                                                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                                                                        } else {
//                                                                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//                                                                        }
//
//                                                                    } catch (JSONException e) {
//                                                                        e.printStackTrace();
//                                                                    }
//                                                                }
//                                                            }, new Response.ErrorListener() {
//                                                        @Override
//                                                        public void onErrorResponse(VolleyError error) {
//                                                            loading.dismiss();
//                                                            Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                                                            // do something...
//                                                        }
//                                                    }) {
//                                                        @Override
//                                                        public Map<String, String> getHeaders() throws AuthFailureError {
//                                                            final Map<String, String> headers = new HashMap<>();
//                                                            headers.put("Authorization", Token);
//                                                            return headers;
//                                                        }
//                                                    };
//
//                                                    requestQueue3.add(jsonObjectRequest3);
//
//
//                                                }
//                                            }
//                                        });
//                                    }
//                                } else {
//                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                    // do something...
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    final Map<String, String> headers = new HashMap<>();
//                    headers.put("Authorization", Token);
//                    return headers;
//                }
//            };
//            editViewQueue.add(jsonRequest);
//        }
//        return myFragmentView;
//    }
//
//    private void promptSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                getString(R.string.speech_prompt));
//        try {
//
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//            //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
//
//
//            //startActivityForResult(intent,REQ_CODE_SPEECH_INPUT_1);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getActivity(), getString(R.string.speech_not_supported),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> result = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    String speaktxt = result.get(0);
//                    // Toast.makeText(getActivity(),str1,Toast.LENGTH_SHORT).show();
//                    propdescription.setText(speaktxt);
//                }
//
//
//                break;
//            }
//        }
//
//    }
//
//    private void updateDate() {
//        foreclosure.setText(sdf.format(myCalendar.getTime()));
//        Date_Time.setText(sdf.format(myCalendar.getTime()));
//    }
//
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//
//
//}


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
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

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

    private OnFragmentInteractionListener mListener;
    private int PLACE_PICKER_REQUEST = 1;

    RelativeLayout relative_lyt_loggedSeller;
    TextView YearcountMin, bedCount, bathCount, featurestxt, additionalpropchartxt;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date,date2;
    String dateFormat = "yyyy.MM.dd", listingtype_value="Sell", ListingTypeSellRent,forclosureTxt,shortsaleBox_value, foreclosureBox_value, Token, responseResult, message;
    ProgressDialog loading;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    String[] propertytype;
    int PresentYear;
    EditText propdescription, BuyerViewed, phoneviewed, Mail, Date_Time, BuyerComments, propertid, foreclosure, listingprice, address;
    int flag, userId, LeadId;
    String buyerName,viewedDatetime, email, mobile, comments, updateStatus, yearString, bedString, bathString;
    String imageUrl,ImageUrl, editStatus, address_locationTxt, foreclosure_DateTxt, listing_PriceTxt, pointOfInterestTxt,propertyTypeTxt,emaiLTxt, discriptionTxt,shortsaleTxt;
    ImageView yearImgNeg, yearImgPos, bedImgNeg, bedImgPos, bathImgNeg, bathImgPos, loc, AddViewed;
    ImageButton btnSpeak, propphoto;
    Spinner spinnerpropertytype, spinnerpointofinterest;
    Button savebtn_property;
    String url,testdate,testdate2;
    int bedsTxt, yearBuiltStartTxt, bathsTxt;
    GoogleMap googlemap;
    SwitchCompat listingtype;
    CheckBox shortsaleBox, foreclosureBox;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    RequestQueue requestQueue, editViewQueue, update, requestQueue3;

    public AddNewPropertySellerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewPropertySellerFragment.
     */

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
        Toast.makeText(getActivity(), "year:"+PresentYear, Toast.LENGTH_SHORT).show();
        btnSpeak = (ImageButton) myFragmentView.findViewById(R.id.btnSpeak);
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


        BuyerViewed = (EditText) myFragmentView.findViewById(R.id.BuyerViewed);
        BuyerComments = (EditText) myFragmentView.findViewById(R.id.BuyerComments);

        phoneviewed = (EditText) myFragmentView.findViewById(R.id.phoneviewed);
        Mail = (EditText) myFragmentView.findViewById(R.id.Mail);
        Date_Time = (EditText) myFragmentView.findViewById(R.id.Date_Time);
        AddViewed = (ImageView) myFragmentView.findViewById(R.id.AddViewed);

        featurestxt = (TextView) myFragmentView.findViewById(R.id.featurestxt);
        ImageButton ownerbtn = (ImageButton) myFragmentView.findViewById(R.id.ownerinfo);
        propertid = (EditText) myFragmentView.findViewById(R.id.propertyidedittext);
        address = (EditText) myFragmentView.findViewById(R.id.addressedittext);

        foreclosure = (EditText) myFragmentView.findViewById(R.id.datepic);
        listingprice = (EditText) myFragmentView.findViewById(R.id.listingprice);
        additionalpropchartxt = (TextView) myFragmentView.findViewById(R.id.additionalpropertychartxt);
        propphoto = (ImageButton) myFragmentView.findViewById(R.id.addphoto);
        loc = (ImageView) myFragmentView.findViewById(R.id.addressloc);
        savebtn_property = (Button) myFragmentView.findViewById(R.id.savebtn_property);

//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag=sharedpreferences.getInt("flag",0);
//        LeadId=sharedpreferences.getInt("LeadId",0);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                promptSpeechInput();
            }
        });
        if (listingtype.isChecked() == false) {
            listingtype_value = "Sell";
        } else {
            listingtype_value = "Buy";
        }
        if (shortsaleBox.isChecked() == false) {
            shortsaleBox_value = "false";
        } else {
            shortsaleBox_value = "true";
        }
        if (foreclosureBox.isChecked() == false) {
            foreclosureBox_value = "false";
        } else {
            foreclosureBox_value = "true";
        }
        spinnerpropertytype.setSelection(0, true);
        propertytype = getResources().getStringArray(R.array.Property_Type);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpropertytype.setAdapter(adapter);


        spinnerpointofinterest.setSelection(0, true);
        propertytype = getResources().getStringArray(R.array.point_of_interest);
        final ArrayAdapter<String> pointofinterestadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpointofinterest.setAdapter(pointofinterestadapter);

//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag = sharedpreferences.getInt("flag", 0);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
//        LeadId = sharedpreferences.getInt("LeadId", 1);
        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        LeadId = sharedpreferences.getInt("LeadId", 1);

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

        foreclosure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Date_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        AddViewed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BuyerComments.setText("");
                BuyerViewed.setText("");
                phoneviewed.setText("");
                Mail.setText("");
                Date_Time.setText("");
            }
        });

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

//        propphoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ownerInfo = new Intent(getActivity(), AddNewPhotoActivity.class);
//                ownerInfo.putExtra("lead_Id", 3);
//                getActivity().startActivity(ownerInfo);
//
//            }
//        });

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
                getActivity().startActivity(addfeature);

            }
        });

        additionalpropchartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addchar = new Intent(getActivity(), Additional_charecterestics.class);
                getActivity().startActivity(addchar);
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file
                dialog.setContentView(R.layout.lead_location_dlg);
                dialog.setCanceledOnTouchOutside(true);
                // Set dialog title
                String titleText = "Select Your Location";
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


            }
        });

        if (googlemap == null) {


            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.PropertyMapViewDetails_Map);

            //googlemap.setMyLocationEnabled(true);
            // mapFragment.getMapAsync(this.getActivity());
        }
        if (flag == 0) {


            savebtn_property.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);
                    final String propertidTxt = propertid.getText().toString();
                    final String addressTxt = address.getText().toString();
                    final String foreclosureTxt = foreclosure.getText().toString();
                    Log.v("forclosuredate",""+foreclosureTxt);
                    final String listingtype_valueTxt = listingtype_value.toString();
                    final String shortsaleBox_valueTxt = shortsaleBox_value.toString();
                    final String foreclosureBox_valueTxt = foreclosureBox_value.toString();
//                    final String foreclosure_valueTxt = foreclosure.toString();
                    final String listingpriceTxt = listingprice.getText().toString();
                    final String bedCountTxt = bedCount.getText().toString();
                    final String bathCountTxt = bathCount.getText().toString();
                    final String YearcountMinxt = YearcountMin.getText().toString();
                    final String propdescriptionTxt = propdescription.getText().toString();
                    final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
                    final String spinnerpointofinterestTxt = spinnerpointofinterest.getSelectedItem().toString();

                    if ((addressTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) || (YearcountMinxt.equals("")) || (propdescriptionTxt.equals(""))) {
                        Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {

                        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        url = "http://202.88.239.14:8169/api/Lead/AddPropertyDetails";
                        Map<String, Object> jsonParams = new ArrayMap<>();
                        jsonParams.put("Lead_ID", LeadId);
                        jsonParams.put("Update", 0);
                        jsonParams.put("Beds", bedCountTxt);
                        jsonParams.put("Baths", bathCountTxt);
                        jsonParams.put("YearBuilt_Start", YearcountMinxt);
                        jsonParams.put("PropertyType", spinnerpropertytypeTxt);
                        jsonParams.put("ListingType", listingtype_valueTxt);
                        jsonParams.put("Listing_Price", listingpriceTxt);
                        jsonParams.put("PointOfInterest", spinnerpointofinterestTxt);
                        jsonParams.put("Address_location", addressTxt);
                        jsonParams.put("Short_Sale", shortsaleBox_value);
                        jsonParams.put("ForeClosure", foreclosureBox_value);
                        jsonParams.put("Foreclosure_Date", testdate);
                        jsonParams.put("Property_Description", propdescriptionTxt);


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            responseResult = response.getString("status").toString().trim();
                                            message = response.getString("message").toString().trim();
//                                                Lead_ID = response.getInt("Lead_ID");


                                            String str3 = "Success";
                                            int response_result = responseResult.compareTo(str3);
                                            if (response_result == 0) {
                                                loading.dismiss();
                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                            } else {
                                                loading.dismiss();
                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        } else {
            update = Volley.newRequestQueue(getActivity().getApplicationContext());
            editViewQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
           // imageUrl = "http://202.88.239.14:8169/FileUploads/";
            url = "http://202.88.239.14:8169/api/Lead/GetPropertyDetails";

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

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("Lead_ID", LeadId);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                editStatus = response.getString("status").toString().trim();
                                JSONArray jsonArray = response.getJSONArray("propertyDetails");
                                if (jsonArray.length() > 0) {
                                    JSONObject propertyDetails = jsonArray.getJSONObject(0);
                                    address_locationTxt = propertyDetails.getString("address_location");
                                    ListingTypeSellRent = propertyDetails.getString("listingType").toString();
                                    Log.v("listtype",ListingTypeSellRent);
                                    foreclosure_DateTxt = propertyDetails.getString("foreclosure_Date");
                                    listing_PriceTxt = propertyDetails.getString("listing_Price");
                                    bedsTxt = propertyDetails.getInt("beds");
                                    bathsTxt = propertyDetails.getInt("baths");
                                    emaiLTxt = propertyDetails.getString("email");
                                    propertyTypeTxt=propertyDetails.getString("propertyType");
                                    pointOfInterestTxt=propertyDetails.getString("pointOfInterest");
                                    yearBuiltStartTxt = propertyDetails.getInt("yearBuiltStart");
                                    discriptionTxt = propertyDetails.getString("propertyDescription");
                                    shortsaleTxt = propertyDetails.getString("short_Sale");
                                    forclosureTxt=propertyDetails.getString("foreClosure");
                                    yearString = String.valueOf(yearBuiltStartTxt);
                                    bedString = String.valueOf(bedsTxt);
                                    bathString = String.valueOf(bathsTxt);
                                } else {
                                    yearBuiltStartTxt = 2018;
                                    bedsTxt = 3;
                                    bathsTxt = 3;
                                    yearString = String.valueOf(yearBuiltStartTxt);
                                    bedString = String.valueOf(bedsTxt);
                                    bathString = String.valueOf(bathsTxt);
                                }


                                //  Log.v("year",""+year);
                                JSONArray jsonArray2 = response.getJSONArray("viewpropdetails");


                                if (jsonArray2.length() > 0) {
                                    JSONObject viewpropdetails = jsonArray2.getJSONObject(0);
                                    buyerName = viewpropdetails.getString("buyerName");
                                    mobile = viewpropdetails.getString("mobile");
                                    email = viewpropdetails.getString("email");
                                    viewedDatetime = viewpropdetails.getString("viewedDatetime");
                                    comments = viewpropdetails.getString("comments");
                                }

                                String str3 = "Success";
                                String listType="Sell";
                                int response_result = editStatus.compareTo(str3);
                                if (response_result == 0) {

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

                                    BuyerViewed.setText(buyerName);
                                    BuyerComments.setText(comments);
                                    Mail.setText(email);
                                    Date_Time.setText(viewedDatetime);
                                    phoneviewed.setText(mobile);
                                    String trueTxt="true";
                                    if(shortsaleTxt==trueTxt){
                                        shortsaleBox.setChecked(true);
                                    }
                                    if(forclosureTxt==trueTxt){
                                        foreclosureBox.setChecked(true);
                                    }
//                                    if(ListingTypeSellRent.compareTo(listType)==0){
//                                        listingtype.setChecked(false);
//                                    }
//                                    else {
//                                        listingtype.setChecked(true);
//                                    }
                                    if (jsonArray.length() > 0) {

///////////////////////////UPDATE PROPERTY DETAILS/////////////////////////////////////////
                                        savebtn_property.setOnClickListener(new View.OnClickListener() {
                                            @SuppressLint("NewApi")
                                            @Override
                                            public void onClick(View v) {


                                                Map<String, Object> jsonParams = new ArrayMap<>();
                                                String addressValue = address.getText().toString();
                                                String discriptionValue = propdescription.getText().toString();
                                                String bedCountValue = bedCount.getText().toString();
                                                String bathCountValue = bathCount.getText().toString();
                                                String listingpriceValue = listingprice.getText().toString();
                                                String YearcountMinValue = YearcountMin.getText().toString();
                                                String foreclosureValue = foreclosure.getText().toString();
                                                String BuyerViewedValue = BuyerViewed.getText().toString();
                                                String BuyerCommentsValue = BuyerComments.getText().toString();
                                                String buyerViewedDate = Date_Time.getText().toString();
                                                String MailValue = Mail.getText().toString();
                                                String phoneviewedValue = phoneviewed.getText().toString();
                                                String propertyTypeValue = spinnerpropertytype.getSelectedItem().toString();
                                                String pointofInterestvalue = spinnerpointofinterest.getSelectedItem().toString();

                                                if (listingtype.isChecked() == false) {
                                                    listingtype_value = "Sell";
                                                } else {
                                                    listingtype_value = "Buy";
                                                }
                                                if (shortsaleBox.isChecked() == false) {
                                                    shortsaleBox_value = "false";
                                                } else {
                                                    shortsaleBox_value = "true";
                                                }
                                                if (foreclosureBox.isChecked() == false) {
                                                    foreclosureBox_value = "false";
                                                } else {
                                                    foreclosureBox_value = "true";
                                                }

                                                jsonParams.put("Lead_ID", LeadId);
                                                jsonParams.put("Address_location", addressValue);
                                                jsonParams.put("Beds", bedCountValue);
                                                jsonParams.put("Baths", bathCountValue);
                                                jsonParams.put("YearBuilt_Start", YearcountMinValue);
                                                jsonParams.put("ListingType", listingtype_value);
                                                jsonParams.put("Short_Sale", shortsaleBox_value);
                                                jsonParams.put("ForeClosure", foreclosureBox_value);
                                                jsonParams.put("Listing_Price", listingpriceValue);
                                                jsonParams.put("Foreclosure_Date", testdate);
                                                jsonParams.put("Property_Description", discriptionValue);
                                                jsonParams.put("BuyerName", BuyerViewedValue);
                                                jsonParams.put("Comments", BuyerCommentsValue);
                                                jsonParams.put("Email", MailValue);
                                                jsonParams.put("PropertyType", propertyTypeValue);
                                                jsonParams.put("PointOfInterest", pointofInterestvalue);
                                                jsonParams.put("ViewedDatetime",testdate2);
                                                jsonParams.put("Mobile", phoneviewedValue);

                                                url = "http://202.88.239.14:8169/api/Lead/UpdatePropertyDetails";

                                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Log.v("ADDPROPFRAG",response.toString());
                                                                try {
                                                                    updateStatus = response.getString("status").toString().trim();


                                                                    String str3 = "Success";
                                                                    int response_result = updateStatus.compareTo(str3);
                                                                    if (response_result == 0) {
                                                                        loading.dismiss();
                                                                        Toast.makeText(getActivity(), "Property details Updated", Toast.LENGTH_SHORT).show();

                                                                    } else {
                                                                        loading.dismiss();
                                                                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                                                                    }

                                                                } catch (JSONException e) {
                                                                    loading.dismiss();
                                                                    e.printStackTrace();
                                                                    loading.cancel();
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
                                                loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);

                                                update.add(jsonObjectRequest1);
                                            }
                                        });
                                    } else {
                                        savebtn_property.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final String propertidTxt = propertid.getText().toString();
                                                final String addressTxt = address.getText().toString();
                                                final String foreclosureTxt = foreclosure.getText().toString();
                                                final String listingpriceTxt = listingprice.getText().toString();
                                                final String bedCountTxt = bedCount.getText().toString();
                                                final String bathCountTxt = bathCount.getText().toString();
                                                final String YearcountMinxt = YearcountMin.getText().toString();
                                                final String propdescriptionTxt = propdescription.getText().toString();
                                                final String spinnerpropertytypeTxt = spinnerpropertytype.getSelectedItem().toString();
                                                final String spinnerpointofinterestTxt = spinnerpointofinterest.getSelectedItem().toString();
                                                String BuyerViewedValue = BuyerViewed.getText().toString();
                                                String BuyerDateTime = Date_Time.getText().toString();
                                                String BuyerCommentsValue = BuyerComments.getText().toString();
                                                String MailValue = Mail.getText().toString();
                                                String phoneviewedValue = phoneviewed.getText().toString();
                                                if ((addressTxt.equals("")) || (bedCountTxt.equals("")) || (bathCountTxt.equals("")) ||  (YearcountMinxt.equals(""))) {
                                                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    requestQueue3 = Volley.newRequestQueue(getActivity().getApplicationContext());
                                                    url = "http://202.88.239.14:8169/api/Lead/AddPropertyDetails";
                                                    Map<String, Object> jsonParams = new ArrayMap<>();
                                                    jsonParams.put("Lead_ID", LeadId);
                                                    jsonParams.put("Update", 1);
                                                    jsonParams.put("Beds", bedCountTxt);
                                                    jsonParams.put("Baths", bathCountTxt);
                                                    jsonParams.put("YearBuilt_Start", YearcountMinxt);
                                                    jsonParams.put("PropertyType", spinnerpropertytypeTxt);
                                                    jsonParams.put("ListingType", listingtype_value);
                                                    jsonParams.put("Listing_Price", listingpriceTxt);
                                                    jsonParams.put("PointOfInterest", spinnerpointofinterestTxt);
                                                    jsonParams.put("Address_location", addressTxt);
                                                    jsonParams.put("Short_Sale", shortsaleBox_value);
                                                    jsonParams.put("ForeClosure", foreclosureBox_value);
                                                    jsonParams.put("Foreclosure_Date", testdate);
                                                    jsonParams.put("Property_Description", propdescriptionTxt);
                                                    jsonParams.put("BuyerName", BuyerViewedValue);
                                                    jsonParams.put("Comments", BuyerCommentsValue);
                                                    jsonParams.put("Email", MailValue);
                                                    jsonParams.put("ViewedDatetime", testdate2);
                                                    jsonParams.put("Mobile", phoneviewedValue);


                                                    JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    try {
                                                                        responseResult = response.getString("status").toString().trim();
                                                                        message = response.getString("message").toString().trim();
                      //                                                Lead_ID = response.getInt("Lead_ID");


                                                                        String str3 = "Success";
                                                                        int response_result = responseResult.compareTo(str3);
                                                                        if (response_result == 0) {
                                                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

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

                                                    requestQueue3.add(jsonObjectRequest3);


                                                }
                                            }
                                        });
                                    }
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
    private void updateDate2() {
        Date_Time.setText(sdf.format(myCalendar.getTime()));
        testdate2=Date_Time.getText().toString();
        Log.v("Date",testdate2);
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


}
