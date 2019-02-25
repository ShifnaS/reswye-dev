package com.nexgensm.reswye.ui.propertylisting;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity  {
/*implements View.OnDragListener*/
 /*   private MainActivityBinding mainActivityBinding;
    public ObservableArrayList<ExcercisePojo> exerciseList;
    public ObservableArrayList<ExcercisePojo> exerciseSelectedList = new ObservableArrayList<>();
    public ExcercisePojo exerciseToMove;
    private int newContactPosition = -1;
    private Button bt_submit;
    private int currentPosition = -1;
    private boolean isExerciseAdded = false;
    public static boolean isFromExercise = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loadExerciseData();
        mainActivityBinding.setMainActivity(this);
        mainActivityBinding.rcvSelectedExercise.setOnDragListener(this);


        mainActivityBinding.rcvChooseExercise.setOnDragListener(new MyDragInsideRcvListener());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.scale_3dp);
        mainActivityBinding.rcvChooseExercise.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        bt_submit=findViewById(R.id.submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendData();

            }
        });
        getData();*/

    }

    /*private void getData() {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.getList();
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    //   pd.dismiss();
                    ArrayList<Result> resultArray=new ArrayList<Result>();
                    String status=response.body().getStatus();
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        resultArray =response.body().getResult();
                        for(int i=0;i<resultArray.size();i++)
                        {
                            exerciseToMove=new ExcercisePojo();
                            Result result=resultArray.get(i); String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            exerciseToMove.setExerciseId(id);
                            exerciseToMove.setName(features);
                            exerciseSelectedList.add(exerciseToMove);

                        }


                    }


                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    //  pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
            //  pd.dismiss();
            e.printStackTrace();
        }
    }
*/
/*    private void sendData() {
        JsonObject jo ;
        JsonArray ja=new JsonArray();
        JsonObject jo_outer=new JsonObject();

        if(exerciseSelectedList.size()==0)
        {
            Toast.makeText(this, "Please Select any property feature", Toast.LENGTH_SHORT).show();

        }
        else
        {

            for(int i=0;i<exerciseSelectedList.size();i++)
            {
                ExcercisePojo pojo=exerciseSelectedList.get(i);

                jo=new JsonObject();
                //  Toast.makeText(getContext(), ""+quantity, Toast.LENGTH_SHORT).show();
                jo.addProperty("id",pojo.exerciseId);
                jo.addProperty("features_characteristics",pojo.name);
                ja.add(jo);
                Toast.makeText(this, "id "+pojo.exerciseId, Toast.LENGTH_SHORT).show();
            }
            jo_outer.addProperty("lead_id",1);
            jo_outer.add("array",ja);

        }
       // Toast.makeText(this, "hiii "+exerciseSelectedList.size(), Toast.LENGTH_SHORT).show();


        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.getResponse(jo_outer);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                 //   pd.dismiss();

                    String status=response.body().getStatus();
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        //Result result=response.body().getResult();

                    }
                    else
                    {
                        //Result result=response.body().getResult();
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                  //  pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
          //  pd.dismiss();
            e.printStackTrace();
        }

    }*/

/*
    public void loadExerciseData() {
        exerciseList = new ObservableArrayList<>();
        for (int i = 1; i <= 10; i++) {
            exerciseList.add(new ExcercisePojo(i, "exercise " + i));
        }
    }*/

/*    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View selectedView = (View) dragEvent.getLocalState();
        RecyclerView rcvSelected = (RecyclerView) view;

        try {
            int currentPosition = mainActivityBinding.rcvChooseExercise.getChildAdapterPosition(selectedView);

            // Ensure the position is valid.
            if (currentPosition != -1) {
                exerciseToMove = exerciseList.get(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                View onTopOf = rcvSelected.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
                newContactPosition = rcvSelected.getChildAdapterPosition(onTopOf);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                //when Item is dropped off to recyclerview.
                if (isFromExercise) {
                    exerciseSelectedList.add(exerciseToMove);
                    exerciseList.remove(exerciseToMove);
                    mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemRemoved(currentPosition);
                    mainActivityBinding.executePendingBindings();
                }
                //This is to hide/add the container!
                *//*ViewGroup owner = (ViewGroup) (view.getParent());
                if (owner != null) {
                    //owner.removeView(selectedView);
                    //owner.addView(selectedView);

                    try {
                        LinearLayout rlContainer = (LinearLayout) view;
                        rlContainer.addView(selectedView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //selectedView.setVisibility(View.VISIBLE);
                }*//*

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                // Reset the visibility for the Contact item's view.
                // This is done to reset the state in instances where the drag action didn't do anything.
                selectedView.setVisibility(View.VISIBLE);
                // Boundary condition, scroll to top is moving list item to position 0.
                if (newContactPosition != -1) {
                    rcvSelected.scrollToPosition(newContactPosition);
                    newContactPosition = -1;
                } else {
                    rcvSelected.scrollToPosition(0);
                }
            default:
                break;
        }
        return true;
    }*/

    /**
     * This listener class is for Vertical Recyclerview.
     */
  /*  class MyDragInsideRcvListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            RecyclerView rcv = (RecyclerView) v;

            View selectedView = (View) event.getLocalState();
            try {
                int currentPosition = rcv.getChildAdapterPosition(selectedView);
                // Ensure the position is valid.
                if (currentPosition != -1) {
                    exerciseToMove = exerciseSelectedList.get(currentPosition);
                    //exerciseSelectedList.get(currentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    View onTopOf = rcv.findChildViewUnder(event.getX(), event.getY());
                    newContactPosition = rcv.getChildAdapterPosition(onTopOf);


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset the visibility for the Contact item's view.
                    // This is done to reset the state in instances where the drag action didn't do anything.
                    selectedView.setVisibility(View.VISIBLE);
                    // Boundary condition, scroll to top is moving list item to position 0.
                    if (newContactPosition != -1) {
                        rcv.scrollToPosition(newContactPosition);
                        newContactPosition = -1;
                    } else {
                        rcv.scrollToPosition(0);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if (!isFromExercise) {
                        //THIS IS FOR WHEN WE TAKE ITEM OF OTHER LIST AND DROP IN THIS LIST.
                        exerciseList.add(exerciseToMove);
                        exerciseSelectedList.remove(exerciseToMove);
                        mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemInserted(currentPosition);
                        mainActivityBinding.executePendingBindings();
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    }*/
}

