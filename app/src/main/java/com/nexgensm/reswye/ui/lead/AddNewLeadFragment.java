//package com.nexgensm.reswye.ui.lead;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SwitchCompat;
//import android.util.ArrayMap;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
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
//import com.nexgensm.reswye.R;
//import com.nexgensm.reswye.ui.Dashboard.DormantItems;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.media.MediaRecorder.VideoSource.CAMERA;
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddNewLeadFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddNewLeadFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AddNewLeadFragment extends Fragment {
//
//    private String TAG = "AddNewLeadFragment";
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private OnFragmentInteractionListener mListener;
//    private static final int RESULT_OK = 1;
//    private static final int RESULT_CANCELED = 0;
//    private String mParam1;
//    private String mParam2;
//    Bitmap bitmap;
//    private static final String IMAGE_DIRECTORY = "/demonuts";
//    private int GALLERY = 1, CAMERA = 2;
//    Drawable btn_click;
//    Drawable btn_unclick;
//    Context context;
//
//    ImageView picview;
//    Spinner spinneradditionaldetails;
//    String[] additionaldetails;
//    CircleImageView circleView;
//    int flag, Lead_ID;
//    TextView firstname, lastname, AddressSeller, mobilenum, email;
//    Spinner spinner_additional_details;
//    Button newbtn, incontract, success, failure;
//    String gender_value, status, firstnametext, lastnametext, mobilenumbertext, leadStatusBtn, message, encodedImage, leadwarmth;
//    TextView lead_status;
//    SwitchCompat selectgender_selection;
//    RelativeLayout leadstauslayout;
//    Button warmbtn, coldbtn, neutral, newStatus, convertedStatus, inactiveStatus, addnewleadbtn;
//    Spinner additional_details;
//    RequestQueue requestQueue, requestQueueDisplay, editedSave;
//    String result;
//    String firstNameTxt, lastNameTxt, lead_StatusTxt, lead_CreatedDateTxt, addressTxt, leadProfileimageTxt, lead_CategoryTxt, mobileNoTxt, emailIDTxt;
//    String Token, Status_missed, name1, address, appointment_Date, leadstatus;
//    String url, imageUrl;
//    int userId, lead_ID, encodedImage_flag = 0, LeadId;
//    SharedPreferences sharedpreferences;
//    ProgressDialog loading;
//
//    public static final String mypreference = "mypref";
//
//    public AddNewLeadFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddNewLeadFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddNewLeadFragment newInstance(String param1, String param2) {
//        AddNewLeadFragment fragment = new AddNewLeadFragment();
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
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View myFragmentView = inflater.inflate(R.layout.fragment_add_new_lead, container, false);
//        spinneradditionaldetails = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);
//
//        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
//        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);
//
//
//        spinneradditionaldetails.setSelection(0, true);
//        additionaldetails = getResources().getStringArray(R.array.AddNewLead);
//        additional_details = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);
//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag = sharedpreferences.getInt("flag", 0);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
//        LeadId = sharedpreferences.getInt("LeadId", 1);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, additionaldetails);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinneradditionaldetails.setAdapter(adapter);
//
//
//        final EditText firstnameedittext = (EditText) myFragmentView.findViewById(R.id.firstname);
//        firstnameedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    firstnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    firstnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//        final EditText AddressSelleredittext = (EditText) myFragmentView.findViewById(R.id.AddressSeller);
//        AddressSelleredittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    AddressSelleredittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    AddressSelleredittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//        final EditText lastnameedittext = (EditText) myFragmentView.findViewById(R.id.lastname);
//        lastnameedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    lastnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    lastnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//
//        final EditText mobilenumberedittext = (EditText) myFragmentView.findViewById(R.id.mobilenum);
//        mobilenumberedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    mobilenumberedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    mobilenumberedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//
//        final EditText emailedittext = (EditText) myFragmentView.findViewById(R.id.email);
//        emailedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View view, boolean hasfocus) {
//                if (hasfocus) {
//
//                    view.setBackgroundResource(R.drawable.editbox_style);
//                    emailedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);
//
//                } else {
//                    view.setBackgroundResource(R.drawable.spinner_focus);
//                    emailedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);
//
//                }
//            }
//        });
//        selectgender_selection = (SwitchCompat) myFragmentView.findViewById(R.id.selectgender_selection);
//        if (selectgender_selection.isChecked() == false) {
//            gender_value = "Male";
//        } else {
//            gender_value = "Female";
//        }
//        if (flag == 1) {
//            lead_status = (TextView) myFragmentView.findViewById(R.id.lead_status);
//            leadstauslayout = (RelativeLayout) myFragmentView.findViewById(R.id.leadstauslayout);
//            lead_status.setVisibility(View.VISIBLE);
//            leadstauslayout.setVisibility(View.VISIBLE);
//        } else {
//            lead_status = (TextView) myFragmentView.findViewById(R.id.lead_status);
//            leadstauslayout = (RelativeLayout) myFragmentView.findViewById(R.id.leadstauslayout);
//            lead_status.setVisibility(View.GONE);
//            leadstauslayout.setVisibility(View.GONE);
//        }
//        warmbtn = (Button) myFragmentView.findViewById(R.id.warmbtn);
//        coldbtn = (Button) myFragmentView.findViewById(R.id.coldbtn);
//        neutral = (Button) myFragmentView.findViewById(R.id.neutral);
//        warmbtn.setBackground(btn_click);
//        coldbtn.setBackground(btn_unclick);
//        neutral.setBackground(btn_unclick);
//
//        warmbtn.setTextColor(Color.WHITE);
//        coldbtn.setTextColor(Color.BLACK);
//        neutral.setTextColor(Color.BLACK);
//        leadwarmth = "Warm";
//        warmbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                warmbtn.setBackground(btn_click);
//                coldbtn.setBackground(btn_unclick);
//                neutral.setBackground(btn_unclick);
//
//                warmbtn.setTextColor(Color.WHITE);
//                coldbtn.setTextColor(Color.BLACK);
//                neutral.setTextColor(Color.BLACK);
//                leadwarmth = "Warm";
//            }
//        });
//
//        coldbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                coldbtn.setBackground(btn_click);
//                warmbtn.setBackground(btn_unclick);
//                neutral.setBackground(btn_unclick);
//
//                coldbtn.setTextColor(Color.WHITE);
//                warmbtn.setTextColor(Color.BLACK);
//                neutral.setTextColor(Color.BLACK);
//                leadwarmth = "Cold";
//            }
//        });
//
//        neutral.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                neutral.setBackground(btn_click);
//                coldbtn.setBackground(btn_unclick);
//                warmbtn.setBackground(btn_unclick);
//
//                neutral.setTextColor(Color.WHITE);
//                coldbtn.setTextColor(Color.BLACK);
//                warmbtn.setTextColor(Color.BLACK);
//                leadwarmth = "Neutral";
//            }
//        });
//        circleView = (CircleImageView) myFragmentView.findViewById(R.id.circleView);
//        circleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                encodedImage_flag = 1;
//                showPictureDialog();
//                //      encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//            }
//        });
////        additional_detailstxt=additional_details.getText().toString();
//        addnewleadbtn = (Button) myFragmentView.findViewById(R.id.adddetails_lead_save);
//        if (flag == 0) {
//            addnewleadbtn.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onClick(View v) {
//
//                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                    final String firstnametext = firstnameedittext.getText().toString();
//                    final String lastnametext = lastnameedittext.getText().toString();
//                    final String addressSeller_edittext = AddressSelleredittext.getText().toString();
//                    final String mobilenumbertext = mobilenumberedittext.getText().toString();
//                    final String emailtext = emailedittext.getText().toString();
//                    //final String  warmth = leadstatus.toString();
//                    final String category = "Seller";
//                    final String additional_detailsTxt = additional_details.getSelectedItem().toString();
//                    final int userIdTxt = userId;
//                    final String gender_valueTxt = gender_value.toString();
//                    //  String cc=bitmap.toString();
//                    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_2);
//
//                    if ((firstnametext.equals("")) || (lastnametext.equals("")) || (additional_detailsTxt.equals("")) || (mobilenumbertext.equals("")) || (emailtext.equals("")) || (addressSeller_edittext.equals(""))) {
//
//                        Toast.makeText(myFragmentView.getContext(), "Please fill all the mandatoryfields", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (encodedImage_flag == 0) {
//                            encodedImage = "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";
//                        }
//
//
//                        if (emailtext.matches(emailPattern)) {
//
//
//                            // encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//                            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//                            url = "http://202.88.239.14:8169/api/Lead/Createlead";
//                            Map<String, Object> jsonParams = new ArrayMap<>();
//                            jsonParams.put("leadcategory_Type", category);
//                            jsonParams.put("address", addressSeller_edittext);
//                            jsonParams.put("first_name", firstnametext);
//                            jsonParams.put("lastname", lastnametext);
//                            jsonParams.put("Lead_warmth", leadwarmth);
//                            jsonParams.put("userid", userId);
//                            jsonParams.put("email", emailtext);
//                            jsonParams.put("mobileno", mobilenumbertext);
//                            jsonParams.put("gender", gender_valueTxt);
//                            jsonParams.put("hwfindabtus", additional_detailsTxt);
//                            jsonParams.put("base64image", encodedImage);
//
//                            //   loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);
//
//                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                                    new Response.Listener<JSONObject>() {
//                                        @Override
//                                        public void onResponse(JSONObject response) {
//                                            try {
//                                                Log.v("ADDNEW", "" + response);
//                                                Status_missed = response.getString("status").toString().trim();
//                                                message = response.getString("message").toString().trim();
//                                                Lead_ID = response.getInt("leadID");
//                                                SharedPreferences.Editor editor = sharedpreferences.edit();
//                                                editor.putInt("LeadId", Lead_ID);
//                                                editor.commit();
//
//                                                String str3 = "Success";
//                                                int response_result = Status_missed.compareTo(str3);
//                                                if (response_result == 0) {
//                                                    loading.dismiss();
//                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                                                } else {
//                                                    loading.dismiss();
//                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            } catch (JSONException e) {
//                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                                                loading.dismiss();
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    loading.dismiss();
//                                    Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//                                    // do something...
//                                }
//                            }) {
//                                @Override
//                                public Map<String, String> getHeaders() throws AuthFailureError {
//                                    final Map<String, String> headers = new HashMap<>();
//                                    headers.put("Authorization", Token);
//
//                                    return headers;
//                                }
//                            };
//                            loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);
//
//                            requestQueue.add(jsonObjectRequest);
//
//
//                        } else {
//                            Toast.makeText(myFragmentView.getContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                }
//
//            });
//
//        } else {
//
//            sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//            LeadId = sharedpreferences.getInt("LeadId", 0);
//            Token = sharedpreferences.getString("token", "");
//
//            circleView = (CircleImageView) myFragmentView.findViewById(R.id.circleView);
//            firstname = (TextView) myFragmentView.findViewById(R.id.firstname);
//            lastname = (TextView) myFragmentView.findViewById(R.id.lastname);
//            AddressSeller = (TextView) myFragmentView.findViewById(R.id.AddressSeller);
//            mobilenum = (TextView) myFragmentView.findViewById(R.id.mobilenum);
//            email = (TextView) myFragmentView.findViewById(R.id.email);
//            spinner_additional_details = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);
//            newbtn = (Button) myFragmentView.findViewById(R.id.newbtn);
//            incontract = (Button) myFragmentView.findViewById(R.id.incontract);
//            success = (Button) myFragmentView.findViewById(R.id.success);
//            failure = (Button) myFragmentView.findViewById(R.id.failure);
//            neutral = (Button) myFragmentView.findViewById(R.id.neutral);
//            warmbtn = (Button) myFragmentView.findViewById(R.id.warmbtn);
//            coldbtn = (Button) myFragmentView.findViewById(R.id.coldbtn);
//
//            newStatus = (Button) myFragmentView.findViewById(R.id.newStatus);
//            convertedStatus = (Button) myFragmentView.findViewById(R.id.convertedStatus);
//            inactiveStatus = (Button) myFragmentView.findViewById(R.id.inactiveStatus);
//            newStatus.setBackground(btn_click);
//            convertedStatus.setBackground(btn_unclick);
//            inactiveStatus.setBackground(btn_unclick);
//
//            newStatus.setTextColor(Color.WHITE);
//            convertedStatus.setTextColor(Color.BLACK);
//            inactiveStatus.setTextColor(Color.BLACK);
//            leadStatusBtn = "New";
//            newStatus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    newStatus.setBackground(btn_click);
//                    convertedStatus.setBackground(btn_unclick);
//                    inactiveStatus.setBackground(btn_unclick);
//
//                    newStatus.setTextColor(Color.WHITE);
//                    convertedStatus.setTextColor(Color.BLACK);
//                    inactiveStatus.setTextColor(Color.BLACK);
//                    leadStatusBtn = "New";
//                }
//            });
//
//            convertedStatus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    convertedStatus.setBackground(btn_click);
//                    newStatus.setBackground(btn_unclick);
//                    inactiveStatus.setBackground(btn_unclick);
//
//                    convertedStatus.setTextColor(Color.WHITE);
//                    newStatus.setTextColor(Color.BLACK);
//                    inactiveStatus.setTextColor(Color.BLACK);
//                    leadStatusBtn = "Converted";
//                }
//            });
//
//            inactiveStatus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    inactiveStatus.setBackground(btn_click);
//                    convertedStatus.setBackground(btn_unclick);
//                    newStatus.setBackground(btn_unclick);
//
//                    inactiveStatus.setTextColor(Color.WHITE);
//                    convertedStatus.setTextColor(Color.BLACK);
//                    newStatus.setTextColor(Color.BLACK);
//                    leadStatusBtn = "Inactive";
//                }
//            });
//            requestQueueDisplay = Volley.newRequestQueue(getActivity().getApplicationContext());
//
//
//            imageUrl = "http://202.88.239.14:8169/FileUploads/";
//            url = "http://202.88.239.14:8169/api/Lead/GetLeadPersonalDetails/" + LeadId;
//
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                Status_missed = response.getString("status").toString().trim();
//                                JSONArray jsonArray = response.getJSONArray("data");
//
//                                JSONObject data = jsonArray.getJSONObject(0);
//
//                                firstNameTxt = data.getString("firstName");
//                                lastNameTxt = data.getString("lastName");
//                                lead_StatusTxt = data.getString("lead_Status");
//                                lead_CreatedDateTxt = data.getString("lead_CreatedDate");
//                                addressTxt = data.getString("address");
//                                leadProfileimageTxt = data.getString("leadProfileimage");
//                                lead_CategoryTxt = data.getString("lead_Category");
//                                //transfered_Status = data.getString("transfered_Status");
//                                mobileNoTxt = data.getString("mobileNo");
//                                //hwfindabtus = data.getString("hwfindabtus");
//                                emailIDTxt = data.getString("emailID");
//                                //transfered_AgentName = data.getString("transfered_AgentName");
//                                String image = imageUrl + leadProfileimageTxt;
//                                String str3 = "Success";
//                                int response_result = Status_missed.compareTo(str3);
//                                if (response_result == 0) {
//
//                                    firstname.setText(firstNameTxt);
//                                    lastname.setText(lastNameTxt);
//                                    AddressSeller.setText(addressTxt);
//                                    mobilenum.setText(mobileNoTxt);
//                                    email.setText(emailIDTxt);
//                                    Picasso.with(getContext()).load(image).into(circleView);
//
//                                    addnewleadbtn.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//
//                                            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                                            final String firstnametext = firstnameedittext.getText().toString();
//                                            final String lastnametext = lastnameedittext.getText().toString();
//                                            final String addressSeller_edittext = AddressSelleredittext.getText().toString();
//                                            final String mobilenumbertext = mobilenumberedittext.getText().toString();
//                                            final String emailtext = emailedittext.getText().toString();
//                                            //final String  warmth = leadstatus.toString();
//                                            final String category = "Seller";
//                                            final String additional_detailsTxt = additional_details.getSelectedItem().toString();
//                                            final int userIdTxt = userId;
//                                            final String gender_valueTxt = gender_value.toString();
//                                            //  String cc=bitmap.toString();
//                                            // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_2);
//
//                                            if ((firstnametext.equals("")) || (lastnametext.equals("")) || (additional_detailsTxt.equals("")) || (mobilenumbertext.equals("")) || (emailtext.equals("")) || (addressSeller_edittext.equals(""))) {
//
//                                                Toast.makeText(myFragmentView.getContext(), "Please fill all the mandatoryfields", Toast.LENGTH_SHORT).show();
//                                            } else {
////                                                    if (encodedImage_flag == 0) {
////                                                    // default value
////                                                    // encodedImage="";
////                                                }
//
//
//                                                if (emailtext.matches(emailPattern)) {
//
//
//                                                    editedSave = Volley.newRequestQueue(getActivity().getApplicationContext());
//                                                    url = "http://202.88.239.14:8169/api/Lead/UpdateLead";
//                                                    Map<String, Object> jsonParams = new ArrayMap<>();
//                                                    if (selectgender_selection.isChecked() == false) {
//                                                        gender_value = "Male";
//                                                    } else {
//                                                        gender_value = "Female";
//                                                    }
//                                                    String gender_valueTt = gender_value.toString();
//                                                    String leadWarmth = leadwarmth.toString();
//                                                    String leadStatusBtnTxt = leadStatusBtn.toString();
//                                                    jsonParams.put("address", addressSeller_edittext);
//                                                    jsonParams.put("firstname", firstnametext);
//                                                    jsonParams.put("lastname", lastnametext);
//                                                    jsonParams.put("leadId", LeadId);
//                                                    jsonParams.put("mobileno", mobilenumbertext);
//                                                    jsonParams.put("email", emailtext);
//                                                    jsonParams.put("gender", gender_valueTt);
//                                                    // jsonParams.put("leadstatus", mobilenumbertext);
//                                                    jsonParams.put("leadwarmth", leadWarmth);
////                                                    jsonParams.put("leadwarmth", leadWarmth);
//
//                                                    jsonParams.put("userid", 33);
//                                                    jsonParams.put("hwfindabtus", additional_detailsTxt);
//                                                    jsonParams.put("base64image", encodedImage);
//                                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
//                                                            new Response.Listener<JSONObject>() {
//                                                                @Override
//                                                                public void onResponse(JSONObject response) {
//                                                                    try {
//
//                                                                        Status_missed = response.getString("status").toString().trim();
//                                                                        message = response.getString("message").toString().trim();
//                                                                        String str3 = "Success";
//                                                                        //  loading.dismiss();
//                                                                        int response_result = Status_missed.compareTo(str3);
//                                                                        if (response_result == 0) {
//                                                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                                                                        } else {
//
//                                                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                                                                        }
//
//                                                                    } catch (JSONException e) {
//                                                                        e.printStackTrace();
//                                                                    }
//                                                                }
//                                                            }, new Response.ErrorListener() {
//                                                        @Override
//                                                        public void onErrorResponse(VolleyError error) {
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
//                                                    editedSave.add(jsonObjectRequest);
//
//
//                                                } else {
//                                                    Toast.makeText(myFragmentView.getContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//
//
//                                        }
//
//                                    });
//                                } else {
//                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
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
//            requestQueueDisplay.add(jsonObjectRequest);
//
//        }
//
//
//        //ImageView back = (ImageView) myFragmentView.findViewById(R.id.AddLead_Back);
//        //back.setImageResource(R.mipmap.back_arrow);
//
//
//        return myFragmentView;
//    }
//
//    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
//        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
//        image.compress(compressFormat, quality, byteArrayOS);
//        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
//    }
//
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this.getActivity());
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//
//
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//
//    private void takePhotoFromCamera() {
//
//        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
//
//
//            } else {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
//            }
//
//        }
//        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    circleView.setImageBitmap(bitmap);
//                    encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//                    Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Failed to Update Image!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            bitmap = (Bitmap) data.getExtras().get("data");
//            circleView.setImageBitmap(bitmap);
//            encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//            Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();
//
//
//        }
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
//}
package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nexgensm.reswye.ui.Dashboard.DormantItems;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.MediaRecorder.VideoSource.CAMERA;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewLeadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewLeadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewLeadFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private static final int RESULT_OK = 1;
    private static final int RESULT_CANCELED = 0;
    private String mParam1;
    private String mParam2;
    Bitmap bitmap;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    Drawable btn_click;
    Drawable btn_unclick;
    Context context;
    String image;
    ImageView picview;
    Spinner spinneradditionaldetails;
    String[] additionaldetails;
    CircleImageView circleView;
    int flag, Lead_ID;
    TextView firstname, lastname, AddressSeller, mobilenum, email;
    Spinner spinner_additional_details;
    Button newbtn, incontract, success, failure;
    String gender_value, status, firstnametext, lastnametext, mobilenumbertext, hwfindabtusTxt, leadStatusBtn, message, encodedImage, leadwarmth;
    TextView lead_status;
    String category, additional_detailsTxt;
    SwitchCompat selectgender_selection;
    LinearLayout leadstauslayout;
    Button warmbtn, coldbtn, neutral, newStatus, convertedStatus, inactiveStatus, addnewleadbtn;
    Spinner additional_details;
    RequestQueue requestQueue, requestQueueDisplay, editedSave;
    String result;
    String firstNameTxt, ImageUrl,lastNameTxt, lead_StatusTxt, lead_CreatedDateTxt, addressTxt, leadProfileimageTxt, lead_CategoryTxt, mobileNoTxt, emailIDTxt;
    String Token, Status_missed, name1, address, appointment_Date, leadstatus;
    String url, lead_warmthTxt, imageUrl, transfered_StatusTxt;
    int userId, lead_ID, encodedImage_flag = 0, LeadId,leadCategory_flag;
    SharedPreferences sharedpreferences;
    ProgressDialog loading;
    public static final String mypreference = "mypref";

    public AddNewLeadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewLeadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewLeadFragment newInstance(String param1, String param2) {
        AddNewLeadFragment fragment = new AddNewLeadFragment();
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
        final View myFragmentView = inflater.inflate(R.layout.fragment_add_new_lead, container, false);
        spinneradditionaldetails = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);

        btn_click = getResources().getDrawable(R.drawable.add_new_btn_click);
        btn_unclick = getResources().getDrawable(R.drawable.add_new_btn);


        spinneradditionaldetails.setSelection(0, true);
        additionaldetails = getResources().getStringArray(R.array.AddNewLead);
        additional_details = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);
