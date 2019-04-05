package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Singleton;
import com.nexgensm.reswye.Utlity;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AddNewPhotoActivity extends AppCompatActivity {
    private String TAG = "AddNewPhotoActivity";
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    //    ImageView picview;
    ArrayList<ImageItems> arrayList;
    ArrayList<String> list;
    private RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewadapter;
    MyAdapter m;
    ArrayList<ImageItems> encodedImages;
    private JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    int flag, userId;
    int LeadId;
    String url, ImageUrl, image;
    List<LeadListingRecyclerDataAdapter> GetDataAdapter1;
    RequestQueue requestQueue;
    private ProgressDialog spinner;
    ArrayList<File> fileList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_photo);
        fileList=new ArrayList<>();

        pd = new ProgressDialog(AddNewPhotoActivity.this);

        Singleton.getInstance().clearArray();
        arrayList = new ArrayList<>();
        jsonObject = new JSONObject();
        m = new MyAdapter();
        GetDataAdapter1 = new ArrayList<>();
        flag = SharedPrefsUtils.getInstance(getApplicationContext()).getFlag();
        if(flag==1)
        {
            LeadId = SharedPrefsUtils.getInstance(getApplicationContext()).getLId();
        }
        else
        {
            LeadId = SharedPrefsUtils.getInstance(getApplicationContext()).getLeadId();
        }
//        ImageUrl = sharedPreferences.getString("imageURL", "");
        Log.v(TAG, "FLAg" + flag);
        showPictureDialog();
        testphotocheck();


    }

    public  void testphotocheck(){

        encodedImages = Singleton.getInstance().getArrayImages();
//        Log.v("Tag", "arrayImagecount" + encodedImages.size());
//        Log.d("oncreate", "set adapter");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        //params.height = 500;
        //recyclerView.setLayoutParams(params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(m);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        encodedImages.remove(position);
                        recyclerView.setAdapter(m);
//                        Toast.makeText(getApplicationContext(), "Long press on position :" + position,
//                                Toast.LENGTH_LONG).show();
                    }
                })
        );
        //  picview = (ImageView) findViewById(R.id.pic);

        Button addphoto = (Button) findViewById(R.id.addphoto);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();

            }
        });

        ImageButton close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveButton = (Button) findViewById(R.id.addnew_photo);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pd.setMessage("Loading");
                pd.show();
                multipartImageUpload();


            }

        });






    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();


    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            }

        }
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //String path1 = contentURI.getPath();
                    //Toast.makeText(getApplicationContext(),path1,Toast.LENGTH_SHORT).show();

                    String path = saveImage(bitmap);

                   // Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
                    ImageItems imageitems = new ImageItems();
                    imageitems.setImage(path);
                    Singleton.getInstance().setArrayImages(imageitems);
                    arrayList.add(imageitems);
                    File f=new File(path);
                    fileList.add(f);
                    recyclerView.setAdapter(m);

                   // Toast.makeText(getApplicationContext(), "Image Saved! "+path, Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            String path = saveImage(thumbnail);
            ImageItems imageitems = new ImageItems();
            imageitems.setImage(path);
            Singleton.getInstance().setArrayImages(imageitems);
            arrayList.add(imageitems);
            File f=new File(path);
            fileList.add(f);

            recyclerView.setAdapter(m);
         //   Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();

        }
    }

    public String saveImage(Bitmap myBitmap) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted1");
                //return true;
            } else {

                //Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                //return false;
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
///////////// converting to base64

        String encodedImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        Singleton.getInstance().setArrayList(encodedImage);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myviewholder> {

        @Override
        public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.add_photo_row, parent, false);
            Myviewholder myviewholder = new Myviewholder(v);
            return myviewholder;
        }

        @Override
        public void onBindViewHolder(Myviewholder holder, final int position) {

            //ImageItems m = arrayList.get(position);
            ImageItems m = Singleton.getInstance().getArrayImages().get(position);
            //Log.d(" myactivty", "onBindviewholder" + position);
            //holder.imageView.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            //options.inSampleSize = 20;
            final Bitmap bitmap = BitmapFactory.decodeFile(m.getImage(), options);
            holder.imageView.setImageBitmap(bitmap);


        }

        @Override
        public int getItemCount() {
            return encodedImages.size();
        }

        public class Myviewholder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public Myviewholder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.pic);
            }
        }
    }
    private void multipartImageUpload() {
        try {

            File fileDir;


            MultipartBody.Part[] body = new MultipartBody.Part[fileList.size()];
            for (int i=0;i<fileList.size();i++)
            {
                fileDir=fileList.get(i);
                //file=new File(fileDir, "image"+i + ".jpg");



                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), fileDir);
                // MultipartBody.Part body= MultipartBody.Part.createFormData("file", file.getName(), reqFile);
                body[i] = MultipartBody.Part.createFormData("file", fileDir.getName(), reqFile);

            }



         //   int lid= SharedPrefsUtils.getInstance(getApplicationContext()).getLeadId();
          //  Toast.makeText(this, "LeadId "+lid, Toast.LENGTH_SHORT).show();
            ApiInterface apiService =ApiClient.getClient().create(ApiInterface.class);

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            RequestBody lead_id = RequestBody.create(MediaType.parse("text/plain"), ""+LeadId);

            Call<com.nexgensm.reswye.model.Response> req = apiService.uploadOwnerDocument(body,name,lead_id);
            req.enqueue(new Callback<com.nexgensm.reswye.model.Response>() {
                @Override
                public void onResponse(Call<com.nexgensm.reswye.model.Response> call, retrofit2.Response<com.nexgensm.reswye.model.Response> response) {
                    pd.dismiss();

                    if (response.code() == 200) {
                        if(response.body().getStatus().equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),  "Uploaded Successfully! ", Toast.LENGTH_SHORT).show();
                            Result result=response.body().getResult();
                            int lead_id=result.getLead_Id();
                           // SharedPrefsUtils.getInstance(getApplicationContext()).setLeadId(lead_id);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),  "Failed! ", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }

                  //  Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<com.nexgensm.reswye.model.Response> call, Throwable t) {
                    pd.dismiss();

                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR ","11111 "+t.getMessage());
                    t.printStackTrace();
                }
            });






        } catch (Exception e) {
            pd.dismiss();

            e.printStackTrace();
        }
    }
}

