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
    private TextView Style,StyleTxt,Doc_Rights,Doc_RightsTxt,Garage_Spaces,Garage_SpacesTxt,Township,TownshipTxt,Rooms,RoomsTxt,Zone,ZoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_charecterestics);

        Style = (TextView)findViewById(R.id.Style);
        Doc_Rights = (TextView)findViewById(R.id.Doc_Rights);
        Garage_Spaces = (TextView)findViewById(R.id.Garage_Spaces);
        Township = (TextView)findViewById(R.id.Township);
        Rooms = (TextView)findViewById(R.id.Rooms);
        Zone = (TextView)findViewById(R.id.Zone);

        StyleTxt = (TextView)findViewById(R.id.StyleTxt);
        Doc_RightsTxt = (TextView)findViewById(R.id.Doc_RightsTxt);
        Garage_SpacesTxt = (TextView)findViewById(R.id.Garage_SpacesTxt);
        TownshipTxt = (TextView)findViewById(R.id.TownshipTxt);
        RoomsTxt = (TextView)findViewById(R.id.RoomsTxt);
        ZoneTxt = (TextView)findViewById(R.id.ZoneTxt);

        Style.setOnTouchListener(this);
        Doc_Rights.setOnTouchListener(this);
        Garage_Spaces.setOnTouchListener(this);
        Township.setOnTouchListener(this);
        Rooms.setOnTouchListener(this);
        Zone.setOnTouchListener(this);

        StyleTxt.setOnDragListener(this);
        Doc_RightsTxt.setOnDragListener(this);
        Garage_SpacesTxt.setOnDragListener(this);
        TownshipTxt.setOnDragListener(this);
        RoomsTxt.setOnDragListener(this);
        ZoneTxt.setOnDragListener(this);

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
            if(text.equals(Style.getText().toString())) Style.setVisibility(View.VISIBLE);
            else if(text.equals(Doc_Rights.getText().toString())) Doc_Rights.setVisibility(View.VISIBLE);
            else if(text.equals(Garage_Spaces.getText().toString())) Garage_Spaces.setVisibility(View.VISIBLE);
            else if(text.equals(Township.getText().toString())) Township.setVisibility(View.VISIBLE);
            else if(text.equals(Rooms.getText().toString())) Rooms.setVisibility(View.VISIBLE);
            else if(text.equals(Zone.getText().toString())) Zone.setVisibility(View.VISIBLE);
            //update the text and color in the target view to reflect the data being dropped
            dropTarget.setText(dropped.getText());
            // dropTarget.setBackgroundColor(R.drawable.red_round_textview);
            dropTarget.setBackgroundResource(R.drawable.red_round_textview);
        }
        return true;    }
}
