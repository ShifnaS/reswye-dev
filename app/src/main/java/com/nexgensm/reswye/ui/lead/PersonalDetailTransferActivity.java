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
import android.widget.AutoCompleteTextView;
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
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.Dashboard.DormantItems;
import com.nexgensm.reswye.util.SharedPrefsUtils;

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
    AutoCompleteTextView SelectAgentTxt;
    int LeadId;
    int position;
    com.nexgensm.reswye.model.Request request;
    TextView Agent_btnTxt,transferBtn;
    public static final String mypreference = "mypref";
    int agent_id,user_id;
    String transferred_to_agentid="";
    ArrayList<com.nexgensm.reswye.model.Request> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_transfer);
        ImageButton back= (ImageButton)findViewById(R.id.Personal_Lead_Back);
        sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Token=sharedpreferences.getString("token","");
        LeadId = SharedPrefsUtils.getInstance(getApplicationContext()).getLId();

        SelectAgentTxt=findViewById(R.id.SelectAgent);
        transferBtn=(TextView)findViewById(R.id.transferTXT);

     ///////////Get All Agents///////////
        requestQueue = Volley.newRequestQueue(this);
        ///////////// Agent Name list///////////////////
        user_id=SharedPrefsUtils.getInstance(getApplicationContext()).getAgentId();
        agent_id=SharedPrefsUtils.getInstance(getApplicationContext()).getUserId();

        url = ApiClient.BASE_URL+"agentlist";
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("agent_id", agent_id);
        jsonParams.put("user_id", user_id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,  new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("111111111","Response "+response);

                        try {
                            getAgentStatus = response.getString("status").toString().trim();
                            JSONArray jsonArray=response.getJSONArray("result");
                            Log.v("Json Array",""+jsonArray);
                                String SpinnerTxt="Select Agent";
                                if (getAgentStatus.equals("success"))
                                {
                                  //  Toast.makeText(PersonalDetailTransferActivity.this, "Result "+jsonArray.length(), Toast.LENGTH_SHORT).show();
                                    agentNameArray=new String[jsonArray.length()];
                                  //  agentNameArray[0]=SpinnerTxt;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jo=jsonArray.getJSONObject(i);
                                        String name=jo.getString("first_name");
                                        int id=jo.getInt("agentid");
                                      //  Toast.makeText(PersonalDetailTransferActivity.this, "Result "+name, Toast.LENGTH_SHORT).show();

                                        Log.v("Agentname",""+name);

                                        agentNameArray[i]=name;
                                        request=new com.nexgensm.reswye.model.Request(id,name) ;
                                        arrayList.add(request);


                                    }
                                    ArrayAdapter adapter = new
                                            ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,agentNameArray);

                                    SelectAgentTxt.setAdapter(adapter);
                                    SelectAgentTxt.setThreshold(1);
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
                Selected_Agent=SelectAgentTxt.getText().toString().trim();
                Toast.makeText(PersonalDetailTransferActivity.this, ""+Selected_Agent, Toast.LENGTH_SHORT).show();
               for (int i=0;i<agentNameArray.length;i++)
               {
                   if(agentNameArray[i].equals(Selected_Agent))
                   {
                       position=i;

                   }

               }


                url = ApiClient.BASE_URL+"transferlead";
                int t_agent_id=arrayList.get(position).getAgent_id();

                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("lead_id",LeadId);
                jsonParams.put("transferred_to_agentname",Selected_Agent);
                jsonParams.put("transferred_from_agentid",agent_id);
                jsonParams.put("transferred_to_agentid",t_agent_id);
               JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    transferStatus = response.getString("status").toString().trim();
                                        if (transferStatus.equals("success")) {

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
