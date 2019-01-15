package com.nexgensm.reswye.ui.signupactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.Dashboard.DashboardFragment;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;

public class PaymentActivity extends AppCompatActivity {

    Button bt_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bt_confirm=findViewById(R.id.confirm);
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),BottomTabbarActivity.class);
                startActivity(i);
            }
        });
    }
}
