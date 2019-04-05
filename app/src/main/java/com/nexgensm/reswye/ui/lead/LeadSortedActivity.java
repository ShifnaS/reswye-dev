package com.nexgensm.reswye.ui.lead;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Singleton;
import com.nexgensm.reswye.util.Message;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LeadSortedActivity extends AppCompatActivity {
    private List<LeadSortedList> leadSortedLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeadSortedAdapter leadSortedAdapter;
    View view;
    String FieldName, OrderType;
    Button save;
    int radiochecked, sortCheck;
    private RadioGroup radioGroup;
    private RadioButton nameAtoZ, nameZtoA, dateOldToNew, dateNewToOld, priceL2H, priceH2L, yearOldtoNew, yearNewtoOld, sqftL2H, sqftH2L;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_sorted);

        nameAtoZ = (RadioButton) findViewById(R.id.radioButton1);
        nameZtoA = (RadioButton) findViewById(R.id.radioButton2);
        dateOldToNew = (RadioButton) findViewById(R.id.radioButton3);
        dateNewToOld = (RadioButton) findViewById(R.id.radioButton4);
        priceL2H = (RadioButton) findViewById(R.id.radioButton5);
        priceH2L = (RadioButton) findViewById(R.id.radioButton6);
        yearOldtoNew = (RadioButton) findViewById(R.id.radioButton7);
        yearNewtoOld = (RadioButton) findViewById(R.id.radioButton8);
        sqftL2H = (RadioButton) findViewById(R.id.radioButton9);
        sqftH2L = (RadioButton) findViewById(R.id.radioButton10);
        radiochecked = Singleton.getInstance().getSortboxcheck();
        sortCheck = Singleton.getInstance().getSortSaveFlag();
        if (sortCheck == 1)
            radiochecked = Singleton.getInstance().getSortboxcheck();
        else
            radiochecked = 1;

        switch (radiochecked){

            case 1:
                nameAtoZ.setChecked(true);
                break;
            case 2:
                nameZtoA.setChecked(true);
                break;
            case 3:
                dateOldToNew.setChecked(true);
                break;
            case 4:
                dateNewToOld.setChecked(true);
                break;
            case 5:
                priceL2H.setChecked(true);
                break;
            case 6:
                priceH2L.setChecked(true);
                break;
            case 7:
                yearOldtoNew.setChecked(true);
                break;
            case 8:
                yearNewtoOld.setChecked(true);
                break;
            case 9:
                sqftL2H.setChecked(true);
                break;
            case 10:
                sqftH2L.setChecked(true);
                break;
            default:
                    break;

        }


        save = (Button) findViewById(R.id.sortSavebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameAtoZ.isChecked()) {
                    radiochecked = 1;
                    FieldName = "FirstName";
                    OrderType = "Ascending";
                } else if (nameZtoA.isChecked()) {
                    radiochecked = 2;
                    FieldName = "FirstName";
                    OrderType = "Descending";
                } else if (dateOldToNew.isChecked()) {
                    radiochecked = 3;
                    FieldName = "Date";
                    OrderType = "Ascending";
                } else if (dateNewToOld.isChecked()) {
                    radiochecked = 4;
                    FieldName = "Date";
                    OrderType = "Descending";
                } else if (priceL2H.isChecked()) {
                    radiochecked = 5;
                    FieldName = "MinPrice";
                    OrderType = "Ascending";
                } else if (priceH2L.isChecked()) {
                    radiochecked = 6;
                    FieldName = "MaxPrice";
                    OrderType = "Descending";
                } else if (yearOldtoNew.isChecked()) {
                    radiochecked = 7;
                    FieldName = "YearStart";
                    OrderType = "Ascending";
                } else if (yearNewtoOld.isChecked()) {
                    radiochecked = 8;
                    FieldName = "YearEnd";
                    OrderType = "Descending";
                } else if (sqftL2H.isChecked()) {
                    radiochecked = 9;
                    FieldName = "SqftMin";
                    OrderType = "Ascending";
                } else if (sqftH2L.isChecked()) {
                    radiochecked = 10;
                    FieldName = "SqftMax";
                    OrderType = "Descending";
                }
                sharedPreferences = getApplication().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
               // EventBus.getDefault().post(new Message("hello world",1));
                EventBus.getDefault().postSticky(new Message("sort",radiochecked));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("sortFlag", 1);
              //  Intent i=new Intent(getApplicationContext(),LeadSortedActivity.class);
               // startActivity(i);
                // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show(); // print the value of selected super star
                finish();
            }
        });


    }


}
