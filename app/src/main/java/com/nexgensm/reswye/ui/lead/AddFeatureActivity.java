package com.nexgensm.reswye.ui.lead;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.databinding.MainActivityBinding;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.ResponseList;
import com.nexgensm.reswye.model.ResultData;
import com.nexgensm.reswye.ui.propertylisting.ExcercisePojo;
import com.nexgensm.reswye.ui.propertylisting.SpaceItemDecoration;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class AddFeatureActivity extends AppCompatActivity implements View.OnDragListener {

    MainActivityBinding mainActivityBinding;
    public ObservableArrayList<ExcercisePojo> exerciseList=new ObservableArrayList<>();
    public ObservableArrayList<ExcercisePojo> exerciseSelectedList = new ObservableArrayList<>();
    public ExcercisePojo exerciseToMove;
    private int newContactPosition = -1;
    private Button bt_submit;
    private int currentPosition = -1;
    private boolean isExerciseAdded = false;
    public static boolean isFromExercise = false;
    String data="";
    ProgressDialog pd;
    int lead_id=0;
    int flag=0;
    int fid=0;
    int lid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_feature);
        mainActivityBinding.setMainActivity(this);
        mainActivityBinding.rcvSelectedExercise.setOnDragListener(this);
        pd = new ProgressDialog(AddFeatureActivity.this);

        lead_id= SharedPrefsUtils.getInstance(getApplicationContext()).getLeadId();
        flag=SharedPrefsUtils.getInstance(getApplicationContext()).getFlag();
        lid= SharedPrefsUtils.getInstance(getApplicationContext()).getLId();

    //    Toast.makeText(this, "flag "+flag+" lid "+lid, Toast.LENGTH_SHORT).show();
        mainActivityBinding.rcvChooseExercise.setOnDragListener(new MyDragInsideRcvListener());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.scale_3dp);
        mainActivityBinding.rcvChooseExercise.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        Intent i=getIntent();
        data=i.getStringExtra("data");
        pd.setMessage("Loading");
        pd.show();
        if(data.equals("feature"))
        {
            if(flag==0)
            {
                getData1();
            }
            else
            {
                getData11(lid);
            }


        }
        else
        {

            if(flag==0)
            {
                getData2();
            }
            else
            {
                getData22(lid);
            }
           // getData2();

        }
        bt_submit=findViewById(R.id.save_chara);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("Loading");
                pd.show();
                if(data.equals("feature"))
                {

                    sendData1();

                }
                else
                {
                    sendData2();

                }

            }
        });

    }

    private void getData11(int lid) {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseList> call = apiService.getList1(lid);
            call.enqueue(new Callback<ResponseList>() {
                @Override
                public void onResponse(Call<ResponseList> call, retrofit2.Response<ResponseList> response) {
                    pd.dismiss();

                    ArrayList<ResultData> resultArray=new ArrayList<ResultData>();
                    ArrayList<ResultData> resultArraySelected=new ArrayList<ResultData>();
                    String status=response.body().getStatus();
                    //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        resultArraySelected =response.body().getResult();
                        resultArray =response.body().getRes();
                        Log.e("111111111","resultArray "+resultArray);
                        Log.e("111111111","resultArraySelected "+resultArraySelected);

                        //  Toast.makeText(AddFeatureActivity.this, "size "+resultArray.size(), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<resultArray.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArray.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                            //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setFid(fid);
                            exerciseToMove1.setName(features);
                            exerciseList.add(exerciseToMove1);

                        }


                        for(int i=0;i<resultArraySelected.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArraySelected.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                            //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setFid(fid);
                            exerciseToMove1.setName(features);
                            exerciseSelectedList.add(exerciseToMove1);

                        }


                    }


                }

                @Override
                public void onFailure(Call<ResponseList> call, Throwable t) {
                    pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
            pd.dismiss();
            e.printStackTrace();
        }
    }


    private void getData1() {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseList> call = apiService.getList();
            call.enqueue(new Callback<ResponseList>() {
                @Override
                public void onResponse(Call<ResponseList> call, retrofit2.Response<ResponseList> response) {
                       pd.dismiss();

                    ArrayList<ResultData> resultArray=new ArrayList<ResultData>();
                    String status=response.body().getStatus();
                 //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        resultArray =response.body().getResult();
                      //  Toast.makeText(AddFeatureActivity.this, "size "+resultArray.size(), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<resultArray.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArray.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                          //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setFid(fid);
                            exerciseToMove1.setName(features);
                            exerciseList.add(exerciseToMove1);

                        }


                    }


                }

                @Override
                public void onFailure(Call<ResponseList> call, Throwable t) {
                      pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
              pd.dismiss();
            e.printStackTrace();
        }
    }

    private void getData2() {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseList> call = apiService.getListChara();
            call.enqueue(new Callback<ResponseList>() {
                @Override
                public void onResponse(Call<ResponseList> call, retrofit2.Response<ResponseList> response) {
                       pd.dismiss();

                    ArrayList<ResultData> resultArray=new ArrayList<ResultData>();
                    String status=response.body().getStatus();
                    //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        resultArray =response.body().getResult();
                        //  Toast.makeText(AddFeatureActivity.this, "size "+resultArray.size(), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<resultArray.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArray.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                            //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setName(features);
                            exerciseToMove1.setFid(fid);
                            exerciseList.add(exerciseToMove1);

                        }


                    }


                }

                @Override
                public void onFailure(Call<ResponseList> call, Throwable t) {
                      pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
              pd.dismiss();
            e.printStackTrace();
        }
    }
    private void getData22(int lid) {
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseList> call = apiService.getListChara1(lid);
            call.enqueue(new Callback<ResponseList>() {
                @Override
                public void onResponse(Call<ResponseList> call, retrofit2.Response<ResponseList> response) {
                    pd.dismiss();

                    ArrayList<ResultData> resultArraySelected=new ArrayList<ResultData>();
                    String status=response.body().getStatus();
                    ArrayList<ResultData> resultArray=new ArrayList<ResultData>();

                    //   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        resultArray =response.body().getResult();
                        resultArraySelected=response.body().getRes();


                        for(int i=0;i<resultArray.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArray.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                            //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setName(features);
                            exerciseToMove1.setFid(fid);
                            exerciseList.add(exerciseToMove1);

                        }
                        for(int i=0;i<resultArraySelected.size();i++)
                        {
                            ExcercisePojo exerciseToMove1=new ExcercisePojo();
                            ResultData result=resultArraySelected.get(i);
                            String features=result.getFeatures_characteristics();
                            int id=result.getId();
                            int fid=result.getFid_cid();

                            //  Toast.makeText(AddFeatureActivity.this, "feature "+features+" id "+id, Toast.LENGTH_SHORT).show();
                            exerciseToMove1.setExerciseId(id);
                            exerciseToMove1.setName(features);
                            exerciseToMove1.setFid(fid);
                            exerciseSelectedList.add(exerciseToMove1);

                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseList> call, Throwable t) {
                    pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
            pd.dismiss();
            e.printStackTrace();
        }
    }

    private void sendData1() {
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
                jo.addProperty("fid_cid",pojo.fid);
                fid=pojo.fid;
                ja.add(jo);
              //  Toast.makeText(this, "id "+pojo.exerciseId, Toast.LENGTH_SHORT).show();
            }
            jo_outer.addProperty("flag",flag);
            jo_outer.addProperty("fid",fid);
            jo_outer.addProperty("lead_id",lead_id);
            jo_outer.add("feature_name",ja);

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
                    pd.dismiss();
                    String status=response.body().getStatus();
                   // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        //Result result=response.body().getResult();
                        Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                        //Result result=response.body().getResult();
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                      pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
              pd.dismiss();
            e.printStackTrace();
        }

    }

    private void sendData2() {
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
                jo.addProperty("fid_cid",pojo.fid);
                fid=pojo.fid;
                ja.add(jo);
             //   Toast.makeText(this, "id "+pojo.exerciseId, Toast.LENGTH_SHORT).show();
            }
            jo_outer.addProperty("fid",fid);
            jo_outer.addProperty("flag",flag);
            jo_outer.addProperty("lead_id",lead_id);
            jo_outer.add("feature_name",ja);

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
                       pd.dismiss();

                    String status=response.body().getStatus();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    if(status.equals("success"))
                    {
                        //Result result=response.body().getResult();
                        Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                        //Result result=response.body().getResult();
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                      pd.dismiss();

                }
            });
        }
        catch (Exception e)
        {
              pd.dismiss();
            e.printStackTrace();
        }

    }


    @Override
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
                /*ViewGroup owner = (ViewGroup) (view.getParent());
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
                }*/

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
    }

    /**
     * This listener class is for Vertical Recyclerview.
     */
    class MyDragInsideRcvListener implements View.OnDragListener {
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
    }
}

