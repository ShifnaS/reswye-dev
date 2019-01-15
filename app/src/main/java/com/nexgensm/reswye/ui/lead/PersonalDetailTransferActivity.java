package com.nexgensm.reswye.ui.lead;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.nexgensm.reswye.ui.Dashboard.DormantItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalDetailTransferActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
public DialogInterface dialog;
    TextView agent_pop_up;
    RequestQueue requestQueue,transferAgentQueue;
    String getAgentStatus,url,Token,Selected_Agent,transferStatus;
    SharedPreferences sharedpreferences;
    String[] agentNameArray;
    Spinner SelectAgentTxt;
    int LeadId;
    TextView Agent_btnTxt,transferBtn;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_transfer);
        ImageButton back= (ImageButton)findViewById(R.id.Personal_Lead_Back);
      //  Agent_btnTxt=(TextView)findViewById(R.id.Agent_btn);
//        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        Token = sharedpreferences.getString("token", "");
//        LeadId = sharedpreferences.getInt("LeadId", 1);
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
       // userId=sharedpreferences.getInt("UserId",0);
      //  flag = sharedpreferences.getInt("flag", 0);
        Token=sharedpreferences.getString("token","");
      //  ImageUrl=sharedpreferences.getString("imageURL","");
        LeadId = sharedpreferences.getInt("LeadId", 1);

        SelectAgentTxt=(Spinner) findViewById(R.id.SelectAgent);
        transferBtn=(TextView)findViewById(R.id.transferTXT);
//     /*   final ListView lv = (ListView) findViewById(R.id.lv);
//        final TextView tv = (TextView) findViewById(R.id.tv);
//        String[] fruits = new String[] {
//                "Japanese Persimmon",
//                "Kakadu lime",
//                "Illawarra Plum",
//                "Malay Apple ",
//                "Mamoncillo",
//                "Persian lime"
//        };
//
//        // Create a List from String Array elements
//        List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));
//
//        // Create a ArrayAdapter from List
//       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (getApplicationContext(), android.R.layout.activity_list_item, fruits_list);
//        lv.setAdapter(arrayAdapter);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        Button clickButton = (Button) findViewById(R.id.Agent_btn);
//        clickButton.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                final Dialog dialog = new Dialog(PersonalDetailTransferActivity.this);
////                dialog.setContentView(R.layout.custom_dialog);
////                dialog.setTitle("Title...");
////                ListView myNames = (ListView) dialog.findViewById(R.id.List);
////                myNames.setOnItemClickListener(new AdapterView.OnItemClickListener());
////                dialog.show();
////                dialog.setCancelable(true);
////
////              //  dialog.dismiss();
//                // Initializing a new String Array
//
//
//                // Populate ListView with items from ArrayAdapter
//
//
//                // Set an item click listener for ListView
////                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        // Get the selected item text from ListView
////                        String selectedItem = (String) parent.getItemAtPosition(position);
////
////                        // Display the selected item text on TextView
////                        tv.setText("Your favorite : " + selectedItem);
////                    }
////                });
//            }
//
//        });*/
     ///////////Get All Agents///////////
        requestQueue = Volley.newRequestQueue(this);
        ///////////// Agent Name list///////////////////
        url = "http://192.168.0.4:88/api/UserRegistration/GetAllUsers";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getAgentStatus = response.getString("status").toString().trim();
                            JSONArray jsonArray=response.getJSONArray("data");
                            Log.v("Json Array",""+jsonArray);
                                String SpinnerTxt="Select Agent";
                                String str3 = "Success";
                                int response_result = getAgentStatus.compareTo(str3);
                                if (response_result == 0)
                                {
                                     agentNameArray=new String[jsonArray.length()];
                                     agentNameArray[0]=SpinnerTxt;
                                    for (int i = 1; i < jsonArray.length(); i++) {
                                      //  String dataobj = jsonArray.getJSONObject(i).toString();
                                       // Log.v("Json object",""+dataobj);

                                          String name=jsonArray.optString(i);
                                          Log.v("Agentname",""+name);
                                          agentNameArray[i]=name;

                                    }
                                  //  Log.v("Jsonarray",""+agentNameArray);

                                  //  Agent_btnTxt.setText("");
                                   // SelectAgentTxt.setPrompt("Select Agent");
                                    SelectAgentTxt.setSelection(0, true);
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,agentNameArray);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    SelectAgentTxt.setAdapter(adapter);
                                }
                                else
                                    {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                // do something...
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",Token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

        //////////////TRANSFER AGENT API////////////////
        transferAgentQueue = Volley.newRequestQueue(this);

        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Selected_Agent=SelectAgentTxt.getSelectedItem().toString();
                 String agent=Selected_Agent.toString();
                url = "http://202.88.239.14:8169/api/Lead/Transferlead";

                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("Lead_ID",LeadId);
                jsonParams.put("Transfered_AgentName",agent);

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    transferStatus = response.getString("status").toString().trim();

                                        String str3 ="Success";
                                        int response_result = transferStatus.compareTo(str3);
                                        if (response_result == 0) {

                                            Toast.makeText(getApplicationContext(), "Successfully transfer the Agent", Toast.LENGTH_SHORT).show();
finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Agent Transfer Failed", Toast.LENGTH_SHORT).show();
finish();
                                        }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Volley Error"+error, Toast.LENGTH_SHORT).show();
                        // do something...
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization",Token);
                        return headers;
                    }
                };
                transferAgentQueue.add(jsonObjectRequest1);

            }
        });

//      agent_pop_up = (TextView) findViewById(R.id.Agent_btn);
//        agent_pop_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(PersonalDetailTransferActivity.this, v);
//                popup.setOnMenuItemClickListener(PersonalDetailTransferActivity.this);
//                popup.inflate(R.menu.popup_menu);
//                popup.show();
//            }
//        });
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
      //  Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.search_item:
                agent_pop_up.setText(item.getTitle());
                // do your code
                return true;
            case R.id.upload_item:
                agent_pop_up.setText(item.getTitle());

                return true;
            case R.id.copy_item:
                agent_pop_up.setText(item.getTitle());
                return true;
            case R.id.print_item:
                agent_pop_up.setText(item.getTitle());
                return true;
            case R.id.share_item:
                agent_pop_up.setText(item.getTitle());
                return true;
            case R.id.bookmark_item:
                agent_pop_up.setText(item.getTitle());
                return true;
            default:
                return false;
        }
    }
    public interface OnFragmentInteractionListener {
    }
}