//        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        flag = sharedpreferences.getInt("flag", 0);
//        userId = sharedpreferences.getInt("UserId", 0);
//        Token = sharedpreferences.getString("token", "");
        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        leadCategory_flag=sharedpreferences.getInt("leadCategory_flag",0);
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        LeadId = sharedpreferences.getInt("LeadId", 1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, additionaldetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneradditionaldetails.setAdapter(adapter);


        final EditText firstnameedittext = (EditText) myFragmentView.findViewById(R.id.firstname);
        firstnameedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    firstnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    firstnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);

                }
            }
        });
        final EditText AddressSelleredittext = (EditText) myFragmentView.findViewById(R.id.AddressSeller);
        AddressSelleredittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    AddressSelleredittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    AddressSelleredittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);

                }
            }
        });
        final EditText lastnameedittext = (EditText) myFragmentView.findViewById(R.id.lastname);
        lastnameedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    lastnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    lastnameedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);

                }
            }
        });

        final EditText mobilenumberedittext = (EditText) myFragmentView.findViewById(R.id.mobilenum);
        mobilenumberedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    mobilenumberedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    mobilenumberedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);

                }
            }
        });

        final EditText emailedittext = (EditText) myFragmentView.findViewById(R.id.email);
        emailedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                    emailedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star_error, 0);

                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                    emailedittext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.mandadatory_star, 0);

                }
            }
        });
        selectgender_selection = (SwitchCompat) myFragmentView.findViewById(R.id.selectgender_selection);
        if (selectgender_selection.isChecked() == false) {
            gender_value = "Male";
        } else {
            gender_value = "Female";
        }
        if (flag == 1) {
            lead_status = (TextView) myFragmentView.findViewById(R.id.lead_status);
            leadstauslayout = (LinearLayout) myFragmentView.findViewById(R.id.leadstauslayout);
            lead_status.setVisibility(View.VISIBLE);
            leadstauslayout.setVisibility(View.VISIBLE);
        } else {
            lead_status = (TextView) myFragmentView.findViewById(R.id.lead_status);
            leadstauslayout = (LinearLayout) myFragmentView.findViewById(R.id.leadstauslayout);
            lead_status.setVisibility(View.GONE);
            leadstauslayout.setVisibility(View.GONE);
        }
        warmbtn = (Button) myFragmentView.findViewById(R.id.warmbtn);
        coldbtn = (Button) myFragmentView.findViewById(R.id.coldbtn);
        neutral = (Button) myFragmentView.findViewById(R.id.neutral);
        warmbtn.setBackground(btn_click);
        coldbtn.setBackground(btn_unclick);
        neutral.setBackground(btn_unclick);

        warmbtn.setTextColor(Color.WHITE);
        coldbtn.setTextColor(Color.BLACK);
        neutral.setTextColor(Color.BLACK);
        leadwarmth = "Warm";
        warmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warmbtn.setBackground(btn_click);
                coldbtn.setBackground(btn_unclick);
                neutral.setBackground(btn_unclick);

                warmbtn.setTextColor(Color.WHITE);
                coldbtn.setTextColor(Color.BLACK);
                neutral.setTextColor(Color.BLACK);
                leadwarmth = "Warm";
            }
        });

        coldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coldbtn.setBackground(btn_click);
                warmbtn.setBackground(btn_unclick);
                neutral.setBackground(btn_unclick);

                coldbtn.setTextColor(Color.WHITE);
                warmbtn.setTextColor(Color.BLACK);
                neutral.setTextColor(Color.BLACK);
                leadwarmth = "Cold";
            }
        });

        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neutral.setBackground(btn_click);
                coldbtn.setBackground(btn_unclick);
                warmbtn.setBackground(btn_unclick);

                neutral.setTextColor(Color.WHITE);
                coldbtn.setTextColor(Color.BLACK);
                warmbtn.setTextColor(Color.BLACK);
                leadwarmth = "Neutral";
            }
        });
        circleView = (CircleImageView) myFragmentView.findViewById(R.id.circleView);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodedImage_flag = 1;
                showPictureDialog();

            }
        });
        addnewleadbtn = (Button) myFragmentView.findViewById(R.id.adddetails_lead_save);
        if (flag == 0) {
           ////////////////////////// ADD NEW LEAD////////////////////////
            addnewleadbtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    final String firstnametext = firstnameedittext.getText().toString();
                    final String lastnametext = lastnameedittext.getText().toString();
                    final String addressSeller_edittext = AddressSelleredittext.getText().toString();
                    final String mobilenumbertext = mobilenumberedittext.getText().toString();
                    final String emailtext = emailedittext.getText().toString();
                    int position= additional_details.getSelectedItemPosition();
                   if(position!=0){
                       additional_detailsTxt = additional_details.getSelectedItem().toString();
                   }
                   else{
                       additional_detailsTxt = "";

                   }
                    final int userIdTxt = userId;
                    final String gender_valueTxt = gender_value.toString();
                    //final String  warmth = leadstatus.toString();
                    if(leadCategory_flag==0){
                        category = "Buyer";
                    }
                    else{
                         category = "Seller";
                    }


                    if ((firstnametext.equals("")) || (lastnametext.equals("")) || (emailtext.equals("")) || (additional_detailsTxt.equals("")) || (mobilenumbertext.equals("")) || (emailtext.equals("")) || (leadwarmth.equals(""))) {

                        Toast.makeText(myFragmentView.getContext(), "Please fill all the mandatoryfields", Toast.LENGTH_SHORT).show();
                    } else {

  if (encodedImage_flag == 0) {///////Default base64 image/////////////////////////
   encodedImage = "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";
                        }

                        if (mobilenumbertext.length() != 10) {
                            Toast.makeText(getActivity(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                        if (emailtext.matches(emailPattern)) {

                            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            url = "http://202.88.239.14:8169/api/Lead/Createlead";
                            Map<String, Object> jsonParams = new ArrayMap<>();
                            jsonParams.put("leadcategory_Type", category);
                            jsonParams.put("address", addressSeller_edittext);
                            jsonParams.put("first_name", firstnametext);
                            jsonParams.put("lastname", lastnametext);
                            jsonParams.put("Lead_warmth", leadwarmth);
                            jsonParams.put("userid", userId);
                            jsonParams.put("email", emailtext);
                            jsonParams.put("mobileno", mobilenumbertext);
                            jsonParams.put("gender", gender_valueTxt);
                            jsonParams.put("hwfindabtus", additional_detailsTxt);
                            jsonParams.put("base64image", encodedImage);
                            loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.v("ADDNEW", "" + response);
                                                Status_missed = response.getString("status").toString().trim();
                                                message = response.getString("message").toString().trim();
                                                Lead_ID = response.getInt("leadID");
                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                editor.putInt("LeadId", Lead_ID);
                                                editor.commit();

                                                String str3 = "Success";
                                                int response_result = Status_missed.compareTo(str3);
                                                if (response_result == 0) {
                                                    loading.dismiss();
                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                                } else {
                                                    loading.dismiss();
                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (JSONException e) {
                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                loading.dismiss();
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

                            requestQueue.add(jsonObjectRequest);


                        } else {
                            Toast.makeText(myFragmentView.getContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                        }
                    }


                }

            });

        } else {

            sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            LeadId = sharedpreferences.getInt("LeadId", 0);
            Token = sharedpreferences.getString("token", "");

            circleView = (CircleImageView) myFragmentView.findViewById(R.id.circleView);
            firstname = (TextView) myFragmentView.findViewById(R.id.firstname);
            lastname = (TextView) myFragmentView.findViewById(R.id.lastname);
            AddressSeller = (TextView) myFragmentView.findViewById(R.id.AddressSeller);
            mobilenum = (TextView) myFragmentView.findViewById(R.id.mobilenum);
            email = (TextView) myFragmentView.findViewById(R.id.email);
            spinner_additional_details = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);
            newbtn = (Button) myFragmentView.findViewById(R.id.newbtn);
            incontract = (Button) myFragmentView.findViewById(R.id.incontract);
            success = (Button) myFragmentView.findViewById(R.id.success);
            failure = (Button) myFragmentView.findViewById(R.id.failure);
            neutral = (Button) myFragmentView.findViewById(R.id.neutral);
            warmbtn = (Button) myFragmentView.findViewById(R.id.warmbtn);
            coldbtn = (Button) myFragmentView.findViewById(R.id.coldbtn);

            newStatus = (Button) myFragmentView.findViewById(R.id.newStatus);
            convertedStatus = (Button) myFragmentView.findViewById(R.id.convertedStatus);
            inactiveStatus = (Button) myFragmentView.findViewById(R.id.inactiveStatus);
            newStatus.setBackground(btn_click);
            convertedStatus.setBackground(btn_unclick);
            inactiveStatus.setBackground(btn_unclick);

            newStatus.setTextColor(Color.WHITE);
            convertedStatus.setTextColor(Color.BLACK);
            inactiveStatus.setTextColor(Color.BLACK);
            leadStatusBtn = "New";
            newStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newStatus.setBackground(btn_click);
                    convertedStatus.setBackground(btn_unclick);
                    inactiveStatus.setBackground(btn_unclick);

                    newStatus.setTextColor(Color.WHITE);
                    convertedStatus.setTextColor(Color.BLACK);
                    inactiveStatus.setTextColor(Color.BLACK);
                    leadStatusBtn = "New";
                }
            });

            convertedStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertedStatus.setBackground(btn_click);
                    newStatus.setBackground(btn_unclick);
                    inactiveStatus.setBackground(btn_unclick);

                    convertedStatus.setTextColor(Color.WHITE);
                    newStatus.setTextColor(Color.BLACK);
                    inactiveStatus.setTextColor(Color.BLACK);
                    leadStatusBtn = "Success";
                }
            });

            inactiveStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inactiveStatus.setBackground(btn_click);
                    convertedStatus.setBackground(btn_unclick);
                    newStatus.setBackground(btn_unclick);

                    inactiveStatus.setTextColor(Color.WHITE);
                    convertedStatus.setTextColor(Color.BLACK);
                    newStatus.setTextColor(Color.BLACK);
                    leadStatusBtn = "Failure";
                }
            });
            requestQueueDisplay = Volley.newRequestQueue(getActivity().getApplicationContext());

        ///////////////////EDIT ---> LEAD TEXT DISPLAY/////////////////

            //imageUrl = "http://202.88.239.14:8169/FileUploads/";
            url = "http://202.88.239.14:8169/api/Lead/GetLeadPersonalDetails/" + LeadId;


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("wamth", "" + response.toString());
                            try {
                                Status_missed = response.getString("status").toString().trim();
                                JSONArray jsonArray = response.getJSONArray("data");

                                JSONObject data = jsonArray.getJSONObject(0);

                                firstNameTxt = data.getString("firstName");
                                lastNameTxt = data.getString("lastName");
                                lead_StatusTxt = data.getString("lead_Status");
                                lead_warmthTxt = data.getString("lead_Warmth");
                                lead_warmthTxt = data.getString("lead_Warmth");
                                lead_CreatedDateTxt = data.getString("lead_CreatedDate");
                                addressTxt = data.getString("address");
                                leadProfileimageTxt = data.getString("leadProfileimage");
                                lead_CategoryTxt = data.getString("lead_Category");
                                // transfered_StatusTxt = data.getString("transfered_Status");
                                mobileNoTxt = data.getString("mobileNo");
                                hwfindabtusTxt = data.getString("hwfindabtus");
                                emailIDTxt = data.getString("emailID");
                                //transfered_AgentName = data.getString("transfered_AgentName");
                                image = ImageUrl + leadProfileimageTxt;
                                String str3 = "Success";
                                int response_result = Status_missed.compareTo(str3);
                                if (response_result == 0) {

                                    firstname.setText(firstNameTxt);
                                    lastname.setText(lastNameTxt);
                                    AddressSeller.setText(addressTxt);
                                    mobilenum.setText(mobileNoTxt);
                                    email.setText(emailIDTxt);
                                    int position = adapter.getPosition(hwfindabtusTxt);
                                    additional_details.setSelection(position);
                                    String WarmStatus = "Warm";
                                    String ColdStatus = "Cold";
                                    String NeutralStatus = "Neutral";

                                    String NewStatus = "New";
                                    String ConvertedStatus = "Success";
                                    String InactiveStatus = "Failure";
                                    if (lead_warmthTxt.compareTo(WarmStatus) == 0) {
                                        warmbtn.setBackground(btn_click);
                                        coldbtn.setBackground(btn_unclick);
                                        neutral.setBackground(btn_unclick);

                                        warmbtn.setTextColor(Color.WHITE);
                                        coldbtn.setTextColor(Color.BLACK);
                                        neutral.setTextColor(Color.BLACK);
                                        leadwarmth = "Warm";
                                    }
                                    if (lead_warmthTxt.compareTo(ColdStatus) == 0) {
                                        warmbtn.setBackground(btn_unclick);
                                        coldbtn.setBackground(btn_click);
                                        neutral.setBackground(btn_unclick);

                                        warmbtn.setTextColor(Color.BLACK);
                                        coldbtn.setTextColor(Color.WHITE);
                                        neutral.setTextColor(Color.BLACK);
                                        leadwarmth = "Cold";
                                    }
                                    if (lead_warmthTxt.compareTo(NeutralStatus) == 0) {
                                        warmbtn.setBackground(btn_unclick);
                                        coldbtn.setBackground(btn_unclick);
                                        neutral.setBackground(btn_click);

                                        warmbtn.setTextColor(Color.BLACK);
                                        coldbtn.setTextColor(Color.BLACK);
                                        neutral.setTextColor(Color.WHITE);
                                        leadwarmth = "Neutral";
                                    }


                                    if (lead_StatusTxt.compareTo(NewStatus) == 0) {
                                        newStatus.setBackground(btn_click);
                                        convertedStatus.setBackground(btn_unclick);
                                        inactiveStatus.setBackground(btn_unclick);

                                        newStatus.setTextColor(Color.WHITE);
                                        convertedStatus.setTextColor(Color.BLACK);
                                        inactiveStatus.setTextColor(Color.BLACK);
                                        leadStatusBtn = "New";
                                    }
                                    if (lead_StatusTxt.compareTo(ConvertedStatus) == 0) {
                                        newStatus.setBackground(btn_unclick);
                                        convertedStatus.setBackground(btn_click);
                                        inactiveStatus.setBackground(btn_unclick);

                                        newStatus.setTextColor(Color.BLACK);
                                        convertedStatus.setTextColor(Color.WHITE);
                                        inactiveStatus.setTextColor(Color.BLACK);
                                        leadStatusBtn = "Success";
                                    }
                                    if (lead_StatusTxt.compareTo(InactiveStatus) == 0) {
                                        newStatus.setBackground(btn_unclick);
                                        convertedStatus.setBackground(btn_unclick);
                                        inactiveStatus.setBackground(btn_click);

                                        newStatus.setTextColor(Color.BLACK);
                                        convertedStatus.setTextColor(Color.BLACK);
                                        inactiveStatus.setTextColor(Color.WHITE);
                                        leadStatusBtn = "Failure";
                                    }

                                    Picasso.with(getContext()).load(image).into(circleView);

                                    addnewleadbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

///////////////////////////////////////// EDIT ---> UPDATE LEAD /////////////////////////////////////

                                            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                                            final String firstnametext = firstnameedittext.getText().toString();
                                            final String lastnametext = lastnameedittext.getText().toString();
                                            final String addressSeller_edittext = AddressSelleredittext.getText().toString();
                                            final String mobilenumbertext = mobilenumberedittext.getText().toString();
                                            final String emailtext = emailedittext.getText().toString();
                                            //final String  warmth = leadstatus.toString();
                                            if(leadCategory_flag==0){
                                                category = "Buyer";
                                            }
                                            else {
                                                 category = "Seller";
                                            }

                                            final String additional_detailsTxt = additional_details.getSelectedItem().toString();
                                            final int userIdTxt = userId;
                                            final String gender_valueTxt = gender_value.toString();

                                            if ((firstnametext.equals("")) || (lastnametext.equals("")) || (additional_detailsTxt.equals("")) || (mobilenumbertext.equals("")) || (emailtext.equals(""))) {

                                                Toast.makeText(myFragmentView.getContext(), "Please fill all the mandatoryfields", Toast.LENGTH_SHORT).show();
                                            }
                                            if (mobilenumbertext.length() != 10) {
                                                Toast.makeText(getActivity(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();

                                            }
                                            if (emailtext.matches(emailPattern)) {


                                                editedSave = Volley.newRequestQueue(getActivity().getApplicationContext());
                                                url = "http://202.88.239.14:8169/api/Lead/UpdateLead";
                                                Map<String, Object> jsonParams = new ArrayMap<>();
                                                if (selectgender_selection.isChecked() == false) {
                                                    gender_value = "Male";
                                                } else {
                                                    gender_value = "Female";
                                                }
                                                String gender_valueTt = gender_value.toString();
                                                String leadWarmthValue = leadwarmth.toString();
                                                String leadStatusBtnTxt = leadStatusBtn.toString();
                                                jsonParams.put("address", addressSeller_edittext);
                                                jsonParams.put("firstname", firstnametext);
                                                jsonParams.put("lastname", lastnametext);
                                                jsonParams.put("leadId", LeadId);
                                                jsonParams.put("mobileno", mobilenumbertext);
                                                jsonParams.put("email", emailtext);
                                                jsonParams.put("gender", gender_valueTt);
                                                jsonParams.put("leadstatus", leadStatusBtnTxt);
                                                jsonParams.put("leadwarmth", leadWarmthValue);
                                                jsonParams.put("userid", userId);
                                                jsonParams.put("hwfindabtus", additional_detailsTxt);
                                                jsonParams.put("base64image", encodedImage);
                                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                try {

                                                                    Status_missed = response.getString("status").toString().trim();
                                                                    message = response.getString("message").toString().trim();
                                                                    String str3 = "Success";
                                                                    //  loading.dismiss();
                                                                    int response_result = Status_missed.compareTo(str3);
                                                                    if (response_result == 0) {
                                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                                                    } else {

                                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

                                                editedSave.add(jsonObjectRequest);


                                            } else {
                                                Toast.makeText(myFragmentView.getContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                    });
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
            requestQueueDisplay.add(jsonObjectRequest);

        }


        //ImageView back = (ImageView) myFragmentView.findViewById(R.id.AddLead_Back);
        //back.setImageResource(R.mipmap.back_arrow);


        return myFragmentView;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this.getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();


    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            }

        }
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    circleView.setImageBitmap(bitmap);
                    encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

                    Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed to Update Image!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            circleView.setImageBitmap(bitmap);
            encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

            Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();


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
