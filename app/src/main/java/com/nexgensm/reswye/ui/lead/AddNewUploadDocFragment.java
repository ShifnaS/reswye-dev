package com.nexgensm.reswye.ui.lead;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;
import com.shockwave.pdfium.PdfiumCore;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewUploadDocFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewUploadDocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewUploadDocFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_REQUEST_CODE = 42;
    String Token, Status_missed, name1, address, lead_CreatedDate;
    String url, ImageUrl, profileimage, encodedImage;
    int userId, lead_ID;
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
    ImageView picview;
    MyAdapter m;
    TextView fileName, fileDescription;
    int flag, leadId;
    Button save_uploadDoc;
    Uri pdfUriS;
    ImageView browseIcon;
    ArrayList<DocumentItems> docList;
    ArrayList<File> fileList;

    LinearLayout uploadSpace;
    private RecyclerView recyclerView;
    Bitmap bmp;
    ProgressDialog pd;


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
        pd = new ProgressDialog(getActivity());

        docList = new ArrayList<>();
        fileList=new ArrayList<>();
        m = new MyAdapter();
        Log.d("oncreate", "set adapter");
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
                getDocument();
            }
        });
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getInt("UserId", 0);
        Token = sharedpreferences.getString("token", "");
        leadId = sharedpreferences.getInt("LeadId", 0);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        fileName = (TextView) myFragmentView.findViewById(R.id.fileName);
        fileDescription = (TextView) myFragmentView.findViewById(R.id.fileDescription);
        save_uploadDoc = (Button) myFragmentView.findViewById(R.id.save_uploadDoc);
        save_uploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = encodedImage;
                multipartImageUpload();
       }
        });
        setback();
        return myFragmentView;

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void generateImageFromPdf(Uri pdfUri, Context context) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        pdfUriS = pdfUri;
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor

            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                } else {

                    //Log.v(TAG,"Permission is revoked1");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                    //return false;
                }
            }

            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            com.shockwave.pdfium.PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);

            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            encodedImage = encodeToBase64(bmp, Bitmap.CompressFormat.JPEG, 100);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage1(bmp);
            pdfiumCore.closeDocument(pdfDocument);
            String path = saveImage(bmp);
           // Toast.makeText(getActivity(), "Image "+path, Toast.LENGTH_SHORT).show();
            DocumentItems doc = new DocumentItems();
            doc.setDoc(path);
            docList.add(doc);
            File f=new File(path);
            fileList.add(f);
            recyclerView.setAdapter(m);
            // important!
        } catch (Exception e) {
            //todo with exception
            e.printStackTrace();
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void getDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        //intent.get
        startActivityForResult(intent, READ_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int req, int result, Intent data) {

        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);

        if (result == RESULT_OK) {
            //Intent intent = getIntent();
            //String name = intent.getData().getPath();


            //String name = data.getData().getPath();
            //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            Uri fileuri = data.getData();


            Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();

            try {
                generateImageFromPdf(fileuri, getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";

    private void saveImage1(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if (!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance

        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }

    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
            MediaScannerConnection.scanFile(myFragmentView.getContext(),
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

    class MyAdapter extends RecyclerView.Adapter<AddNewUploadDocFragment.MyAdapter.Myviewholder> {

        @Override
        public AddNewUploadDocFragment.MyAdapter.Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.add_doc_row, parent, false);
            AddNewUploadDocFragment.MyAdapter.Myviewholder myviewholder = new AddNewUploadDocFragment.MyAdapter.Myviewholder(v);
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








    private void multipartImageUpload() {

        pd.setMessage("Loading");
        pd.show();
        try {

            File fileDir;
            MultipartBody.Part[] body = new MultipartBody.Part[fileList.size()];
            for (int i=0;i<fileList.size();i++)
            {
                fileDir=fileList.get(i);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), fileDir);
                body[i] = MultipartBody.Part.createFormData("file", fileDir.getName(), reqFile);

            }

            String fname=fileName.getText().toString();
            String fndis=fileDescription.getText().toString();
            int lid= SharedPrefsUtils.getInstance(getActivity()).getLeadId();
           // Toast.makeText(getActivity(), "LeadId "+lid, Toast.LENGTH_SHORT).show();

            ApiInterface apiService =ApiClient.getClient().create(ApiInterface.class);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");
            RequestBody leadwarmth1 = RequestBody.create(MediaType.parse("text/plain"), fname);
            RequestBody additional1 = RequestBody.create(MediaType.parse("text/plain"), fndis);
            RequestBody lead_id = RequestBody.create(MediaType.parse("text/plain"), ""+lid);


            if(fname.equals("")||fndis.equals(""))
            {
                Toast.makeText(getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Call<Response> req = apiService.uploadLeadDocument(body,name,additional1,leadwarmth1,lead_id);
                req.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        pd.dismiss();
                        if (response.code() == 200) {
                            if(response.body().getStatus().equals("success"))
                            {
                                Toast.makeText(getActivity(),  "Uploaded Successfully! ", Toast.LENGTH_SHORT).show();
                                Result result=response.body().getResult();
                                int lead_id=result.getLead_Id();
                                //SharedPrefsUtils.getInstance(getActivity()).setLeadId(lead_id);
                                getActivity().finish();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),  "Failed! ", Toast.LENGTH_SHORT).show();

                            }

                        }

                      //  Toast.makeText(getActivity(), response.code() + " ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR ","11111 "+t.getMessage());
                        t.printStackTrace();
                    }
                });

            }


        } catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
        }
    }

    private void setback() {
        fileName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });
        fileDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.editbox_style);
                } else {
                    view.setBackgroundResource(R.drawable.spinner_focus);
                }
            }
        });


    }

}
