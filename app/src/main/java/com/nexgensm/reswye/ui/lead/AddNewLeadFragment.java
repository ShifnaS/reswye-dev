
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
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class AddNewLeadFragment extends Fragment {
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    Bitmap bitmap;
    String additional="",frtsName="",address="",lname="",mobile="",email="",gender="",status="",leadwarmth="";
    //private int GALLERY = 1, CAMERA = 2;
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
    TextView lead_status;
    SwitchCompat selectgender_selection;
    LinearLayout leadstauslayout;
    Button warmbtn, coldbtn, neutral, addnewleadbtn,success;
    Spinner additional_details;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    EditText firstnameedittext,AddressSelleredittext,lastnameedittext,mobilenumberedittext,emailedittext;

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "Path_to_your_server";
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

        context=getContext();
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
        askPermissions();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userId=SharedPrefsUtils.getInstance(getContext()).getUserId();
            userId=SharedPrefsUtils.getInstance(getContext()).getUserId();

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, additionaldetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneradditionaldetails.setAdapter(adapter);



        if (selectgender_selection.isChecked() == false) {
            gender = "Male";
        } else {
            gender = "Female";
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
               // showPictureDialog();
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);

            }
        });


        addnewleadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 additional=additional_details.getSelectedItem().toString();
                 frtsName=firstnameedittext.getText().toString();
                 address= AddressSelleredittext.getText().toString();
                 lname= lastnameedittext .getText().toString();
                 mobile=mobilenumberedittext.getText().toString();
                 email= emailedittext .getText().toString();
                // gender=selectgender_selection .getText().toString();
                 status= lead_status .getText().toString();

                if(frtsName.equals("")||address.equals("")||lname.equals("")||mobile.equals("")||email.equals(""))
                {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()!=10)
                {
                    Toast.makeText(getContext(), "Please Enter a valid mobile no", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidEmail(email))
                {
                    Toast.makeText(getContext(), "Please Enter a valid email id", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    multipartImageUpload();


                }

            }
        });

        return myFragmentView;
    }
    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(getActivity(),permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == IMAGE_RESULT) {


                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    bitmap = BitmapFactory.decodeFile(filePath);
                    circleView.setImageBitmap(bitmap);
                }
            }

        }

    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = context.getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }
    private void multipartImageUpload() {
        try {
            File filesDir = getActivity().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            int uid= SharedPrefsUtils.getInstance(getActivity()).getUserId();


           /* RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");
            RequestBody lname = RequestBody.create(MediaType.parse("text/plain"), "Shifna");*/


            ApiInterface apiService =ApiClient.getClient().create(ApiInterface.class);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            RequestBody leadwarmth1 = RequestBody.create(MediaType.parse("text/plain"), leadwarmth);
            RequestBody additional1 = RequestBody.create(MediaType.parse("text/plain"), additional);
            RequestBody fname = RequestBody.create(MediaType.parse("text/plain"), frtsName);
            RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
            RequestBody lname1 = RequestBody.create(MediaType.parse("text/plain"), lname);
            RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);
            RequestBody gender1 = RequestBody.create(MediaType.parse("text/plain"), gender);
            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), ""+uid);
            Log.e("11111","DATA "+leadwarmth+" "+additional);
            Log.e("11111","DATA "+uid+" "+address);


            Call<Response> req = apiService.uploadLeadData(body,name,fname,address1,lname1,email1,mobile1,gender1,additional1,leadwarmth1,user_id);
            req.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                    if (response.code() == 200) {
                      //  textView.setText("Uploaded Successfully!");
                       // textView.setTextColor(Color.BLUE);
                        if(response.body().getStatus().equals("success"))
                        {
                            Toast.makeText(getActivity(),  "Uploaded Successfully! ", Toast.LENGTH_SHORT).show();
                            Result result=response.body().getResult();
                            int lead_id=result.getLead_Id();
                            SharedPrefsUtils.getInstance(getActivity()).setLeadId(lead_id);
                        }
                        else
                        {
                            Toast.makeText(getActivity(),  "Failed! ", Toast.LENGTH_SHORT).show();

                        }

                       /* {
                            "status": "success",
                                "result": {
                            "user_id": "15",
                                    "lead_id": "2"
                        }
                        }*/

                    }

                    Toast.makeText(getActivity(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                    Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR ","11111 "+t.getMessage());
                    t.printStackTrace();
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
