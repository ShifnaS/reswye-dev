
package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddNewLeadFragment extends Fragment implements EasyPermissions.PermissionCallbacks{
    Bitmap bitmap;
    ApiInterface apiService;
    Uri picUri;
    private int GALLERY = 1, CAMERA = 2;
    private static final int RESULT_OK = 1;
    private static final int RESULT_CANCELED = 0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private int  userId,  encodedImage_flag = 0, LeadId,flag;
    Drawable btn_click;
    Drawable btn_unclick;
    Context context;
    Spinner spinneradditionaldetails;
    String[] additionaldetails;
    CircleImageView circleView;
    String gender_value, status, leadwarmth,image,Token,url,ImageUrl ,encodedImage;
    TextView lead_status;
    SwitchCompat selectgender_selection;
    LinearLayout leadstauslayout;
    Button warmbtn, coldbtn, neutral, addnewleadbtn,success;
    Spinner additional_details;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    EditText firstnameedittext,AddressSelleredittext,lastnameedittext,mobilenumberedittext,emailedittext;
    MultipartBody.Part fileToUpload;
    RequestBody filename;

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "Path_to_your_server";
    private Uri uri;
    public AddNewLeadFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static AddNewLeadFragment newInstance(String param1, String param2) {
        AddNewLeadFragment fragment = new AddNewLeadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myFragmentView = inflater.inflate(R.layout.fragment_add_new_lead, container, false);
        spinneradditionaldetails = (Spinner) myFragmentView.findViewById(R.id.spinner_additional_details);

        spinneradditionaldetails.setSelection(0, true);
        additionaldetails = getResources().getStringArray(R.array.AddNewLead);

        additional_details =  myFragmentView.findViewById(R.id.spinner_additional_details);
        firstnameedittext = (EditText) myFragmentView.findViewById(R.id.firstname);
        AddressSelleredittext = (EditText) myFragmentView.findViewById(R.id.AddressSeller);
        lastnameedittext = (EditText) myFragmentView.findViewById(R.id.lastname);
        mobilenumberedittext = (EditText) myFragmentView.findViewById(R.id.mobilenum);
        emailedittext = (EditText) myFragmentView.findViewById(R.id.email);
        selectgender_selection = (SwitchCompat) myFragmentView.findViewById(R.id.selectgender_selection);
        lead_status = (TextView) myFragmentView.findViewById(R.id.lead_status);
        leadstauslayout = (LinearLayout) myFragmentView.findViewById(R.id.leadstauslayout);
        warmbtn = (Button) myFragmentView.findViewById(R.id.warmbtn);
        coldbtn = (Button) myFragmentView.findViewById(R.id.coldbtn);
        neutral = (Button) myFragmentView.findViewById(R.id.neutral);

        circleView = (CircleImageView) myFragmentView.findViewById(R.id.circleView);
        addnewleadbtn = (Button) myFragmentView.findViewById(R.id.adddetails_lead_save);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userId=SharedPrefsUtils.getInstance(getContext()).getUserId();
            userId=SharedPrefsUtils.getInstance(getContext()).getUserId();

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, additionaldetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneradditionaldetails.setAdapter(adapter);



        if (selectgender_selection.isChecked() == false) {
            gender_value = "Male";
        } else {
            gender_value = "Female";
        }

        if (flag == 1) {

            lead_status.setVisibility(View.VISIBLE);
            leadstauslayout.setVisibility(View.VISIBLE);
        } else {

            lead_status.setVisibility(View.GONE);
            leadstauslayout.setVisibility(View.GONE);
        }

        warmbtn.setBackground(btn_click);
        coldbtn.setBackground(btn_unclick);
        neutral.setBackground(btn_unclick);

        warmbtn.setTextColor(Color.WHITE);
        coldbtn.setTextColor(Color.BLACK);
        neutral.setTextColor(Color.BLACK);
        leadwarmth = "Warm";
        warmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warmbtn.setBackground(btn_click);
                coldbtn.setBackground(btn_unclick);
                neutral.setBackground(btn_unclick);

                warmbtn.setTextColor(Color.WHITE);
                coldbtn.setTextColor(Color.BLACK);
                neutral.setTextColor(Color.BLACK);
                leadwarmth = "Warm";
            }
        });

        coldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coldbtn.setBackground(btn_click);
                warmbtn.setBackground(btn_unclick);
                neutral.setBackground(btn_unclick);

                coldbtn.setTextColor(Color.WHITE);
                warmbtn.setTextColor(Color.BLACK);
                neutral.setTextColor(Color.BLACK);
                leadwarmth = "Cold";
            }
        });

        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neutral.setBackground(btn_click);
                coldbtn.setBackground(btn_unclick);
                warmbtn.setBackground(btn_unclick);

                neutral.setTextColor(Color.WHITE);
                coldbtn.setTextColor(Color.BLACK);
                warmbtn.setTextColor(Color.BLACK);
                leadwarmth = "Neutral";
            }
        });


        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodedImage_flag = 1;
                showPictureDialog();
            }
        });


        addnewleadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String additional=additional_details.getSelectedItem().toString();
                String frtsName=firstnameedittext.getText().toString();
                String address= AddressSelleredittext.getText().toString();
                String lname= lastnameedittext .getText().toString();
                String mobile=mobilenumberedittext.getText().toString();
                String email= emailedittext .getText().toString();
                String gender=selectgender_selection .getText().toString();
                String status= lead_status .getText().toString();

                if(frtsName.equals("")||address.equals("")||lname.equals("")||mobile.equals("")||email.equals(""))
                {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()!=10)
                {
                    Toast.makeText(context, "Please Enter a valid mobile no", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidEmail(email))
                {
                    Toast.makeText(context, "Please Enter a valid email id", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    int uid= SharedPrefsUtils.getInstance(getContext()).getUserId();

                    RequestBody leadwarmth1 = RequestBody.create(MediaType.parse("text/plain"), leadwarmth);
                    RequestBody additional1 = RequestBody.create(MediaType.parse("text/plain"), additional);
                    RequestBody fname = RequestBody.create(MediaType.parse("text/plain"), frtsName);
                    RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
                    RequestBody lname1 = RequestBody.create(MediaType.parse("text/plain"), lname);
                    RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
                    RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);
                    RequestBody gender1 = RequestBody.create(MediaType.parse("text/plain"), gender);
                    RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), ""+uid);


                    ApiInterface apiService =ApiClient.getClient().create(ApiInterface.class);
                    Call<Response> fileUpload = apiService.uploadLeadData(fileToUpload, filename,fname,address1,lname1,email1,mobile1,gender1,additional1,leadwarmth1,user_id);
                    fileUpload.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Toast.makeText(getActivity(), "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), "Success " + response.body().getStatus(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Log.d("TAG", "Error " + t.getMessage());
                        }
                    });
                }

            }
        });

        return myFragmentView;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this.getActivity());
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
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
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

        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            circleView.setImageBitmap(bitmap);
            if(EasyPermissions.hasPermissions(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, getActivity());
                File file = new File(filePath);
                Log.d("TAG", "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            }
            else
            {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        }
        else if (requestCode == CAMERA) {

            
            bitmap = (Bitmap) data.getExtras().get("data");
            circleView.setImageBitmap(bitmap);
            encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

            Toast.makeText(getActivity(), "Image Updated!", Toast.LENGTH_SHORT).show();
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("TAG", "Permission has been denied");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddNewLeadFragment.this);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
