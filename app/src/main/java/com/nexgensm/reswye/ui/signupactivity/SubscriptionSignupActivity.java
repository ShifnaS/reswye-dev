package com.nexgensm.reswye.ui.signupactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nexgensm.reswye.R;

/**
 * Created by Nexmin on 14-02-2018.
 */

public class SubscriptionSignupActivity extends AppCompatActivity {
    Button bt_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        bt_confirm=findViewById(R.id.subcribe_btnbottom);
        ImageView backbutton = (ImageView) findViewById(R.id.signup_back);
        backbutton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }

        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(i);

            }
        });

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }



}