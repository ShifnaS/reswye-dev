package com.nexgensm.reswye.ui.lead;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;

public class AddFeatureActivity extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {

    private TextView Adult,hourse,HandiCap,water_View,water_front,pool,tennis,Attic,AdultTxt,AtticTxt,HandiCapTxt,hourseTxt,poolTxt,water_ViewTxt,water_frontTxt,tennisTxt,Carpet,Beach_right,masterBed,Basement,CarpetTxt,Beach_rightTxt,masterBedTxt,BasementTxt;

    //When touched text gets dropped into either text4 or text5 or text6 then this method will be called
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
            if(text.equals(Adult.getText().toString())) Adult.setVisibility(View.VISIBLE);
            else if(text.equals(Attic.getText().toString())) Attic.setVisibility(View.VISIBLE);
            else if(text.equals(HandiCap.getText().toString())) HandiCap.setVisibility(View.VISIBLE);
            else if(text.equals(hourse.getText().toString())) hourse.setVisibility(View.VISIBLE);

            else if(text.equals(tennis.getText().toString())) tennis.setVisibility(View.VISIBLE);
            else if(text.equals(pool.getText().toString())) pool.setVisibility(View.VISIBLE);
            else if(text.equals(water_front.getText().toString())) water_front.setVisibility(View.VISIBLE);
            else if(text.equals(water_View.getText().toString())) water_View.setVisibility(View.VISIBLE);

            else if(text.equals(Carpet.getText().toString())) Carpet.setVisibility(View.VISIBLE);
            else if(text.equals(Beach_right.getText().toString())) Beach_right.setVisibility(View.VISIBLE);
            else if(text.equals(masterBed.getText().toString())) masterBed.setVisibility(View.VISIBLE);
            else if(text.equals(Basement.getText().toString())) Basement.setVisibility(View.VISIBLE);
            //update the text and color in the target view to reflect the data being dropped
            dropTarget.setText(dropped.getText());
           // dropTarget.setBackgroundColor(R.drawable.red_round_textview);
            dropTarget.setBackgroundResource(R.drawable.red_round_textview);
        }
        return true;
    }

    //When text1 or text2 or text3 gets clicked or touched then this method will be called
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null, shadowBuilder, v, 0);
            return true;
        }
      return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feature);

        Adult = (TextView)findViewById(R.id.Adult);
        HandiCap = (TextView)findViewById(R.id.HandiCap);
        hourse = (TextView)findViewById(R.id.hourse);
        tennis = (TextView)findViewById(R.id.tennis);
        pool = (TextView)findViewById(R.id.pool);
        water_View = (TextView)findViewById(R.id.water_View);
        water_front = (TextView)findViewById(R.id.water_front);
        Attic = (TextView)findViewById(R.id.Attic);
        Carpet = (TextView)findViewById(R.id.Carpet);
        Beach_right = (TextView)findViewById(R.id.Beach_right);
        masterBed = (TextView)findViewById(R.id.masterBed);
        Basement = (TextView)findViewById(R.id.Basement);

        AdultTxt = (TextView)findViewById(R.id.AdultTxt);
        HandiCapTxt = (TextView)findViewById(R.id.HandiCapTxt);
        hourseTxt = (TextView)findViewById(R.id.hourseTxt);
        tennisTxt = (TextView)findViewById(R.id.tennisTxt);
        poolTxt = (TextView)findViewById(R.id.poolTxt);
        water_ViewTxt = (TextView)findViewById(R.id.water_ViewTxt);
        water_frontTxt = (TextView)findViewById(R.id.water_frontTxt);
        AtticTxt = (TextView)findViewById(R.id.AtticTxt);
        CarpetTxt = (TextView)findViewById(R.id.CarpetTxt);
        Beach_rightTxt = (TextView)findViewById(R.id.Beach_rightTxt);
        masterBedTxt = (TextView)findViewById(R.id.masterBedTxt);
        BasementTxt = (TextView)findViewById(R.id.BasementTxt);
        //Setting touch and drag listeners
        Adult.setOnTouchListener(this);
        HandiCap.setOnTouchListener(this);
        hourse.setOnTouchListener(this);
        tennis.setOnTouchListener(this);
        pool.setOnTouchListener(this);
        water_View.setOnTouchListener(this);
        water_front.setOnTouchListener(this);
        Attic.setOnTouchListener(this);
        Carpet.setOnTouchListener(this);
        Beach_right.setOnTouchListener(this);
        masterBed.setOnTouchListener(this);
        Basement.setOnTouchListener(this);


        AdultTxt.setOnDragListener(this);
        HandiCapTxt.setOnDragListener(this);
        hourseTxt.setOnDragListener(this);
        tennisTxt.setOnDragListener(this);
        poolTxt.setOnDragListener(this);
        water_ViewTxt.setOnDragListener(this);
        water_frontTxt.setOnDragListener(this);
        AtticTxt.setOnDragListener(this);
        CarpetTxt.setOnDragListener(this);
        Beach_rightTxt.setOnDragListener(this);
        masterBedTxt.setOnDragListener(this);
        BasementTxt.setOnDragListener(this);

        ImageButton back= (ImageButton)findViewById(R.id.AddFeature_Back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
