package com.nexgensm.reswye.ui.lead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nexgensm.reswye.R;

public class Additional_charecterestics extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_charecterestics);



        ImageButton back= (ImageButton)findViewById(R.id.AddFeature_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null, shadowBuilder, v, 0);
            return true;
        }
        return false;    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction()==DragEvent.ACTION_DROP)
        {
            //handle the dragged view being dropped over a target view
            TextView dropped = (TextView)event.getLocalState();
            TextView dropTarget = (TextView) v;
            //stop displaying the view where it was before it was dragged
            dropped.setVisibility(View.INVISIBLE);

            //if an item has already been dropped here, there will be different string
            String text=dropTarget.getText().toString();
            //if there is already an item here, set it back visible in its original place

            dropTarget.setText(dropped.getText());
            // dropTarget.setBackgroundColor(R.drawable.red_round_textview);
            dropTarget.setBackgroundResource(R.drawable.red_round_textview);
        }
        return true;    }
}
