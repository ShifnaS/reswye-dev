package com.nexgensm.reswye.ui.lead;

import android.Manifest;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PowerManager;

import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.shockwave.pdfium.PdfiumCore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.POWER_SERVICE;

public class AddNewUploadDocFragment extends Fragment {

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    Bitmap bitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    RequestQueue requestQueue;
    View myFragmentView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private OnFragmentInteractionListener mListener;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    MyAdapter m;
    TextView fileName, fileDescription;
    int flag, leadId;
    Button save_uploadDoc;
    ImageView browseIcon;
    ArrayList<DocumentItems> docList;
    LinearLayout uploadSpace;
    private RecyclerView recyclerView;
    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";


    private static final String TAG = AddNewUploadDocFragment.class.getSimpleName();
    private String selectedFilePath;



    TextView tvFileName;
    ProgressDialog dialog;
    PowerManager.WakeLock wakeLock;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewUploadDocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewUploadDocFragment newInstance(String param1, String param2) {
        AddNewUploadDocFragment fragment = new AddNewUploadDocFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (getArguments() != null) {
            mParam3 = getArguments().getString("params");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_add_new_upload_doc, container, false);

        docList = new ArrayList<>();
        m = new MyAdapter();
        Log.d("oncreate", "set adapter");
        tvFileName = (TextView) myFragmentView.findViewById(R.id.tv_file_name);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view_doc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myFragmentView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        browseIcon = (ImageView) myFragmentView.findViewById(R.id.browseIcon);
        browseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadSpace = (LinearLayout) myFragmentView.findViewById(R.id.uploadSpace);
                uploadSpace.setVisibility(View.VISIBLE);
               // getDocument();
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);

            }
        });

//        flag = sharedpreferences.getInt("flag", 0);
        //Token=sharedpreferences.getString("token","");
       // ImageUrl=sharedpreferences.getString("imageURL","");
        leadId = SharedPrefsUtils.getInstance(getActivity()).getUserId();

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        fileName = (TextView) myFragmentView.findViewById(R.id.fileName);
        fileDescription = (TextView) myFragmentView.findViewById(R.id.fileDescription);

        save_uploadDoc = (Button) myFragmentView.findViewById(R.id.save_uploadDoc);
        save_uploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multipartImageUpload();

                if (selectedFilePath != null) {
                    dialog = ProgressDialog.show(getActivity(), "", "Uploading File...", true);

                 dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please choose a File First", Toast.LENGTH_SHORT).show();
                }





            }
        });
        return myFragmentView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == IMAGE_RESULT) {


                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    bitmap = BitmapFactory.decodeFile(filePath);
                    //circleView.setImageBitmap(bitmap);
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
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }






    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();

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
        File getImage = getActivity().getExternalFilesDir("");
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













    private void multipartImageUpload() {
        try {
            File filesDir = getActivity().getFilesDir();
            File file = new File(filesDir, "image" + ".pdf");

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

            int lid= SharedPrefsUtils.getInstance(getActivity()).getLeadId();
            ApiInterface apiService =ApiClient.getClient().create(ApiInterface.class);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            RequestBody leadwarmth1 = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody additional1 = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody lead_id = RequestBody.create(MediaType.parse("text/plain"), ""+lid);


            Call<Response> req = apiService.uploadLeadDocument(body,name,additional1,leadwarmth1,lead_id);
            req.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                    if (response.code() == 200) {
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














    ///////////////////////////////////////////////////////RecyclerVIEW/////////////////////////////////////////////////////////////

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myviewholder> {

        @Override
        public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.add_doc_row, parent, false);
            Myviewholder myviewholder = new Myviewholder(v);
            Log.d("myactivty ", "oncreateViewHolder");

            return myviewholder;
        }

        @Override
        public void onBindViewHolder(Myviewholder holder, int position) {
            DocumentItems documentItem = docList.get(position);

            BitmapFactory.Options options = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(documentItem.getDoc(), options);
            holder.imageView.setImageBitmap(bitmap);
        }

        @Override
        public int getItemCount() {
            return docList.size();
        }

        public class Myviewholder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public Myviewholder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.docImg);
            }
        }
    }

    public int getItemCount() {
        return docList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public Myviewholder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.docImg);
        }
    }
    ///////////////////////////////////////////////////////RecyclerVIEW/////////////////////////////////////////////////////////////


}


