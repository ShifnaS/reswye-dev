package com.nexgensm.reswye.api;

import com.google.gson.JsonObject;
import com.nexgensm.reswye.model.Request;
import com.nexgensm.reswye.model.Response;
import com.nexgensm.reswye.model.ResponseList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


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
                                  @Part("user_id") RequestBody uid,
                                  @Part("flag") RequestBody flags,
                                  @Part("lead_id") RequestBody lead_id,
                                  @Part("lead_category") RequestBody lead_category,
                                  @Part("status") RequestBody status);


    @Multipart
    @POST("documentupload")
    Call<Response> uploadLeadDocument(@Part MultipartBody.Part[] file,
                                  @Part("file") RequestBody name,
                                  @Part("name") RequestBody dname,
                                  @Part("description") RequestBody discription,
                                  @Part("lead_id") RequestBody lead_id);

    @Multipart
    @POST("propertyphotoupload")
    Call<Response> uploadOwnerDocument(@Part MultipartBody.Part[] file,
                                       @Part("file") RequestBody name,
                                       @Part("lead_id") RequestBody lead_id);
  //  http://192.168.0.3:3000/reswy/listleads/"+userId;
    @GET("listleads/{input}")
    Call<ResponseList> getLeadData(
            @Path("userId") int input
    );

    @POST("savefeature")
    Call<Response> getResponse(@Body JsonObject jsonObject);

    @GET("freaturelist")
    Call<ResponseList> getList();

    @GET("featurelistedit/{input}")
    Call<ResponseList> getList1(
            @Path("input") int input
    );

    @GET("charalistedit/{input}")
    Call<ResponseList> getListChara1(
            @Path("input") int input
    );


    @GET("charalist")
    Call<ResponseList> getListChara();
    @POST("buyerinfo")
    Call<Response> addBuyerComment(@Body Request body);
    @POST("buyerinfovisited")
    Call<Response> addBuyerComment2(@Body Request body);
}
