package com.nexgensm.reswye.api;

import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {
    @POST("signup")
    Call<Response> getSigupresponse(@Body Request body);
    @POST("signup_step2")
    Call<Response> getAgentresponse(@Body Request body);
    @POST("login")
    Call<Response> getLoginresponse(@Body Request body);
    @Multipart
    @POST("addlead_step1")
    Call<Response> uploadLeadData(@Part MultipartBody.Part file,
                                  @Part("file") RequestBody name,
                                  @Part("first_name") RequestBody fname,
                                  @Part("address") RequestBody address1,
                                  @Part("last_name") RequestBody lname1,
                                  @Part("email_id") RequestBody email1,
                                  @Part("mobile") RequestBody mobile1,
                                  @Part("gender") RequestBody gender1,
                                  @Part("howfindabtus") RequestBody additional1,
                                  @Part("lead_prospect") RequestBody lead_prospect,
                                  @Part("user_id") RequestBody uid);


    @Multipart
    @POST("documentupload")
    Call<Response> uploadLeadDocument(@Part MultipartBody.Part file,
                                  @Part("file") RequestBody name,
                                  @Part("name") RequestBody dname,
                                  @Part("description") RequestBody discription,
                                  @Part("lead_id") RequestBody lead_id);


}
