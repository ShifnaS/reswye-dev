package com.nexgensm.reswye.ui.lead;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.onboarding.OnBoardingActivity;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.ui.signupactivity.SignupActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    private OnFragmentInteractionListener mListener;
    String[] propertytype;
    ImageView yearImgNeg,yearImgPos,yearImgNegMin,yearImgPosMax;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
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
        Spinner spinnerpropertytype = (Spinner) myFragmentView.findViewById(R.id.spinnerpropertytype);

        spinnerpropertytype.setSelection(0,true);
        propertytype = getResources().getStringArray(R.array.Property_Type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, propertytype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpropertytype.setAdapter(adapter);
        TextView featurestxt = (TextView) myFragmentView.findViewById(R.id.featurestxt);
        TextView additionalpropchartxt = (TextView) myFragmentView.findViewById(R.id.additionalpropertychartxt);

        final  TextView bedCount = (TextView)myFragmentView.findViewById(R.id.bed_number);
        ImageView bedImgNeg = (ImageView)myFragmentView.findViewById(R.id.bed_negative);
        ImageView bedImgPos = (ImageView)myFragmentView.findViewById(R.id.bed_positive);

        final  TextView bathCount = (TextView)myFragmentView.findViewById(R.id.bath_number);
        ImageView bathImgNeg = (ImageView)myFragmentView.findViewById(R.id.bath_negative);
        ImageView bathImgPos = (ImageView)myFragmentView.findViewById(R.id.bath_positive);

        final  TextView YearcountMin = (TextView)myFragmentView.findViewById(R.id.year_txt_min);
        final  TextView YearcountMax = (TextView)myFragmentView.findViewById(R.id.year_txt_max);
         yearImgNeg = (ImageView)myFragmentView.findViewById(R.id.year_negative_min);
         yearImgPos = (ImageView)myFragmentView.findViewById(R.id.year_positive_min);
         yearImgNegMin = (ImageView)myFragmentView.findViewById(R.id.year_negative_max);
         yearImgPosMax = (ImageView)myFragmentView.findViewById(R.id.year_positive_max);

        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        flag=sharedpreferences.getInt("flag",0);
        String str = Integer.toString(flag);
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
//        if (getArguments() != null) {
//            flag = getArguments().getInt("flag");
//        }
        if(flag==1) {
            relative_lyt_logged=(RelativeLayout)myFragmentView.findViewById(R.id.relative_lyt_logged);
            relative_lyt_logged.setVisibility(View.VISIBLE);
        }
        else{
            relative_lyt_logged=(RelativeLayout)myFragmentView.findViewById(R.id.relative_lyt_logged);
            relative_lyt_logged.setVisibility(View.GONE);
        }


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
        Dateviewed=(EditText)myFragmentView.findViewById(R.id.DateViewed);
        propertyId=(EditText)myFragmentView.findViewById(R.id.PropertyId);
        LeadComments=(EditText)myFragmentView.findViewById(R.id.LeadComments);

        ImageView AddViewed=(ImageView)myFragmentView.findViewById(R.id.AddViewed);

        AddViewed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dateviewed.setText("");
                propertyId.setText("");
                LeadComments.setText("");
            }

        });

        final EditText preffered_location=(EditText) myFragmentView.findViewById(R.id.Preffered_location);
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
        final EditText Min_price=(EditText) myFragmentView.findViewById(R.id.min_price);
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
        final EditText Max_price=(EditText) myFragmentView.findViewById(R.id.max_price);
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
        final EditText Min_sqrft=(EditText) myFragmentView.findViewById(R.id.min_sqrft);
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
        final EditText Max_sqrft=(EditText) myFragmentView.findViewById(R.id.max_sqrft);
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


return myFragmentView;

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
