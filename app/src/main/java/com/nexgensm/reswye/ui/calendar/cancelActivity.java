package com.nexgensm.reswye.ui.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.ui.signupactivity.SignupActivity;

public class cancelActivity extends AppCompatActivity {
Button reshedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        reshedule = (Button)findViewById(R.id.reshedule_btn);
        reshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(cancelActivity.this, AppointmentResheduleActivity.class);
                startActivity(in);
            }
        });
    }
}
