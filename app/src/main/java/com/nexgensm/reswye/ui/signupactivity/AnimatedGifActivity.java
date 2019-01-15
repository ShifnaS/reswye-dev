package com.nexgensm.reswye.ui.signupactivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;

public class AnimatedGifActivity extends AppCompatActivity {
    private GIFView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_gif);

        gifView = (GIFView) findViewById(R.id.GifImageView);
        gifView.setGifImageResource(R.drawable.animatedimagesuccess);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent in = new Intent(AnimatedGifActivity.this, SigninActivity.class);
                startActivity(in);
                //start your activity here
            }

        }, 8000L);
    }
}
