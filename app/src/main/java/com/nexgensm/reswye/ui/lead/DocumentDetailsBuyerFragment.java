package com.nexgensm.reswye.ui.lead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.ui.Dashboard.MissedItems;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocumentDetailsBuyerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocumentDetailsBuyerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentDetailsBuyerFragment extends Fragment {
    RequestQueue requestQueue;
    String url, Token,docName, DocDiscription, Status_missed;
int flag;
String ImageUrl;
    private List<Document_List_items> docItemsList = new ArrayList<Document_List_items>();
    //private List<MissedItems> MissedItemsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DocumentRecyclerViewAdapter documentRecyclerViewAdapter;
    private static final Integer[] doc_images = {R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round, R.mipmap.property_icon_with_round};
    private ArrayList<Integer> imageArray = new ArrayList<Integer>();
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    int LeadId,userId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DocumentDetailsBuyerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentDetailsBuyerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentDetailsBuyerFragment newInstance(String param1, String param2) {
        DocumentDetailsBuyerFragment fragment = new DocumentDetailsBuyerFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        for (int i = 0; i < doc_images.length; i++)
            imageArray.add(doc_images[i]);
        // Inflate the layout for this fragment
        final View myFragmentView = inflater.inflate(R.layout.fragment_document_details_buyer, container, false);
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);
        documentRecyclerViewAdapter = new DocumentRecyclerViewAdapter(imageArray, getActivity(), docItemsList);

        sharedpreferences =getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId=SharedPrefsUtils.getInstance(getActivity()).getUserId();
        flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
        ImageUrl=sharedpreferences.getString("imageURL","");
        LeadId = SharedPrefsUtils.getInstance(getActivity()).getLId();
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Toast.makeText(getActivity(), "LEAD id "+LeadId, Toast.LENGTH_SHORT).show();
       viewData();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, final int position) {
                Document_List_items documentListItems=docItemsList.get(position);
                final int docId=documentListItems.getDoc_id();
                final String docName=documentListItems.getDocument_Name();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm delete !");
                builder.setMessage("Delete Image . Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int p=position;
                        Log.e("5555555","docName "+docName+"docId "+docId);
                        deletephoto(docName,docId,p);
                        Toast.makeText(getActivity(), "docName "+docName+"docId "+docId, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Action cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

                //Toast.makeText(getActivity(), "hiii "+docId+""+docName, Toast.LENGTH_SHORT).show();
                //deletephoto(docName,docId);


            }
        }));


        return myFragmentView;
    }

    private void viewData() {


        url = "http://192.168.0.3:3000/reswy/documentlist/"+LeadId;
        Map<String, Object> jsonParams = new ArrayMap<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Status_missed = response.getString("status").toString().trim();
                            JSONArray jsonArray = response.getJSONArray("document");
                            if(jsonArray.length()>0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    //    imageArray.add(""+ApiClient.BASE_URL_IMG+docName);
                                    docName = data.getString("doc_name");
//                                    Toast.makeText(getActivity(), "docNAme "+docName, Toast.LENGTH_SHORT).show();
                                    DocDiscription = data.getString("doc_description");
                                    // String image=imageUrl+profileimage;
                                    String str3 = "success";
                                    int response_result = Status_missed.compareTo(str3);
                                    if (response_result == 0) {

                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(documentRecyclerViewAdapter);
                                        //Document_List_items  docItems = new Document_List_items(ApiClient.BASE_URL_IMG+docName,DocDiscription, data.getString("doc_uploadeddate"));

                                        Document_List_items  docItems = new Document_List_items(ApiClient.BASE_URL_IMG+docName,DocDiscription, data.getString("doc_uploadeddate"),data.getInt("documentid"),docName);
                                        docItemsList.add(docItems);
                                        // prepareData();

                                    } else {
                                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "No Documents for this lead", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);



    }


    public void deletephoto(String imagenames, int photoId, final int p) {
        String deleteurl = ApiClient.BASE_URL+"deletedocuments";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("lead_id", LeadId);
        jsonParams.put("doc_name", imagenames);
        jsonParams.put("documentid", photoId);


        final int position=p;

        //   loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, deleteurl, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                         //   documentRecyclerViewAdapter.notifyDataSetChanged ();



                            //viewData();
                            Log.e("ADDNEW", "" + response);
                            String message = response.getString("status").toString().trim();
                           // Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT);
                            Log.e("ADDNEW", "" + message.equals("success"));
                            if (message.equals("success"))
                            {
                                Toast.makeText(getActivity(), "Delete Done", Toast.LENGTH_SHORT).show();
                                Log.e("ADDNEW", "" + message.equals("success"));
                                docItemsList.remove(position);
                                recyclerView.removeViewAt(position);
                                documentRecyclerViewAdapter.notifyItemRemoved(position);
                                documentRecyclerViewAdapter.notifyItemRangeChanged(position, docItemsList.size());
                                documentRecyclerViewAdapter.notifyDataSetChanged();

                            }
                            else {

                                Toast.makeText(getActivity(), "Deletion failed, try again ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("6666666666666", "" + error.getMessage());

//            loading.dismiss();
//            Toast.makeText(getActivity(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Utlity.testToken);

                return headers;
            }
        };
        // loading = ProgressDialog.show(getContext(), "Please wait...", "Uploading Details...", false, false);

        requestQueue.add(jsonObjectRequest);


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

  /*  private void prepareData() {
        Document_List_items docItems = new Document_List_items("R.mipmap.property_icon_with_round", "Property Documents(1)", "20 jan 2017 12.30 PM ");
        docItemsList.add(docItems);

    }*/

}
