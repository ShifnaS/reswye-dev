package com.nexgensm.reswye.ui.navigationdrawer;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.Utlity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class ProfileSettingsActivity extends AppCompatActivity {
    EditText profile_settings_nameTxt,profile_settings_emailTxt,profile_settings_passwordTxt,  profile_settings_mobnoTxt,   profile_settings_corresaddressTxt;
    EditText profile_settings_cityTxt,profile_settings_stateTxt;
    String Token,ImageUrl,encodedImage,encodeToBase64;
    int userId,encodedImage_flag;
    CircleImageView circleImageView;
    Bitmap bitmap;
    Button update_profileBtn;
    CircleImageView profile_settings_imageTxt;
    RequestQueue requestQueue,requestQueueUpdate;
    private int GALLERY = 1, CAMERA = 2;
    String firstNameTxt,email_IdTxt,passwordTxt,mobileTxt,cityTxt,stateTxt,profileImageNameTxt,correspondence_AddTxt;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        profile_settings_nameTxt=(EditText)findViewById(R.id.profile_settings_name);
        profile_settings_emailTxt=(EditText)findViewById(R.id.profile_settings_email);
        profile_settings_passwordTxt=(EditText)findViewById(R.id.profile_settings_password);
        profile_settings_mobnoTxt=(EditText)findViewById(R.id.profile_settings_mobno);
        profile_settings_cityTxt=(EditText)findViewById(R.id.profile_settings_city);
        profile_settings_stateTxt=(EditText)findViewById(R.id.profile_settings_state);
        profile_settings_corresaddressTxt=(EditText)findViewById(R.id.profile_settings_corresaddress);
        profile_settings_imageTxt=(CircleImageView)findViewById(R.id.profile_settings_image);
        update_profileBtn=(Button)findViewById(R.id.update_profile);


        profile_settings_imageTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodedImage_flag = 1;
                showPictureDialog();

            }
        });
        progressDialog=new ProgressDialog(ProfileSettingsActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=sharedpreferences.getInt("UserId",0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        requestQueue= Volley.newRequestQueue(this);
        requestQueueUpdate=Volley.newRequestQueue(this);
        //////////////////////////////////// GET USER PROFILE DETAILS ///////////////////////////////////////
        String Url="http://192.168.0.4:88/api/UserRegistration/GetuserDetails";
        final Map<String,Object> jsonParams=new ArrayMap<>();
        jsonParams.put("userId",userId);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Status_missed = response.getString("status").toString().trim();
                            Toast.makeText(getApplicationContext(), Status_missed, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = response.getJSONArray("data");

                            JSONObject data = jsonArray.getJSONObject(0);

                            firstNameTxt =data.getString("first_Name");
                          //  lastNameTxt = data.getString("last_Name");
                            email_IdTxt = data.getString("email_Id");
                            passwordTxt = data.getString("password");
                            mobileTxt = data.getString("mobile");
                            cityTxt = data.getString("city");
                            stateTxt = data.getString("state");
                            profileImageNameTxt = data.getString("profileImageName");
                            correspondence_AddTxt = data.getString("correspondence_Add");
                            String image = ImageUrl + profileImageNameTxt;
                            String str3 = "Success";
                            int response_result = Status_missed.compareTo(str3);
                            if (response_result == 0){
                                progressDialog.dismiss();
                                profile_settings_nameTxt.setText(firstNameTxt);
                                profile_settings_emailTxt.setText(email_IdTxt);
                                profile_settings_passwordTxt.setText(passwordTxt);
                                profile_settings_mobnoTxt.setText(mobileTxt);
                                profile_settings_cityTxt.setText(cityTxt);
                                profile_settings_stateTxt.setText(stateTxt);
                                profile_settings_corresaddressTxt.setText(correspondence_AddTxt);
                                Picasso.with(getApplicationContext()).load(image).into(profile_settings_imageTxt);

                                update_profileBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String name=profile_settings_nameTxt.getText().toString();
                                        String email=profile_settings_emailTxt.getText().toString();
                                        String password=profile_settings_passwordTxt.getText().toString();
                                        String mob=profile_settings_mobnoTxt.getText().toString();
                                        String cityT=profile_settings_cityTxt.getText().toString();
                                        String stateT=profile_settings_stateTxt.getText().toString();
                                        String Corr_Addd=profile_settings_corresaddressTxt.getText().toString();
                                        if (encodedImage_flag == 0) {
                                            encodedImage = "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAQAAAAAYLlVAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAAAmJLR0QAAKqNIzIAAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfiBRIMMBRpLhunAAAFrUlEQVRo3q3ZX2xWdxkH8E/fVlbSRqdLJSt1cZLZzT8sM1FEZQmk4o0Qgy6Q3kAGJu4CdrFkmSxxS6ZOE5bMmyXEmdjEuF0MBbIZoBUFZmIihWRax6YQnfJ2BPkzUtaX0vbx4n1LC/T8zjnv+J6b983z/J7ne57zO7/z/GlRFh1WWaL72kX12nXSQZdLWyyMLg/ba1zMua66et3/cXs9rKu40ZZCWgts1u8rKqgZdNxpVadVnUWXbot1W+wBX9eOaX/ykl+YuBX33aLfKSGcM2CdjqR2h3UGnBPCKf0FbzCB1Y4J4ZA+bYVXtelzSAjHrG7eeY9BIYxY09T6NUaEMKinmeXLjQpVW7Q2fQuttqgKo5aXXbpRTdils2nnM+i0S6jZWIb3DmHa0x98C4EWT5kWdhSLZavdwph1OXof8VkPWGaF7gJW1xkTdhehsEN4x9IEwS/7gdevO4BO+qXP59hd6h1hR577jcJYpvsOT/pfw+llr/u9QfudFMKEn/hS8pRYakyk98JyNdOZwV/vjDDtL35spQVzJD0ebxzRU/7qe9ozH8S0WvYb0WNUeCpD+qRpYY/7M+T3etFh7wrhb+7K0HpaGM06FwaFXRk7f5tQ0y8PLVYbFqoZW7PFLmFwPtFqoZrx3i9VE76Z676OhV4Rns+QdqqKmw/oFseELRmL9gi/K+geuox738czpFuEYzdGul8YyXhL7zZl0mdKEGCn8NMMWasRcf3jXOCUyPzkbBB+Xco9vcKJTOka4dTc9+gR4VCm+nbh+yUJcEktcZQfEh6Z/XtE6MtUfvHGgBXC30XikO4Tjsz86TLlXCLdOCh8tTSBA8LXMqVtzpnSRQVrVbxqMlP5Q3i/NIEqme8Bk15VsbZO4FvYkzA1ikWlCSzAWEK+p+65okOfmv23nMDHcDEh36+mT0fFKu0Gk+XEu00TeC8hv2xQu1UVS3A8aerf+NQtJ1D3uqSiG6eTiicoeQ7OELiY1DiN7kqjuksTCPeVdF9xuwm1pE51lkA6Ap8W2kqUJdDqPW2+kB+B+v2lt9he4dGSEeBx4bWkxqL69+KSqyo5TKd9uDSBj5o2mtSouOpSPoE2k86Xdg8XXE0+uAaB/EdwVDRR3d0l/DmpsUg4UanvxaTiH7GsNIEvkvjEa3it1gksziXwI/eUcn+3HzZWZmPxLIF0BIa8ptdxKwu7X+kN99rt4K2JQM1aO3V4tjCBZ3Xa6duu5Eegnp/tzTXZ6m2ht5D7XuHtAmXo3noe2mHceE7nB54XthUisC1RFcyi4bfisiHtvpG7YB8FtDS09hXQajdUTwM2CwO5CxYaN17gPOgxbtzCXL0BYXP9Z15SOoMXhJdytX4lvJCrdS0prSOdls/gDudz8+Plpp13R66t69LyvMJkFluF3yY1fiNsLWDphsIkXZrNokv4Z1LjTVGgU3xTaZYuTmfRYsp4UuOCqdze2jzFaV55PjcCJz9wBOYtz9MNihncL/whqTEoMps4ddzQoJhNRQ4YcqeBZAA/p56kZ+O/Da0stBhwpyEH5hOmm1RUjAjrkwTWCyOJDCvZpMpr023KMT5LclOGNKdNR6pRud6YsEEeNghj88apQKOS+Vu1bZ4TwjO57uEZITx3w+FesFV7c7O61UOGhYnMwN6MTSaEYQ9dO1lKNKvntus7bW3Mi45aUdg9rHC0MTfaqrNcu76O+sBiQgjDTY9shhtt7JIDizqWuyKEwyWrwrloc1gIV8qPbGCZM43G/BNNuX/CZSGcaaKquIbtao2Z4Xa3FV51m+2N2WHN9uadzxh72WRjFjDisdzB5WNGTAlh0sv5pIuNpTr9zHcaFXI4601veMs/vOU/+IRe9+i11H26GhYvecWjyS5ZE3jQPheuG1XPd12wz4PFjZYfzHX5rhU+6Xad2rVhUs2Yi/7liJ87W87c/wEf0DxLWVOPxQAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxOC0wNS0xOFQxMjo0ODoyMCswMjowML90CMQAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTgtMDUtMThUMTI6NDg6MjArMDI6MDDOKbB4AAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAABJRU5ErkJggg==";
                                        }


        String url="http://202.88.239.14:8169/api/UserRegistration/UpdateUserProfile";
        Map<String,Object> jsonParams=new ArrayMap<>();
        jsonParams.put("User_Id",userId);
        jsonParams.put("ProfileImageName",encodedImage);
        jsonParams.put("First_Name",name);
        jsonParams.put("Email_Id",email);
        jsonParams.put("Mobile",mob);
        jsonParams.put("Password",password);
        jsonParams.put("City",cityT);
        jsonParams.put("State",stateT);
        String cc=encodedImage;
        jsonParams.put("Correspondence_Add",Corr_Addd);

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Sttus=response.getString("status").toString();
                            String message=response.getString("message").toString();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error:"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error:"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getHeaders()throws AuthFailureError{
              final  Map<String,String >headers=new HashMap<>();
              headers.put("Authorization",Token);
              return headers;
            }
        };
         requestQueueUpdate.add(jsonObjectRequest2);
                                    }
                                });
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         progressDialog.dismiss();
                         Toast.makeText(getApplicationContext(), "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

                }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String>headers=new HashMap<>();
                headers.put("Authorization",Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

        /////////////////////////////////////// UPDATE USER PROFILE //////////////////////////////

//        requestQueueUpdate=Volley.newRequestQueue(this);


        ImageButton close = (ImageButton)findViewById(R.id.profile_settings_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }

        }
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    profile_settings_imageTxt.setImageBitmap(bitmap);
                    encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

                    Toast.makeText(this, "Image Updated!", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to Update Image!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            profile_settings_imageTxt.setImageBitmap(bitmap);
            encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

            Toast.makeText(this, "Image Updated!", Toast.LENGTH_SHORT).show();


        }
    }
}
