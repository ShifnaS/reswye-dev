package com.nexgensm.reswye.ui.bottomtabbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.Dashboard.DashboardFragment;
import com.nexgensm.reswye.ui.calendar.CalendarFragment;
import com.nexgensm.reswye.ui.lead.AddNewLeadCategoryActivity;
import com.nexgensm.reswye.ui.lead.LeadFragment;
import com.nexgensm.reswye.ui.lead.LeadItems;
import com.nexgensm.reswye.ui.lead.LeadRecyclerViewAdapter;
import com.nexgensm.reswye.ui.property.PropertyFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BottomTabbarActivity extends AppCompatActivity implements LeadFragment.OnFragmentInteractionListener,DashboardFragment.OnFragmentInteractionListener,CalendarFragment.OnFragmentInteractionListener {
    LeadFragment leadFragment = new LeadFragment();
    PropertyFragment propertyFragment = new PropertyFragment();

    CalendarFragment calendarFragment = new CalendarFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    FragmentManager fragmentManager = getFragmentManager();
    LeadRecyclerViewAdapter adapter ;
    private List<LeadItems> leadItemsList = new ArrayList<>();

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    item.setIcon(R.drawable.dashboard_click);
                    //mTextMessage.setText(R.string.title_dashboard);
                    FragmentTransaction fragmentTransaction_nav = fragmentManager.beginTransaction();
                    fragmentTransaction_nav.replace(R.id.framelayout, dashboardFragment);
                    fragmentTransaction_nav.addToBackStack(null);
                    fragmentTransaction_nav.commit();
                    return true;

                case R.id.navigation_lead:
                    item.setIcon(R.drawable.lead_click);
                    FragmentTransaction fragmentTransaction_lead = fragmentManager.beginTransaction();
                    fragmentTransaction_lead.replace(R.id.framelayout, leadFragment);
                    fragmentTransaction_lead.addToBackStack(null);
                    fragmentTransaction_lead.commit();
                    return true;



               case R.id.navigation_properties:
                   item.setIcon(R.drawable.properties_click);
                   FragmentTransaction fragmentTransaction_property = fragmentManager.beginTransaction();
                   fragmentTransaction_property.replace(R.id.framelayout, propertyFragment);
                   fragmentTransaction_property.addToBackStack(null);
                   fragmentTransaction_property.commit();
                   return true;

                case R.id.navigation_calendar:
                    item.setIcon(R.drawable.calender_click);
                    FragmentTransaction fragmentTransaction_cal = fragmentManager.beginTransaction();
                    fragmentTransaction_cal.replace(R.id.framelayout, calendarFragment);
                    fragmentTransaction_cal.addToBackStack(null);
                    fragmentTransaction_cal.commit();
                    return true;


            }
            return false;
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabbar);

        adapter = new LeadRecyclerViewAdapter();

        FragmentTransaction fragmentTransaction_nav = fragmentManager.beginTransaction();
        fragmentTransaction_nav.replace(R.id.framelayout, dashboardFragment);
        fragmentTransaction_nav.addToBackStack(null);
        fragmentTransaction_nav.commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, LeadFragment.newInstance(2));
//        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menu1 = menu.findItem(R.id.navigation_dashboard);
        menu1.setIcon(R.drawable.dashboard_click);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "activityresult", Toast.LENGTH_SHORT).show();
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    LeadItems leaditem = (LeadItems) b.getSerializable("Obj");
                   // leadItemsList.add(leaditem);
                   // String num = leaditem.getLead_number();
                    leadFragment.update(leaditem);
                    //adapter.update(leadItemsList);
                    //adapter.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(),"my num"+ num, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}





