package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalDetailsBuyerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    int LeadId,flag;
    Bitmap mIcon_val;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView circleView;
    String type="";
    ImageView deletebin,edit_icon,transfer_icon;
    RequestQueue requestQueue;
    String Token,Status_count,name1,address,imageUrl,url,Status_missed,hwfindabtusTxt;
    int userId;
    String firstName,lastName,transferedStatus,transferedAgentName,lead_Status,lead_CreatedDate,leadProfileimage,lead_Category,mobileNo,emailID,ImageUrl,profileimage;
    private PersonalDetailsFragment.OnFragmentInteractionListener mListener;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView leadpersonal_name,leadpersonal_date,leadpersonal_transfer_statusTxt,leadpersonal_transferred_toTxt,leadpersonal_address,leadpersonal_phone_txt,leadpersonal_mail_txt,leadpersonal_find;
    public PersonalDetailsBuyerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalDetailsBuyerFragment newInstance(String param1, String param2) {
        PersonalDetailsBuyerFragment fragment = new PersonalDetailsBuyerFragment();
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
        final View myFragmentView=inflater.inflate(R.layout.fragment_personal_details_buyer, container, false);


        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        type = getArguments().getString("type");
        Toast.makeText(getActivity(), "type "+type, Toast.LENGTH_SHORT).show();
        circleView=(CircleImageView)myFragmentView.findViewById(R.id.circleView);
        leadpersonal_name=(TextView)myFragmentView.findViewById(R.id.leadpersonal_name);
        leadpersonal_date=(TextView)myFragmentView.findViewById(R.id.leadpersonal_date);
        leadpersonal_address=(TextView)myFragmentView.findViewById(R.id.leadpersonal_address);
        leadpersonal_phone_txt=(TextView)myFragmentView.findViewById(R.id.leadpersonal_phone_txt);
        leadpersonal_mail_txt=(TextView)myFragmentView.findViewById(R.id.leadpersonal_mail_txt);
        leadpersonal_find=(TextView)myFragmentView.findViewById(R.id.leadpersonal_find_about_us);
        leadpersonal_transfer_statusTxt=(TextView)myFragmentView.findViewById(R.id.leadpersonal_transfer_status);
        leadpersonal_transferred_toTxt=(TextView)myFragmentView.findViewById(R.id.leadpersonal_transferred_to);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        LeadId=SharedPrefsUtils.getInstance(getActivity()).getLId();
        Toast.makeText(getActivity(), "Lead Id "+LeadId, Toast.LENGTH_SHORT).show();
        imageUrl=ApiClient.BASE_URL_IMG;
        url = "http://192.168.0.3:3000/reswy/leadlistpersonal/"+LeadId;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Status_missed = response.getString("status").toString().trim();

                            if(Status_missed.equals("success"))
                            {

                                JSONArray jsonArray=response.getJSONArray("result");

                                JSONObject data = jsonArray.getJSONObject(0);

                                firstName = data.getString("firstname");
                                lastName = data.getString("lastname");
                                lead_Status = data.getString("lead_statusid");
                                lead_CreatedDate = data.getString("lead_createddate");
                                address = data.getString("address");
                                leadProfileimage = data.getString("leadprofileimage");
                                lead_Category = data.getString("lead_category");
                                transferedStatus = data.getString("transfered_status");
                                mobileNo = data.getString("mobileno");
                                hwfindabtusTxt = data.getString("hwfindabtus");
                                emailID = data.getString("emailid");
                                //   transferedAgentName = data.getString("transfered_AgentName");
                                String image=imageUrl+leadProfileimage;
                                Log.e("33333","url "+image);

                                leadpersonal_name.setText(firstName);
                                leadpersonal_date.setText(lead_CreatedDate);
                                leadpersonal_address.setText(address);
                                leadpersonal_phone_txt.setText(mobileNo);
                                leadpersonal_mail_txt.setText(emailID);
                                leadpersonal_find.setText(hwfindabtusTxt);
                                leadpersonal_transfer_statusTxt.setText(transferedStatus);

                                Picasso.with(getActivity()).load(image).into(circleView);

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("refresh",0);
        editor.commit();
        deletebin=(ImageView)myFragmentView.findViewById(R.id.deletebin);
        transfer_icon=(ImageView)myFragmentView.findViewById(R.id.transfer_icon);
        edit_icon=(ImageView)myFragmentView.findViewById(R.id.edit_icon);

        deletebin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selleractivity = new Intent(getActivity(), PersonalDetailDeleteActivity.class);
                getActivity().startActivity(selleractivity);
            }
        });
        transfer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selleractivity = new Intent(getActivity(), PersonalDetailTransferActivity.class);
                getActivity().startActivity(selleractivity);
            }
        });
        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefsUtils.getInstance(getActivity()).setFlag(1);
                Intent buyeractivity = new Intent(getActivity(), AddNewBuyerActivity.class);
                getActivity().startActivity(buyeractivity);
            }
        });

        return myFragmentView;

        //return inflater.inflate(R.layout.fragment_personal_details_seller, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

