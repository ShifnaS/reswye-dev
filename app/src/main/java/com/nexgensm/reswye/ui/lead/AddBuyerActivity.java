package com.nexgensm.reswye.ui.lead;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nexgensm.reswye.R;
import com.nexgensm.reswye.api.ApiClient;
import com.nexgensm.reswye.api.ApiInterface;
import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.Result;
import com.nexgensm.reswye.ui.signinpage.SigninActivity;
import com.nexgensm.reswye.util.SharedPrefsUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class AddBuyerActivity extends AppCompatActivity {

    EditText et_name,et_phone,et_email,et_date,et_comment;
    Button bt_save;
    ImageButton img_back;
    String nameString,emailString,phoneString,dateString,commentString;
    ProgressDialog pd;
    int lid; Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String dateFormat = "yyyy/MM/dd";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buyer);
        et_name=findViewById(R.id.name);
        et_phone=findViewById(R.id.phone);
        et_email=findViewById(R.id.email);
        et_date=findViewById(R.id.date);
        et_comment=findViewById(R.id.comment);
        bt_save=findViewById(R.id.save);
        img_back=findViewById(R.id.back);
        pd = new ProgressDialog(AddBuyerActivity.this);
        lid=SharedPrefsUtils.getInstance(getApplicationContext()).getLId();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginText();
                //Toast.makeText(SigninActivity.this, ""+checkLoginValidation(), Toast.LENGTH_SHORT).show();
                if(checkLoginValidation())
                {

                    postData();
                }

            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        et_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddBuyerActivity.this, R.style.DialogTheme,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDate() {
        et_date.setText(sdf.format(myCalendar.getTime()));
        dateString=et_date.getText().toString();
        Log.v("Date",dateString);

    }
    public void getLoginText() {

        nameString=et_name.getText().toString();
        phoneString=et_phone.getText().toString();
        dateString=et_date.getText().toString();
        emailString = et_email.getText().toString();
        commentString = et_comment.getText().toString();
    }
    private boolean checkLoginValidation()
    {
        if(nameString.equals(""))
        {
            et_email.setError("Please enter Buyer Name");
            return false;
        } else if(phoneString.equals(""))
        {
            et_phone.setError("Please enter Buyer Phone number");
            return false;
        }

        else if(emailString.equals(""))
        {
            et_email.setError("Please enter Buyer Email id");
            return false;
        }
        else if(dateString.equals(""))
        {
            et_date.setError("Please enter a date");
            return false;
        }
        else if(commentString.equals(""))
        {
            et_comment.setError("Please enter the comments");
            return false;
        }
        else
        {
            return true;

        }
    }
    private void postData() {

        pd.setMessage("Loading");
        pd.show();
        try
        {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Response> call = apiService.addBuyerComment(new Request(nameString,emailString,phoneString,dateString,commentString,lid));
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    pd.dismiss();

                    String status=response.body().getStatus();
                    if(status.equals("success"))
                    {

                        Toast.makeText(getApplicationContext(), "Succesfulley Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
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
}
